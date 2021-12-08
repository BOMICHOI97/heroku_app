package com.example.myapplication.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DBHelper;
import com.example.myapplication.ListViewAdapter;
import com.example.myapplication.ManagePublicData;
import com.example.myapplication.ProductVo;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    private ListViewAdapter adapter;

    TextView tName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);*/
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView = root.findViewById(R.id.recycler_view);

        //recyclerView 레이아웃 설정
        recyclerView.setLayoutManager(layoutManager);

        //recyclerView 어댑터 설정
        adapter = new ListViewAdapter();
        recyclerView.setAdapter(adapter);

        tName = root.findViewById(R.id.name);
        ManagePublicData.getInstance().parseBarCd.execute();
        Log.v("test","데이터");
        getProductList();

        return root;

    }
    private void getProductList(){
        println("<<getProductList>>");
        adapter.removeAllItem();
        final DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext(),"tb_contact.db",null,1);
        Cursor cursor =dbHelper.getProductList();

        int count=0;

        while(cursor.moveToNext()){
            ProductVo vo = new ProductVo();
            //vo.set_id(cursor.getLong(0));
            vo.setName(cursor.getString(0));
            vo.setType(cursor.getString(1));
            vo.setDate(cursor.getString(2));
            adapter.addItem(vo);
            count++;
        }
        adapter.notifyDataSetChanged();
        println("상품수:"+adapter.getItemCount());
    }
    public void println(String msg){
        Log.v("HomeFragment",msg);
    }
}