package com.example.chris.beerme;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Chris on 4/22/18.
 */

public class SearchActivity extends AppCompatActivity {

    private Context mContext;
    private TextView gridTextView;
    private Button searchButton;
    private GridView mainGrid;
    private ArrayList<String> clickedStyleArray;
    GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_view);

        final ArrayList<String> styleList = new ArrayList<String>();
        final ArrayList<Beer> beerList = Beer.getbeersFromFile("beers.json", this);
        for(int i = 0; i<beerList.size(); i++) {
            String newStyleLabel = beerList.get(i).style;

            //if the array doesn't already contain this style, add it
            if(!styleList.contains(newStyleLabel)) {
                styleList.add(newStyleLabel);
            }
        }

        final ArrayList<String> styleLabelList = new ArrayList<String>();
        for(int i = 0; i<beerList.size(); i++) {
            String newStyleLabel = beerList.get(i).styleLabel;

            //if the array doesn't already contain this style, add it
            if(!styleLabelList.contains(newStyleLabel)) {
                styleLabelList.add(newStyleLabel);
            }
        }

        mContext = this;
        //arraylists for restrictions
        gridTextView = findViewById(R.id.grid_text_view);
        mainGrid = findViewById(R.id.search_grid);
        gridAdapter = new GridAdapter(this, styleLabelList);
        searchButton = findViewById(R.id.search_button);
        mainGrid.setAdapter(gridAdapter);

        clickedStyleArray = new ArrayList<String>();
        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Beer selectedBeer = beerList.get(position);

                String selectedStyle = styleLabelList.get(position);
                for(int i = 0; i<beerList.size(); i++) {
                    //if the array doesn't already contain this style, add it
                    if(!clickedStyleArray.contains(selectedStyle)) {
                        clickedStyleArray.add(selectedStyle);
                    }
                }

                int originalbgcolor = getResources().getColor(R.color.lightGray);
                int changedbgcolor = getResources().getColor(R.color.lightTan);
                int test = view.getSolidColor();
                if (view.getSolidColor() == test) {
                    selectedBeer.clickCounter ++;
                    if(selectedBeer.clickCounter%2==1){
                        view.setBackgroundColor(changedbgcolor);
                    }
                    else{
                        view.setBackgroundColor(originalbgcolor);
                        for(int k = 0; k<clickedStyleArray.size(); k++){
                            if(selectedStyle == clickedStyleArray.get(k)){
                                clickedStyleArray.remove(k);
                            }
                        }
                    }
                }

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent(mContext, SearchResultsActivity.class);
                resultIntent.putExtra("beerStylesList", clickedStyleArray);
                startActivity(resultIntent);
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

}
