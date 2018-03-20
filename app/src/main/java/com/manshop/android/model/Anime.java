package com.manshop.android.model;

public class Anime {
    private Integer id;
    private String title;
    private String detail;
    private String year;
    private String episodes;
    private String produce;
    private String producer;
    private String pictrue;

    public Anime() {
    }

    public Anime(Integer id, String title, String detail, String year, String episodes, String produce, String producer) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.year = year;
        this.episodes = episodes;
        this.produce = produce;
        this.producer = producer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getPictrue() {
        return pictrue;
    }

    public void setPictrue(String pictrue) {
        this.pictrue = pictrue;
    }

}
