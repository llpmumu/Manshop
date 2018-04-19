package com.manshop.android.ui.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.R;
import com.manshop.android.model.Anime;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class AnimeDataActivity extends BaseActivity {
    @Bind(R.id.lv_comic_name)
    ListView listvName;
    private List<String> listName = new ArrayList<>();
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_data);
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
        getAnime();
    }

    public void getAnime(){
        okHttp.doGet(Constant.baseURL + "anime/getAnime", new CallBack(AnimeDataActivity.this) {
            @Override
            public void onError(Response response, Exception e) throws IOException {

            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                System.out.println(jsonArray);
                final List<Anime> listAnime = JSON.parseArray(jsonArray + "", Anime.class);
                for (Anime anime:listAnime){
                    listName.add(anime.getTitle());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AnimeDataActivity.this, android.R.layout.simple_list_item_1, listName);
                listvName.setAdapter(adapter);
            }
        });
    }


}
