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

@Service
public class FilesService {
    private FilesMapper filesMapper;
    private UserService userService;

    public FilesService(FilesMapper filesMapper, UserService userService) {
        this.filesMapper = filesMapper;
        this.userService = userService;
    }

    //save file
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
            //e.printStackTrace();
            return FILE_UPLOAD_ERROR_GENERIC;
        }
        return SUCCESS;
    }

    //get file by name
    public Files getFileByName(String filename, String username) {
        Integer userId = userService.getUseridByName(username);
        return filesMapper.getFileByNameAndUserId(filename,userId);
    }

    //get files for a user
    public List<Files> getFilesByUserName(String username) {
        Integer userId = userService.getUseridByName(username);
        return filesMapper.getFilesByUserId(userId);
    }

    //get list of filenames for a user
    public List<String> getFilenamesByUserName(String username){
        List<Files> userFiles = getFilesByUserName(username);
        List<String> fileNames = userFiles.stream().map(x -> x.getFilename()).collect(Collectors.toList());
        return fileNames;
    }

    //delete file
    public String deleteFileByName(String filename, String username) {
        try{
            Integer userId = userService.getUseridByName(username);
            Integer deleteCt = filesMapper.deleteFileByNameAndUserId(filename, userId);
        }catch (Exception e){
            return FILE_DELETE_ERROR;
        }
        return SUCCESS;
    }

    //check if filename already used
    public Boolean isFileNameUsed(String filename) {
        return (null != filesMapper.getFileByName(filename));
    }

}
