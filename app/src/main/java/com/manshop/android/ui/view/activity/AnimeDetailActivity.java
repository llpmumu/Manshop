package com.manshop.android.ui.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.table.TableData;
import com.bumptech.glide.Glide;
import com.manshop.android.R;
import com.manshop.android.model.Anime;
import com.manshop.android.model.Goods;
import com.manshop.android.model.Role;
import com.manshop.android.model.RoleAndAkira;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class AnimeDetailActivity extends BaseActivity {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    //    private SmartTable table;
    @Bind(R.id.toolbar_title)
    TextView toolTitle;
    @Bind(R.id.iv_picture)
    ImageView ivPic;
    @Bind(R.id.tv_JapanName)
    TextView tvJapanName;
    @Bind(R.id.tv_produce)
    TextView tvProduce;
    @Bind(R.id.tv_producer)
    TextView tvProducer;
    @Bind(R.id.tv_year)
    TextView tvYear;
    @Bind(R.id.tv_episodes)
    TextView tvEpisodes;
    @Bind(R.id.detail)
    TextView tvDetail;
    @Bind(R.id.table_akira)
    TableLayout table;

    private String name;
    private List<RoleAndAkira> list = new ArrayList<>();
    private OkHttp okHttp = OkHttp.getOkhttpHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);
        ButterKnife.bind(this);
        showToolbar();
        init();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public void init() {
        name = getIntent().getStringExtra("title");
        toolTitle.setText(name);
        getData();
    }

    public void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("title",name);
        Log.d("anime",name);
        okHttp.doPost(Constant.baseURL+"anime/getOneAnime", new CallBack(AnimeDetailActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {

            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Anime anime = json.getObject("data", Anime.class);
                Glide.with(AnimeDetailActivity.this).load(anime.getPictrue()).into(ivPic);
                tvJapanName.setText(anime.getJapanName());
                tvProduce.setText(anime.getProducer());
                tvProducer.setText(anime.getProduce());
                tvYear.setText(anime.getYear());
                tvEpisodes.setText(anime.getEpisodes());
                tvDetail.setText(anime.getDetail());

                List<Role> roleList = anime.getRoleList();
                //清除表格所有行
                table.removeAllViews();
                //全部列自动填充空白处
                table.setStretchAllColumns(true);
                for (int i = 0; i < roleList.size(); i++) {
                    TableRow tableRow = new TableRow(AnimeDetailActivity.this);
                    for (int j = 1; j <= 2; j++) {
                        //tv用于显示
                        TextView tv = new TextView(AnimeDetailActivity.this);
                        if(j == 1)
                            tv.setText(roleList.get(i).getName());
                        else
                            tv.setText(roleList.get(i).getAkiraName());
                        tableRow.addView(tv);
                    }
                    //新建的TableRow添加到TableLayout
                    table.addView(tableRow, new TableLayout.LayoutParams(MP, WC, 1));
                }
            }
        },params);
    }
}
