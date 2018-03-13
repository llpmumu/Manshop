package com.manshop.android.ui.view.activity;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.manshop.android.R;
import com.manshop.android.model.city.District;
import com.manshop.android.model.city.Privince;
import com.manshop.android.model.city.XmlParserHandler;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ComicActivity extends AppCompatActivity {
    private List<String> province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
    }

//    public void getAdrMsg(List<String> privince) {
//        province = new ArrayList<String>();
//        for (int i = 0; i < privinceModels.size(); i++) {
//            province.add(privinceModels.get(i).getName());
//            List<String> privinceNames = new ArrayList<String>();
//            for (int j = 0; j < privinceModels.get(i).getCityModels().size(); j++) {
//
//                privinceNames.add(privinceModels.get(i).getCityModels().get(j).getName());
//
//                for (int k = 0; k < privinceModels.get(i).getCityModels().get(j).getDistrictModels().size(); k++) {
//
//                    DistrictNames.add(privinceModels.get(i).getCityModels().get(j).getDistrictModels().get(k).getName());
//                    DistrictNamesCode.add(privinceModels.get(i).getCityModels().get(j).getDistrictModels().get(k).getZipcode());
//                }
//                District.add(DistrictNames);
//            }
//        }
//    }
//
//    //获取全国地址信息
//    public void getAddressData() throws ParserConfigurationException, SAXException, IOException {
//        SAXParserFactory spf = SAXParserFactory.newInstance();
//        SAXParser sp = spf.newSAXParser();
//        XmlParserHandler sfh = new XmlParserHandler();
//
//        AssetManager am = this.getAssets();
//        InputStream is = am.open("province_data.xml");
//
//        sp.parse(is, sfh);
//        getAdrMsg(sfh.priElement());
//    }
}
