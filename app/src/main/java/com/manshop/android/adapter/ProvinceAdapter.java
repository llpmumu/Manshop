package com.manshop.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.manshop.android.R;

import java.util.List;

/**
 * Created by Lin on 2018/3/16.
 */

public class ProvinceAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private int selectItem = 0;

    public ProvinceAdapter(Context context,List<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder=new ViewHolder();
            convertView = View.inflate(context, R.layout.item_province,null);
            holder.tvProvince = (TextView) convertView.findViewById(R.id.tv_province);
            convertView.setTag(holder);
        }else
            holder= (ViewHolder) convertView.getTag();
        if(position==selectItem){
            holder.tvProvince.setBackgroundColor(Color.WHITE);
            holder.tvProvince.setTextColor(context.getResources().getColor(R.color.splitline));
        }
        else {
            holder.tvProvince.setBackgroundColor(Color.TRANSPARENT);
            holder.tvProvince.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.tvProvince.setText(list.get(position));
        return convertView;
    }

    static class ViewHolder {
        private TextView tvProvince;
    }
}
