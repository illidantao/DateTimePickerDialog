package com.illidan.dhunt.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
    private Calendar mCurrentDate;
    private int year, month, dayOfMonth;
    private OnDateSetListener mOnYearDateChangedListener;


    public YearDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCurrentDate = Calendar.getInstance();
        mCurrentDate.setTimeInMillis(System.currentTimeMillis());
        year = mCurrentDate.get(Calendar.YEAR);
        month = mCurrentDate.get(Calendar.MONTH);
        dayOfMonth = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        inflate(context, R.layout.ll_date_pick, this);

        mYearSpinner = (NumberPicker) this.findViewById(R.id.np_year);
        mYearSpinner.setMinValue(1900);
        mYearSpinner.setMaxValue(3000);
        mYearSpinner.setValue(year);
        mYearSpinner.setOnValueChangedListener(mOnYearChangedListener);

        mMonthSpinner = (NumberPicker) this.findViewById(R.id.np_month);
        mMonthSpinner.setMaxValue(12);
        mMonthSpinner.setMinValue(1);
        mMonthSpinner.setValue(month+1);
        mMonthSpinner.setOnValueChangedListener(mOnMonthChangedListener);

        mDaySpinner = (NumberPicker) this.findViewById(R.id.np_day);
        mDaySpinner.setMaxValue(mCurrentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        mDaySpinner.setMinValue(1);
        mDaySpinner.setValue(dayOfMonth);
        mDaySpinner.setOnValueChangedListener(mOnDayChangedListener);
    }

    public YearDatePicker(Context context,AttributeSet attrs){
        this(context, attrs, 0);
    }

    public YearDatePicker(Context context) {
        this(context,null);
    }

    public void setDateTime(long time){
        mCurrentDate.setTimeInMillis(time);
        year = mCurrentDate.get(Calendar.YEAR);
        month = mCurrentDate.get(Calendar.MONTH);
        dayOfMonth = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        updateDateControl();
    }

    private NumberPicker.OnValueChangeListener mOnYearChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            year = newVal;
            mCurrentDate.set(Calendar.YEAR, year);
            updateDateControl();
            onDateTimeChanged();
        }
    };

    private NumberPicker.OnValueChangeListener mOnMonthChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            month = newVal-1;
            updateDateControl();
            onDateTimeChanged();
        }
    };

    private NumberPicker.OnValueChangeListener mOnDayChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            dayOfMonth = newVal;
            onDateTimeChanged();
        }
    };

    private void updateDateControl() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year,month,10);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(dayOfMonth > maxDay){
            dayOfMonth = maxDay;
        }
        mDaySpinner.setMaxValue(maxDay);
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
