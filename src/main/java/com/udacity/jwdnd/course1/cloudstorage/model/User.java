package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.List;

public class User {
    private Integer userid;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;
    private List<Notes> notesList;
    private List<Files> filesList;
    private List<Credentials> credentialsList;

    public List<Files> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<Files> filesList) {
        this.filesList = filesList;
    }

    public List<Credentials> getCredentialsList() {
        return credentialsList;
    }

    public void setCredentialsList(List<Credentials> credentialsList) {
        this.credentialsList = credentialsList;
    }

    public List<Notes> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Notes> notesList) {
        this.notesList = notesList;
    }

    /*public User(Integer userid, String username, String salt, String password,
                String firstname, String lastname, List<Notes> notesList, List<Files> filesList,
                List<Credentials> credentialsList) {
        this.userid = userid;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.notesList = notesList;
        this.filesList = filesList;
        this.credentialsList = credentialsList;
    }*/

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
