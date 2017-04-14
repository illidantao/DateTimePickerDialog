package com.illidan.dhunt;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.illidan.dhunt.fragment.DateTimePickerDialog;
import com.illidan.dhunt.fragment.YearMonthDialog;
import com.illidan.dhunt.view.DateTimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends FragmentActivity {

    private Button btnYearMouth,btnDateTime;
    private TextView tvDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnYearMouth = (Button) findViewById(R.id.btn_year_mouth);
        btnDateTime = (Button) findViewById(R.id.btn_date_time);
        btnYearMouth.setOnClickListener(clickListener);
        btnDateTime.setOnClickListener(clickListener);
        tvDate = (TextView) findViewById(R.id.tv_date);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == btnYearMouth){
                YearMonthDialog dialog = new YearMonthDialog();
                dialog.show(getSupportFragmentManager(),"year_month");
            }else if (v == btnDateTime){
                DateTimePickerDialog dateTimePickerDialog = new DateTimePickerDialog(MainActivity.this,System.currentTimeMillis());
                dateTimePickerDialog.setOnDateTimeSetListener(onDateTimeSetListener);
                dateTimePickerDialog.show();
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
