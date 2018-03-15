package com.manshop.android.ui.view.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.manshop.android.R;
import com.manshop.android.ui.base.BaseActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComicActivity extends BaseActivity {
    List<String> province;

    @Bind(R.id.list_pro)
    ListView listProvince;

    private Fragment showFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
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
        province = new ArrayList<>();
        province.add("全部");
        final String[] provinceName = {""};
        final int[] pos = new int[1];
        listProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinceName[0] = String.valueOf(province.get(position));
            }
        });

        showFragment = new Fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, showFragment);
        Bundle bundle = new Bundle();
        bundle.putString("province", provinceName[0]);
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
            province.add(content);
            ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, province);
            listProvince.setAdapter(fileList);
            Log.d("show", content);
        }
    }
}
