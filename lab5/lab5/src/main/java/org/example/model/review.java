package org.example.model;
import java.sql.Date;

public class review {
    private int reviewId;
    private Date reviewDate;
    private String comments;
    private int articleId;
    private int reviewerId;

    public review() {}

    public review(int reviewId, Date reviewDate, String comments, int articleId, int reviewerId) {
        this.reviewId = reviewId;
        this.reviewDate = reviewDate;
        this.comments = comments;
        this.articleId = articleId;
        this.reviewerId = reviewerId;
    }

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }
    public Date getReviewDate() { return reviewDate; }
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public int getArticleId() { return articleId; }
    public void setArticleId(int articleId) { this.articleId = articleId; }
    public int getReviewerId() { return reviewerId; }
    public void setReviewerId(int reviewerId) { this.reviewerId = reviewerId; }
}