package com.example.myapplication;

import android.app.AlertDialog;
import android.bluetooth.le.ScanRecord;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    String name ="";
    String period="";

    DBHelper dbHelper;
    AlertDialog.Builder builder;

    ArrayList<ProductVo> items = new ArrayList<ProductVo>();

    @NonNull
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.listviewitem,viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ViewHolder viewHolder, final int position) {
        final ProductVo item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                dbHelper = new DBHelper(context.getApplicationContext(),"tb_contact.db",null,1);

                name = items.get(position).getName();
                period = items.get(position).getDate();

                builder=new AlertDialog.Builder(context);
                builder.setTitle("삭제");
                builder.setMessage("["+period+":"+name+"] 해당 항목을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                        notifyDataSetChanged();
                        dbHelper.delete(period,name);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

    }
    /**
     * 아이템 갯수 가져오기
     * @return
     */
    @Override
    public int getItemCount() {
        return items.size();
    }
    /**
     * 아이템 등록
     * @param alaramVo
     */
    public void addItem(ProductVo alaramVo){
        items.add(alaramVo);
    }

    public void deleteItem(int position){

        items.remove(position);
    }

    /**
     * 아이템 전체삭제
     */
    public void removeAllItem(){
        items.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tName;
        TextView tType;
        TextView tDate;
        CardView cardView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tName = itemView.findViewById(R.id.name);
            tType = itemView.findViewById(R.id.type);
            tDate = itemView.findViewById(R.id.date);
            cardView = itemView.findViewById(R.id.cardView);
        }
        public void setItem(ProductVo vo){
            //텍스트에 데이터 담기
            tName.setText(vo.getName());
            tType.setText(vo.getType());
            tDate.setText(vo.getDate());

        }
    }
}
