package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;
import com.example.myapplication.DBHelper;

public class missionresult extends AppCompatActivity {
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);


        TextView nameView=(TextView)findViewById(R.id.result_name);
        TextView typeView=(TextView)findViewById(R.id.result_type);
        TextView dateView=(TextView)findViewById(R.id.result_date);
        TextView codeView=(TextView)findViewById(R.id.result_code);

        DBHelper helper=new DBHelper(getApplicationContext(),"tb_contact.db",null,1);
        SQLiteDatabase db=helper.getWritableDatabase();

        Cursor cursor=db.rawQuery("select name,type,date,code from tb_contact ",null);
        while(cursor.moveToNext()){
            nameView.setText(cursor.getString(0));
            typeView.setText(cursor.getString(1));
            dateView.setText(cursor.getString(2));
            codeView.setText(cursor.getString(3));
        }
        db.close();
    }

}
