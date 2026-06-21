package org.example.model;

public class evaluation {
    private int evaluationId;
    private int score;
    private String recommendation;
    private int reviewId;

    public evaluation() {}

    public evaluation(int evaluationId, int score, String recommendation, int reviewId) {
        this.evaluationId = evaluationId;
        this.score = score;
        this.recommendation = recommendation;
        this.reviewId = reviewId;
    }

    public int getEvaluationId() { return evaluationId; }
    public void setEvaluationId(int evaluationId) { this.evaluationId = evaluationId; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }
}