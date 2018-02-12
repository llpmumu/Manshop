package com.manshop.android.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.longsh.optionframelibrary.OptionMaterialDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.model.Address;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.ui.view.activity.AddressActivity;
import com.manshop.android.ui.view.activity.EditAddressActivity;
import com.manshop.android.util.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by Lin on 2017/12/28.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context context;
    private List<Address> mList;
    //    private OnItemClickListener mListener;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mListener = listener;
//    }

    //接口实现监听
//    public interface OnItemClickListener {
//        void onClick(View v, int position);
//    }

    public AddressAdapter(Context context, List<Address> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_address, null);
        final ViewHolder holder = new ViewHolder(view);
        //删除按钮监听
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                final Address address = mList.get(position);
                final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(context);
                mMaterialDialog.setMessage("确定删除改地址？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteAdr(address.getId(), position);
                                mMaterialDialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        })
                        .setCanceledOnTouchOutside(true)
                        .show();
            }
        });

//        编辑地址
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                final Address address = mList.get(position);
                Intent intent = new Intent(context, EditAddressActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("id", address.getId());
//                bundle.putString("consigneeName", address.getConsignee());
//                bundle.putString("consigneePhone", address.getAddphone());
//                String[] adrs = address.getAddress().split(" ");
//                bundle.putString("selectAdr", adrs[0]);
//                bundle.putString("addAdr", adrs[1]);
//                bundle.putBoolean("isDefault", address.isDefault());
//                intent.putExtras(bundle);
                intent.putExtra("id", address.getId());
                intent.putExtra("consigneeName", address.getConsignee());
                intent.putExtra("consigneePhone", address.getAddphone());
                String[] adrs = address.getAddress().split(" ");
                intent.putExtra("selectAdr", adrs[0]);
                intent.putExtra("addAdr", adrs[1]);
                intent.putExtra("isDefault", address.isDefault());
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        Address address = mList.get(position);
        holder.isCheck.setChecked(address.isDefault());
        holder.name.setText(address.getConsignee());
        holder.telnum.setText(showPhone(address.getAddphone()));
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

    //电话中间5位*显示
    public String showPhone(String phone) {
        return phone.substring(0, 3) + "*****" + phone.substring(phone.length() - 3);
    }

    //删除地址
    public void deleteAdr(int id, final int position) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        okHttp.doPost(Constant.baseURL + "address/delAddress", new CallBack(context) {
            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                mList.remove(position);
                notifyDataSetChanged();
            }
        }, param);
    }
}
