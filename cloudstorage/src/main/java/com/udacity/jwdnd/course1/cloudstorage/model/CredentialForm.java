package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {
    private Integer credentialid;
    private String url;
    private String username;
    private String password;
    private Integer userId;

    public CredentialForm(Integer credentialid, String url, String username, String password, Integer userId) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CredentialForm{" +
                "credentialid=" + credentialid +
                ", credentialUrl='" + url + '\'' +
                ", credentialUsername='" + username + '\'' +
                ", credentialPassword='" + password + '\'' +
                ", userId=" + userId +
                '}';
    }
}
