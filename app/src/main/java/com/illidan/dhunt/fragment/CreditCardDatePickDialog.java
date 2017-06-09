package com.illidan.dhunt.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.illidan.dhunt.R;
import com.illidan.dhunt.listener.OnDateListener;
import com.illidan.dhunt.view.YearDatePicker;

import java.util.Calendar;

/**
 * Created by hzhuangdantao
 * on 2017/6/9 11:04
 * for what:
 */

public class CreditCardDatePickDialog extends DialogFragment {

    public static void show(FragmentActivity activity, OnDateListener mListener){
        CreditCardDatePickDialog creditCardDatePickDialog = new CreditCardDatePickDialog();
        creditCardDatePickDialog.mListener = mListener;
        creditCardDatePickDialog.show(activity.getSupportFragmentManager(), "CreditCardDatePickDialog");
    }

    private OnDateListener mListener;
    private YearDatePicker yearDatePicker;
    private static long lastDate = System.currentTimeMillis();
    private int year,month;//month:0~11
    private View btnYes,btnNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dialog_credit_date_pick,null);
        yearDatePicker = (YearDatePicker) view.findViewById(R.id.year_date_picker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastDate);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        yearDatePicker.setDateTime(lastDate);
        btnYes = view.findViewById(R.id.btn_twobtnmsg_dialog_right);
        btnNo = view.findViewById(R.id.btn_twobtnmsg_dialog_left);
        btnYes.setOnClickListener(clickListener);
        btnNo.setOnClickListener(clickListener);
        return view;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnNo){
                dismiss();
            } else if(v == btnYes){
                int[] dates = yearDatePicker.getDates();
                if(dates != null && dates.length > 1){
                    year = dates[0];
                    month = dates[1];
                }
                if(mListener != null){
                    String monthString = String.format("%02d", month+1);
                    String MMyy = monthString+"/"+String.format("%02d", year%100);
                    String yyyyMM = String.format("%04d", year)+monthString;
                    mListener.onDateSet(MMyy,yyyyMM);
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,1);
                lastDate = calendar.getTimeInMillis();
                dismiss();
            }
        }
    };

}
