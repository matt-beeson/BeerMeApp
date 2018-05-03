package com.example.chris.beerme;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Matt on 4/10/2018.
 */

public class Beer {

    private static Context mContext;
    public String name;
    public String abv;
    public double abvInt;
    public String category;
    public String description;
    public String style;
    public int rowNumber;
    public String styleLabel;
    public ArrayList<String> searchLabel;
    public int clickCounter;
    // constructor
    // default

    // method
    // static methods that read the json file in and load into beer

    // static method that loads our beers.json using the helper method
    // this method will return an array list of beers constructed from the JSON
    // file
    public static ArrayList<Beer> getbeersFromFile(String filename, Context context) {
        ArrayList<Beer> beerList = new ArrayList<Beer>();

        // try to read from JSON file
        // get information by using the tags
        // construct a beer Object for each beer in JSON
        // add the object to arraylist
        // return arraylist
        try{
            String jsonString = loadJsonFromAsset("beers.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray beers = json.getJSONArray("beers");

            // for loop to go through each beer in your beers array

            for (int i = 0; i < beers.length(); i++){
                Beer beer = new Beer();
                beer.name = beers.getJSONObject(i).getString("name");
                beer.abv = beers.getJSONObject(i).getString("abv");
                beer.description = beers.getJSONObject(i).getString("descript");
                beer.style = beers.getJSONObject(i).getString("style_name");
                beer.category = beers.getJSONObject(i).getString("cat_name");
                beer.searchLabel = new ArrayList<>();
                beer.rowNumber = i;
                beer.clickCounter = 0;

                beer.abvInt = Double.parseDouble(beer.abv);

                //convert serving number from string to int
                double servingInt = Double.parseDouble(beer.abv);
                //add style to each beer according to keywords in style_name
                if (beer.style.contains("Amber")){
                    beer.styleLabel = "Amber";
                }
                else if (beer.style.contains("Belgian")){
                    beer.styleLabel = "Belgian Ale";
                }
                else if (beer.style.contains("Blonde")||beer.style.contains("Blond")){
                    beer.styleLabel = "Blonde";
                }
                else if (beer.style.contains("Brown")){
                    beer.styleLabel = "Brown";
                }
                else if (beer.style.contains("Cream")){
                    beer.styleLabel = "Cream";
                }
                else if (beer.style.contains("Dark")){
                    beer.styleLabel = "Dark";
                }
                else if (beer.style.contains("Fruit") ||beer.style.contains("fruit")){
                    beer.styleLabel = "Fruit";
                }
                else if (beer.style.contains("Golden")){
                    beer.styleLabel = "Golden";
                }
                else if (beer.style.contains("Honey")){
                    beer.styleLabel = "Honey";
                }
                else if (beer.style.contains("India")){
                    beer.styleLabel = "IPA";
                }
                else if (beer.style.contains("Light")){
                    beer.styleLabel = "Light";
                }
                else if (beer.style.contains("Lime")){
                    beer.styleLabel = "Lime";
                }
                else if (beer.style.contains("Pale")){
                    beer.styleLabel = "Pale";
                }
                else if (beer.style.contains("Pilsner")){
                    beer.styleLabel = "Pilsner";
                }
                else if (beer.style.contains("Porter")){
                    beer.styleLabel = "Porter";
                }
                else if (beer.style.contains("Red")){
                    beer.styleLabel = "Red";
                }
                else if (beer.style.contains("Strong")){
                    beer.styleLabel = "Strong";
                }
                else if (beer.style.contains("Wheat")){
                    beer.styleLabel = "Wheat";
                }
                else if (beer.style.contains("White")){
                    beer.styleLabel = "White";
                }
                else {
                    beer.styleLabel = "Other";
                }

                //set styleLable and abv % category for each beer
                beer.searchLabel.add(beer.styleLabel);
               // beer.searchLabel.add(beer.abvLabel);

                //add beer to arraylist of beers
                beerList.add(beer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return beerList;
    }

    // helper method that loads from any Json file
    public static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}

