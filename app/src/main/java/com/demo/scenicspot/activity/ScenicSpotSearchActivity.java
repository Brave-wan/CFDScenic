package com.demo.scenicspot.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.fragment.BaseActivity;
import com.demo.fragment.MainActivity;
import com.demo.scenicspot.adapter.SearchListAdapter;
import com.demo.scenicspot.adapter.SearchAdapter;
import com.demo.scenicspot.adapter.YuyinBean;
import com.demo.scenicspot.bean.GoodsBean;
import com.demo.scenicspot.bean.ObscureSelectBean;
import com.demo.scenicspot.bean.ScenicSpotListBean;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.NoScrollGridView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： on 2016/8/3 0003 08:52
 */
public class ScenicSpotSearchActivity extends Activity {
    @Bind(R.id.et_search_word)
    EditText etSearchWord;
    @Bind(R.id.iv_search_voice)
    ImageView ivSearchVoice;
    @Bind(R.id.tv_search_delete)
    TextView tvSearchDelete;
    @Bind(R.id.ll_main)
    LinearLayout llMain;
    @Bind(R.id.sgv_search)
    NoScrollGridView sgvSearch;
    @Bind(R.id.ll_search_hide)
    LinearLayout llSearchHide;
    @Bind(R.id.iv_search_find)
    ImageView ivSearchFind;
    @Bind(R.id.search_have)
    ListView searchHave;
    @Bind(R.id.tv_noresult)
    TextView tvNoresult;
    @Bind(R.id.imageView)
    ImageView imageView;

    PermissionsChecker mPermissionsChecker;
    private SelectImagePopupWindow SelectphotoPPW;
    StringBuffer stringBuffer;//存放搜索的内容

    DialogProgressbar dialogProgressbar;
    private List<ScenicSpotListBean.DataBean> list=new ArrayList<>();
    private List<ObscureSelectBean.DataBean> obscureSelectList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.science_activity_search);
        ButterKnife.bind(this);
        init();

        mPermissionsChecker=new PermissionsChecker(this);
        dialogProgressbar=new DialogProgressbar(ScenicSpotSearchActivity.this,R.style.AlertDialogStyle);
        //热门搜索
        scenicSpotList();
        etSearchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) etSearchWord.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ScenicSpotSearchActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (etSearchWord.getText().toString().equals("")){
                        ToastUtil.show(getApplicationContext(),"搜索内容为空");
                        return true;
                    }
                    //搜索
                    obscureSelect(etSearchWord.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void init() {
       /* //隐藏输入法
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etSearchWord.getWindowToken(), 0);*/

        //APPID需要换成商家的  已改
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=57d8b737");

        sgvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ScenicSpotSearchActivity.this, ScienceMoreActivity.class);
                intent.putExtra("id", list.get(position).getId() + "");
                startActivity(intent);
            }
        });


        searchHave.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ScenicSpotSearchActivity.this, ScienceMoreActivity.class);
                intent.putExtra("id", obscureSelectList.get(position).getId() + "");
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.iv_search_voice, R.id.tv_search_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_voice:
                if (mPermissionsChecker.getAndroidSDKVersion() >= 23) {
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS))
                        startPermissionsActivity();
                }
                dictation();
                break;
            case R.id.tv_search_delete:
                if (etSearchWord.getText().length() > 0) {
                    etSearchWord.setText("");
                    searchHave.setVisibility(View.GONE);
                    tvNoresult.setVisibility(View.GONE);//没有找到
                    llSearchHide.setVisibility(View.VISIBLE);
                } else {
                    finish();
                }
                break;
        }
    }


    //景点列表
    private void scenicSpotList() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", 1 + "");
        params.addQueryStringParameter("rows", 9 + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.scenicSpotList, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            ScenicSpotListBean scenicSpotListBean = new Gson().fromJson(responseInfo.result, ScenicSpotListBean.class);
                            int i = scenicSpotListBean.getHeader().getStatus();
                            if (i == 0) {
                                list = scenicSpotListBean.getData();
                                sgvSearch.setAdapter(new SearchAdapter(getApplication(), list));
                            } else {
                                ToastUtil.show(getApplicationContext(), scenicSpotListBean.getHeader().getMsg());
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
    }

    //搜索访问网络
    private void obscureSelect(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("name", name);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.obscureSelect, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        dialogProgressbar.setCancelable(false);//点击对话框以外的地方不关闭  把返回键禁止了
                        dialogProgressbar.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dialogProgressbar.dismiss();

                        try {
                            ObscureSelectBean obscureSelectBean = new Gson().fromJson(responseInfo.result, ObscureSelectBean.class);
                            int i = obscureSelectBean.getHeader().getStatus();
                            if (i == 0) {
                                if (obscureSelectBean.getData() == null) {
                                    searchHave.setVisibility(View.GONE);
                                    tvNoresult.setVisibility(View.VISIBLE);//没有找到
                                    llSearchHide.setVisibility(View.GONE);
                                    return;
                                }
                                if (obscureSelectBean.getData().size() == 0) {
                                    tvNoresult.setVisibility(View.VISIBLE);//没有找到
                                    llSearchHide.setVisibility(View.GONE);
                                    searchHave.setVisibility(View.GONE);
                                } else {//添加数据
                                    obscureSelectList = obscureSelectBean.getData();
                                    tvNoresult.setVisibility(View.GONE);
                                    llSearchHide.setVisibility(View.GONE);
                                    searchHave.setVisibility(View.VISIBLE);
                                    searchHave.setAdapter(new SearchListAdapter(ScenicSpotSearchActivity.this, obscureSelectList));
                                }
                            } else {
                                ToastUtil.show(getApplicationContext(), obscureSelectBean.getHeader().getMsg());
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
    }

    private void dictation() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etSearchWord.getWindowToken(), 0);

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
                etSearchWord.setText(stringBuffer.toString());
                Editable etext = etSearchWord.getText();
                Selection.setSelection(etext, etext.length());

                //搜索
                obscureSelect(etSearchWord.getText().toString());
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
            Log.i("volume",volume+"");
            imageView.setVisibility(View.VISIBLE);
            if (volume == 0) {
                imageView.setImageResource(R.mipmap.yuyin);
            } else if (volume == 1 || volume == 2) {
                imageView.setImageResource(R.mipmap.yuyin1);
            } else if (volume == 3 || volume == 4) {
                imageView.setImageResource(R.mipmap.yuyin2);
            } else if (volume == 5 || volume == 6) {
                imageView.setImageResource(R.mipmap.yuyin3);
            } else if (volume == 7 || volume == 8) {
                imageView.setImageResource(R.mipmap.yuyin4);
            } else if (volume == 9 || volume == 10) {
                imageView.setImageResource(R.mipmap.yuyin5);
            } else if (volume == 11 || volume == 12) {
                imageView.setImageResource(R.mipmap.yuyin6);
            } else if (volume == 13 || volume == 14) {
                imageView.setImageResource(R.mipmap.yuyin7);
            } else if (volume == 15 || volume == 16) {
                imageView.setImageResource(R.mipmap.yuyin8);
            } else if (volume == 17 || volume == 18) {
                imageView.setImageResource(R.mipmap.yuyin9);
            } else if (volume == 19 || volume == 20) {
                imageView.setImageResource(R.mipmap.yuyin10);
            } else if (volume == 21 || volume == 22) {
                imageView.setImageResource(R.mipmap.yuyin11);
            } else if (volume == 23 || volume == 24) {
                imageView.setImageResource(R.mipmap.yuyin12);
            } else if (volume == 25 || volume == 26) {
                imageView.setImageResource(R.mipmap.yuyin13);
            } else if (volume == 27 || volume == 28) {
                imageView.setImageResource(R.mipmap.yuyin14);
            } else if (volume == 29 || volume == 30) {
                imageView.setImageResource(R.mipmap.yuyin15);
            }

        }

        //结束录音
        public void onEndOfSpeech() {
            imageView.setVisibility(View.GONE);
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
        } /*else
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
