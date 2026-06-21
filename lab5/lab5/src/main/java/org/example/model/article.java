package org.example.model;
import java.sql.Date;

public class article {
    private int articleId;
    private String title;
    private Date submissionDate;
    private String status;
    private int journalCategoryId;

    public article() {}

    public article(int articleId, String title, Date submissionDate, String status, int journalCategoryId) {
        this.articleId = articleId;
        this.title = title;
        this.submissionDate = submissionDate;
        this.status = status;
        this.journalCategoryId = journalCategoryId;
    }

    public int getArticleId() { return articleId; }
    public void setArticleId(int articleId) { this.articleId = articleId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Date getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(Date submissionDate) { this.submissionDate = submissionDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getJournalCategoryId() { return journalCategoryId; }
    public void setJournalCategoryId(int journalCategoryId) { this.journalCategoryId = journalCategoryId; }
}