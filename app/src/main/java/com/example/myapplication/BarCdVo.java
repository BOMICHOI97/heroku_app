package com.example.myapplication;

import java.io.Serializable;

public class BarCdVo {
    private String Bar_CD;//바코드
    private String Product_NM;//제품명
    private String Product_DCNM;//식품유형
    private String Product_DAYCNT;//유통기한

     public BarCdVo(){}
    public BarCdVo(String Bar_CD, String Product_NM, String Product_DCNM, String Product_DAYCNT){
        this.Bar_CD =Bar_CD;
        this.Product_NM = Product_NM;
        this.Product_DCNM = Product_DCNM;
        this.Product_DAYCNT = Product_DAYCNT;
    }
    public String getBar_CD() {  return Bar_CD;  }

    public void setBar_CD(String bar_CD) {
        this.Bar_CD = bar_CD;
    }

    public String getProduct_NM() {
        return Product_NM;
    }

    public void setProduct_NM(String product_NM) { this.Product_NM = product_NM; }

    public String getProduct_DCNM() {
        return Product_DCNM;
    }

    public void setProduct_DCNM(String product_DCNM) {
        this.Product_DCNM = product_DCNM;
    }

    public String getProduct_DAYCNT() {
        return Product_DAYCNT;
    }

    public void setProduct_DAYCNT(String product_DAYCNT) {
        this.Product_DAYCNT = product_DAYCNT;
    }

}
