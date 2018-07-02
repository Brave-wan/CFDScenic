package com.demo.scenicspot.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.demo.demo.myapplication.R;
import com.demo.utils.ToastUtil;
import com.demo.view.DialogProgressbar;
import com.demo.view.seeImage.photoview.gestures.GestureDetector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/12/13 0013 10:49
 */
public class MapSearchActivity extends Activity implements PoiSearch.OnPoiSearchListener {

    @Bind(R.id.et_mapsearch_word)
    EditText etMapsearchWord;
    @Bind(R.id.tv_search_delete)
    TextView tvSearchDelete;
    @Bind(R.id.lv_mapsear)
    ListView lvMapsear;
    @Bind(R.id.tv_mapnoresult)
    TextView tvMapnoresult;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    ItemAdapter adapter;
    List<PoiItem> poiItems;
    DialogProgressbar dialogProgressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapsearch);
        ButterKnife.bind(this);
        etMapsearchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) etMapsearchWord.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MapSearchActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (etMapsearchWord.getText().toString().equals("")) {
                        ToastUtil.show(getApplicationContext(), "搜索内容为空");
                        return true;
                    }
                    //搜索
                    doSearchQuery(etMapsearchWord.getText().toString());
                    return true;
                }
                return false;
            }
        });
        lvMapsear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(poiItems.get(position).getLatLonPoint()==null||poiItems.get(position).getLatLonPoint()==null){

                }else {
//                    ToastUtil.show(MapSearchActivity.this,"经度"+poiItems.get(position).getLatLonPoint().getLongitude()+"纬度"+poiItems.get(position).getLatLonPoint().getLatitude()+"");
                    Intent intent = new Intent();
                    intent.putExtra("lat", poiItems.get(position).getLatLonPoint().getLatitude());
                    intent.putExtra("lng", poiItems.get(position).getLatLonPoint().getLongitude());
                    setResult(123123, intent);
                    finish();

                }
            }
        });
    }

    @OnClick(R.id.tv_search_delete)
    public void onClick() {
        if (etMapsearchWord.getText().length() > 0) {
            etMapsearchWord.setText("");
            lvMapsear.setVisibility(View.GONE);
            tvMapnoresult.setVisibility(View.GONE);//没有找到
        } else {
            finish();
        }
    }
    private void doSearchQuery(String keyWord) {
         dialogProgressbar=new DialogProgressbar(MapSearchActivity.this,R.style.AlertDialogStyle);
//            dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
            dialogProgressbar.show();
        query = new PoiSearch.Query(keyWord, "", "唐山");
        poiSearch = new PoiSearch(this, query);
        List<LatLonPoint> points = new ArrayList<LatLonPoint>();
        points.add(new LatLonPoint(39.24985243, 118.41313362));
        points.add(new LatLonPoint(39.21637842, 118.39494824));
        points.add(new LatLonPoint(39.21614567, 118.39487314));
        points.add(new LatLonPoint(39.19943588, 118.39587092));
        points.add(new LatLonPoint(39.17996105, 118.39661121));
        points.add(new LatLonPoint(39.14910792, 118.39754462));
        points.add(new LatLonPoint(39.14816772, 118.32550049));
        points.add(new LatLonPoint(39.15900003, 118.32567215));
        points.add(new LatLonPoint(39.16149572, 118.32383752));
        points.add(new LatLonPoint(39.17656778, 118.32389116));
        points.add(new LatLonPoint(39.24664534, 118.3249855));
        points.add(new LatLonPoint(39.24819074, 118.36661339));
        points.add(new LatLonPoint(39.24917115, 118.39358568));
        points.add(new LatLonPoint(39.24985243, 118.41313362));
//        points.add(new LatLonPoint(38.005855,114.598082));
//        points.add(new LatLonPoint(38.029191, 114.618307));
        poiSearch.setBound(new PoiSearch.SearchBound(points));
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    // 取得搜索到的poiitems有多少页
                     poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems != null && poiItems.size() > 0) {
                        dialogProgressbar.dismiss();
                        tvMapnoresult.setVisibility(View.GONE);
                        lvMapsear.setVisibility(View.VISIBLE);
                        adapter = new ItemAdapter(this, poiItems);
                        lvMapsear.setAdapter(adapter);
                    }else{
                        dialogProgressbar.dismiss();
                        tvMapnoresult.setVisibility(View.VISIBLE);
                        lvMapsear.setVisibility(View.GONE);
                    }
                } else {
                    tvMapnoresult.setVisibility(View.VISIBLE);
                    lvMapsear.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public class ItemAdapter extends BaseAdapter {
        Context context;
        List<PoiItem> list;

        public ItemAdapter(Context context, List<PoiItem> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CyzMode cyzMode = null;
            if (convertView == null) {
                cyzMode = new CyzMode();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_search, null);
                cyzMode.textView = (TextView) convertView.findViewById(R.id.tv_item_search);
                convertView.setTag(cyzMode);
            } else {
                cyzMode = (CyzMode) convertView.getTag();
            }
            cyzMode.textView.setText(list.get(position).getTitle());
            return convertView;
        }

        private class CyzMode {
            TextView textView;
        }
    }

}
