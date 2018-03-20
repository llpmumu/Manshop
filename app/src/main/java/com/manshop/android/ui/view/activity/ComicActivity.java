package com.manshop.android.ui.view.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.R;
import com.manshop.android.adapter.ProvinceAdapter;
import com.manshop.android.adapter.ShowAdapter;
import com.manshop.android.model.Show;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.util.Constant;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class ComicActivity extends BaseActivity {
    @Bind(R.id.tv_titile)
    TextView tvTitle;
    @Bind(R.id.list_pro)
    ListView lvProvince;
    @Bind(R.id.rv_show)
    RecyclerView rvShow;
    @Bind(R.id.tv_tip)
    TextView tvTip;

    private List<String> listProvince;
    private List<Show> listShow;
    private ProvinceAdapter provinceAdapter;
    private ShowAdapter showAdapter;

    private OkHttp okHttp = OkHttp.getOkhttpHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        showToolbar();
        init();
        try {
            getProvinceData();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        listProvince = new ArrayList<>();
        listShow = new ArrayList<>();
        listProvince.add("全部");

        final Map<String, Object> params = new HashMap<>();
        params.put("province", listProvince.get(0));
        requestShow("show/getAllShow", params);
        lvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinceAdapter.setSelectItem(position);
                provinceAdapter.notifyDataSetInvalidated();
                tvTitle.setText(listProvince.get(position));
                params.put("province", listProvince.get(position));
                if (position == 0) {
                    requestShow("show/getAllShow", params);
                } else {
                    Log.d("show", "   " + params.get("province"));
                    requestShow("show/getAddressShow", params);
                }
            }
        });
    }

    //    获取市级信息
    public void getProvinceData() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        AssetManager am = this.getAssets();
        InputStream is = am.open("province_data.xml");
        Document document = db.parse(is);

        NodeList node = document.getElementsByTagName("province");
        for (int i = 0; i < node.getLength(); i++) {
            Element element = (Element) node.item(i);
            // 获取属性省份
            String content = element.getAttribute("name");
            listProvince.add(content);
            provinceAdapter = new ProvinceAdapter(this, listProvince);
            lvProvince.setAdapter(provinceAdapter);
        }
    }


    public void requestShow(String url, Map<String, Object> params) {
        listShow.clear();
        Log.d("show", "size" + listShow.size());
        okHttp.doPost(Constant.baseURL + url, new CallBack(ComicActivity.this) {
            @Override
            public void onError(Response response, Exception e) throws IOException {
                rvShow.setVisibility(View.GONE);
                tvTip.setVisibility(View.VISIBLE);
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                rvShow.setVisibility(View.VISIBLE);
                tvTip.setVisibility(View.GONE);
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                System.out.println(jsonArray);
                List<Show> lsShow = JSON.parseArray(jsonArray + "", Show.class);
                for (Show show : lsShow) {
                    listShow.add(show);
                }
                rvShow.setLayoutManager(new LinearLayoutManager(ComicActivity.this));
                showAdapter = new ShowAdapter(ComicActivity.this, listShow);
                rvShow.setAdapter(showAdapter);
                showAdapter.notifyDataSetChanged();
            }
        }, params);
    }
}
