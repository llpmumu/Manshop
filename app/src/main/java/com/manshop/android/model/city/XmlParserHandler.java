package com.manshop.android.model.city;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Lin on 2018/1/4.
 */

public class XmlParserHandler extends DefaultHandler {

    private List<Province> provinceModels;
    private List<City> cityModels ;
    private List<District> districtModels ;
    private Province provinceModel;
    private District districtModel ;
    private City cityModel ;
    private String preTag ;


    public List<Province> getProvinceModels() {
        return provinceModels;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        provinceModels = new ArrayList<>() ;

        Log.d(TAG, "startDocument: ------------------------------");

        //当读到第一个标签的时候会触发这个方法
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //开始解析节点

        if ("province".equals(localName)){

            Log.d(TAG, "新建省: --------------------------------------------");
            cityModels = new ArrayList<>() ;
            provinceModel = new Province() ;
            provinceModel.setName(attributes.getValue("name") );

        }else if ("city".equals(localName)){

            districtModels = new ArrayList<>() ;
            cityModel = new City() ;
            cityModel.setName(attributes.getValue("name") );
        }else if ("district".equals(localName)){

            districtModel = new District() ;
            districtModel.setName(attributes.getValue("name") );
            districtModel.setZipcode(attributes.getValue("zipcode") );
        }

        preTag = localName ;

    }


    public void endDocument () {
        //文档解析结束
        Log.d(TAG, "endDocument: --------------------------------------------");

    }

    public void characters (char[] ch, int start, int length) {
        //保存节点内容

        if ("province".equals(preTag)){
            provinceModels.add(provinceModel) ;
            Log.d(TAG, "添加省: --------------------------------------------");

        }else if ("city".equals(preTag)){
            cityModels.add(cityModel) ;
            provinceModel.setCityModels(cityModels);
            Log.d(TAG, "添加市: --------------------------------------------");
        }else if ("district".equals(preTag)){
            districtModels.add(districtModel) ;
            cityModel.setDistrictModels(districtModels);
            Log.d(TAG, "添加县: --------------------------------------------");
        }

        preTag = null ;

    }

    public void endElement (String uri, String localName, String qName) {
        //结束解析节点

        Log.d(TAG, "endElement: ------------------------"+ uri + "   " + localName + " " + qName);
    }
}
