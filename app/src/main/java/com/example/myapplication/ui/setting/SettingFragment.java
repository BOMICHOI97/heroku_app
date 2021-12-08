package com.example.myapplication.ui.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.myapplication.BroadcastD;
import com.example.myapplication.DBHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.Inflater;

public class SettingFragment extends PreferenceFragmentCompat {
    SharedPreferences pref;
    Preference periodPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String s) {
        setPreferencesFromResource(R.xml.settting_prefernce,s);
        if(s==null){
            periodPreference=findPreference("period");
            pref=PreferenceManager.getDefaultSharedPreferences(getActivity());
            if(!pref.getString("period","").equals("")) {
                periodPreference.setSummary(pref.getString("period", "알림은 당일 10:00AM 에 발생합니다."));
            }
            pref.registerOnSharedPreferenceChangeListener(listener);
            //key 값이 "message"인 설정의 저장값 가져오기.
          // boolean isMessage= pref.getBoolean("vibration", true); //두번째 파라미터 : default값
            // Toast.makeText(getActivity(), "진동알림"+isMessage, Toast.LENGTH_SHORT).show();

        }
    }

    //설정값 변경리스너 객체 맴버변수
    SharedPreferences.OnSharedPreferenceChangeListener listener= new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("period")) {
                periodPreference.setSummary(pref.getString("period", "마감 1일전"));
            }
            if(key.equals("vibration")){
                Log.v("진동","2");
                boolean b= pref.getBoolean("vibration",true);
                Toast.makeText(getActivity(), "진동알림 : "+ b, Toast.LENGTH_SHORT).show();

            }
            else if(key.equals("popup")){
                Log.v("진동","3");
                boolean b= pref.getBoolean("popup",true);
                Toast.makeText(getActivity(), "팝업알림 : "+ b, Toast.LENGTH_SHORT).show();
                if(b==true){

                }else{

                }
            }
        }
    };
}
