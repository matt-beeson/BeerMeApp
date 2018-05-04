package com.example.chris.beerme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Chris on 4/25/18.
 */

public class BeerDetailActivity extends AppCompatActivity {
    private Context myContext;
    private TextView nameText;
    private ImageView beerImage;
    private TextView descriptionText;
    private TextView styleText;
    private TextView ABVText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_detail_activity);
        
        myContext = this;
        nameText = findViewById(R.id.name_detail);
        beerImage= findViewById(R.id.image_detail);
        descriptionText= findViewById(R.id.description_detail);
        styleText = findViewById(R.id.style_detail);
        ABVText = findViewById(R.id.abv_detail);

        String name = this.getIntent().getExtras().getString("name");
        String description = this.getIntent().getExtras().getString("description");
        String abv = this.getIntent().getExtras().getString("abv");
        String style = this.getIntent().getExtras().getString("style");
        beerImage.setImageDrawable(getDrawable(R.drawable.beerimage));
        setTitle(name);
        descriptionText.setText(description);
        nameText.setText(name);
        styleText.setText(style);

        int abvLength = abv.length();
        if(abv == "0"){
            ABVText.setText("ABV n/a");
        }
        else if (abv.toString().length() > 4) {
            String abvSubstring = abv.toString().substring(0,4);
            ABVText.setText(abvSubstring + "%");
        }
        else if (abvLength >0 && abvLength <5){
            ABVText.setText(abv + "%");
        }
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

            case R.id.action_near_me:
                startActivity(new Intent(this, MapActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}