package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    private Object tb_contact;

    public DBHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version)
    {
       // super(context,"contactdb",null,DATABASE_VERSION);
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String tableSql="create table tb_contact("+
                "_id integer primary key autoincrement,"+
                "name not null,"+
                "type,"+
                "date,"+
                "code)";

       String apiSql ="CREATE TABLE API(" +
                "num integer primary key autoincrement,"+
                "Bar_CD VARCHAR NOT NULL," +
                "Product_NM VARCHAR," +
                "Product_DCNM VARCHAR," +
                "Product_DAYCNT VARCHAR)";

        db.execSQL(tableSql);
        db.execSQL(apiSql);
    }

    //DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==DATABASE_VERSION){
            db.execSQL("drop table tb_contact");
            db.execSQL("drop table API");
            onCreate(db);
        }
    }

    /**
     *  등록
     * @param  name 상품명
     * @param type 상품유형
     * @param date 유통기한
     * @param code 유통기한
     */
    public void insert(String name, String type, String date, String code) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO tb_contact VALUES(null, '" + name + "', '" + type + "', '" + date + "','" + code + "');");
        db.close();
    }


    public void insertData(ArrayList<BarCdVo> list){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for(BarCdVo entity : list){
            values.put("Bar_CD",entity.getBar_CD());
            values.put("Product_NM",entity.getProduct_NM());
            values.put("Product_DCNM",entity.getProduct_DCNM());
            values.put("Product_DAYCNT",entity.getProduct_DAYCNT());
            db.insert("API",null,values);

        }
        db.close();
    }


    /**
     * 수정
     * @param name 상품명
     * @param type 상품유형
     * @param date 유통기한
     * @param code 유통기한
     *//*
    public void update(String name, String type, String date, String code) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE tb_contact SET type=" + type + "  WHERE name='" + name + "';");
        db.close();
    }*/

    /**
     * 삭제
     * @param  period 유통기한
     * @param name 상품명
     */
    public void delete(String period, String name) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제

        db.execSQL("DELETE FROM tb_contact WHERE date='"+ period +"'and name='"+ name +"';");
        db.close();
    }

    /**
     * 조회
     * @return
     */
    public Cursor getProductList() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT name, type, date, code FROM tb_contact ORDER BY _id desc", null);

        return cursor;
    }

    public ArrayList<BarCdVo> getItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT Bar_CD,Product_NM,Product_DCNM,Product_DAYCNT FROM API", null);
        cursor.moveToFirst();
        ArrayList<BarCdVo> list = new ArrayList<>();
        while(cursor.moveToNext()){
            BarCdVo entity = new BarCdVo();
            entity.setBar_CD(cursor.getString(0));
            entity.setProduct_NM(cursor.getString(1));
            entity.setProduct_DCNM(cursor.getString(2));
            entity.setProduct_DAYCNT(cursor.getString(3));
            list.add(entity);
        }
        return list;
    }

    public Cursor getDate() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT date FROM tb_contact", null);

        return cursor;
    }


}


