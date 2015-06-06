package com.example.administrator.soldierbook;

import android.content.Context;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/20.
 */
public class ListAdapter extends BaseAdapter{
    private Context mContext;
    private static LayoutInflater inflater = null;
    private ArrayList<soldier> mProducts;

    public ListAdapter(Context c, ArrayList<soldier> arrayProducts) {
        mContext = c;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mProducts = arrayProducts;
    }
    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int position) {

        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {


        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        vi = inflater.inflate(R.layout.list_item, null);
        TextView text = (TextView) vi.findViewById(R.id.textview);
        text.setText(mProducts.get(position).getTime());
        return vi;
    }
}
