package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Files.
 * @author Sudhir Tyagi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Files {
    private Integer fileId;
    private String filename ;
    private String contentType ;
    private String fileSize ;
    private Integer userId ;
    private byte[] fileData;
}
