package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {
    private String credentialUrl;
    private String credentialUsername;
    private String credentialPassword;
    private Integer userId;

    public CredentialForm(String credentialUrl, String credentialUsername, String credentialPassword, Integer userId) {
        this.credentialUrl = credentialUrl;
        this.credentialUsername = credentialUsername;
        this.credentialPassword = credentialPassword;
        this.userId = userId;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }

    public String getCredentialUsername() {
        return credentialUsername;
    }

    public void setCredentialUsername(String credentialUsername) {
        this.credentialUsername = credentialUsername;
    }

    public String getCredentialPassword() {
        return credentialPassword;
    }

    public void setCredentialPassword(String credentialPassword) {
        this.credentialPassword = credentialPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
