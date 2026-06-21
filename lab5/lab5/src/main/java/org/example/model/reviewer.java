package org.example.model;

public class reviewer {
    private int reviewerId;
    private String lastName;
    private String firstName;
    private String email;
    private String scientificDegree;

    public reviewer() {}

    public reviewer(int reviewerId, String lastName, String firstName, String email, String scientificDegree) {
        this.reviewerId = reviewerId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.scientificDegree = scientificDegree;
    }

    public int getReviewerId() { return reviewerId; }
    public void setReviewerId(int reviewerId) { this.reviewerId = reviewerId; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getScientificDegree() { return scientificDegree; }
    public void setScientificDegree(String scientificDegree) { this.scientificDegree = scientificDegree; }
}