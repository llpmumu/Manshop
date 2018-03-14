package com.manshop.android.model.city;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Lin on 2018/3/13.
 */

public class XmlProvince extends DefaultHandler {
    private List<String> province;
    private String preTag ;


    public List<String> getProvince() {
        return province;
    }
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        province = new ArrayList<>() ;

        Log.d(TAG, "startDocument: ------------------------------");

        //当读到第一个标签的时候会触发这个方法
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //开始解析节点

        if ("province".equals(localName)){

            Log.d(TAG, "新建省: --------------------------------------------");
            province = new ArrayList<>() ;
            province.add(attributes.getValue("name") );

        }
        preTag = localName ;

    }

    public void endDocument () {
        //文档解析结束
        Log.d(TAG, "endDocument: --------------------------------------------");

    }

    public void characters (char[] ch, int start, int length) {
        //保存节点内容
//        super.characters(ch, start, length);
        String value = new String(ch,start,length);
        if ("province".equals(preTag)){
            province.add(value) ;
            Log.d(TAG, "添加省: --------------------------------------------");

        }
        preTag = null ;

    }

    public void endElement (String uri, String localName, String qName) {
        //结束解析节点

        Log.d(TAG, "endElement: ------------------------"+ uri + "   " + localName + " " + qName);
    }
}
