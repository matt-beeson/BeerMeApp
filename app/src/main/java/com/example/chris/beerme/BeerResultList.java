package com.example.chris.beerme;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Matt on 4/10/2018.
 */

public class BeerResultList extends AppCompatActivity {
    private Context mContext;
    private ListView mListView;
    EditText searchBar;
    BeerAdapter adapter;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private ImageButton micButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_result_view);


        micButton = findViewById(R.id.mic_button);
        micButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("Does it work?");
                askSpeechInput();
            }
        });

        searchBar = findViewById(R.id.search_bar);
        mContext = this;
        // abvSpinner = findViewById(R.id.abv_dropdown);
        //beerTypeSpinner = findViewById(R.id.beer_type_dropdown);

        final ArrayList<Beer> beerList = Beer.getbeersFromFile("beers.json",this);

        adapter = new BeerAdapter(this, beerList);

        mListView = findViewById(R.id.beer_list_view);
        mListView.setAdapter(adapter);

        String searchedBeer = this.getIntent().getExtras().getString("searchedBeer");
        searchBar.setText(searchedBeer);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                BeerResultList.this.adapter.getFilter().filter(s.toString());
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