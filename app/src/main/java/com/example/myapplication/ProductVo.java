package com.example.myapplication;

public class ProductVo {
    private Integer _id;
    private String name;
    private String type;
    private String date;
    private String code;

    public ProductVo(){
    }
    public ProductVo(Integer _id, String name, String type, String date, String code) {
        this._id=_id;
        this.name=name;
        this.type=type;
        this.date=date;
        this.code=code;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
