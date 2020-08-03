package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Login form.
 * @author Sudhir Tyagi
 */
@Data
@AllArgsConstructor
public class LoginForm {
    private String inputUsername;
    private String inputPassword;
}
