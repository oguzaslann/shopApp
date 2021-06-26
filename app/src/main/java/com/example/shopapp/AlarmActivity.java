package com.example.shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    EditText alarmDate;
    Button changeDateButton, senderButton;
    TimePicker timePicker;
    Spinner spinner;
    Basket currentBasket;
    Calendar calNow = Calendar.getInstance();
    Calendar calSet = (Calendar) calNow.clone();
    int dayCdt, monthCdt, yearCdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        defineVeriables();;

        SqlLiteHelperBasket sqlLiteHelperBasket = new SqlLiteHelperBasket(this);
        ArrayList<Basket> baskets = new ArrayList<Basket>();
        baskets = sqlLiteHelperBasket.GetBaskets();
        ArrayAdapter<Basket> dataAdapter = new ArrayAdapter<Basket>(this, android.R.layout.simple_spinner_item, baskets);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Basket basket = (Basket) parent.getSelectedItem();
                currentBasket = basket;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yearC = calendar.get(Calendar.YEAR);
                int mounthC = calendar.get(Calendar.MONTH);
                int dayC = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(AlarmActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month += 1;
                                alarmDate.setText(dayOfMonth + "/" + month + "/" + year);
                                /*
                                dayCdt = dayOfMonth;
                                monthCdt = month;
                                yearCdt = year;

                                 */
                            }
                        }, yearC, mounthC, dayC);

                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();
            }
        });

        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm(calSet);
            }
        });
    }


    private void defineVeriables() {
        alarmDate = findViewById(R.id.alarmDate);
        spinner = findViewById(R.id.spinner);
        timePicker = findViewById(R.id.timePicker);
        changeDateButton = findViewById(R.id.changeDateButton);
        senderButton = findViewById(R.id.senderButton);
    }


    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);
            /*
            calSet.set(Calendar.DAY_OF_MONTH, dayCdt);
            calSet.set(Calendar.MONTH, monthCdt);
            calSet.set(Calendar.YEAR, yearCdt);

             */

            if(calSet.compareTo(calNow) <= 0){

                calSet.add(Calendar.DATE, 1);
            }

            //setAlarm(calSet);
        }};

    private void setAlarm(Calendar alarmCalender){
        Toast.makeText(getApplicationContext(),"Alarm kuruldu.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("basketName", spinner.getSelectedItem().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalender.getTimeInMillis(), pendingIntent);
        Intent mainIntent = new Intent(AlarmActivity.this, MainActivity.class);
        AlarmActivity.this.startActivity((mainIntent));
    }
}