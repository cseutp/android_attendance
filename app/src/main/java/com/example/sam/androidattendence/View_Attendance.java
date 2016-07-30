package com.example.sam.androidattendence;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.microedition.khronos.egl.EGLDisplay;
import javax.security.auth.Subject;

/**
 * Created by sam on 24/7/16.
 */
public class View_Attendance extends Activity{
    TextView TV_disp_Subject , TV_disp_Attendance , TV_disp_attendance_percentage;
    Button BT_Calculate;
    EditText EDT_Attendance;
    UserListDbHelper uldh;
    UserListDbHelper2 uldh2;
    int spinner_subject_pos;
    String att , Subject ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_attendance);
        TV_disp_Subject = (TextView) findViewById(R.id.textView_disp_subject);
        TV_disp_Attendance = (TextView) findViewById(R.id.textView_disp_attendance);
        EDT_Attendance = (EditText) findViewById(R.id.editText);
        TV_disp_attendance_percentage = (TextView) findViewById(R.id.textView_disp_attendance_percentage);
        BT_Calculate = (Button) findViewById(R.id.button_calculate_attendance);

        Intent intent = getIntent();
        String Spinner_pos = intent.getStringExtra("Spinner_pos");

        if (Spinner_pos == "0") {
            spinner_subject_pos = 0;
        }
        if (Spinner_pos == "1") {
            spinner_subject_pos = 1;
        }
        if (Spinner_pos == "2") {
            spinner_subject_pos = 2;
        }
        if (Spinner_pos == "3") {
            spinner_subject_pos = 3;
        }
        if (Spinner_pos == "4") {
            spinner_subject_pos = 4;
        }
        if (Spinner_pos == "5") {
            spinner_subject_pos = 5;
        }
        if (Spinner_pos == "6") {
            spinner_subject_pos = 6;
        }


        uldh2 = new UserListDbHelper2(getApplicationContext(), null, null, 3);
        Cursor check = uldh2.Checkdata();
        if (check != null) {
            check.moveToFirst();
            Subject = check.getString(spinner_subject_pos);
        }


        uldh2 = new UserListDbHelper2(getApplicationContext(), null, null, 3);
        check = uldh2.Checkdata();
        if (check != null) {
            check.moveToFirst();
            int attendance;
            attendance = check.getInt(spinner_subject_pos + 6);
            att = ""+attendance;
        }

        TV_disp_Subject.setText(Subject);
        TV_disp_Attendance.setText(att);
        BT_Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate_attendance_percentage(att);
            }
        });


    }


    private void calculate_attendance_percentage(String att) {
        String classes_held = EDT_Attendance.getText().toString();
        int classes_held_integer=Integer.parseInt(classes_held);
        //Toast.makeText(getApplicationContext(),""+classes_held_integer , Toast.LENGTH_SHORT).show();

        int att_integer=Integer.parseInt(att);
        //Toast.makeText(getApplicationContext(),att , Toast.LENGTH_SHORT).show();

        double percentage = (double)att_integer/classes_held_integer *100;
        float k = (float) Math.round(percentage * 100) / 100;
        TV_disp_attendance_percentage.setText(""+k );

    }
}
