package com.example.AsishWorks.controller;

import com.example.AsishWorks.model.Photo;
import com.example.AsishWorks.model.Video;
import com.example.AsishWorks.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipOutputStream;


// Links

//https://baeldung-cn.com/spring-boot-mongodb-upload-file#large

//https://www.baeldung.com/java-compress-and-uncompress

@RestController
public class FileController {

    @Autowired
    FileService fileService;


    //               PHOTO FILE  HANDLING
    //              **********************

    // Photo Upload into DB
    @PostMapping("/photo")
    public String addPhoto(@RequestParam("title") String title, @RequestParam("image") MultipartFile image) throws IOException {
        String id = fileService.addPhoto(title, image);
        return  "your photo's id "+id;
    }

    // Photo Get from DB
    @GetMapping("/photo/{id}")
    public void getPhoto(@PathVariable String id,HttpServletResponse response) throws IOException {
        Photo photo=fileService.getPhoto(id);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="+photo.getFileName());
        FileCopyUtils.copy(photo.getImage().getData(), response.getOutputStream());
    }


    //               ZIP  FILE  HANDLING
    //              *********************


    //      File upload via postman and save localSystem in your choosing path
    @PostMapping("/fileUploadMethod1")
    public String fileUploadMethod1(@RequestParam("file") MultipartFile file){return fileService.fileUploadMethod1(file);}


    // Another Method ofFile upload via postman and save local system
    // it was a good method for save a file in your system
    @PostMapping("/fileUploadMethod2")
    public ResponseEntity fileUploadMethod2(@RequestParam("file") MultipartFile file) {return fileService.fileUploadMethod2(file);}

    //  Download a zip file but only file will be make zip file
    @GetMapping("/downloadZipFile")
    public void downloadZipFile(HttpServletResponse response) {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=download.zip");
        fileService.downloadZipFile(response);
    }


    // Download Zip file but this time we can make directory zipping a file
    @GetMapping("/downloadZipDirectory")
    public void downloadZipDirectory(HttpServletResponse response) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=dirDownload.zip");
       // FileOutputStream fos = new FileOutputStream("D:\\dirCompressed.zip");
        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
      //  ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File("C:\\Users\\aasis\\Documents\\workspace-spring-tool-suite-4-4.13.1.RELEASE\\demo-mongo-2.zip");
        fileService.zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        //fos.close();
    }


    //   Zip file extact with your wishable place
    @GetMapping("/extractZipFile")
    public void unzip() throws IOException {  fileService.unzip();}


    //  Input Zip File to Uploading and Extract your system
    @PostMapping("/inputZipFileExtracting")
    public String inputZipExtracting(@RequestParam("file") MultipartFile file){return fileService.inputZipExtracting(file);}




    //               VEDIO  FILE  HANDLING
    //              ***********************



    //  Upload vedio file
    @PostMapping("/video")
    public String addVideo(@RequestParam("title") String title,
                           @RequestParam("file") MultipartFile file) throws IOException {
         String id=fileService.addVideo(title, file);
         return id+" This id for get This vedio";
    }

    //Get video for direct Download
    @GetMapping("/video/{id}")
    public void streamVideo(@PathVariable String id, HttpServletResponse response) throws Exception {
        Video video = fileService.getVideo(id);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="+video.getFileName());
        FileCopyUtils.copy(video.getVideo().getData(), response.getOutputStream());
    }

}