package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Application form.
 * @author Sudhir Tyagi
 */
@Data
@AllArgsConstructor
public class ApplicationForm {
    private Credentials credentials;
    private Files files;
    private Notes notes;

    /**
     * Instantiates a new Application form.
     * Class constructor.
     */
    public ApplicationForm() {
        this.credentials = new Credentials();
        this.files = new Files();
        this.notes = new Notes();
    }
}
