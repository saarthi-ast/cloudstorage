package com.udacity.jwdnd.course1.cloudstorage.model;

public class ApplicationForm {
    private Credentials credentials;
    private Files files;
    private Notes notes;
    private String activeTab;

    public ApplicationForm() {
        this.credentials = new Credentials();
        this.files = new Files();
        this.notes = new Notes();
    }

    public ApplicationForm(Credentials credentials, Files files, Notes notes, String activeTab) {
        this.credentials = credentials;
        this.files = files;
        this.notes = notes;
        this.activeTab = activeTab;
    }

    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }
}
