package com.illidan.dhunt.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateUtils;
import com.illidan.dhunt.view.DateTimePicker;
import java.util.Calendar;

/**
 * 日期+时间选择器
 * @author illidantao
 * @date 2016/6/27 15:22
 */
public class DateTimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener {

    private DateTimePicker mDateTimePicker;
    private Calendar mDate = Calendar.getInstance();
    private OnDateTimeSetListener mOnDateTimeSetListener;
    @SuppressWarnings("deprecation")
    public DateTimePickerDialog(Context context, long date) {
        super(context,android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDateTimePicker = new DateTimePicker(context,date);
        setView(mDateTimePicker);
        /*
         *实现接口，实现里面的方法
         */
        mDateTimePicker
                .setOnDateTimeChangedListener(new DateTimePicker.OnDateTimeChangedListener() {
                    @Override
                    public void onDateTimeChanged(DateTimePicker view,
                                                  int year, int month, int day, int hour, int minute) {
                        mDate.set(Calendar.YEAR, year);
                        mDate.set(Calendar.MONTH, month);
                        mDate.set(Calendar.DAY_OF_MONTH, day);
                        mDate.set(Calendar.HOUR_OF_DAY, hour);
                        mDate.set(Calendar.MINUTE, minute);
                        mDate.set(Calendar.SECOND, 0);
                        /**
                         * 更新日期
                         */
                        updateTitle(mDate.getTimeInMillis());
                    }
                });

        setButton("设置", this);
        setButton2("取消", (OnClickListener) null);
        mDate.setTimeInMillis(date);
        updateTitle(mDate.getTimeInMillis());
    }
    /*
     *接口回調
     *控件 秒数
     */
    public interface OnDateTimeSetListener {
        void OnDateTimeSet(AlertDialog dialog, long date);
    }
    /**
     * 更新对话框日期
     * @param date
     */
    private void updateTitle(long date) {
        int flag = DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_TIME;
        setTitle(DateUtils.formatDateTime(this.getContext(), date, flag));
    }

    public void setOnDateTimeSetListener(OnDateTimeSetListener callBack) {
        mOnDateTimeSetListener = callBack;
    }

    public void onClick(DialogInterface arg0, int arg1) {
        if (mOnDateTimeSetListener != null) {
            mOnDateTimeSetListener.OnDateTimeSet(this, mDate.getTimeInMillis());
        }
    }

}
