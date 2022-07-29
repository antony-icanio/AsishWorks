package com.example.AsishWorks.controller;


import com.example.AsishWorks.model.Photo;
import com.example.AsishWorks.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam("title") String title,
                           @RequestParam("image") MultipartFile image)
            throws IOException {
        String id = fileService.addPhoto(title, image);
        return  "your photo's id "+id;
    }

    @GetMapping("/photos/{id}")
    public Photo getPhoto(@PathVariable String id) {
        return fileService.getPhoto(id);
    }



    //      File upload via postman and save localSystem in your choosing path
    @PostMapping("/fileUploadMethod1")
    public String fileUpload(@RequestParam("file") MultipartFile file)
    {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File dir = new File("D:\\icanio" + File.separator + "newFolder");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                //This two line also work like same
                //BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
               // FileOutputStream stream =new FileOutputStream(serverFile);
                FileOutputStream stream =new FileOutputStream(serverFile);
                stream.write(bytes);
                stream.close();
                return "sucessfull upload";
            } catch (Exception e) {
                return "not upload";
            }
        }
        return "file is empty";
    }


    // Another Method ofFile upload via postman and save local system
    // it was a good method for save a file in your system
    @PostMapping("/fileUploadMethod2")
    public ResponseEntity fileUploadMethod2(@RequestParam("file") MultipartFile file) {
        try {
            Path path = Paths.get("D:\\icanio\\" + StringUtils.cleanPath(file.getOriginalFilename()));
            File newFile = new File("D:\\icanio\\" + StringUtils.cleanPath(file.getOriginalFilename()));
            if(!newFile.exists())
                newFile.mkdirs();
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok("not uploaded");
        }
        return ResponseEntity.ok("uploaded");
    }


    //  Download a zip file but only file will be make zip file
    @GetMapping("/downloadZipFile")
    public void downloadZipFile(HttpServletResponse response) {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=download.zip");
        List<String> listOfFileNames=new ArrayList<>();
       listOfFileNames.add("C:\\Users\\aasis\\Documents\\workspace-spring-tool-suite-4-4.13.1.RELEASE\\interviewStatus\\src\\main\\resources\\spider.png");
       listOfFileNames.add("C:\\Users\\aasis\\Documents\\workspace-spring-tool-suite-4-4.13.1.RELEASE\\interviewStatus\\src\\main\\resources\\ironman.jpg");
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            for(String fileName : listOfFileNames) {
                File myFile=new File(fileName);
                if(!myFile.exists())
                {
                    System.out.println(fileName+" This file not found");
                }
                FileSystemResource fileSystemResource = new FileSystemResource(fileName);
                ZipEntry zipEntry = new ZipEntry(fileSystemResource.getFilename());
                zipEntry.setSize(fileSystemResource.contentLength());
                zipEntry.setTime(System.currentTimeMillis());
                zipOutputStream.putNextEntry(zipEntry);
               // StreamUtils.copy(fileSystemResource.getInputStream(), zipOutputStream);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.finish();
        } catch (IOException e) {
            System.out.println(e);
        }
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
    public void unzip() throws IOException {
        //String fileZip = "C:\\Users\\aasis\\Downloads\\download.zip";
        String fileZip="C:\\Users\\aasis\\Documents\\workspace-spring-tool-suite-4-4.13.1.RELEASE\\demo-mongo-2.zip";
        File destDir = new File("D:\\ziptry");
        if(!destDir.exists())
            destDir.mkdirs();
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = new File(destDir, String.valueOf(zipEntry));
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }


    //  Input Zip File to Uploading and Extract your system
    @PostMapping("/inputZipFileExtracting")
    public String inputZipExtracting(@RequestParam("file") MultipartFile file)
    {
        try {
            Path path = Paths.get("D:\\source\\" + StringUtils.cleanPath(file.getOriginalFilename()));
            File myFile = new File("D:\\source\\" + StringUtils.cleanPath(file.getOriginalFilename()));
            if(!myFile.exists())
                myFile.mkdirs();
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            String fileZip="D:\\source\\" + StringUtils.cleanPath(file.getOriginalFilename());
            File destDir = new File("D:\\ExtractingFolder");
            if(!destDir.exists())
                destDir.mkdir();
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(destDir, String.valueOf(zipEntry));
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Some Error "+e);
        }
        return "Successfully uploaded";
    }


}
