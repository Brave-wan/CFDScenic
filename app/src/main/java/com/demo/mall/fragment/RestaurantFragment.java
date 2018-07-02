package com.demo.mall.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.demo.demo.myapplication.R;
import com.demo.mall.activity.RestaurantActivity;
import com.demo.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 饭店首页
 * 作者：sonnerly on 2016/8/6 0006 17:02
 */
public class RestaurantFragment extends Fragment {
    @Bind(R.id.tv_frag_chosetime_date)
    TextView tvFragChosetimeDate;
    @Bind(R.id.tv_frag_chosetime_time)
    TextView tvFragChosetimeTime;
    @Bind(R.id.tv_frag_chosetime_query)
    TextView tvFragChosetimeQuery;
    /**
     * 年变量
     */
    private int year;
    /**
     * 月份变量
     */
    private int month;
    /**
     * 天数变量
     */
    private int day;
    /**
     * 小时变量
     */
    private int hour;
    /**
     * 分钟变量
     */
    private int minute;
    /**
     * 日期选择器
     */
    private DatePicker date;
    /**
     * 时间选择器
     */
    private TimePicker time;
    Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mall_fragment_chosetime, container,
                false);
        ButterKnife.bind(this, view);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_frag_chosetime_date, R.id.tv_frag_chosetime_time, R.id.tv_frag_chosetime_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_frag_chosetime_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int years,
                                                  int monthOfYear, int dayOfMonth) {
                                tvFragChosetimeDate.setText(years + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                        String monthStr = monthOfYear+1>=10?monthOfYear+1+"":"0"+(monthOfYear+1);
                                //获得更改时间后的数据
//                        getlist(years + "-" + monthStr + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

                break;
            case R.id.tv_frag_chosetime_time:
                new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvFragChosetimeTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true).show();


                break;
            case R.id.tv_frag_chosetime_query:
                if (tvFragChosetimeDate.getText().toString().equals("请选择就餐日期")) {
                    ToastUtil.show(getContext(), "请选择就餐日期");
                    return;
                }
                if (tvFragChosetimeTime.getText().toString().equals("请选择就餐时间")) {
                    ToastUtil.show(getContext(), "请选择就餐时间");
                    return;
                }
                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                intent.putExtra("date", tvFragChosetimeDate.getText().toString());
                intent.putExtra("time", tvFragChosetimeTime.getText().toString());
                startActivity(intent);
                break;
        }
    }


}
