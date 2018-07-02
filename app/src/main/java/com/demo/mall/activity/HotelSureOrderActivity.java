package com.demo.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.bean.AlipayBean;
import com.demo.fragment.MainActivity;
import com.demo.mall.bean.SaveHotelOrderBean;
import com.demo.utils.SpName;
import com.demo.utils.SpUtil;
import com.demo.utils.StringUtil;
import com.demo.utils.ToastUtil;
import com.demo.utils.URL;
import com.demo.view.DialogProgressbar;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.demo.demo.myapplication.R;
import com.demo.view.DialogChoseRoom;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/9 0009 14:09
 */
public class HotelSureOrderActivity extends Activity {
    @Bind(R.id.iv_aty_hotelsureorder_img)
    ImageView ivAtyHotelsureorderImg;
    @Bind(R.id.tv_aty_hotelsureorder_type)
    TextView tvAtyHotelsureorderType;
    @Bind(R.id.tv_aty_hotelsureorder_ruzhu)
    TextView tvAtyHotelsureorderRuzhu;
    @Bind(R.id.tv_aty_hotelsureorder_likai)
    TextView tvAtyHotelsureorderLikai;
    @Bind(R.id.tv_aty_hotelsureorder_num)
    TextView tvAtyHotelsureorderNum;
    @Bind(R.id.tv_aty_hotelsureorder_price)
    TextView tvAtyHotelsureorderPrice;
    @Bind(R.id.tv_aty_hotelsureorder_pricebefore)
    TextView tvAtyHotelsureorderPricebefore;
    @Bind(R.id.tv_aty_hotelsureorder_roomnum)
    public TextView tvAtyHotelsureorderRoomnum;

    @Bind(R.id.et_aty_hotelsureorder_phone)
    EditText etAtyHotelsureorderPhone;
    @Bind(R.id.tv_aty_hotelsureorder_total)
    public TextView tvAtyHotelsureorderTotal;
    @Bind(R.id.tv_aty_hotelsureorder_sumit)
    TextView tvAtyHotelsureorderSumit;
    @Bind(R.id.tv_aty_hotelsureorder_title)//酒店名称
            TextView tvAtyHotelsureorderTitle;
    @Bind(R.id.et_aty_hotelsureorder_name1)//第一个入住人
            EditText etAtyHotelsureorderName1;
    @Bind(R.id.et_aty_hotelsureorder_name2)//第二个
            EditText etAtyHotelsureorderName2;
    @Bind(R.id.ll_aty_hotelsureorder2)
    public LinearLayout llAtyHotelsureorder2;
    @Bind(R.id.et_aty_hotelsureorder_name3)//第三个
            EditText etAtyHotelsureorderName3;
    @Bind(R.id.ll_aty_hotelsureorder3)
    public LinearLayout llAtyHotelsureorder3;
    @Bind(R.id.et_aty_hotelsureorder_name4)//第四个
            EditText etAtyHotelsureorderName4;
    @Bind(R.id.ll_aty_hotelsureorder4)
    public LinearLayout llAtyHotelsureorder4;
    @Bind(R.id.et_aty_hotelsureorder_name5)//第五个
            EditText etAtyHotelsureorderName5;
    @Bind(R.id.ll_aty_hotelsureorder5)
    public LinearLayout llAtyHotelsureorder5;
    @Bind(R.id.et_aty_hotelsureorder_name6)//第六个
            EditText etAtyHotelsureorderName6;
    @Bind(R.id.ll_aty_hotelsureorder6)
    public LinearLayout llAtyHotelsureorder6;
    @Bind(R.id.et_aty_hotelsureorder_name7)
    EditText etAtyHotelsureorderName7;
    @Bind(R.id.ll_aty_hotelsureorder7)
    public LinearLayout llAtyHotelsureorder7;
    @Bind(R.id.et_aty_hotelsureorder_name8)
    EditText etAtyHotelsureorderName8;
    @Bind(R.id.ll_aty_hotelsureorder8)
    public LinearLayout llAtyHotelsureorder8;

    DialogChoseRoom dialogChoseRoom;
    ArrayList<EditText> textList = new ArrayList<>();
    String price;
    String start;
    String end;
    String shopInformationId="";
    public double zong;
    public int num = 1;//房间数
    public int xianjia;
    public int total;//几晚
    AlipayBean alipayBean=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_hotelsureorder);
        ButterKnife.bind(this);
        alipayBean=new AlipayBean();

        init();
    }

    private void init() {
        shopInformationId = getIntent().getStringExtra("shopInformationId");

        tvAtyHotelsureorderTitle.setText(getIntent().getStringExtra("jiuName"));
        tvAtyHotelsureorderType.setText(getIntent().getStringExtra("fangName"));
        start = getIntent().getStringExtra("start");
        tvAtyHotelsureorderRuzhu.setText("入住：" + start.substring(5));
        end = getIntent().getStringExtra("end");
        tvAtyHotelsureorderLikai.setText("离店：" + end.substring(5));
        total = Integer.parseInt(getIntent().getStringExtra("total"));
        tvAtyHotelsureorderNum.setText(total + "晚");
        xianjia = Integer.parseInt(getIntent().getStringExtra("xianjia"));
        tvAtyHotelsureorderPrice.setText("￥" + xianjia);
        zong = total * xianjia * num;
        tvAtyHotelsureorderTotal.setText("￥" + zong);

        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("image"), ivAtyHotelsureorderImg);
        getAlipay();
        //EditText集合
        textList.add(etAtyHotelsureorderName1);
        textList.add(etAtyHotelsureorderName2);
        textList.add(etAtyHotelsureorderName3);
        textList.add(etAtyHotelsureorderName4);
        textList.add(etAtyHotelsureorderName5);
        textList.add(etAtyHotelsureorderName6);
        textList.add(etAtyHotelsureorderName7);
        textList.add(etAtyHotelsureorderName8);
    }
    private void getAlipay() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("siId",getIntent().getStringExtra("shopInformationId"));
        HttpUtils http = new HttpUtils();
        http.configResponseTextCharset(HTTP.UTF_8);
        http.configCurrentHttpCacheExpiry(0*1000);
        http.configTimeout(15 * 1000);// 连接超时  //指的是连接一个url的连接等待时间。
        http.configSoTimeout(15 * 1000);// 获取数据超时  //指的是连接上一个url，获取response的返回等待时间
        http.send(HttpRequest.HttpMethod.GET, URL.getAlipay, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("1111", responseInfo.result);
                        try {
                            alipayBean= new Gson().fromJson(responseInfo.result, AlipayBean.class);
                            if(alipayBean.getHeader().getStatus()==0){

                            }else if(alipayBean.getHeader().getStatus()==3){

                            }else {
                                ToastUtil.show(HotelSureOrderActivity.this, alipayBean.getHeader().getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.show(HotelSureOrderActivity.this,"解析数据错误");
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show(HotelSureOrderActivity.this, "连接网络失败");
                    }
                });

    }

    @OnClick({R.id.rl_hotelsureorder, R.id.tv_aty_hotelsureorder_sumit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_hotelsureorder:

                dialogChoseRoom = new DialogChoseRoom(HotelSureOrderActivity.this, R.style.AlertDialogStyle, num);
                dialogChoseRoom.show();

                zong = total * xianjia * dialogChoseRoom.number;
                tvAtyHotelsureorderTotal.setText("￥" + zong);
                break;
            case R.id.tv_aty_hotelsureorder_sumit:
                int stock=getIntent().getIntExtra("stock",-1);
                if (stock==-1){
                    ToastUtil.show(getApplicationContext(), "错误");
                    return;
                }
                if (num>stock){
                    ToastUtil.show(getApplicationContext(), "房间不足，仅剩"+stock+"间");
                    return;
                }
                for (int i = 0; i < num; i++) {
                    if (textList.get(i).getText().toString().equals("")) {
                        ToastUtil.show(getApplicationContext(), "入住人姓名不能为空");
                        return;
                    }
                    if (!StringUtil.isChinese(textList.get(i).getText().toString())) {
                        ToastUtil.show(getApplicationContext(), "入住人姓名有误");
                        return;
                    }
                }
                if (etAtyHotelsureorderPhone.getText().toString().equals("")) {
                    ToastUtil.show(getApplicationContext(), "手机号不能为空");
                    return;
                }
                if (etAtyHotelsureorderPhone.getText().toString().length() != 11) {
                    ToastUtil.show(getApplicationContext(), "手机号格式错误");
                    return;
                }
                if (!StringUtil.checkPhone(etAtyHotelsureorderPhone.getText().toString())) {
                    ToastUtil.show(getApplicationContext(), "手机号格式错误");
                    return;
                }

                saveHotelOrder();
                break;
        }
    }

    private void saveHotelOrder() {
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", SpUtil.getString(getApplication(), SpName.token, ""));
        params.addQueryStringParameter("name", tvAtyHotelsureorderTitle.getText().toString());
        params.addQueryStringParameter("orderDescribe", getIntent().getStringExtra("fangmiaoshu"));
        params.addQueryStringParameter("price", getIntent().getStringExtra("xianjia"));
        params.addQueryStringParameter("startDate", start);
        params.addQueryStringParameter("endDate", end);
        params.addQueryStringParameter("quantity", num + "");
        params.addQueryStringParameter("shopInformationId", shopInformationId);
        params.addQueryStringParameter("checkDays", total + "");
        params.addQueryStringParameter("realPrice", zong + "");
        params.addQueryStringParameter("goodsId", getIntent().getStringExtra("goodsId") + "");
        params.addQueryStringParameter("telphone", etAtyHotelsureorderPhone.getText().toString());

        StringBuffer stringBuffer = new StringBuffer();
        final ArrayList<String> stringList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            stringList.add(textList.get(i).getText().toString());
            stringBuffer.append(textList.get(i).getText().toString() + ",");
        }
        String personName = stringBuffer.toString();

        params.addQueryStringParameter("personName", personName.substring(0, personName.length() - 1));
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(0 * 1000);
        http.send(HttpRequest.HttpMethod.GET, URL.saveHotelOrder, params, new RequestCallBack<String>() {
            DialogProgressbar dialogProgressbar = new DialogProgressbar(HotelSureOrderActivity.this, R.style.AlertDialogStyle);

            @Override
            public void onStart() {
                super.onStart();
                dialogProgressbar.setCancelable(false);//点击对话框意外的地方不关闭  把返回键禁止了
                dialogProgressbar.show();
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dialogProgressbar.dismiss();
                try {
                    SaveHotelOrderBean saveHotelOrder = new Gson().fromJson(responseInfo.result, SaveHotelOrderBean.class);
                    int i = saveHotelOrder.getHeader().getStatus();
                    if (i == 0) {
                        Intent intent = new Intent(getApplicationContext(), ChoossePaymentJdActivity.class);
                        intent.putStringArrayListExtra("StringList", stringList);
                        intent.putExtra("id", saveHotelOrder.getData().getId() + "");
                        intent.putExtra("orderCode", saveHotelOrder.getData().getOrderCode() + "");
                        if(alipayBean.getData()==null||alipayBean.getData().equals("")){
                           // ToastUtil.show(HotelSureOrderActivity.this,"支付宝暂时无法支付");
                            startActivity(intent);
                        }else{
                            intent.putExtra("partner",alipayBean.getData().getPartner());
                            intent.putExtra("seller",alipayBean.getData().getSeller());
                            intent.putExtra("privateKey",alipayBean.getData().getPrivateKey());
                            startActivity(intent);
                        }
                        finish();
                    } else if (i==3){
                        //异地登录对话框，必须传.this  不能传Context
                        MainActivity.state_Three(HotelSureOrderActivity.this);
                    }else {
                        ToastUtil.show(getApplicationContext(), "输入有误");
                    }
                } catch (Exception e) {
                    ToastUtil.show(getApplicationContext(), "解析数据错误");
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                ToastUtil.show(getApplicationContext(), "连接网络出错");
            }
        });
    }


    private boolean strings(String txt) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(txt);
        if (m.matches()) {
            //Toast.makeText(Main.this, "输入的是数字", Toast.LENGTH_SHORT).show();
            return false;
        }
       /* p = Pattern.compile("[a-zA-Z]");
        m = p.matcher(txt);
        if (m.matches()) {
            Toast.makeText(Main.this, "输入的是字母", Toast.LENGTH_SHORT).show();
        }*/
        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(txt);
        if (m.matches()) {
            //Toast.makeText(Main.this, "输入的是汉字", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    public static boolean isContainsChinese(String str) {
        String regEx = "[u4e00-u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }
}
