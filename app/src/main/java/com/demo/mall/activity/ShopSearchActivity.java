package com.demo.mall.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.adapter.HomeGoodGridViewAdapter;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.mall.adapter.MallSearchGoodsAdapter;
import com.demo.mall.adapter.MallSearchHotAdapter;
import com.demo.mall.adapter.MallSearchUserAdapter;
import com.demo.mall.adapter.ShopSearchAdapter;
import com.demo.mall.bean.FindFeatureShopBean;
import com.demo.mall.bean.MallSearchBean;
import com.demo.mall.bean.MallSearchHotBean;
import com.demo.scenicspot.adapter.YuyinBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.NoScrollGridView;
import com.demo.view.permission.PermissionsActivity;
import com.demo.view.permission.PermissionsChecker;
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

import org.apache.http.protocol.HTTP;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/10/17 0017 15:00
 */
public class ShopSearchActivity extends Activity {
    @Bind(R.id.et_search_word_mall_shop)
    EditText etSearchWordMallShop;
    @Bind(R.id.iv_search_voice_mall_shop)
    ImageView ivSearchVoiceMallShop;
    @Bind(R.id.tv_search_delete_mall_shop)
    TextView tvSearchDeleteMallShop;
    @Bind(R.id.ll_main_shop)
    LinearLayout llMainShop;
    @Bind(R.id.sgv_search_mall_shop)
    NoScrollGridView sgvSearchMallShop;
    @Bind(R.id.ll_search_hide_shop)
    LinearLayout llSearchHideShop;
    @Bind(R.id.gv_search_mall_good_shop)
    GridView gvSearchMallGoodShop;
    @Bind(R.id.ll_search_mall_result_shop)
    LinearLayout llSearchMallResultShop;
    @Bind(R.id.tv_noresult_mall_shop)
    TextView tvNoresultMallShop;
    @Bind(R.id.imageView_mall_shop)
    ImageView imageViewMallShop;
    StringBuffer stringBuffer;//存放搜索的内容
    FindFeatureShopBean findFeatureShopBean;
    HomeGoodGridViewAdapter homeGoodGridViewAdapter;
    ShopSearchAdapter shopSearchAdapter;

    PermissionsChecker mPermissionsChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_shop_search);
        ButterKnife.bind(this);

        mPermissionsChecker=new PermissionsChecker(this);

        //APPID需要换成商家的  已改
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=57d8b737");

        SearchMallHot();
        etSearchWordMallShop.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) etSearchWordMallShop.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ShopSearchActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (etSearchWordMallShop.getText().toString().equals("")){
                        ToastUtil.show(ShopSearchActivity.this,"搜索内容为空");
                        return true;
                    }
                    //搜索
                    SearchMall(etSearchWordMallShop.getText().toString());
                    return true;
                }
                return false;
            }
        });
        sgvSearchMallShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShopSearchActivity.this, PurchaseDetailsActivity.class);
                intent.putExtra("id", findFeatureShopBean.getData().get(position).getShopGoodsId() + "");//商品ID
                startActivity(intent);
            }
        });
        gvSearchMallGoodShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShopSearchActivity.this, PurchaseDetailsActivity.class);
                intent.putExtra("id",findFeatureShopBean.getData().get(position).getShopGoodsId()+"");//商品ID
                startActivity(intent);
            }
        });

    }
    private void SearchMallHot() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(ShopSearchActivity.this, SpName.token, ""));
        params.addQueryStringParameter("informationId", getIntent().getStringExtra("id"));
        params.addQueryStringParameter("page", 1 + "");
        params.addQueryStringParameter("rows", 9 + "");
        params.addQueryStringParameter("type","2");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findFeatureShop, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            findFeatureShopBean = new Gson().fromJson(responseInfo.result, FindFeatureShopBean.class);
                            int i = findFeatureShopBean.getHeader().getStatus();
                            if (i == 0) {
                                shopSearchAdapter = new ShopSearchAdapter(ShopSearchActivity.this, findFeatureShopBean.getData());
                                sgvSearchMallShop.setAdapter(shopSearchAdapter);
                            } else if(i==3){
                                //异地登录对话框，必须传.this  不能传Context
                                MainActivity.state_Three(ShopSearchActivity.this);
                            }else {
                                ToastUtil.show(ShopSearchActivity.this, findFeatureShopBean.getHeader().getMsg());
                            }


                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });
    }
    private void SearchMall(String s) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("siId", getIntent().getStringExtra("id"));
        params.addQueryStringParameter("goodsName", s);
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.shopSearch, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            findFeatureShopBean = new Gson().fromJson(responseInfo.result, FindFeatureShopBean.class);
                            int i = findFeatureShopBean.getHeader().getStatus();
                            if (i == 0) {
                                if (findFeatureShopBean.getData() == null || findFeatureShopBean.getData().size()==0) {
                                    llSearchMallResultShop.setVisibility(View.GONE);
                                    tvNoresultMallShop.setVisibility(View.VISIBLE);//没有找到
                                    llSearchHideShop.setVisibility(View.GONE);
                                } else {
                                    llSearchMallResultShop.setVisibility(View.VISIBLE);
                                    tvNoresultMallShop.setVisibility(View.GONE);//没有找到
                                    llSearchHideShop.setVisibility(View.GONE);
                                    homeGoodGridViewAdapter = new HomeGoodGridViewAdapter(ShopSearchActivity.this, findFeatureShopBean.getData());
                                    gvSearchMallGoodShop.setAdapter(homeGoodGridViewAdapter);
                                }

                            }  else {
                                ToastUtil.show(getApplicationContext(), findFeatureShopBean.getHeader().getMsg());
                            }

                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), s);
                    }
                });

    }

    @OnClick({R.id.tv_search_delete_mall_shop, R.id.iv_search_voice_mall_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_delete_mall_shop:
                if (etSearchWordMallShop.getText().length() > 0) {
                    etSearchWordMallShop.setText("");
                    llSearchMallResultShop.setVisibility(View.GONE);
                    tvNoresultMallShop.setVisibility(View.GONE);//没有找到
                    llSearchHideShop.setVisibility(View.VISIBLE);
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etSearchWordMallShop.getWindowToken(), 0);
                    finish();
                }
                break;
            case R.id.iv_search_voice_mall_shop:
                if (mPermissionsChecker.getAndroidSDKVersion() >= 23) {
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
                        startPermissionsActivity();
                }
                dictation();
                break;
        }
    }
    private void dictation() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etSearchWordMallShop.getWindowToken(), 0);

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
                etSearchWordMallShop.setText(stringBuffer.toString());
                Editable etext = etSearchWordMallShop.getText();
                Selection.setSelection(etext, etext.length());

                //搜索
                SearchMall(etSearchWordMallShop.getText().toString());
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
            imageViewMallShop.setVisibility(View.VISIBLE);
            if (volume == 0) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin);
            } else if (volume == 1 || volume == 2) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin1);
            } else if (volume == 3 || volume == 4) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin2);
            } else if (volume == 5 || volume == 6) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin3);
            } else if (volume == 7 || volume == 8) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin4);
            } else if (volume == 9 || volume == 10) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin5);
            } else if (volume == 11 || volume == 12) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin6);
            } else if (volume == 13 || volume == 14) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin7);
            } else if (volume == 15 || volume == 16) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin8);
            } else if (volume == 17 || volume == 18) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin9);
            } else if (volume == 19 || volume == 20) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin10);
            } else if (volume == 21 || volume == 22) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin11);
            } else if (volume == 23 || volume == 24) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin12);
            } else if (volume == 25 || volume == 26) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin13);
            } else if (volume == 27 || volume == 28) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin14);
            } else if (volume == 29 || volume == 30) {
                imageViewMallShop.setImageResource(R.mipmap.yuyin15);
            }

        }

        //结束录音
        public void onEndOfSpeech() {
            imageViewMallShop.setVisibility(View.GONE);
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
