package com.flickrphotosearchapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flickrphotosearchapp.models.SubjectData;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

class CustomAdapter implements ListAdapter {
    ArrayList<SubjectData> arrayList;
    Context context;

    public CustomAdapter(Context context, ArrayList<SubjectData> arrayList) {
        this.arrayList=arrayList;
        this.context= context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public SubjectData getItem(int position) {
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubjectData subjectData = arrayList.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_row, null);
            convertView.setOnClickListener(v -> {
            });

            ImageView image = convertView.findViewById(R.id.list_image);
            image.setContentDescription(subjectData.getName());
            Picasso.with(context)
                    .load(subjectData.getImage())
                    .into(image);

            TextView imageName = convertView.findViewById(R.id.list_name);
            imageName.setText(subjectData.getName());
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return ListAdapter.super.getAutofillOptions();
    }
}
