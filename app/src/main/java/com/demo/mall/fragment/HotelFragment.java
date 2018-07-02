package com.demo.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.HotelActivity;
import com.demo.utils.ToastUtil;
import com.demo.view.DoubleDatePickerDialog;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：sonnerly on 2016/8/6 0006 17:01
 */
public class HotelFragment extends Fragment {
    @Bind(R.id.tv_frag_chosedate_ruzhu)
    TextView tvFragChosedateRuzhu;
    @Bind(R.id.tv_frag_chosedate_likai)
    TextView tvFragChosedateLikai;
    @Bind(R.id.tv_frag_chosedate_total)
    TextView tvFragChosedateTotal;
    @Bind(R.id.iv_frag_chosedate_jiantou)
    RelativeLayout ivFragChosedateJiantou;
    @Bind(R.id.tv_frag_chosedate_query)
    TextView tvFragChosedateQuery;
    public static String start;
    public static String end;
    String dates;//当天日期
    long m_intervalday = 1;//初始化时间间隔的值为0

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mall_fragment_chosedate, container,
                false);
        ButterKnife.bind(this, view);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dates = sDateFormat.format(new java.util.Date());
        tvFragChosedateRuzhu.setText(dates);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        tvFragChosedateLikai.setText(sf.format(c.getTime()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_frag_chosedate_jiantou, R.id.tv_frag_chosedate_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_frag_chosedate_jiantou:
                Calendar start_calendar = null;
                Calendar end_calendar = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date start_date = sdf.parse(tvFragChosedateRuzhu.getText().toString());
                    start_calendar = Calendar.getInstance();
                    start_calendar.setTime(start_date);

                    Date end_date=sdf.parse(tvFragChosedateLikai.getText().toString());
                    end_calendar = Calendar.getInstance();
                    end_calendar.setTime(end_date);
                } catch (Exception e) {
                }

                int start_year = start_calendar.get(Calendar.YEAR);
                int start_month = start_calendar.get(Calendar.MONTH);
                int start_day = start_calendar.get(Calendar.DAY_OF_MONTH);

                int end_year = end_calendar.get(Calendar.YEAR);
                int end_month = end_calendar.get(Calendar.MONTH);
                int end_day = end_calendar.get(Calendar.DAY_OF_MONTH);


                Calendar c = Calendar.getInstance();
                DoubleDatePickerDialog doubleDatePickerDialog = new DoubleDatePickerDialog(getActivity(), 0, new DoubleDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear,
                                          int endMonthOfYear, int endDayOfMonth) {
                        start = String.format(startYear + "-" + (startMonthOfYear + 1) + "-" + startDayOfMonth);
                        end = String.format(endYear + "-" + (endMonthOfYear + 1) + "-" + endDayOfMonth);
                        SimpleDateFormat m_simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            m_intervalday = 0;
                            //当天日期
                            Date date = m_simpledateformat.parse(dates);
                            //创建两个Date对象
                            Date date1 = m_simpledateformat.parse(start);
                            Date date2 = m_simpledateformat.parse(end);
                            m_intervalday = date2.getTime() - date1.getTime();//计算所得为微秒数
                            m_intervalday = m_intervalday / 1000 / 60 / 60 / 24;//计算所得的天数

                            tvFragChosedateRuzhu.setText(start);
                            tvFragChosedateLikai.setText(end);
                            tvFragChosedateTotal.setText("共 " + m_intervalday + " 晚");
                            //days=(int)m_intervalday;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, start_year, start_month, start_day, true);
                doubleDatePickerDialog.setStartMinDate(true, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
                doubleDatePickerDialog.setEndMinDate(true, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE) + 1);

                doubleDatePickerDialog.updateEndDate(end_year,end_month,end_day);
                doubleDatePickerDialog.show();
                break;
            case R.id.tv_frag_chosedate_query:
                Intent intent = new Intent(getActivity(), HotelActivity.class);
                intent.putExtra("start", tvFragChosedateRuzhu.getText().toString());
                intent.putExtra("end", tvFragChosedateLikai.getText().toString());
                intent.putExtra("total", m_intervalday + "");
                startActivity(intent);
                break;
        }
    }
}
