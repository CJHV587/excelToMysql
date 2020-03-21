package com.aliyun.sqlsample.entity;

public class Function {
    private String id;
    private String name;
    private String functionCategory;
    private String detail;
    private String link;
    private String count;

    @Override
    public String toString() {
        return "Function{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", functionCategory='" + functionCategory + '\'' +
                ", detail='" + detail + '\'' +
                ", link='" + link + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunctionCategory() {
        return functionCategory;
    }

    public void setFunctionCategory(String functionCategory) {
        this.functionCategory = functionCategory;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Function(String id, String name, String functionCategory, String detail, String link, String count) {
        this.id = id;
        this.name = name;
        this.functionCategory = functionCategory;
        this.detail = detail;
        this.link = link;
        this.count = count;
    }

    public Function() {
    }
}
