package com.aliyun.sqlsample.entity;

public class Model {
    private String id;
    private String sql;
    private String title;
    private String detail;
    private String author;
    private String picture;
    private String logSample;
    private String indexConfig;
    private String category;
    private String function;
    private String version;
    private String deleteFlag;
    private String createTime;

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", sql='" + sql + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", author='" + author + '\'' +
                ", picture='" + picture + '\'' +
                ", logSample='" + logSample + '\'' +
                ", indexConfig='" + indexConfig + '\'' +
                ", category='" + category + '\'' +
                ", function='" + function + '\'' +
                ", version='" + version + '\'' +
                ", deleteFlag='" + deleteFlag + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLogSample() {
        return logSample;
    }

    public void setLogSample(String logSample) {
        this.logSample = logSample;
    }

    public String getIndexConfig() {
        return indexConfig;
    }

    public void setIndexConfig(String indexConfig) {
        this.indexConfig = indexConfig;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Model() {
    }

    public Model(String id, String sql, String title, String detail, String author, String picture, String logSample, String indexConfig, String category, String function, String version, String deleteFlag, String createTime) {
        this.id = id;
        this.sql = sql;
        this.title = title;
        this.detail = detail;
        this.author = author;
        this.picture = picture;
        this.logSample = logSample;
        this.indexConfig = indexConfig;
        this.category = category;
        this.function = function;
        this.version = version;
        this.deleteFlag = deleteFlag;
        this.createTime = createTime;
    }
}
