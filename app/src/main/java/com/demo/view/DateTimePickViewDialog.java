package com.demo.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.demo.demo.myapplication.R;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

/**
 * @description 选取日期对话框
 * @author wastrel
 */
public class DateTimePickViewDialog {

	private int YEAR_MIN;
	private int YEAR_MAX;

	private AlertDialog ad;
	private TextView textdate;
//	TextView  texttime;
	private String title;
	private String yearText, monthText, dayText;
	//, hourText, minuteText
	// 年月日数据
	private List<String> datayear;
	private List<String> datamonth;
	private List<String> dataday31, dataday30, dataday29, dataday28;
	private List<String> datahour, dataminute;
	// 日期分隔符
	private String separator;
	// 选取控件定义
	private PickerView year, month, day;
//, hour, minute
	/**
	 *
	 * @param context
	 *            上下文环境
	 * @param text1
	 *            显示数据的textview
	 * @param title
	 *            标题
	 * @param date
	 *            当前日期
	 * @param separator
	 *            日期分割符
	 */
	public DateTimePickViewDialog(Context context, TextView text1, String title, Calendar date,
			String separator) {
		ad = new AlertDialog.Builder(context).create();
		this.textdate = text1;
//		this.texttime = text2;
		this.title = title;
		yearText = String.valueOf(date.get(Calendar.YEAR));
		monthText = addZero(String.valueOf(date.get(Calendar.MONTH) + 1));

		dayText = addZero(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
//		hourText = addZero(String.valueOf(date.get(Calendar.HOUR_OF_DAY)));
//		minuteText = addZero(String.valueOf(date.get(Calendar.MINUTE)));
		this.separator = separator;
		SetYearRange(1900, 2100);
		initDate();
	}
	
	
//	Reservationsecondlistviewadpter adpter;
//	public DateTimePickViewDialog(Context context, TextView text1, String title, Calendar date,
//			String separator) {
//		ad = new AlertDialog.Builder(context).create();
////		this.adpter=adpter;
//		this.textdate = text1;
////		this.texttime = text2;
//		this.title = title;
//		yearText = String.valueOf(date.get(Calendar.YEAR));
//		monthText = addZero(String.valueOf(date.get(Calendar.MONTH) + 1));
//
//		dayText = addZero(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
//		hourText = addZero(String.valueOf(date.get(Calendar.HOUR_OF_DAY)));
//		minuteText = addZero(String.valueOf(date.get(Calendar.MINUTE)));
//		this.separator = separator;
//		SetYearRange(1900, 2100);
//		initDate();
//	}
//	
	

	private String addZero(String str) {
		if (str.length() < 2) {
			return str = "0" + str;
		}
		return str;
	}

	public void dismiss() {
		ad.dismiss();
//		if(adpter!=null)
//			adpter.notifyDataSetChanged();
	}

	/**
	 * @description 初始化年月日数据
	 *
	 */
	private void initDate() {
		datayear = new ArrayList<String>();
		for (int i = YEAR_MIN; i <= YEAR_MAX; i++) {
			datayear.add(String.valueOf(i));
		}
		datamonth = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			if (i < 10) {
				datamonth.add("0" + i);
			} else
				datamonth.add(String.valueOf(i));
		}
		dataday31 = new ArrayList<String>();
		dataday30 = new ArrayList<String>();
		dataday29 = new ArrayList<String>();
		dataday28 = new ArrayList<String>();
		for (int i = 1; i <= 31; i++) {
			if (i < 10) {
				dataday28.add("0" + i);
				dataday29.add("0" + i);
				dataday30.add("0" + i);
				dataday31.add("0" + i);
				continue;
			}
			if (i <= 28) {
				dataday28.add(String.valueOf(i));
				dataday29.add(String.valueOf(i));
				dataday30.add(String.valueOf(i));
				dataday31.add(String.valueOf(i));
				continue;
			}
			if (i == 29) {
				dataday29.add(String.valueOf(i));
				dataday30.add(String.valueOf(i));
				dataday31.add(String.valueOf(i));
				continue;
			}
			if (i == 30) {
				dataday30.add(String.valueOf(i));
				dataday31.add(String.valueOf(i));
				continue;
			}
			if (i == 31) {
				dataday31.add(String.valueOf(i));
			}
		}
		dataminute = new ArrayList<String>();
		for (int i = 0; i < 60; i++) {
			if (i < 10) {
				dataminute.add("0" + i);
			} else
				dataminute.add(String.valueOf(i));
		}
		datahour = new ArrayList<String>();
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				datahour.add("0" + i);
			} else
				datahour.add(String.valueOf(i));
		}

	}

	// 当年月选取改变时，动态填充天数
	private List<String> SelectChange() {
		if (monthText.equals("01") || monthText.equals("03") || monthText.equals("05") || monthText.equals("07")
				|| monthText.equals("08") || monthText.equals("10") || monthText.equals("12")) {
			return dataday31;
		} else if (monthText.equals("04") || monthText.equals("06") || monthText.equals("11")
				|| monthText.equals("09")) {
			return dataday30;
		} else {
			int y = Integer.valueOf(yearText);
			if (y % 4 == 0) {
				if (y % 100 != 0) {
					return dataday29;
				} else if (y % 100 == 0 && y % 400 != 0) {
					return dataday28;
				} else {
					return dataday29;
				}
			} else {
				return dataday28;
			}
		}
	}

	private void setDataDay(List<String> dataday, String select) {
		// 如果前一个月份选择的日期超出了现有日期，则取本月最大日期
		if (Integer.valueOf(select) > dataday.size()) {
			select = String.valueOf(dataday.size());
		}
		day.setData(dataday);
		day.setSelected(select);
	}

	// 设置选取年份上下限
	public void SetYearRange(int YEAR_MIN, int YEAR_MAX) {
		this.YEAR_MIN = YEAR_MIN;
		this.YEAR_MAX = YEAR_MAX;
	}

	public void show() {
		ad.show();
		Window window = ad.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setContentView(R.layout.dialog_select_datetime);
		window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// 初始值
		// t=data.get(1);
		// 设置标题
		TextView titleTextView = (TextView) window.findViewById(R.id.title);

		titleTextView.setText(title);

		year = (PickerView) window.findViewById(R.id.pickerview_year);
		year.setData(datayear);
		year.setSelected(yearText);
		year.setmSuffix("年");
		month = (PickerView) window.findViewById(R.id.pickerview_month);
		month.setData(datamonth);
		month.setSelected(monthText);
		month.setmSuffix("月");

		day = (PickerView) window.findViewById(R.id.pickerview_day);
		setDataDay(SelectChange(), dayText);
		// fullPickerView.setData(data);
		TextView btn_1 = (TextView) window.findViewById(R.id.pickerview_cancel);
		TextView btn_2 = (TextView) window.findViewById(R.id.pickerview_sure);
		btn_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ad.dismiss();

			}
		});
		day.setmSuffix("日");

//		hour = (PickerView) window.findViewById(R.id.pickerview_h);
//		hour.setData(datahour);
//		hour.setSelected(hourText);
//		hour.setmSuffix("时");
//		minute = (PickerView) window.findViewById(R.id.pickerview_m);
//		minute.setData(dataminute);
//		minute.setSelected(minuteText);
//		minute.setmSuffix("分");
		btn_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String datestr = yearText + separator + monthText + separator + dayText;
				
				textdate.setTextColor(Color.rgb(47, 79, 79));
				textdate.setGravity(Gravity.LEFT);
//				String timestr = hourText + ":" + minuteText;
//				texttime.setText(timestr);
//				texttime.setTextColor(Color.rgb(47, 79, 79));
//				texttime.setGravity(Gravity.LEFT);
				textdate.setText(datestr+" ");//+timestr
				dismiss();

			}
		});

		year.setOnSelectListener(new PickerView.onSelectListener() {

			@Override
			public void onSelect(String text) {
				yearText = text;
				setDataDay(SelectChange(), dayText);
			}
		});
		month.setOnSelectListener(new PickerView.onSelectListener() {

			@Override
			public void onSelect(String text) {
				monthText = text;
				setDataDay(SelectChange(), dayText);
			}
		});
		day.setOnSelectListener(new PickerView.onSelectListener() {

			@Override
			public void onSelect(String text) {
				dayText = text;

			}
		});

//		hour.setOnSelectListener(new PickerView.onSelectListener() {
//
//			@Override
//			public void onSelect(String text) {
//				hourText = text;
//
//			}
//		});
//		minute.setOnSelectListener(new PickerView.onSelectListener() {
//
//			@Override
//			public void onSelect(String text) {
//				minuteText = text;
//
//			}
//		});

	}
}
