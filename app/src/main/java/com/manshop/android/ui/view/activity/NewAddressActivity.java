package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.model.city.Province;
import com.manshop.android.model.city.XmlParserHandler;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.util.Constant;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Response;

public class NewAddressActivity extends BaseActivity {
    private EditText etConsigneeName;
    private EditText etConsigneePhone;
    private TextView tvConsigneeAdr;
    private EditText etConsigneeDetailAdr;
    private List<String> province;
    private List<List<String>> city;
    private List<List<List<String>>> county;
    private Boolean isEdit;
    private Intent intent;
    private OkHttp okhttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        showToolbar();
        init();
        edit();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_toolbar_edit_address, menu);
        return true;
    }

    //标题栏按钮功能实现
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Map<String, Object> param = new HashMap<>();
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_saveaddress:
                if (isEdit) {
                    param.put("id", intent.getIntExtra("id", 0));
                    requestAdrData(Constant.baseURL + "address/updateAddress", param);
                }
                else
                    requestAdrData(Constant.baseURL + "address/newAddress", param);
                break;
            default:
        }
//        submitAddress(param);
        return true;
    }

    public void init() {
        etConsigneeName = (EditText) findViewById(R.id.consignee_name);
        etConsigneePhone = (EditText) findViewById(R.id.consignee_phone);
        tvConsigneeAdr = (TextView) findViewById(R.id.consignee_address);
        etConsigneeDetailAdr = (EditText) findViewById(R.id.consignee_detail_address);
        tvConsigneeAdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getAddressData();
                    showAdrPickerView(province, city, county);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //编辑地址跳转数据传入
    public void edit() {
        intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdite" , false) ;
        etConsigneeName.setText(intent.getStringExtra("consigneeName"));
        etConsigneePhone.setText(intent.getStringExtra("consigneePhone"));
        tvConsigneeAdr.setText(intent.getStringExtra("selectAdr"));
        etConsigneeDetailAdr.setText(intent.getStringExtra("addAdr"));
    }

    //提交数据
    public void requestAdrData(String uri, Map<String, Object> params) {
        String name = etConsigneeName.getText().toString();
        String phone = etConsigneePhone.getText().toString();
        String address = tvConsigneeAdr.getText().toString();
        String detailAdr = etConsigneeDetailAdr.getText().toString();
        params.put("uid", MyApplication.getInstance().getUserId());
        params.put("consignee", name);
        params.put("addphone", phone);
        params.put("address", address + " " + detailAdr);
        okhttp.doPost(uri, new CallBack(NewAddressActivity.this) {
            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                Log.d("address", "new success");
                finish();
                Intent intent = new Intent(NewAddressActivity.this, ListAddressActivity.class);
                startActivity(intent);
            }
        }, params);
    }

    public void getAdrMsg(List<Province> provinceModels) {
        province = new ArrayList<String>();
        city = new ArrayList<List<String>>();
        county = new ArrayList<List<List<String>>>();
        for (int i = 0; i < provinceModels.size(); i++) {
            province.add(provinceModels.get(i).getName());
            List<String> cityNames = new ArrayList<String>();
            List<List<String>> District = new ArrayList<List<String>>();
            List<List<String>> DistrictCode = new ArrayList<List<String>>();
            for (int j = 0; j < provinceModels.get(i).getCityModels().size(); j++) {

                cityNames.add(provinceModels.get(i).getCityModels().get(j).getName());

                List<String> DistrictNames = new ArrayList<String>();
                List<String> DistrictNamesCode = new ArrayList<String>();

                for (int k = 0; k < provinceModels.get(i).getCityModels().get(j).getDistrictModels().size(); k++) {

                    DistrictNames.add(provinceModels.get(i).getCityModels().get(j).getDistrictModels().get(k).getName());
                    DistrictNamesCode.add(provinceModels.get(i).getCityModels().get(j).getDistrictModels().get(k).getZipcode());
                }
                District.add(DistrictNames);
                DistrictCode.add(DistrictNamesCode);
            }
            city.add(cityNames);
            county.add(District);
        }
    }

    //获取全国地址信息
    public void getAddressData() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XmlParserHandler sfh = new XmlParserHandler();

        AssetManager am = this.getAssets();
        InputStream is = am.open("province_data.xml");

        sp.parse(is, sfh);
        getAdrMsg(sfh.getProvinceModels());
    }

    //地址选择器
    public void showAdrPickerView(final List<String> province,
                                  final List<List<String>> city,
                                  final List<List<List<String>>> county) {

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tvConsigneeAdr.setText(province.get(options1) + city.get(options1).get(option2) + county.get(options1).get(option2).get(options3));
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLabels("", "", "")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvOptions.setPicker(province, city, county);//添加数据源
        pvOptions.show();
    }
}
