package com.example.myapplication.ui.category;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.DBHelper;
import com.example.myapplication.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class CategoryFragment extends Fragment {
    //private CategoryViewModel categoryViewModel;
    PieChart chart1;
    DBHelper dbHelper ;

    TextView rankView1;
    TextView rankView2;
    TextView rankView3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {


        View root = (ViewGroup) inflater.inflate(R.layout.chart, container, false);
        initView(root);
        setPieChart();

        rankView1=root.findViewById(R.id.ranking1_name);
        rankView2=root.findViewById(R.id.ranking2_name);
        rankView3=root.findViewById(R.id.ranking3_name);
        listCnt();
        return  root;

    }

    public void initView(View v){

        chart1 = (PieChart) v.findViewById(R.id.tab1_chart_1);
        //setPieChart();

    }

    // 파이 차트 설정
    private void setPieChart() {

        Log.v("시작:","setPieChart");


        chart1.clearChart();
        dbHelper = new DBHelper(getActivity(),"tb_contact.db",null,1);

        String sqlCnt = "SELECT type,count(*)cnt from tb_contact group by type order by type ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlCnt, null);
        Log.v("시작:",String.valueOf(cursor.getCount()));

        if (cursor.moveToNext()&&cursor.getCount()>0) {
            do
            {
            String typeCh = cursor.getString(0);
            int cntCh = Integer.parseInt(cursor.getString(1));

            switch (typeCh) {
                case "가공식품":
                    chart1.addPieSlice(new PieModel("가공식품", cntCh, Color.parseColor("#00BFFF")));
                    Log.v("시작:","가공식품");
                    break;
                case "건강식품":
                    chart1.addPieSlice(new PieModel("건강식품", cntCh, Color.parseColor("#CDA67F")));
                    Log.v("시작:","건강식품");
                    break;
                case "냉동식품":
                    chart1.addPieSlice(new PieModel("냉동식품", cntCh, Color.parseColor("#D1B2FF")));
                    Log.v("시작:","냉동식품");
                    break;
                case "스낵류":
                    chart1.addPieSlice(new PieModel("스낵류", cntCh, Color.parseColor("#FAED7D")));
                    Log.v("시작:","스낵류");
                    break;
                case "유제품":
                    chart1.addPieSlice(new PieModel("유제품", cntCh, Color.parseColor("#FFA7A7")));
                    Log.v("시작:","유제품");
                    break;
                case "음료":
                    chart1.addPieSlice(new PieModel("음료", cntCh, Color.parseColor("#D9418C")));
                    Log.v("시작:","음료");
                    break;
                case "장류/양념류":
                    chart1.addPieSlice(new PieModel("장류/양념류", cntCh, Color.parseColor("#23A41A")));
                    Log.v("시작:","장류/양념류");
                    break;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        chart1.startAnimation();
    }



    public void listCnt(){
        dbHelper = new DBHelper(getActivity(),"tb_contact.db",null,1);

        String listCnt = "SELECT type,count(*)cnt from tb_contact group by type order by cnt desc ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(listCnt, null);
        String rank1="";
        String rank2="";
        String rank3="";

        Log.v("rank",String.valueOf(cursor.getCount()));
        if (cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                if (cursor.getPosition() == 0) {
                    rank1 = cursor.getString(0);
                    Log.v("rank",rank1);
                } else if (cursor.getPosition() == 1) {
                    rank2 = cursor.getString(0);
                    Log.v("rank",rank2);
                } else if (cursor.getPosition() == 2) {
                    rank3 = cursor.getString(0);
                    Log.v("rank",rank3);
                }
            }
        }cursor.close();


        rankView1.setText(rank1);
        rankView2.setText(rank2);
        rankView3.setText(rank3);
    }

}


