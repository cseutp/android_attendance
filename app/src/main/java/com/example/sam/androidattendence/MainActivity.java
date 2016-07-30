package com.example.sam.androidattendence;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {
    Button BT_View_Details , BT_Edit_Details, BT_Submit_Attendance,BT_Edit_Subjects , BT_Check_Attendance;
    Spinner SP_Subject;
    UserListDbHelper uldh;
    UserListDbHelper2 uldh2;
    int Spinner_pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        BT_View_Details = (Button) findViewById(R.id.button_view_details);
        BT_Edit_Details = (Button) findViewById(R.id.button_edit_details);
        BT_Submit_Attendance = (Button) findViewById(R.id.button_submit_Attendance);
        BT_Edit_Subjects = (Button) findViewById(R.id.button_edit_subjects);
        BT_Check_Attendance = (Button) findViewById(R.id.button_check_attendance);
        SP_Subject = (Spinner) findViewById(R.id.spinner_show_subject);


        uldh2 = new UserListDbHelper2(getApplicationContext() , null,null,3);
        Cursor check;
        check = uldh2.Checkdata();
        if(check==null){
            Intent GetSubjectDetails = new Intent(getApplicationContext() , GetSubject_Details.class);
            startActivity(GetSubjectDetails);
        }

        spinner_list();
        SP_Subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = ((Spinner) findViewById(R.id.spinner_show_subject)).getSelectedItem().toString();
                Spinner_pos = i;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });







        BT_View_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDetails_List();
            }

            });
        BT_Edit_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent moveToRegisterPage = new Intent(getApplication() , Register.class);
               startActivity(moveToRegisterPage);
                Toast.makeText(getApplicationContext() , "Edit Your Details",Toast.LENGTH_SHORT).show();
            }
        });
        BT_Edit_Subjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext() , "Enter Subject Codes" , Toast.LENGTH_SHORT).show();
                Intent moveToGetSubjectDetails = new Intent(getApplicationContext() , GetSubject_Details.class);
                startActivity(moveToGetSubjectDetails);

            }
        });

        BT_Check_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToViewAttendance = new Intent(getApplicationContext() , View_Attendance.class);
                String str = ""+Spinner_pos;
                moveToViewAttendance.putExtra("Spinner_Pos" , str);
                startActivity(moveToViewAttendance);
                /*uldh2 = new UserListDbHelper2(getApplicationContext(), null,null,3);
                Cursor check;
                check=uldh2.Checkdata();
                if(check!=null){
                    check.moveToFirst();
                    int attendance=100;
                    attendance = check.getInt(6);
                    String att=""+attendance;
                    }*/
            }
        });
        BT_Submit_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 submit_Attendance();
            }
        });





    }

    private void spinner_list() {
        uldh2 = new UserListDbHelper2(getApplicationContext(), null,null,3);
        Cursor check;
        check=uldh2.Checkdata();
        if(check!=null){
            check.moveToFirst();
            String sub1="" , sub2="" , sub3="" , sub4="" , sub5="" , sub6="";
            sub1 = check.getString(0);
            sub2 = check.getString(1);
            sub3 = check.getString(2);
            sub4 = check.getString(3);
            sub5 = check.getString(4);
            sub6 = check.getString(5);
            String Spinner_Content[] = {sub1 , sub2 , sub3 , sub4 , sub5 , sub6};
            ArrayAdapter<String> AD_Spinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Spinner_Content);
            SP_Subject.setAdapter(AD_Spinner);

        }
        else {
            Toast.makeText(getApplicationContext() , "No Subject Details Found" , Toast.LENGTH_SHORT).show();
        }


    }

    private void submit_Attendance() {
       set_attendance(Spinner_pos);
        uldh = new UserListDbHelper(getApplicationContext(), null,null,3);
        Cursor check;
        check=uldh.Checkdata();
        if(check!=null){
            check.moveToFirst();
            String Adm_no="#" , name = null, branch = null, mobile_no = null, hostel = null, room_no = null;
            Adm_no =    check.getString(0);
            name =      check.getString(1);
            branch =    check.getString(2);
            mobile_no = check.getString(3);
            hostel =    check.getString(4);
            room_no =   check.getString(5);


           RequestParams params = new RequestParams();
           params.put("Admission_No", Adm_no);
           params.put("Name",name );
           params.put("Branch",branch);
           params.put("Mobile_No", mobile_no);
           params.put("Hostel",hostel );
           params.put("Room_No",room_no );

           String url="http://192.168.190.103:8080";
           AsyncHttpClient client = new AsyncHttpClient();
           client.post(url, params, new JsonHttpResponseHandler(){

               @Override
               public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   super.onSuccess(statusCode, headers, response);
                   Toast.makeText(getApplicationContext(),"200 OK!",Toast.LENGTH_SHORT).show();
                   Log.i("Success Response ::: ","" + response);
                   System.out.println("Success Response ::: " + response);
                  // set_attendance(Spinner_pos);

               }

               @Override
               public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                   super.onFailure(statusCode, headers, responseString, throwable);
                   Toast.makeText(getApplicationContext(),"404" , Toast.LENGTH_SHORT).show();
                   Log.i("Failure Response ::: ","" + responseString);
                   System.out.println("Failure Response ::: " + responseString);
               }
           });


        }
        else
            Toast.makeText(getApplicationContext(), "No Data For Attendance" , Toast.LENGTH_SHORT).show();
    }

    private void set_attendance(Integer Spinner_pos) {
        uldh2 = new UserListDbHelper2(getApplicationContext() , null,null,3);
        Cursor check;
        check = uldh2.Checkdata();
        if(check!=null){
            check.moveToFirst();
            String Subject1="#" , Subject2 = null, Subject3 = null, Subject4 = null, Subject5 = null, Subject6 = null;
            int attendance;
            Subject1 = check.getString(0);
            Subject2 = check.getString(1);
            Subject3 = check.getString(2);
            Subject4 = check.getString(3);
            Subject5 = check.getString(4);
            Subject6 = check.getString(5);
            attendance = check.getInt(6);
            uldh2.createTable();
            uldh2.insertData(""+Subject1, ""+Subject2, ""+Subject3, ""+Subject4, ""+Subject5, ""+Subject6 , ++attendance);
            Toast.makeText(getApplicationContext() , "Attendance given" , Toast.LENGTH_SHORT).show();
        }


    }


    private void viewDetails_List() {
        uldh = new UserListDbHelper(getApplicationContext(), null,null,3);
        Cursor check;
        check=uldh.Checkdata();
        if(check!=null){

            check.moveToFirst();
            String Adm_no="#" , name = null, branch = null, mobile_no = null, hostel = null, room_no = null;

                Adm_no = check.getString(0);
                name = check.getString(1);
                branch = check.getString(2);
                mobile_no = check.getString(3);
                hostel = check.getString(4);
                room_no = check.getString(5);

            Intent moveTo_View_Details_List = new Intent(this, View_Details_List.class);
                moveTo_View_Details_List.putExtra("Admission_Number" , Adm_no);
                moveTo_View_Details_List.putExtra("Name" , name);
                moveTo_View_Details_List.putExtra("Branch" , branch);
                moveTo_View_Details_List.putExtra("Mobile_Number" , mobile_no);
                moveTo_View_Details_List.putExtra("Hostel" , hostel);
                moveTo_View_Details_List.putExtra("Room_Number" , room_no);
            startActivity(moveTo_View_Details_List);

        }
        else
            Toast.makeText(getApplicationContext(), "No Entry Yet" , Toast.LENGTH_SHORT).show();
    }

}
