package com.manshop.android.ui.view.activity;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.manshop.android.R;
import com.manshop.android.model.city.District;
import com.manshop.android.model.city.XmlParserHandler;
import com.manshop.android.model.city.XmlProvince;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;

public class ComicActivity extends AppCompatActivity {
    List<String> province;

    @Bind(R.id.list_pro)
    ListView listProvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
    }

    public void init(){
        province = new ArrayList<>();
//        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, android.R.layout.item_show_rightlist, province);
    }

    public void getProvinceMsg(List<String> province) {
        this.province = province;
    }

//    获取市级信息
    public void getProvinceData() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XmlProvince xmlProvince = new XmlProvince();

        AssetManager am = this.getAssets();
        InputStream is = am.open("province_data.xml");

        sp.parse(is, xmlProvince);
        getProvinceMsg(xmlProvince.getProvince());
    }
}
