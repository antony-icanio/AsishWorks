package com.example.AsishWorks.service;

import com.example.AsishWorks.model.Photo;
import com.example.AsishWorks.model.Video;
import com.example.AsishWorks.repository.PhotoRepository;
import com.example.AsishWorks.repository.VideoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

@Service
public class FileService {

    @Autowired
    PhotoRepository photoRepo;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    @Autowired
    private VideoRepository videoRepo;


    //               PHOTO FILE  HANDLING  SERVICE
    //              *******************************


    public String addPhoto(String title, MultipartFile image) throws IOException {
        Photo photo = new Photo(title);
        photo.setImage(
                new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        photo.setFileName(image.getOriginalFilename());
        photo = photoRepo.insert(photo);
        return photo.getId();
    }

    public Photo getPhoto(String id) {return photoRepo.findById(id).get();}


    //               ZIP  FILE  HANDLING SERVICE
    //              *****************************


    //      File upload via postman and save localSystem in your choosing path
    public String fileUploadMethod1(MultipartFile file) {
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
    public ResponseEntity fileUploadMethod2(MultipartFile file) {
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

    public void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));

                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    //  Download a zip file but only file will be make zip file
    public void downloadZipFile(HttpServletResponse response) {
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

    //   Zip file extact with your wishable place
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
    public String inputZipExtracting(MultipartFile file) {
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





    //               VEDIO  FILE  HANDLING  SERVICE
    //              ********************************



    //  Upload vedio file to DB
    public String addVideo(String title, MultipartFile file) throws IOException {
//        DBObject metaData = new BasicDBObject();
//        metaData.put("type", "video");
//        metaData.put("title", title);
//        metaData.put("fileName",file.getOriginalFilename());
//        ObjectId id = gridFsTemplate.store(
//                file.getInputStream(), file.getName(), file.getContentType(), metaData);
//        return id.toString();

        Video video=new Video();
        video.setFileName(file.getOriginalFilename());
        video.setVideo(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        video.setTitle(title);
         return  videoRepo.insert(video).getId();
    }

    //  Get vedio file from DB
    public Video getVideo(String id) throws IllegalStateException, IOException {
//        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
//        Video video = new Video();
//        video.setTitle(file.getMetadata().get("title").toString());
//        video.setFileName(file.getMetadata().get("fileName").toString());
//        video.setStream(operations.getResource(file).getInputStream());
        return videoRepo.findById(id).get();
    }

}
