package com.example.chris.beerme;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Chris on 4/27/18.
 */

public class SearchResultsActivity extends AppCompatActivity {

    private Context mContext;
    private ArrayList<Beer> resultBeers;
    BeerAdapter adapter;
    EditText searchBar;
    private ImageButton micButton;
    private ListView mListView;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_result_view);
        resultBeers = new ArrayList<Beer>();
        mContext = this;
        final ArrayList<Beer> beerList = Beer.getbeersFromFile("beers.json",this);
        ArrayList<String> clickedBeerStyles = this.getIntent().getExtras().getStringArrayList("beerStylesList");

        for(int i = 0; i<beerList.size(); i++){
            for (int k =0; k<clickedBeerStyles.size(); k++){
                if(beerList.get(i).style.toString().contains(clickedBeerStyles.get(k).toString())){
                    resultBeers.add(beerList.get(i));
                }
            }
        }


        if (resultBeers.size()>0){
            adapter = new BeerAdapter(this, resultBeers);
        }
        else{
            adapter = new BeerAdapter(this, beerList);
        }

        mListView = findViewById(R.id.beer_list_view);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener( new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){
                Beer selectedBeer = beerList.get(position);

                // create my intent package
                // add all the information needed for detail page
                // startActivity with that intent

                //explicit
                // from, to
                Intent detailIntent = new Intent(mContext, BeerDetailActivity.class);
                //put title and instruction URL
                detailIntent.putExtra("name", selectedBeer.name);
                // detailIntent.putExtra("beerImage", selectedBeer.imageUrl);
                detailIntent.putExtra("description",selectedBeer.description);
                detailIntent.putExtra("style",selectedBeer.style);
                detailIntent.putExtra("category",selectedBeer.category);
                detailIntent.putExtra("abv",selectedBeer.abv);

                launchActivity(detailIntent);
            }
        });

        micButton = findViewById(R.id.mic_button);
        micButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                askSpeechInput();
            }
        });

        searchBar = findViewById(R.id.search_bar);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                SearchResultsActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.action_camera:
                startActivity(new Intent(this, CameraActivity.class));
                return true;

            case R.id.search_nav:
                startActivity(new Intent(this, SearchActivity.class));
                return true;

            case R.id.action_near_me:
                startActivity(new Intent(this, MapsActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void launchActivity(Intent intent){
        startActivityForResult(intent,1);
    }

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchBar.setText(result.get(0));
                }
                break;
            }

        }
    }
}
