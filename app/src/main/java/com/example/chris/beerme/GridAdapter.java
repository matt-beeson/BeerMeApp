package com.example.chris.beerme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Chris on 4/25/18.
 */

public class GridAdapter extends BaseAdapter {

    // adapter takes the app itself and a list of data to display
    private Context mContext;
    private ArrayList<String> mBeerList;
    private LayoutInflater mInflater;

    // constructor
    public GridAdapter(Context mContext, ArrayList<String> mBeerList){

        // initialize instances variables
        this.mContext = mContext;
        this.mBeerList = mBeerList;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // methods
    // a list of methods we need to override

    // gives you the number of beers in the data source
    @Override
    public int getCount() {
        return mBeerList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mBeerList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        //check if the view already exists
        //if yes, you don't need to inflate and findviewbyid again
        if(convertView == null) {
            // inflate
            convertView = mInflater.inflate(R.layout.grid_item_beer, parent, false);
            // add the views to the holder
            holder = new ViewHolder();
            // views
            holder.thumbnailImageView = convertView.findViewById(R.id.beer_grid_image);
            holder.styleTextView = convertView.findViewById(R.id.beer_grid_style);

            //add holder to the view for future use
            convertView.setTag(holder);
        }
        else{
            // get the view holder from converview
            holder = (ViewHolder)convertView.getTag();
        }

        // get corresonpinding recipe for each row
        String beerStlye = (String) getItem(position);
      //  Beer beer = (Beer) getItem(position);

        TextView styleTextView = holder.styleTextView;
//        TextView categoryTextView = holder.categoryTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
//
        styleTextView.setText(beerStlye);
        styleTextView.setTextSize(12);


        // imageView
        // use Picasso library to load image from the image url
        //thumbnailImageView.setImageDrawable(getDrawable(R.drawable.beerimage));
        Picasso.with(mContext).load(R.drawable.beerimage).into(thumbnailImageView);
//        thumbnailImageView.getLayoutParams().height = 20;
//        thumbnailImageView.getLayoutParams().width = 20;

        return convertView;
    }

    private static class ViewHolder {
//        public TextView nameTextView;
//        public TextView abvTextView;
        public TextView styleTextView;
//        public TextView categoryTextView;
        public ImageView thumbnailImageView;
//        public TextView numberOfRecipesTextView;
    }
}