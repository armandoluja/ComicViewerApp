package edu.rosehulman.lujasaa.comicviewer;

/**
 * Created by lujasaa on 1/13/2016.
 */
public class Comic {
    private int num;
    private int month;
    private int day;
    private int year;
    private String link;
    private String news;
    private String transcript;
    private String safe_title;
    private String alt;

    public Comic() {
    }

    @Override
    public String toString() {
        return "Comic{" +
                "num=" + num +
                ", month=" + month +
                ", day=" + day +
                ", year=" + year +
                ", link='" + link + '\'' +
                ", news='" + news + '\'' +
                ", transcript='" + transcript + '\'' +
                ", safe_title='" + safe_title + '\'' +
                ", alt='" + alt + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public int getNum() {

        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getSafe_title() {
        return safe_title;
    }

    public void setSafe_title(String safe_title) {
        this.safe_title = safe_title;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String img;
    private String title;
}
