package com.udacity.jwdnd.course1.cloudstorage.model;

public class ApplicationForm {
    private Credentials credentials;
    private Files files;
    private Notes notes;

    public ApplicationForm() {
        this.credentials = new Credentials();
        this.files = new Files();
        this.notes = new Notes();
    }

    public ApplicationForm(Credentials credentials, Files files, Notes notes) {
        this.credentials = credentials;
        this.files = files;
        this.notes = notes;
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
