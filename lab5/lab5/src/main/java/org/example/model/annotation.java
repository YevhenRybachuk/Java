package org.example.model;

public class annotation {
    private int annotationId;
    private String language;
    private String text;
    private int articleId;

    public annotation() {}

    public annotation(int annotationId, String language, String text, int articleId) {
        this.annotationId = annotationId;
        this.language = language;
        this.text = text;
        this.articleId = articleId;
    }

    public int getAnnotationId() { return annotationId; }
    public void setAnnotationId(int annotationId) { this.annotationId = annotationId; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public int getArticleId() { return articleId; }
    public void setArticleId(int articleId) { this.articleId = articleId; }
}