package com.illidan.dhunt.view;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import com.illidan.dhunt.R;

import java.util.Calendar;

/**
 * @author illidantao
 * @date 2016/6/27 15:18
 */
public class YearDatePicker extends FrameLayout {

    private final NumberPicker mYearSpinner;
    private final NumberPicker mMonthSpinner;
    private final NumberPicker mDaySpinner;
    private Calendar mCurrentDate;;
    private int year, month, dayOfMonth;
    private String[] mDateDisplayValues = new String[7];
    private OnDateSetListener mOnYearDateChangedListener;

    public YearDatePicker(Context context, long time) {
        super(context);
        /*
         *獲取系統時間
         */
        mCurrentDate = Calendar.getInstance();
        mCurrentDate.setTimeInMillis(time);
        year = mCurrentDate.get(Calendar.YEAR);
        month = mCurrentDate.get(Calendar.MONTH);
        dayOfMonth = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        /**
         * 加载布局
         */
        inflate(context, R.layout.ll_date_pick, this);
        /**
         * 初始化控件
         */
        mYearSpinner = (NumberPicker) this.findViewById(R.id.np_year);
        mYearSpinner.setMinValue(1900);
        mYearSpinner.setMaxValue(3000);
        mYearSpinner.setValue(year);
        mYearSpinner.setOnValueChangedListener(mOnYearChangedListener);

        mMonthSpinner = (NumberPicker) this.findViewById(R.id.np_month);
        mMonthSpinner.setMaxValue(11);
        mMonthSpinner.setMinValue(0);
        mMonthSpinner.setValue(month);
        mMonthSpinner.setOnValueChangedListener(mOnMonthChangedListener);

        mDaySpinner = (NumberPicker) this.findViewById(R.id.np_day);
        mDaySpinner.setMaxValue(30);
        mDaySpinner.setMinValue(0);
        mDaySpinner.setValue(dayOfMonth);
        mDaySpinner.setOnValueChangedListener(mOnDayChangedListener);
        updateDateControl();
    }
    /**
     *
     * 控件监听器
     */
    private NumberPicker.OnValueChangeListener mOnYearChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            year = mYearSpinner.getValue();
            mCurrentDate.set(Calendar.DAY_OF_MONTH, year);
            /**
             * 更新日期
             */
            updateDateControl();
            /**
             * 给接口传值
             */
            onDateTimeChanged();
        }
    };

    private NumberPicker.OnValueChangeListener mOnMonthChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            month = mMonthSpinner.getValue();
            mCurrentDate.set(Calendar.MONTH,month);
            updateDateControl();
            onDateTimeChanged();
        }
    };

    private NumberPicker.OnValueChangeListener mOnDayChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            dayOfMonth = mDaySpinner.getValue();
            mCurrentDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            onDateTimeChanged();
        }
    };

    private void updateDateControl() {
        mDaySpinner.setMaxValue(mCurrentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
    }


    /**
     * The listener used to indicate the user has finished selecting a date.
     */
    public interface OnDateSetListener {
        /**
         * @param view the picker associated with the dialog
         * @param year the selected year
         * @param month the selected month (0-11 for compatibility with
         *              {@link Calendar#MONTH})
         * @param dayOfMonth th selected day of the month (1-31, depending on
         *                   month)
         */
        void onDateSet(YearDatePicker view, int year, int month, int dayOfMonth);
    }
    /*
     *对外的公开方法
     */
    public void setOnDateTimeChangedListener(OnDateSetListener callback) {
        mOnYearDateChangedListener = callback;
    }

    private void onDateTimeChanged() {
        if (mOnYearDateChangedListener != null) {
            mOnYearDateChangedListener.onDateSet(this, year, month, dayOfMonth);
        }
    }

}
