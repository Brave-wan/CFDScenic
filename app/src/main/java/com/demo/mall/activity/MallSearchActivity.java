package com.demo.mall.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.adapter.MallSearchGoodsAdapter;
import com.demo.mall.adapter.MallSearchHotAdapter;
import com.demo.mall.adapter.MallSearchUserAdapter;
import com.demo.mall.bean.MallSearchBean;
import com.demo.mall.bean.MallSearchHotBean;
import com.demo.scenicspot.adapter.YuyinBean;
import com.demo.scenicspot.bean.ObscureSelectBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.NoScrollGridView;
import com.demo.view.NoScrollViewListView;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;
import com.demo.view.photo.SelectImagePopupWindow;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 作者：sonnerly on 2016/9/12 0012 17:43
 */
public class MallSearchActivity extends Activity {
    //    @Bind(R.id.iv_search_find)
//    ImageView ivSearchFind;
//    @Bind(R.id.et_search_word)
//    EditText etSearchWord;
//    @Bind(R.id.iv_search_voice)
//    ImageView ivSearchVoice;
//    @Bind(R.id.tv_search_delete)
//    TextView tvSearchDelete;
//    @Bind(R.id.ll_main)
//    LinearLayout llMain;
//    @Bind(R.id.sgv_search)
//    NoScrollGridView sgvSearch;
//    @Bind(R.id.ll_search_hide)
//    LinearLayout llSearchHide;
//    @Bind(R.id.search_have)
//    ListView searchHave;
//    @Bind(R.id.tv_noresult)
//    TextView tvNoresult;
//    @Bind(R.id.imageView)
//    ImageView imageView;
    StringBuffer stringBuffer;//存放搜索的内容
    @Bind(R.id.et_search_word_mall)
    EditText etSearchWordMall;
    @Bind(R.id.iv_search_voice_mall)
    ImageView ivSearchVoiceMall;
    @Bind(R.id.tv_search_delete_mall)
    TextView tvSearchDeleteMall;
    @Bind(R.id.ll_main)
    LinearLayout llMain;
    @Bind(R.id.sgv_search_mall)
    NoScrollGridView sgvSearchMall;
    @Bind(R.id.ll_search_hide)
    LinearLayout llSearchHide;
    @Bind(R.id.slv_search_mall_business)
    NoScrollViewListView slvSearchMallBusiness;
    @Bind(R.id.ll_search_business)
    LinearLayout llSearchBusiness;
    @Bind(R.id.sgv_search_mall_good)
    NoScrollGridView sgvSearchMallGood;
    @Bind(R.id.ll_search_goods)
    LinearLayout llSearchGoods;
    @Bind(R.id.ll_search_mall_result)
    LinearLayout llSearchMallResult;
    @Bind(R.id.tv_noresult_mall)
    TextView tvNoresultMall;
    @Bind(R.id.imageView_mall)
    ImageView imageViewMall;
    MallSearchHotBean mallSearchHotBean=new MallSearchHotBean();//热门
    MallSearchBean mallSearchBean=new MallSearchBean();//搜索商品
    Intent intent=new Intent();

    PermissionsChecker mPermissionsChecker;
    private SelectImagePopupWindow SelectphotoPPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_search);
        ButterKnife.bind(this);


        mPermissionsChecker=new PermissionsChecker(this);
//        init();

        //APPID需要换成商家的  已改
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=57d8b737");

        //热门搜索
        SearchMallHot();
        etSearchWordMall.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) etSearchWordMall.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MallSearchActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (etSearchWordMall.getText().toString().equals("")){
                        ToastUtil.show(MallSearchActivity.this,"搜索内容为空");
                        return true;
                    }
                    //搜索
                    SearchMall(etSearchWordMall.getText().toString());
                    return true;
                }
                return false;
            }
        });
        //搜索出商品网格
        sgvSearchMall.setOnItemClickListener(new AdapterView.OnItemClickListener() {//热门
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mallSearchHotBean.getData().get(position).getType() == 1) {//酒店
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String time = format.format(date);
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    String time1 = format.format(c.getTime());
                    intent.setClass(MallSearchActivity.this, HotelMoreActivity.class);
                    intent.putExtra("id", mallSearchHotBean.getData().get(position).getId() + "");
                    intent.putExtra("start", time);
                    intent.putExtra("end", time1);
                    intent.putExtra("total", "1");
                    startActivity(intent);
                } else if (mallSearchHotBean.getData().get(position).getType() == 2) {
                    intent.setClass(MallSearchActivity.this, RestaurantMoreActivity.class);
                    intent.putExtra("id", mallSearchHotBean.getData().get(position).getId() + "");
                    intent.putExtra("date", "");
                    startActivity(intent);
                } else if (mallSearchHotBean.getData().get(position).getType() == 3) {//饭店
                    intent.setClass(MallSearchActivity.this, PurchaseDetailsActivity.class);
                    intent.putExtra("id", mallSearchHotBean.getData().get(position).getId() + "");
                    startActivity(intent);
                } else {
                    intent.setClass(MallSearchActivity.this, PurchaseDetailsActivity.class);
                    intent.putExtra("id", mallSearchHotBean.getData().get(position).getId() + "");
                    startActivity(intent);
                }
            }
        });
        //店铺搜索
        slvSearchMallBusiness.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(mallSearchBean.getData().getShopUserMap().get(position).getShopGroupId()==1){//酒店
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String time = format.format(date);
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, 1);
                String time1 = format.format(c.getTime());
                intent.setClass(MallSearchActivity.this, HotelMoreActivity.class);
                intent.putExtra("id", mallSearchBean.getData().getShopUserMap().get(position).getInformationId() + "");
                intent.putExtra("start", time);
                intent.putExtra("end", time1);
                intent.putExtra("total", "1");
                startActivity(intent);
            }else if(mallSearchBean.getData().getShopUserMap().get(position).getShopGroupId()==2){//饭店
                intent.setClass(MallSearchActivity.this, RestaurantMoreActivity.class);
                intent.putExtra("id", mallSearchBean.getData().getShopUserMap().get(position).getInformationId() + "");
                intent.putExtra("date", "");
                startActivity(intent);
            }else if(mallSearchBean.getData().getShopUserMap().get(position).getShopGroupId()==3){//特产
                intent.setClass(MallSearchActivity.this,ShopActivity.class);//小吃
                intent.putExtra("id", mallSearchBean.getData().getShopUserMap().get(position).getInformationId() + "");
                intent.putExtra("name",mallSearchBean.getData().getShopUserMap().get(position).getName());
                intent.putExtra("ImageUrl",mallSearchBean.getData().getShopUserMap().get(position).getBackgroudImg());
                startActivity(intent);
            }else if(mallSearchBean.getData().getShopUserMap().get(position).getShopGroupId()==4){//小吃
                intent.setClass(MallSearchActivity.this,ShopActivity.class);//小吃
                intent.putExtra("id", mallSearchBean.getData().getShopUserMap().get(position).getInformationId() + "");
                intent.putExtra("name",mallSearchBean.getData().getShopUserMap().get(position).getName());
                intent.putExtra("ImageUrl",mallSearchBean.getData().getShopUserMap().get(position).getBackgroudImg());
                startActivity(intent);
            }
            }
        });
    }

    private void SearchMallHot() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", 1 + "");
        params.addQueryStringParameter("rows", 9 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.mallSearchHot, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String s = responseInfo.result;
                        try {
                             mallSearchHotBean = new Gson().fromJson(responseInfo.result, MallSearchHotBean.class);
                            if (mallSearchHotBean.getHeader().getStatus() == 0) {
                                sgvSearchMall.setAdapter(new MallSearchHotAdapter(getApplication(), mallSearchHotBean.getData()));
                            } else {
                                ToastUtil.show(getApplicationContext(), mallSearchHotBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
    }

    private void SearchMall(String s) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("keyWord", s);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.MALLSEARCH, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String s = responseInfo.result;
                        try {
                             mallSearchBean = new Gson().fromJson(responseInfo.result, MallSearchBean.class);
                            int i = mallSearchBean.getHeader().getStatus();
                            if (i == 0) {
                                if (mallSearchBean.getData() == null || mallSearchBean.getData().equals("")) {
                                    llSearchMallResult.setVisibility(View.GONE);
                                    tvNoresultMall.setVisibility(View.VISIBLE);//没有找到
                                    llSearchHide.setVisibility(View.GONE);
                                } else {
                                    llSearchMallResult.setVisibility(View.VISIBLE);
                                    tvNoresultMall.setVisibility(View.GONE);//没有找到
                                    llSearchHide.setVisibility(View.GONE);
                                    if (mallSearchBean.getData().getShopGoodsMap() == null || mallSearchBean.getData().getShopGoodsMap().equals("")) {
                                        llSearchGoods.setVisibility(View.GONE);
                                    } else {
                                        llSearchGoods.setVisibility(View.VISIBLE);
                                        sgvSearchMallGood.setAdapter(new MallSearchGoodsAdapter(getApplication(), mallSearchBean.getData().getShopGoodsMap()));
                                    }
                                    if (mallSearchBean.getData().getShopUserMap() == null || mallSearchBean.getData().getShopUserMap().equals("")) {
                                        llSearchMallResult.setVisibility(View.GONE);
                                    } else {
                                        llSearchMallResult.setVisibility(View.VISIBLE);
                                        slvSearchMallBusiness.setAdapter(new MallSearchUserAdapter(getApplication(), mallSearchBean.getData().getShopUserMap()));
                                    }
                                }

                            } else if (i == 3) {
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(MallSearchActivity.this);

                            } else {
                                ToastUtil.show(getApplicationContext(), mallSearchBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });
        sgvSearchMallGood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mallSearchBean.getData().getShopGoodsMap().get(position).getType()==1){//酒店
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String time = format.format(date);
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    String time1=format.format(c.getTime());
                    intent.setClass(MallSearchActivity.this,HotelMoreActivity.class);
                    intent.putExtra("id", mallSearchBean.getData().getShopGoodsMap().get(position).getId() + "");
                    intent.putExtra("start", time);
                    intent.putExtra("end", time1);
                    intent.putExtra("total","1");
                    startActivity(intent);
                }else if(mallSearchBean.getData().getShopGoodsMap().get(position).getType()==2){//饭店
                    intent.setClass(MallSearchActivity.this, RestaurantMoreActivity.class);
                    intent.putExtra("id", mallSearchBean.getData().getShopGoodsMap().get(position).getId() + "");
                    intent.putExtra("date","");
                    startActivity(intent);
                }else if(mallSearchBean.getData().getShopGoodsMap().get(position).getType()==3){
                    intent.setClass(MallSearchActivity.this, PurchaseDetailsActivity.class);
                    intent.putExtra("id", mallSearchBean.getData().getShopGoodsMap().get(position).getId() + "");
                    startActivity(intent);
                }else if(mallSearchBean.getData().getShopGoodsMap().get(position).getType()==4){
                    intent.setClass(MallSearchActivity.this,PurchaseDetailsActivity.class);
                    intent.putExtra("id",mallSearchBean.getData().getShopGoodsMap().get(position).getId()+"");
                    startActivity(intent);
                }
            }
        });
    }

    @OnClick({R.id.iv_search_voice_mall, R.id.tv_search_delete_mall})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_voice_mall:
                if (mPermissionsChecker.getAndroidSDKVersion() >= 23) {
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
                        startPermissionsActivity();
                }
                dictation();
                break;
            case R.id.tv_search_delete_mall:
                if (etSearchWordMall.getText().length() > 0) {
                    etSearchWordMall.setText("");
                    llSearchHide.setVisibility(View.VISIBLE);
                    llSearchMallResult.setVisibility(View.GONE);
                } else {
                    finish();
                }
                break;
        }
    }

    private void dictation() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etSearchWordMall.getWindowToken(), 0);

        stringBuffer = new StringBuffer();
        //1.创建SpeechRecognizer对象，第二个参数：本地识别时传InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, null);
        //2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");//设置是否显示标点0表示不显示，1表示显示
        //3.开始听写
        mIat.startListening(mRecoListener);
    }

    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        //听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        //关于解析Json的代码可参见Demo中JsonParser类；
        //isLast等于true时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            YuyinBean yuyinBean = new Gson().fromJson(results.getResultString(), YuyinBean.class);
            if (isLast == false) {
                for (int i = 0; i < yuyinBean.getWs().size(); i++) {
                    stringBuffer.append(yuyinBean.getWs().get(i).getCw().get(0).getW());
                }
                //说话的结果
                etSearchWordMall.setText(stringBuffer.toString());
                Editable etext = etSearchWordMall.getText();
                Selection.setSelection(etext, etext.length());

                //搜索
                SearchMall(etSearchWordMall.getText().toString());
            }
        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
        }

        //开始录音
        public void onBeginOfSpeech() {
        }

        //volume音量值0~30，data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
            imageViewMall.setVisibility(View.VISIBLE);
            if (volume == 0) {
                imageViewMall.setImageResource(R.mipmap.yuyin);
            } else if (volume == 1 || volume == 2) {
                imageViewMall.setImageResource(R.mipmap.yuyin1);
            } else if (volume == 3 || volume == 4) {
                imageViewMall.setImageResource(R.mipmap.yuyin2);
            } else if (volume == 5 || volume == 6) {
                imageViewMall.setImageResource(R.mipmap.yuyin3);
            } else if (volume == 7 || volume == 8) {
                imageViewMall.setImageResource(R.mipmap.yuyin4);
            } else if (volume == 9 || volume == 10) {
                imageViewMall.setImageResource(R.mipmap.yuyin5);
            } else if (volume == 11 || volume == 12) {
                imageViewMall.setImageResource(R.mipmap.yuyin6);
            } else if (volume == 13 || volume == 14) {
                imageViewMall.setImageResource(R.mipmap.yuyin7);
            } else if (volume == 15 || volume == 16) {
                imageViewMall.setImageResource(R.mipmap.yuyin8);
            } else if (volume == 17 || volume == 18) {
                imageViewMall.setImageResource(R.mipmap.yuyin9);
            } else if (volume == 19 || volume == 20) {
                imageViewMall.setImageResource(R.mipmap.yuyin10);
            } else if (volume == 21 || volume == 22) {
                imageViewMall.setImageResource(R.mipmap.yuyin11);
            } else if (volume == 23 || volume == 24) {
                imageViewMall.setImageResource(R.mipmap.yuyin12);
            } else if (volume == 25 || volume == 26) {
                imageViewMall.setImageResource(R.mipmap.yuyin13);
            } else if (volume == 27 || volume == 28) {
                imageViewMall.setImageResource(R.mipmap.yuyin14);
            } else if (volume == 29 || volume == 30) {
                imageViewMall.setImageResource(R.mipmap.yuyin15);
            }
        }

        //结束录音
        public void onEndOfSpeech() {
            imageViewMall.setVisibility(View.GONE);
        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };



    // 所需权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static int REQUEST_CODE = 123456;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED)
                return;
        }/* else
            SelectphotoPPW.onActivityResult(requestCode, resultCode, data);*/
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }
}
