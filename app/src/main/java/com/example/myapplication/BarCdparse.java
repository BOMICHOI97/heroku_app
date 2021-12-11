package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

public class BarCdparse {

    public final static String PHARM_URL = "http://openapi.foodsafetykorea.go.kr/api/b8e93f7/";
    public final static String KEY ="b8e93f7cc8ea4c63963e";
    /*public BarCdparse() {
        try {
            apiParserSearch();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/


    /**
     * @throws Exception
     */
    public ArrayList<BarCdVo> apiParserSearch() throws Exception {
        println("apiParserSearch 시작");

        ArrayList<BarCdVo> list = new ArrayList<BarCdVo>();

        for (int i = 1; i < 5000; i += 1000) {

            URL url = new URL(getURLParam(null));

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            xpp.setInput(bis, "utf-8");

            String tag = null;
            int event_type = xpp.getEventType();

            String barCD = null, productNM = null, productDCNM = null, productDAYCNT = null;
            while (event_type != XmlPullParser.END_DOCUMENT) {
                if (event_type == XmlPullParser.START_TAG) {
                    tag = xpp.getName();
                } else if (event_type == XmlPullParser.TEXT) {
                    if (tag.equals("BAR_CD")) {
                        barCD = xpp.getText();
                    } else if (tag.equals("PRDLST_NM")) {
                        productNM = xpp.getText();
                    } else if (tag.equals("PRDLST_DCNM")) {
                        productDCNM = xpp.getText();
                    } else if (tag.equals("POG_DAYCNT")) {
                        productDAYCNT = xpp.getText();
                    }
                } else if (event_type == XmlPullParser.END_TAG) {
                    tag = xpp.getName();
                    if (tag.equals("BAR_CD") | tag.equals("PRDLST_NM") |
                            tag.equals("PRDLST_DCNM") | tag.equals("POG_DAYCNT")) {
                        BarCdVo entity = new BarCdVo();
                        entity.setBar_CD(barCD);
                        entity.setProduct_NM(productNM);
                        entity.setProduct_DCNM(productDCNM);
                        entity.setProduct_DAYCNT(productDAYCNT);

                        list.add(entity);
                    }
                }
                event_type = xpp.next();
            }
           /* DBHelper dbHelpernew DBHelper(context,"api.db",null,1);
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            dbHelper.insertData(list);
            db.close();*/

        }
        println(Integer.toString(list.size()));
        return list;
    }
    private String getURLParam(String search){
        String url = PHARM_URL+KEY+"/C005/xml/";
        /*if(search != null){
            url = url+"&yadmNm"+search;
        }*/
        return url;
    }

    public void println(String msg){
        Log.d("BarCdparse",msg);
    }



}