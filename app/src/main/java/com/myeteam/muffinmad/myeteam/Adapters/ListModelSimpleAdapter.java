package com.myeteam.muffinmad.myeteam.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myeteam.muffinmad.myeteam.R;
import com.myeteam.muffinmad.myeteam.classes.ListModelSimple;

import java.util.List;

/**
 * Created by muffinmad on 02/11/2016.
 */

public class ListModelSimpleAdapter extends BaseAdapter {

    private final List<ListModelSimple> itemList;
    private final Context context;

    public ListModelSimpleAdapter(List<ListModelSimple> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public List<ListModelSimple> getItemList() {
        return itemList;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return itemList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ListModelSimple item = itemList.get(i);
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedView = view;

        if (inflatedView == null){
            inflatedView = inflater.inflate(R.layout.list_model_simple, viewGroup, false);
        }


        TextView titleView = (TextView) inflatedView.findViewById(R.id.item_title);
        titleView.setText(item.getTitle());
        TextView subtitleView = (TextView) inflatedView.findViewById(R.id.item_subtitle);
        subtitleView.setText(item.getSubtitle());

        return inflatedView;
    }
}
