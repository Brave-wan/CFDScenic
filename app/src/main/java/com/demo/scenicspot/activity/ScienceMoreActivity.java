package com.demo.scenicspot.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.adapter.GraphicAdapter;
import com.demo.demo.myapplication.R;
import com.demo.fragment.MainActivity;
import com.demo.fragment.SystemBarTintManager;
import com.demo.scenicspot.bean.FindAtlasByVisitorsIdBean;
import com.demo.scenicspot.bean.ScenicSpotParticularsBean;
import com.demo.scenicspot.fragment.ArroundFragment;
import com.demo.scenicspot.fragment.CommentFragment;
import com.demo.scenicspot.fragment.MoreFragment;
import com.demo.scenicspot.fragment.MustKnowFragment;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.demo.view.GradationScrollView;
import com.google.gson.Gson;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： on 2016/7/29 0029 16:29
 * 景区详情
 */
public class ScienceMoreActivity extends FragmentActivity implements GradationScrollView.ScrollViewListener {
    @Bind(R.id.iv_sciencemore_bigpic)
    ImageView ivSciencemoreBigpic;
    @Bind(R.id.tv_sciencemore_title)
    TextView tvSciencemoreTitle;
    @Bind(R.id.tv_sciencemore_price)
    TextView tvSciencemorePrice;
    @Bind(R.id.tv_sciencemore_opentime)
    TextView tvSciencemoreOpentime;
    @Bind(R.id.rl_sciencemore_opentime)
    RelativeLayout rlSciencemoreOpentime;
    @Bind(R.id.rl_sciencemore_location)
    RelativeLayout rlSciencemoreLocation;
    @Bind(R.id.rl_sciencemore_voice)
    RelativeLayout rlSciencemoreVoice;


    @Bind(R.id.tv_sciencemore_more)
    TextView tvSciencemoreMore;
    @Bind(R.id.tv_sciencemore_more_line)
    TextView tvSciencemoreMoreLine;
    @Bind(R.id.ll_sciencemore_more)
    LinearLayout llSciencemoreMore;
    @Bind(R.id.tv_sciencemore_arround)
    TextView tvSciencemoreArround;
    @Bind(R.id.tv_sciencemore_arround_line)
    TextView tvSciencemoreArroundLine;
    @Bind(R.id.ll_sciencemore_arround)
    LinearLayout llSciencemoreArround;
    @Bind(R.id.tv_sciencemore_mustknow)
    TextView tvSciencemoreMustknow;
    @Bind(R.id.tv_sciencemore_mustknow_line)
    TextView tvSciencemoreMustknowLine;
    @Bind(R.id.ll_sciencemore_mustknow)
    LinearLayout llSciencemoreMustknow;
    @Bind(R.id.tv_sciencemore_connent)
    TextView tvSciencemoreConnent;
    @Bind(R.id.tv_sciencemore_connent_line)
    TextView tvSciencemoreConnentLine;
    @Bind(R.id.ll_sciencemore_connent)
    LinearLayout llSciencemoreConnent;
    @Bind(R.id.frag_sciencemore)
    FrameLayout fragSciencemore;
    @Bind(R.id.iv_aty_sciencemore_back)
    ImageView ivAtySciencemoreBack;
    @Bind(R.id.tv_sciencemore_buyonline)
    TextView tvSciencemoreBuyonline;
    @Bind(R.id.btn_science_tuji)
    Button btnScienceTuji;
    @Bind(R.id.dingweixiao)
    ImageView dingweixiao;
    @Bind(R.id.yuyinhei)
    ImageView yuyinhei;
    @Bind(R.id.iv_Broadcast)
    ImageView ivBroadcast;
    @Bind(R.id.tv_goodTitle)
    TextView tvGoodTitle;
    @Bind(R.id.ll_title)
    RelativeLayout llTitle;
    @Bind(R.id.sv_science)
    GradationScrollView svScience;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_bottomline)
    TextView tvBottomline;
    // 底部标签切换的Fragment
    private Fragment arroundFragment, commentFragment, moreFragment, mustknowFragment, currentFragment;
    Intent intent = new Intent();
    ScenicSpotParticularsBean bean;
    String id;

    boolean playState = false;    //是否出去播放状态
    SpeechSynthesizer mTts;
    private int height;
    DialogProgressbar dialogProgressbar;
    FindAtlasByVisitorsIdBean findAtlasByVisitorsIdBean = new FindAtlasByVisitorsIdBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sciencemore);
        ButterKnife.bind(this);

        dialogProgressbar = new DialogProgressbar(ScienceMoreActivity.this, R.style.AlertDialogStyle);
        //播报用   APPID需要更换  已改
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=57d8b737");
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getApplicationContext(), null);

        id = getIntent().getStringExtra("id");
        scenicSpotParticulars();
        findAtlasByVisitorsId();
        initTab();
        //上滑渐变
        initListeners();

        ivBack.setAlpha(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTts != null) {
            mTts.pauseSpeaking();
            mTts.destroy();
        }
        if (dialogProgressbar != null) {
            dialogProgressbar.dismiss();
            dialogProgressbar = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick({R.id.btn_science_tuji, R.id.rl_sciencemore_opentime, R.id.rl_sciencemore_location,
            R.id.ll_sciencemore_more,
            R.id.ll_sciencemore_arround, R.id.ll_sciencemore_mustknow, R.id.ll_sciencemore_connent,
            R.id.iv_aty_sciencemore_back, R.id.tv_sciencemore_buyonline, R.id.iv_Broadcast, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_science_tuji://图集
                intent.setClass(getApplicationContext(), Activity_ScenicspotGraphic.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.rl_sciencemore_opentime://开放时间
                StringBuffer stringBuffer = new StringBuffer();
                for (int index = 0; index < bean.getData().getOpenDateList().size(); index++) {
                    stringBuffer.append(bean.getData().getOpenDateList().get(index).getTime_detail() + "\n");
                }
                final Dialog dialog = new Dialog(ScienceMoreActivity.this, R.style.AlertDialogStyle);
                dialog.getWindow().setContentView(R.layout.dialog_opentime);
                TextView tv_opentime = (TextView) dialog.getWindow().findViewById(R.id.tv_opentime);
                tv_opentime.setText(stringBuffer.toString());
                dialog.getWindow().findViewById(R.id.tv_opentime_iknow).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.rl_sciencemore_location://地图导游
                intent.setClass(ScienceMoreActivity.this, MapTwoPointActivity.class);
                intent.putExtra("lat", bean.getData().getLatitude());
                intent.putExtra("lng", bean.getData().getLongitude());
                startActivity(intent);
                break;
            case R.id.ll_sciencemore_more:
                change(tvSciencemoreMore, tvSciencemoreArround, tvSciencemoreMustknow, tvSciencemoreConnent,
                        tvSciencemoreMoreLine, tvSciencemoreArroundLine, tvSciencemoreMustknowLine, tvSciencemoreConnentLine);
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id + "");
                    moreFragment.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), moreFragment);
                break;
            case R.id.ll_sciencemore_arround://周边
                change(tvSciencemoreArround, tvSciencemoreMore, tvSciencemoreMustknow, tvSciencemoreConnent,
                        tvSciencemoreArroundLine, tvSciencemoreMoreLine, tvSciencemoreMustknowLine, tvSciencemoreConnentLine);
                if (arroundFragment == null) {
                    arroundFragment = new ArroundFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), arroundFragment);
                break;
            case R.id.ll_sciencemore_mustknow://须知
                change(tvSciencemoreMustknow, tvSciencemoreMore, tvSciencemoreArround, tvSciencemoreConnent,
                        tvSciencemoreMustknowLine, tvSciencemoreMoreLine, tvSciencemoreArroundLine, tvSciencemoreConnentLine);
                if (mustknowFragment == null) {
                    mustknowFragment = new MustKnowFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", bean.getData().getNotice_id() + "");
                    mustknowFragment.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), mustknowFragment);
                break;
            case R.id.ll_sciencemore_connent://评价
                change(tvSciencemoreConnent, tvSciencemoreMore, tvSciencemoreArround, tvSciencemoreMustknow,
                        tvSciencemoreConnentLine, tvSciencemoreMoreLine, tvSciencemoreArroundLine, tvSciencemoreMustknowLine);
                if (commentFragment == null) {
                    commentFragment = new CommentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", bean.getData().getId() + "");
                    bundle.putString("num", bean.getData().getCommentCount() + "");
                    bundle.putString("good", bean.getData().getSatisfaction());
                    commentFragment.setArguments(bundle);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), commentFragment);
                break;
            case R.id.iv_aty_sciencemore_back:
                finish();
                break;
            case R.id.tv_sciencemore_buyonline://在线预定
                //intent.putExtra("orderDescribe",bean.getData().getDetailText());
                intent.setClass(ScienceMoreActivity.this, SureOrderActivity.class);
                intent.putExtra("visitorsId", bean.getData().getId() + "");
                intent.putExtra("userId", SpUtil.getString(getApplication(), SpName.userId, ""));
                intent.putExtra("price", bean.getData().getNew_price());
                intent.putExtra("name", bean.getData().getName());
                intent.putExtra("deliver_fee", bean.getData().getDeliver_fee() + "");
                startActivity(intent);
                break;
            case R.id.iv_Broadcast://语音播报
                if (playState) {
                    ivBroadcast.setImageResource(R.mipmap.yuyin_kaishi);
                    mTts.pauseSpeaking();
                    playState = false;
                } else {
                    ivBroadcast.setImageResource(R.mipmap.yuyin_zanting);
                    if (mTts.isSpeaking()) {
                        mTts.resumeSpeaking();
                    } else {
                        read();
                    }
                    playState = true;
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    //单击改变布局
    public void change(TextView tv_1, TextView tv_2, TextView tv_3, TextView tv_4, TextView tvd_1, TextView tvd_2, TextView tvd_3, TextView tvd_4) {
        tv_1.setTextColor(Color.parseColor("#4bc4fb"));
        tv_2.setTextColor(Color.parseColor("#000000"));
        tv_3.setTextColor(Color.parseColor("#000000"));
        tv_4.setTextColor(Color.parseColor("#000000"));
        tvd_1.setVisibility(View.VISIBLE);
        tvd_2.setVisibility(View.INVISIBLE);
        tvd_3.setVisibility(View.INVISIBLE);
        tvd_4.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (moreFragment == null) {
            moreFragment = new MoreFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", id + "");
            moreFragment.setArguments(bundle);
        }

        if (!moreFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frag_sciencemore, moreFragment).commit();

            // 记录当前Fragment
            currentFragment = moreFragment;

        }

    }


    /**
     * 添加或�?显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.frag_sciencemore, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }


    //详情
    private void scenicSpotParticulars() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id", id + "");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.scenicSpotParticulars, params,
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
                            bean = new Gson().fromJson(responseInfo.result, ScenicSpotParticularsBean.class);
                            int i = bean.getHeader().getStatus();
                            if (i == 0) {

                                /*BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
                                bitmapUtils.configDefaultLoadFailedImage(R.mipmap.zhanwei);
                                bitmapUtils.display(ivSciencemoreBigpic, bean.getData().getHead_img());*/

                                tvSciencemoreTitle.setText(bean.getData().getName());
                                StringBuffer stringBuffer = new StringBuffer();
                                for (int index = 0; index < bean.getData().getOpenDateList().size(); index++) {
                                    stringBuffer.append(bean.getData().getOpenDateList().get(index).getTime_detail());
                                }
                                tvSciencemoreOpentime.setText("开放时间：" + stringBuffer.toString());
                                tvSciencemorePrice.setText("￥" + bean.getData().getNew_price());
                            } else {
                                ToastUtil.show(getApplicationContext(), bean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(getApplicationContext(), "解析数据出错");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(getApplicationContext(), "连接网络失败");
                    }
                });

    }

    private void findAtlasByVisitorsId() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("visitorsId", id);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.findAtlasByVisitorsId, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            findAtlasByVisitorsIdBean = new Gson().fromJson(responseInfo.result, FindAtlasByVisitorsIdBean.class);
                            int i = findAtlasByVisitorsIdBean.getHeader().getStatus();
                            if (i == 0) {
                                if (findAtlasByVisitorsIdBean.getData().size() == 0 || findAtlasByVisitorsIdBean.getData() == null) {
                                } else {
                                    ImageLoader.getInstance().displayImage(findAtlasByVisitorsIdBean.getData().get(0), ivSciencemoreBigpic);
                                }

                            } else {
                                ToastUtil.show(getApplicationContext(), findAtlasByVisitorsIdBean.getHeader().getMsg());
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

    private void read() {

        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        //设置发音人（更多在线发音人，用户可参见 附录13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //3.开始合成
        mTts.startSpeaking(bean.getData().getDetailText(), mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            if (error == null) {
                playState = false;
                ivBroadcast.setImageResource(R.mipmap.yuyin_kaishi);
            }
        }

        //缓冲进度回调
//percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }


        //播放进度回调
//percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };


    /**
     * 滑动监听
     */
    public void onScrollChanged(GradationScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            llTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            tvBottomline.setBackgroundColor(Color.argb((int) 0, 205, 205, 205));
            tvGoodTitle.setTextColor(Color.argb((int) 0, 51, 51, 51));
            ivBack.setAlpha((int) 0);
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            tvGoodTitle.setTextColor(Color.argb((int) alpha, 51, 51, 51));
            llTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            tvBottomline.setBackgroundColor(Color.argb((int) alpha, 205, 205, 205));
            ivBack.setAlpha((int) alpha);
        } else {    //滑动到banner下面设置普通颜色
            llTitle.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }
    }


    private void initListeners() {

        ViewTreeObserver vto = ivSciencemoreBigpic.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = ivSciencemoreBigpic.getHeight();

                svScience.setScrollViewListener(ScienceMoreActivity.this);
            }
        });
    }
}
