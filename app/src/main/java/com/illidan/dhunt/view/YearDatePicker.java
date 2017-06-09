package com.illidan.dhunt.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import com.illidan.dhunt.R;

import java.util.Calendar;

/**
 * @author illidantao
 * @date 2016/6/27 15:18
 */
public class YearDatePicker extends FrameLayout {

    private final NumberPicker mYearSpinner,mMonthSpinner,mDaySpinner;
    private EditText edtYear, edtMonth, edtDayOfMonth;
    private Calendar mCurrentDate;
    private int year, month, dayOfMonth;
    private OnDateSetListener mOnYearDateChangedListener;
    private int timeType = TimeType.ALLDATE;

    public YearDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.year_date_picker);
        timeType = ta.getInt(R.styleable.year_date_picker_select_type,TimeType.ALLDATE);

        mCurrentDate = Calendar.getInstance();
        mCurrentDate.setTimeInMillis(System.currentTimeMillis());
        year = mCurrentDate.get(Calendar.YEAR);
        month = mCurrentDate.get(Calendar.MONTH);
        dayOfMonth = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        inflate(context, R.layout.ll_date_pick, this);

        mYearSpinner = (NumberPicker) this.findViewById(R.id.np_year);
        mYearSpinner.setMinValue(1900);
        mYearSpinner.setMaxValue(3000);
        mYearSpinner.setOnValueChangedListener(mOnYearChangedListener);
        edtYear = getNumberEditText(mYearSpinner);
        if((timeType & TimeType.YEAR) == 0){
            mYearSpinner.setVisibility(GONE);
        }

        mMonthSpinner = (NumberPicker) this.findViewById(R.id.np_month);
        mMonthSpinner.setMaxValue(12);
        mMonthSpinner.setMinValue(1);
        mMonthSpinner.setOnValueChangedListener(mOnMonthChangedListener);
        edtMonth = getNumberEditText(mMonthSpinner);
        if((timeType & TimeType.MONTH) == 0){
            mMonthSpinner.setVisibility(GONE);
        }

        mDaySpinner = (NumberPicker) this.findViewById(R.id.np_day);
        mDaySpinner.setMaxValue(mCurrentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        mDaySpinner.setMinValue(1);
        mDaySpinner.setOnValueChangedListener(mOnDayChangedListener);
        edtDayOfMonth = getNumberEditText(mDaySpinner);
        if((timeType & TimeType.DAY) == 0){
            mDaySpinner.setVisibility(GONE);
        }
        refreshNumPicker();

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
        refreshNumPicker();
        updateDateControl();
    }

    private void refreshNumPicker(){
        mYearSpinner.setValue(year);
        mMonthSpinner.setValue(month+1);
        mDaySpinner.setValue(dayOfMonth);
    }

    private EditText getNumberEditText(NumberPicker number){
        EditText editText = (EditText) number.findViewById(getResources().getIdentifier("android:id/numberpicker_input", null, null));
        return editText;
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

    public int[] getDates(){
        if(edtYear != null && edtYear.getText().toString() != null){
            try {
                year = Integer.parseInt(edtYear.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if(edtMonth != null && edtMonth.getText().toString() != null){
            try {
                month = Integer.parseInt(edtMonth.getText().toString()) - 1;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if(edtDayOfMonth != null && edtDayOfMonth.getText().toString() != null){
            try {
                dayOfMonth = Integer.parseInt(edtDayOfMonth.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return new int[]{year,month,dayOfMonth};
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
