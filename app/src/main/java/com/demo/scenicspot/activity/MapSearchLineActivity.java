package com.demo.scenicspot.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.demo.anvi.AMapUtil;
import com.demo.demo.myapplication.R;
import com.demo.utils.ToastUtil;
import com.demo.view.MyTopBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：sonnerly on 2016/11/25 0025 10:28
 */
public class MapSearchLineActivity extends Activity implements Inputtips.InputtipsListener, TextWatcher, GeocodeSearch.OnGeocodeSearchListener{
    @Bind(R.id.bar_search)
    MyTopBar barSearch;
    @Bind(R.id.input_edittext)
    AutoCompleteTextView inputEdittext;
    @Bind(R.id.lv_recommend)
    ListView lvRecommend;
    List<HashMap<String, String>> listString;
    private GeocodeSearch geocoderSearch;
    private String addressName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_searchline_activity);
        ButterKnife.bind(this);
        inputEdittext.addTextChangedListener(this);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        barSearch.setLeftImageOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inputEdittext.setText("");
                inputEdittext.setText(listString.get(position).get("name"));
                inputEdittext.setSelection(inputEdittext.length());
                listString.clear();
            }
        });
        inputEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) inputEdittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MapSearchLineActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (inputEdittext.getText().toString().equals("")) {
                        ToastUtil.show(MapSearchLineActivity.this, "搜索内容为空");
                        return true;
                    }
                    //搜索
//                    obscureSelect(etSearchWord.getText().toString());
                    getLatlon(inputEdittext.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            listString = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < tipList.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", tipList.get(i).getName());
                map.put("address", tipList.get(i).getDistrict());
                listString.add(map);
            }
            SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), listString, R.layout.item_layout,
                    new String[]{"name", "address"}, new int[]{R.id.poi_field_id, R.id.poi_value_id});

            lvRecommend.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        InputtipsQuery inputquery = new InputtipsQuery(s.toString().trim(), "唐山");
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(MapSearchLineActivity.this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {
    if(inputEdittext.getText().toString().equals("")){
        lvRecommend.setVisibility(View.GONE);
    }else{
        lvRecommend.setVisibility(View.VISIBLE);
    }
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    /**
     * 响应地理编码
     */
    public void getLatlon(final String name) {
        GeocodeQuery query = new GeocodeQuery(name, "010");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
//                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                        AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
//                geoMarker.setPosition(AMapUtil.convertToLatLng(address
//                        .getLatLonPoint()));
                addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:"
                        + address.getFormatAddress();
                Intent intent = new Intent();
                intent.putExtra("lat",114.608945);
                intent.putExtra("lng",38.023678);
                setResult(123123, intent);
                finish();
                ToastUtil.show(MapSearchLineActivity.this, addressName);
            } else {
                ToastUtil.show(MapSearchLineActivity.this, "获取失败");
            }
        } else {
            ToastUtil.show(this, rCode + "");
        }
    }
}
