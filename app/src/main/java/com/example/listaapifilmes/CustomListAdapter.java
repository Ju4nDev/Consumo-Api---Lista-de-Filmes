package com.example.listaapifilmes;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listaapifilmes.ItemApiModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<ItemApiModel> customListDataModelArrayList = new ArrayList<ItemApiModel>();
    LayoutInflater layoutInflater = null;

    public CustomListAdapter(Activity activity, ArrayList customListDataModelArray){
        this.activity=activity;
        this.customListDataModelArrayList = customListDataModelArray;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return customListDataModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return customListDataModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder{
        ImageView imageViewFilme;
        TextView textViewNameMovie, textViewYear, textViewType;

    }
    ViewHolder viewHolder = null;


    // this method  is called each time for arraylist data size.
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View vi=view;
        final int pos = position;
        if(vi == null){

            // create  viewholder object for list_rowcell View.
            viewHolder = new ViewHolder();
            // inflate list_rowcell for each row
            vi = layoutInflater.inflate(R.layout.list_view_item_api, null);
            viewHolder.imageViewFilme = (ImageView) vi.findViewById(R.id.imageViewFilme);
            viewHolder.textViewNameMovie = (TextView) vi.findViewById(R.id.textViewNameMovie);
            viewHolder.textViewType = (TextView) vi.findViewById(R.id.textViewType);
            viewHolder.textViewYear = (TextView) vi.findViewById(R.id.textViewYear);
            /*We can use setTag() and getTag() to set and get custom objects as per our requirement.
            The setTag() method takes an argument of type Object, and getTag() returns an Object.*/
            vi.setTag(viewHolder);
        }else {

            /* We recycle a View that already exists */
            viewHolder= (ViewHolder) vi.getTag();
        }

        Picasso.get().load(customListDataModelArrayList.get(pos).Poster).into(viewHolder.imageViewFilme);
        viewHolder.textViewNameMovie.setText(customListDataModelArrayList.get(pos).Title);
        viewHolder.textViewType.setText(customListDataModelArrayList.get(pos).Type);
        viewHolder.textViewYear.setText(customListDataModelArrayList.get(pos).Year);


        return vi;
    }
}






