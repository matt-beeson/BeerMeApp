package com.example.chris.beerme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Matt on 4/10/2018.
 */

public class BeerAdapter extends BaseAdapter implements Filterable{

    // adapter takes the app itself and a list of data to display
    private Context mContext;
    private ArrayList<Beer> mBeerList;
    private LayoutInflater mInflater;
    private ArrayList<Beer> filteredData;
    private ArrayList<Beer> originalData;

    // constructor
    public BeerAdapter(Context mContext, ArrayList<Beer> mBeerList){

        // initialize instances variables
        this.mContext = mContext;
        this.mBeerList = mBeerList;
        this.filteredData = mBeerList;
        this.originalData = mBeerList;
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
            convertView = mInflater.inflate(R.layout.list_item_beer, parent, false);
            // add the views to the holder
            holder = new ViewHolder();
            // views
            holder.nameTextView = convertView.findViewById(R.id.beer_list_name);
            holder.abvTextView = convertView.findViewById(R.id.beer_list_abv);
            holder.thumbnailImageView = convertView.findViewById(R.id.beer_list_thumbnail);
            holder.styleTextView = convertView.findViewById(R.id.beer_list_style);
            holder.categoryTextView = convertView.findViewById(R.id.beer_list_category);
            holder.relativeLayout = convertView.findViewById(R.id.list_beer);
            holder.clickCounter = 0;

            //add holder to the view for future use
            convertView.setTag(holder);
        }
        else{
            // get the view holder from converview
            holder = (ViewHolder)convertView.getTag();
        }

        // get corresonpinding recipe for each row
        final Beer beer = (Beer) getItem(position);

        // get relavate subview of the row view
        TextView nameTextView = holder.nameTextView;
        TextView abvTextView = holder.abvTextView;
        TextView styleTextView = holder.styleTextView;
        TextView categoryTextView = holder.categoryTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        TextView numberOfRecipesTextView = holder.numberOfRecipesTextView;
        RelativeLayout relativeLayout = holder.relativeLayout;

        // update the row view's textviews and imageview to display the information
        nameTextView.setText(beer.name);
        nameTextView.setTextSize(18);

        abvTextView.setText(beer.abv + "%");
        abvTextView.setTextSize(12);

        styleTextView.setText(beer.style);
        styleTextView.setTextSize(12);

        categoryTextView.setText(beer.category);
        categoryTextView.setTextSize(12);

        // imageView
        // use Picasso library to load image from the image url
        Picasso.with(mContext).load(R.drawable.beerpic1).into(thumbnailImageView);

//        relativeLayout.setOnClickListener( new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id){
//                // create my intent package
//                // add all the information needed for detail page
//                // startActivity with that intent
//
//                //explicit
//                // from, to
//                Intent detailIntent = new Intent(mContext, BeerDetailActivity.class);
//                //put title and instruction URL
//                detailIntent.putExtra("name", beer.name);
//                // detailIntent.putExtra("beerImage", selectedBeer.imageUrl);
//                detailIntent.putExtra("description",beer.description);
//                detailIntent.putExtra("style",beer.style);
//                detailIntent.putExtra("category",beer.category);
//                detailIntent.putExtra("abv",beer.abv);
//
//                startActivity(this, );
//
//            }
//        });





        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = originalData;
                    results.count = originalData.size();
                }
                else
                {
                    ArrayList<Beer> filterResultsData = new ArrayList<Beer>();

                    for(Beer beer : originalData)
                    {
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
                        if(beer.name.toLowerCase().contains(charSequence.toString().toLowerCase()))
                        {
                            //System.out.println("Title: " + data.title + "Contains " + charSequence);
                            filterResultsData.add(beer);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //System.out.print("Publish" + filterResults.values);
                mBeerList = (ArrayList<Beer>)filterResults.values;
                notifyDataSetChanged();
            }

        };

    }

    private static class ViewHolder {
        public TextView nameTextView;
        public TextView abvTextView;
        public TextView styleTextView;
        public TextView categoryTextView;
        public ImageView thumbnailImageView;
        public TextView numberOfRecipesTextView;
        public RelativeLayout relativeLayout;
        public int clickCounter;
    }
}

