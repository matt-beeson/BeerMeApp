package com.example.chris.beerme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class MainActivity extends Activity implements View.OnClickListener {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        Button one = (Button) findViewById(R.id.home_button);
        one.setOnClickListener(this);

        Button two = (Button) findViewById(R.id.camera_button);
        two.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.home_button:
                Intent intent1 = new Intent(mContext, SearchActivity.class);


                startActivityForResult(intent1,1);
                break;

            case R.id.camera_button:
                Intent intent2 = new Intent(mContext, CameraActivity.class);

                startActivityForResult(intent2,1);
                break;

            default:
                break;
        }

    }

}




