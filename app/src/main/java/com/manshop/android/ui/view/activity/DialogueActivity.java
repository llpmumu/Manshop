package com.manshop.android.ui.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.DialogueAdapter;
import com.manshop.android.model.Dialogue;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.util.Constant;
import com.manshop.android.util.StringUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class DialogueActivity extends BaseActivity {
    private List<Dialogue> msgList = new ArrayList<Dialogue>();

    private EditText inputText;

    private RecyclerView msgRecyclerView;
    private DialogueAdapter adapter;

    private Intent intent;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);
        showToolbar();
        init();
        initMsg(); // 初始化消息数据
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void init(){
        intent = getIntent();

        inputText = (EditText) findViewById(R.id.input_text);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

    }

    public void initMsg(){
        final Map<String, Object> params = new HashMap<>();
        params.put("sender", intent.getIntExtra("sid",0));
        params.put("receiver", MyApplication.getInstance().getUserId());
        okHttp.doPost(Constant.baseURL + "message/newMsg", new CallBack(DialogueActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                System.out.println(jsonArray);
                List<Dialogue> listDialogue = JSON.parseArray(jsonArray + "", Dialogue.class);
                Log.d("good", " " + msgList.size());
                for (Dialogue dialogue : listDialogue) {
                    Log.d("good", " " + msgList.toString());
                    msgList.add(dialogue);
//                    Log.d("good", " goodtime    " + good.getGoodtime());
                }
                adapter = new DialogueAdapter(msgList);
                msgRecyclerView.setAdapter(adapter);
//                String content = inputText.getText().toString();
//                if (!"".equals(content)) {
////                    Dialogue msg = new Dialogue(content, Dialogue.TYPE_SENT);
////                    msgList.add(msg);
//                    adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
//                    msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
//                    inputText.setText(""); // 清空输入框中的内容
//                }

            }
        }, params);
    }

    public void send(View view){
        final Map<String, Object> params = new HashMap<>();
        params.put("sender", intent.getIntExtra("sid",0));
        params.put("receiver", MyApplication.getInstance().getUserId());
        params.put("msg",inputText.getText().toString());
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        params.put("msgtime", date);
        okHttp.doPost(Constant.baseURL + "message/newMsg", new CallBack(DialogueActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
//                JSONObject json = JSON.parseObject((String) o);
//                String oid = json.getObject("data",String.class);
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
//                    Dialogue msg = new Dialogue(content, Dialogue.TYPE_SENT);
//                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
                    inputText.setText(""); // 清空输入框中的内容
                }
            }
        }, params);
    }

}
