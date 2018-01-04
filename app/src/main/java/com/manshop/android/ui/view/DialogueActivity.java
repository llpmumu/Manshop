package com.manshop.android.ui.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.manshop.android.R;
import com.manshop.android.adapter.DialogueAdapter;
import com.manshop.android.model.Dialogue;

import java.util.ArrayList;
import java.util.List;

public class DialogueActivity extends AppCompatActivity {
    private List<Dialogue> msgList = new ArrayList<Dialogue>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private DialogueAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);
        initMsgs(); // 初始化消息数据
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new DialogueAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Dialogue msg = new Dialogue(content, Dialogue.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
                    inputText.setText(""); // 清空输入框中的内容
                }
            }
        });
    }

    private void initMsgs() {
        Dialogue msg1 = new Dialogue("Hello guy.", Dialogue.TYPE_RECEIVED);
        msgList.add(msg1);
        Dialogue msg2 = new Dialogue("Hello. Who is that?", Dialogue.TYPE_SENT);
        msgList.add(msg2);
        Dialogue msg3 = new Dialogue("This is Tom. Nice talking to you. ", Dialogue.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
