package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

import java.util.List;

/**
 * The type User.
 * @author Sudhir Tyagi
 */
@Data
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
}
