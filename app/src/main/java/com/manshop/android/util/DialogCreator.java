package com.manshop.android.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.FileContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.LocationContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.android.eventbus.EventBus;
import cn.jpush.im.api.BasicCallback;


public class DialogCreator {
    public static Dialog mLoadingDialog;

    public static Dialog createResendDialog(Context context, View.OnClickListener listener) {
        Dialog dialog = new Dialog(context, IdHelper.getStyle(context, "jmui_default_dialog_style"));
        View view = LayoutInflater.from(context).inflate(
                IdHelper.getLayout(context, "jmui_dialog_base_with_button"), null);
        dialog.setContentView(view);
        Button cancelBtn = (Button) view.findViewById(IdHelper.getViewID(context, "jmui_cancel_btn"));
        Button resendBtn = (Button) view.findViewById(IdHelper.getViewID(context, "jmui_commit_btn"));
        cancelBtn.setOnClickListener(listener);
        resendBtn.setOnClickListener(listener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog createLogoutStatusDialog(Context context, String title, View.OnClickListener listener) {
        Dialog dialog = new Dialog(context, IdHelper.getStyle(context, "jmui_default_dialog_style"));
        View view = LayoutInflater.from(context).inflate(IdHelper.getLayout(context,
                "jmui_dialog_base_with_button"), null);
        dialog.setContentView(view);
        TextView titleTv = (TextView) view.findViewById(IdHelper.getViewID(context, "jmui_title"));
        titleTv.setText(title);
        final Button cancel = (Button) view.findViewById(IdHelper.getViewID(context, "jmui_cancel_btn"));
        final Button commit = (Button) view.findViewById(IdHelper.getViewID(context, "jmui_commit_btn"));
        cancel.setOnClickListener(listener);
        commit.setOnClickListener(listener);
        cancel.setText("退出");
        commit.setText("重新登录");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
}
