/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.demo.view;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.NumberPicker;

import com.demo.demo.myapplication.R;

/**
 * A simple dialog containing an {@link DatePicker}.
 * <p>
 * <p>
 * See the <a href="{@docRoot}guide/topics/ui/controls/pickers.html">Pickers</a>
 * guide.
 * </p>
 */
public class DoubleDatePickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {

    private static final String START_YEAR = "start_year";
    private static final String END_YEAR = "end_year";
    private static final String START_MONTH = "start_month";
    private static final String END_MONTH = "end_month";
    private static final String START_DAY = "start_day";
    private static final String END_DAY = "end_day";

    //设置开始最小日期
    private boolean start_min_boolean = false;
    private int start_min_year = 0;
    private int start_min_month = 0;
    private int start_min_day = 0;
    //设置结束最小日期
    private boolean end_min_boolean = false;
    private int end_min_year = 0;
    private int end_min_month = 0;
    private int end_min_day = 0;

    private final DatePicker mDatePicker_start;
    private final DatePicker mDatePicker_end;
    private final OnDateSetListener mCallBack;

    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListener {

        /**
         * //* @param view
         * The view associated with this listener.
         * // * @param year
         * The year that was set.
         * // * @param monthOfYear
         * The month that was set (0-11) for compatibility with
         * {@link java.util.Calendar}.
         * //* @param dayOfMonth
         * The day of the month that was set.
         */
        void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth,
                       DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth);
    }

    /**
     * @param context     The context the dialog is to run in.
     * @param callBack    How the parent is notified that the date is set.
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth  The initial day of the dialog.
     */
    public DoubleDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        this(context, 0, callBack, year, monthOfYear, dayOfMonth);
    }

    public DoubleDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,
                                  int dayOfMonth) {
        this(context, 0, callBack, year, monthOfYear, dayOfMonth, true);
    }

    /**
     * @param context     The context the dialog is to run in.
     * @param theme       the theme to apply to this dialog
     * @param callBack    How the parent is notified that the date is set.
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth  The initial day of the dialog.
     */
    public DoubleDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,
                                  int dayOfMonth, boolean isDayVisible) {
        super(context, theme);

        mCallBack = callBack;

        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, "确 定", this);
        setButton(BUTTON_NEGATIVE, "取 消", this);
        // setButton(BUTTON_POSITIVE,
        // themeContext.getText(android.R.string.date_time_done), this);
        setIcon(0);

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker_dialog, null);
        setView(view);
        mDatePicker_start = (DatePicker) view.findViewById(R.id.datePickerStart);
        mDatePicker_end = (DatePicker) view.findViewById(R.id.datePickerEnd);

        mDatePicker_start.init(year, monthOfYear, dayOfMonth, this);
        mDatePicker_end.init(year, monthOfYear, dayOfMonth+1, this);

        // updateTitle(year, monthOfYear, dayOfMonth);

        // 如果要隐藏当前日期，则使用下面方法。
        if (!isDayVisible) {
            hidDay(mDatePicker_start);
            hidDay(mDatePicker_end);
        }
    }


    /**
     * 隐藏DatePicker中的日期显示
     *
     * @param mDatePicker
     */

    private void hidDay(DatePicker mDatePicker) {
        Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
        for (Field datePickerField : datePickerfFields) {
            if ("mDaySpinner".equals(datePickerField.getName())) {
                datePickerField.setAccessible(true);
                Object dayPicker = new Object();
                try {
                    dayPicker = datePickerField.get(mDatePicker);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                // datePicker.getCalendarView().setVisibility(View.GONE);
                ((View) dayPicker).setVisibility(View.GONE);
            }
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        // Log.d(this.getClass().getSimpleName(), String.format("which:%d",
        // which));
        // 如果是“取 消”按钮，则返回，如果是“确 定”按钮，则往下执行
        if (which == BUTTON_POSITIVE)
            tryNotifyDateSet();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        if (view.getId() == R.id.datePickerStart) {
            //判断是否判断显示最小
            if (start_min_boolean) {
                if (year < start_min_year) {
                    mDatePicker_start.init(start_min_year, start_min_month - 1, start_min_day, this);
                    return;
                }
                if (month < start_min_month - 1) {
                    if (year == start_min_year) {
                        mDatePicker_start.init(start_min_year, start_min_month - 1, start_min_day, this);
                        return;
                    }
                }
                if (day < start_min_day) {
                    if (month == start_min_month - 1) {
                        if (year == start_min_year) {
                            mDatePicker_start.init(start_min_year, start_min_month - 1, start_min_day, this);
                            return;
                        }
                    }
                }
            }
            mDatePicker_start.init(year, month, day, this);
        }

        if (view.getId() == R.id.datePickerEnd) {
            //判断是否是最小
            if (end_min_boolean) {
                if (year < end_min_year) {
                    mDatePicker_end.init(end_min_year, end_min_month - 1, end_min_day, this);
                    return;
                }
                if (month < end_min_month - 1) {
                    if (year == end_min_year) {
                        mDatePicker_end.init(end_min_year, end_min_month - 1, end_min_day, this);
                        return;
                    }
                }
                if (day < end_min_day) {
                    if (month == end_min_month - 1) {
                        if (year == end_min_year) {
                            mDatePicker_end.init(end_min_year, end_min_month - 1, end_min_day, this);
                            return;
                        }
                    }
                }
            }
            mDatePicker_end.init(year, month, day, this);
        }

        // updateTitle(year, month, day);
    }


    //设置开始日期最小的
    public void setStartMinDate(boolean setStartB, int startMinYear, int startMinMonth, int startMinDay) {
        start_min_boolean = setStartB;
        start_min_year = startMinYear;
        start_min_month = startMinMonth;
        start_min_day = startMinDay;
    }

    //设置开始日期最大的
    public void setStartMaxDate(int startMaxYear, int startMaxMonth, int startMaxDay) {
        Date date = new Date(startMaxYear, startMaxMonth, startMaxDay);
        mDatePicker_start.setMaxDate(date.getTime());
    }

    //设置结束日期最小的
    public void setEndMinDate(boolean setEndB, int endMinYear, int endMinMonth, int endMinDay) {
        end_min_boolean = setEndB;
        end_min_year = endMinYear;
        end_min_month = endMinMonth;
        end_min_day = endMinDay;
    }

    /**
     * 获得开始日期的DatePicker
     *
     * @return The calendar view.
     */
    public DatePicker getDatePickerStart() {
        return mDatePicker_start;
    }

    /**
     * 获得结束日期的DatePicker
     *
     * @return The calendar view.
     */
    public DatePicker getDatePickerEnd() {
        return mDatePicker_end;
    }

    /**
     * Sets the start date.
     *
     * @param year        The date year.
     * @param monthOfYear The date month.
     * @param dayOfMonth  The date day of month.
     */
    public void updateStartDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker_start.updateDate(year, monthOfYear, dayOfMonth);
    }

    /**
     * Sets the end date.
     *
     * @param year        The date year.
     * @param monthOfYear The date month.
     * @param dayOfMonth  The date day of month.
     */
    public void updateEndDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker_end.updateDate(year, monthOfYear, dayOfMonth);
    }

    private void tryNotifyDateSet() {
        if (mCallBack != null) {
            mDatePicker_start.clearFocus();
            mDatePicker_end.clearFocus();
            mCallBack.onDateSet(mDatePicker_start, mDatePicker_start.getYear(), mDatePicker_start.getMonth(),
                    mDatePicker_start.getDayOfMonth(), mDatePicker_end, mDatePicker_end.getYear(),
                    mDatePicker_end.getMonth(), mDatePicker_end.getDayOfMonth());
        }
    }

    @Override
    protected void onStop() {
        // tryNotifyDateSet();
        super.onStop();
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(START_YEAR, mDatePicker_start.getYear());
        state.putInt(START_MONTH, mDatePicker_start.getMonth());
        state.putInt(START_DAY, mDatePicker_start.getDayOfMonth());
        state.putInt(END_YEAR, mDatePicker_end.getYear());
        state.putInt(END_MONTH, mDatePicker_end.getMonth());
        state.putInt(END_DAY, mDatePicker_end.getDayOfMonth());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int start_year = savedInstanceState.getInt(START_YEAR);
        int start_month = savedInstanceState.getInt(START_MONTH);
        int start_day = savedInstanceState.getInt(START_DAY);
        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        mDatePicker_start.init(start_year, start_month, start_day, this);

        int end_year = savedInstanceState.getInt(END_YEAR);
        int end_month = savedInstanceState.getInt(END_MONTH);
        int end_day = savedInstanceState.getInt(END_DAY);
        mDatePicker_end.init(end_year, end_month, end_day, this);

    }
}
