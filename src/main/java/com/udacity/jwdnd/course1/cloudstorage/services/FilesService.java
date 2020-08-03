package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

/**
 * The type Files service.
 * @author Sudhir Tyagi
 */
@Service
public class FilesService {
    private final FilesMapper filesMapper;
    private final UserService userService;

    /**
     * Class constructor.
     *
     * @param filesMapper the files mapper
     * @param userService the user service
     */
    public FilesService(FilesMapper filesMapper, UserService userService) {
        this.filesMapper = filesMapper;
        this.userService = userService;
    }

    /**
     * Save file string.
     *
     * @param file     the file
     * @param username the username
     * @return the string
     */
    public String saveFile(MultipartFile file, String username) {
        try {
            String filename = file.getOriginalFilename();
            if (isFileNameUsed(filename)) {
                return FILE_UPLOAD_DUPLICATE_ERROR;
            } else {
                User loggedinUser = userService.getUserByName(username);
                byte[] bytes = file.getBytes();
                if(bytes.length > MAX_FILE_SIZE){
                    return FILE_UPLOAD_SIZE_ERROR;
                }
                Files userFile = new Files(null,
                        file.getOriginalFilename(),
                        file.getContentType(),
                        String.valueOf(file.getSize()),
                        loggedinUser.getUserid(), bytes);
                filesMapper.saveFile(userFile);
            }
        } catch (IOException e) {
            return FILE_UPLOAD_ERROR_GENERIC;
        }
        return SUCCESS;
    }

    /**
     * Gets file by name.
     *
     * @param filename the filename
     * @param username the username
     * @return the file by name
     */
    public Files getFileByName(String filename, String username) {
        Integer userId = userService.getUseridByName(username);
        return filesMapper.getFileByNameAndUserId(filename,userId);
    }

    /**
     * Gets files by user name.
     *
     * @param username the username
     * @return the files by user name
     */
    public List<Files> getFilesByUserName(String username) {
        Integer userId = userService.getUseridByName(username);
        return filesMapper.getFilesByUserId(userId);
    }

    /**
     * Get filenames by user name list.
     *
     * @param username the username
     * @return the list
     */
    public List<String> getFilenamesByUserName(String username){
        List<Files> userFiles = getFilesByUserName(username);
        return userFiles.stream().map(x -> x.getFilename()).collect(Collectors.toList());
    }

    /**
     * Delete file by name string.
     *
     * @param filename the filename
     * @param username the username
     * @return the string
     */
    public String deleteFileByName(String filename, String username) {
        try{
            Integer userId = userService.getUseridByName(username);
            Integer deleteCt = filesMapper.deleteFileByNameAndUserId(filename, userId);
        }catch (Exception e){
            return FILE_DELETE_ERROR;
        }
        return SUCCESS;
    }

    /**
     * Is file name used boolean.
     *
     * @param filename the filename
     * @return the boolean
     */
    public Boolean isFileNameUsed(String filename) {
        return (null != filesMapper.getFileByName(filename));
    }

}
