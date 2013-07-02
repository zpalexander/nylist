package com.example.nylist;

import android.app.Activity;
import android.content.Context;
import android.view.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListRowAdapter extends ArrayAdapter<ListRow> {
	
	Context context;
    int layoutResourceId;   
    ListRow data[] = null;
    
    public ListRowAdapter(Context context, int layoutResourceId, ListRow[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListRowHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new ListRowHolder();
            holder.title = (TextView)row.findViewById(R.id.title);
            holder.date = (TextView)row.findViewById(R.id.date);
            holder.price = (TextView)row.findViewById(R.id.price);
            holder.location = (TextView)row.findViewById(R.id.location);
           
            row.setTag(holder);
        }
        else
        {
            holder = (ListRowHolder)row.getTag();
        }
       
        ListRow listrow = data[position];
        holder.title.setText(listrow.title);
        holder.date.setText(listrow.date);
        holder.price.setText(listrow.price);
        holder.location.setText(listrow.location);
        return row;
    }
    
    static class ListRowHolder
    {
        TextView title;
        TextView date;
        TextView price;
        TextView location;
    }
    
}
