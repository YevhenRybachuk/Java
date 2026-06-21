package org.example.model;

public class author {
    private int authorId;
    private String lastName;
    private String firstName;
    private String email;
    private String affiliation;

    public author() {}

    public author(int authorId, String lastName, String firstName, String email, String affiliation) {
        this.authorId = authorId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.affiliation = affiliation;
    }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAffiliation() { return affiliation; }
    public void setAffiliation(String affiliation) { this.affiliation = affiliation; }
}