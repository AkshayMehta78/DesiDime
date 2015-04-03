package com.desidime.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.desidime.activity.R;
import com.desidime.model.Deals;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Akshay M on 4/2/2015.
 */
public class DealAdapter extends ArrayAdapter<Deals> {

    private static class ViewHolder {
        TextView tv_dealTitle,tv_dealDesc;
        ImageView iv_dealThumb;
    }
    Context context;
    public DealAdapter(Context context, ArrayList<Deals> arr_deals) {
        super(context, R.layout.deal_layout_row, arr_deals);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Deals deal = getItem(position);
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.deal_layout_row, parent, false);
            holder.tv_dealTitle= (TextView) convertView.findViewById(R.id.tv_dealTitle);
            holder.tv_dealDesc= (TextView) convertView.findViewById(R.id.tv_dealDesc);
            holder.iv_dealThumb= (ImageView) convertView.findViewById(R.id.iv_dealThumb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String url =deal.getDeal_image();
        holder.tv_dealTitle.setText(deal.getDeal_title());
        holder.tv_dealDesc.setText(Html.fromHtml(deal.getDeal_desc()));
        if(url!=null) {
            // picasso library: A powerful image downloading and caching library for Android
            Picasso.with(context).load(url).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(holder.iv_dealThumb);
        }
        else
            Picasso.with(context).load(R.drawable.ic_launcher).into(holder.iv_dealThumb);
        return convertView;
    }
}
