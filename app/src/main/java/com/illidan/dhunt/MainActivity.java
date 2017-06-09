package com.illidan.dhunt;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.illidan.dhunt.fragment.CreditCardDatePickDialog;
import com.illidan.dhunt.fragment.DateTimePickerDialog;
import com.illidan.dhunt.fragment.YearMonthDialog;
import com.illidan.dhunt.listener.OnDateListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends FragmentActivity {

    private Button btnYearMonth,btnYearMonthDay,btnDateTime;
    private TextView tvDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnYearMonthDay = (Button) findViewById(R.id.btn_year_mouth_day);
        btnYearMonth = (Button) findViewById(R.id.btn_year_mouth);
        btnDateTime = (Button) findViewById(R.id.btn_date_time);
        btnYearMonthDay.setOnClickListener(clickListener);
        btnYearMonth.setOnClickListener(clickListener);
        btnDateTime.setOnClickListener(clickListener);
        tvDate = (TextView) findViewById(R.id.tv_date);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == btnYearMonthDay){
                YearMonthDialog.show(MainActivity.this, new OnDateListener() {
                    @Override
                    public void onDateSet(String mmyy, String yymm) {
                        tvDate.setText(mmyy+"\n"+yymm);
                    }
                });
            } else if (v == btnDateTime){
                DateTimePickerDialog dateTimePickerDialog = new DateTimePickerDialog(MainActivity.this,System.currentTimeMillis());
                dateTimePickerDialog.setOnDateTimeSetListener(onDateTimeSetListener);
                dateTimePickerDialog.show();
            } else if (v == btnYearMonth){
                CreditCardDatePickDialog.show(MainActivity.this, new OnDateListener() {
                    @Override
                    public void onDateSet(String mmyy, String yymm) {
                        tvDate.setText(mmyy+"\n"+yymm);
                    }
                });
            }
        }
    };

    DateTimePickerDialog.OnDateTimeSetListener onDateTimeSetListener = new DateTimePickerDialog.OnDateTimeSetListener() {
        @Override
        public void OnDateTimeSet(AlertDialog dialog, long date) {
            tvDate.setText(dateFormat.format(new Date(date)));
        }
    };
}
