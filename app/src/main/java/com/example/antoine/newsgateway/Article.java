package com.example.antoine.newsgateway;

/**
 * Created by antoine on 4/18/17.
 */

import java.io.Serializable;

public class Article implements Serializable {

    //Var String Declaration
    private String Author;
    private String Title;
    private String Description;
    private String Url;
    private String UrlToImage;
    private String PublishedAt;

    /**
     * Article creator
     */
    public Article(String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.Author = author;
        this.Title = title.trim();
        this.Description = description.trim();
        this.Url = url;
        this.UrlToImage = urlToImage;
        this.PublishedAt = publishedAt;
    }

    /**
     * Author getter
     */
    public String getAuthor() {
        return Author;
    }

    /**
     * Author setter
     */
    public void setAuthor(String author) {
        this.Author = author;
    }

    /**
     * Title getter
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Title setter
     */
    public void setTitle(String title) {
        this.Title = title;
    }

    /**
     * Description getter
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Description setter
     */
    public void setDescription(String description) {
        this.Description = description;
    }

    /**
     * Url getter
     */
    public String getUrl() {
        return Url;
    }

    /**
     * Url setter
     */
    public void setUrl(String url) {
        this.Url = url;
    }

    /**
     * UrlToImage getter
     */
    public String getUrlToImage() {
        return UrlToImage;
    }

    /**
     * UrlToImage setter
     */
    public void setUrlToImage(String urlToImage) {
        this.UrlToImage = urlToImage;
    }

    /**
     * PublishedAt getter
     */
    public String getPublishedAt() {
        return PublishedAt;
    }

    /**
     * PublishedAt setter
     */
    public void setPublishedAt(String publishedAt) {
        this.PublishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + Author + '\'' +
                ", title='" + Title + '\'' +
                ", description='" + Description + '\'' +
                ", url='" + Url + '\'' +
                ", urlToImage='" + UrlToImage + '\'' +
                ", publishedAt='" + PublishedAt + '\'' +
                '}';
    }
}
