package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

public class ManagePublicData {
    private static ManagePublicData managePublicData;

    BarCdVo barCdVo;
    public ArrayList<BarCdVo> barCdVoArrayList;

    public ParseBarCd parseBarCd;

    String tag_name = null;
    boolean bSet = false;

    public static ManagePublicData getInstance() {
        if (managePublicData == null) {
            managePublicData = new ManagePublicData();
        }
        return managePublicData;
    }

    private ManagePublicData() {
        barCdVo = new BarCdVo();
        barCdVoArrayList = new ArrayList<BarCdVo>(1000);
        parseBarCd = new ParseBarCd();
    }

    public ArrayList<BarCdVo> getBarCdVoArrayList() {
        return barCdVoArrayList;
    }

    public void setBarCdVoArrayList(ArrayList<BarCdVo> barCdVoArrayList) {
        this.barCdVoArrayList = barCdVoArrayList;
    }


        public class ParseBarCd extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            for (int i = 1; i < 10; i += 10) {
                try {
                   // http://openapi.foodsafetykorea.go.kr/api/b8e93f7cc8ea4c63963e/C005/xml/1/1000
                    String url = "http://openapi.foodsafetykorea.go.kr/api/b8e93f7cc8ea4c63963e/C005/xml/"+ i + "/" + (i + 999) + "/";
                    //String url = "http://openapi.seoul.go.kr:8088/(인증키)/xml/SearchPublicToiletPOIService/" + i + "/" + (i + 999) + "/";
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    URL xmlUrl = new URL(url);
                    xmlUrl.openConnection().getInputStream();
                    Log.v("test", "바코드11111");
                    parser.setInput(xmlUrl.openStream(), "utf-8");
                    Log.v("test", "바코드111111");


                    int eventType = parser.getEventType();
                    Log.v("test", String.valueOf(eventType));

                    while (eventType != XmlPullParser.END_DOCUMENT) {


                        if (eventType == XmlPullParser.START_TAG) {
                            tag_name = parser.getName();

                            if (tag_name.equals("BAR_CD") | tag_name.equals("PRDLST_NM") | tag_name.equals("PRDLST_DCNM") | tag_name.equals("POG_DAYCNT"))
                                bSet = true;
                        } else if (eventType == XmlPullParser.TEXT) {

                            if (bSet) {
                                String data = parser.getText();
                                switch (tag_name) {
                                    case "BAR_CD":
                                        barCdVo.setBar_CD(data);
                                        break;
                                    case "PRDLST_NM":
                                        barCdVo.setProduct_NM(data);
                                        break;
                                    case "PRDLST_DCNM":
                                        barCdVo.setProduct_DCNM(data);
                                        break;
                                    case "POG_DAYCNT":
                                        barCdVo.setProduct_DAYCNT(data);
                                        barCdVoArrayList.add(new BarCdVo(barCdVo.getBar_CD(), barCdVo.getProduct_NM(), barCdVo.getProduct_DCNM(), barCdVo.getProduct_DAYCNT()));
                                        Log.v("test", "바코드 : " + barCdVoArrayList.get(barCdVoArrayList.size() - 1).getBar_CD());
                                        //Log.v("test", String.valueOf(barCdVoArrayList.size()));
                                        break;
                                }

                                bSet = false;
                            }
                        } else if (eventType == XmlPullParser.END_TAG) {

                        }
                        eventType = parser.next();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.v("test", "loa완료");
            return "";
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }


}

