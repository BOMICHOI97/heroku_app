package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.myapplication.ui.category.CategoryFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.setting.SettingFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private static int ONE_MINUTE = 5626;
    FragmentManager manager = getSupportFragmentManager();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new AlarmHATT(getApplicationContext()).Alarm();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //new AlarmHATT(getApplicationContext()).Alarm();

        //플로팅버튼
        FloatingActionButton fab = findViewById(R.id.fab);//플로팅버튼
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), mission.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_setting, R.id.nav_category)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
    // 네비게이션 메뉴 클릭 이벤트


    /*    public void onChangedFragment(int position, Bundle bundle) {
            Fragment fragment = null;

            switch (position){
                case 1:
                    fragment = homeFragment;
                    toolbar.setTitle("첫 번째 화면");
                    break;
                case 2:
                    fragment = settingFragment;
                    toolbar.setTitle("두 번째 화면");
                    break;
                case 3:
                    fragment = categoryFragment;
                    toolbar.setTitle("세 번째 화면");
                    break;
                default:
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }*/
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            manager.beginTransaction().replace(R.id.nav_home, new HomeFragment()).commit();
        } else if (id == R.id.nav_setting) {
            manager.beginTransaction().replace(R.id.nav_setting, new SettingFragment()).commit();
        } else if (id == R.id.nav_category) {
            manager.beginTransaction().replace(R.id.nav_category, new CategoryFragment()).commit();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public class AlarmHATT {
        DBHelper dbHelper;
        private Context context;

        public AlarmHATT(Context context) {
            this.context = context;
        }

        public void Alarm() {
            dbHelper = new DBHelper(context.getApplicationContext(), "tb_contact.db", null, 1);

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, BroadcastD.class);
            intent.putExtra("action", "Refresh");
            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");

            Calendar calendar = Calendar.getInstance();
            String today = format1.format(calendar.getTime());
            today.trim();
           // Log.v("날짜", today);

            String dateSql = "SELECT date FROM tb_contact";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(dateSql, null);

            cursor.moveToFirst();
            try {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String period = cursor.getString(0);
                        period.trim();
                        //Log.v("날짜2", period);

                        if (period.equals(today)) {
                            Log.v("날짜44", "true??");
                            //알람시간 calendar에 set해주기]
                            Integer Year = calendar.get(Calendar.YEAR);//2020
                            Integer Month = calendar.get(Calendar.MONTH);//11
                            Integer Date = calendar.get(Calendar.DATE);


                            calendar.set(Year, Month, Date, 20, 45, 0);
                            //알람 예약
                            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

                        } else {

                        }
                    }
                } else {
                    Log.v("바코드:", "값없음");
                }
            } finally {
                if (cursor != null) {
                    Log.v("바코드:", "끝");
                    cursor.close();
                }
            }
            cursor.close();
            db.close();
        }
    }

}