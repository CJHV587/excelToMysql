package com.aliyun.sqlsample.entity;

public class Reference {
    private String id;
    private String count;
    private String recommend;

    @Override
    public String toString() {
        return "Reference{" +
                "id='" + id + '\'' +
                ", count='" + count + '\'' +
                ", recommend='" + recommend + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommond) {
        this.recommend = recommond;
    }

    public Reference(String id, String count, String recommend) {
        this.id = id;
        this.count = count;
        this.recommend = recommend;
    }

    public Reference() {
    }
}
