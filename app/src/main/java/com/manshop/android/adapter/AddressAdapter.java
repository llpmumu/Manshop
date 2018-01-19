package com.manshop.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.model.Address;

import java.util.List;

/**
 * Created by Lin on 2017/12/28.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    List<Address> mList;

    public AddressAdapter(Context context, List<Address> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.item_address, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        Address address = mList.get(position);
        holder.isCheck.setChecked(address.isDefault());
        holder.name.setText(address.getConsignee());
        holder.telnum.setText(showPhone(address.getAddressPhone()));
        holder.address.setText(address.getAddress());
        holder.checktext.setText(address.isDefault() ? "默认地址" : "设为默认");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView telnum;
        TextView address;
        RadioButton isCheck;
        TextView checktext;
        TextView edit;
        TextView delete;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.address_consignee);
            telnum = (TextView) itemView.findViewById(R.id.address_phone);
            address = (TextView) itemView.findViewById(R.id.address);
            isCheck = (RadioButton) itemView.findViewById(R.id.radio_selected);
            checktext = (TextView) itemView.findViewById(R.id.radio_selectedText);
            edit = (TextView) itemView.findViewById(R.id.edite_address);
            delete = (TextView) itemView.findViewById(R.id.delete_address);
        }
    }

    private String showPhone(String phone) {
        return phone.substring(0, 3) + "*****" + phone.substring(phone.length() - 3);
    }
}
