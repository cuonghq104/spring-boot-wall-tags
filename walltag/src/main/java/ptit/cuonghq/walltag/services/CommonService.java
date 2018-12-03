package ptit.cuonghq.walltag.services;

import org.springframework.beans.factory.annotation.Autowired;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.Image;
import ptit.cuonghq.walltag.models.repositories.ImageRepository;
import ptit.cuonghq.walltag.models.requestmodels.AddNewImageRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.UploadFileRequestBody;
import ptit.cuonghq.walltag.utils.FileStorageException;
import ptit.cuonghq.walltag.utils.FileStorageProperties;
import ptit.cuonghq.walltag.utils.MyFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CommonService {
//
//    private final Path fileStorageLocation;
//
//    public CommonService(FileStorageProperties properties) {
//        this.fileStorageLocation = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
//        try {
//            Files.createDirectories(this.fileStorageLocation);
//        } catch (IOException e) {
//            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
//        }
//    }
//
//    public String storeFile(UploadFileRequestBody requestBody, int ownerId) {
//        String fileName = ownerId + "_" + System.currentTimeMillis() + "." + requestBody.getExt();
//        Path targetLocation = this.fileStorageLocation.resolve(fileName);
//        try {
//            FileOutputStream fos = new FileOutputStream(targetLocation.toFile());
//            byte[] btData = new BASE64Decoder().decodeBuffer(requestBody.getData());
//            fos.write(btData);
//            fos.flush();
//            return fileName;
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }
//    }
//
//    public Resource loadFileAsResource(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if(resource.exists()) {
//                return resource;
//            } else {
//                throw new MyFileNotFoundException("File not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
//        }
//    }
//
//
//    @Autowired
//    private ImageRepository imageRepository;
//
//    public ResponseObjectResult addNewImage(AddNewImageRequestBody requestBody) {
//        Image image = new Image();
//        image.setImageUrl(requestBody.getImageUrl());
//        image.setBelongTo(requestBody.getBelongTo());
//        image.setType(requestBody.getType());
//
//        Image saved = imageRepository.save(image);
//        return new ResponseObjectResult(true, 201, "OK", saved);
//    }
//
//    public ResponseObjectResult deleteImage(int id) {
//        Image image = imageRepository.findById(id).orElse(null);
//        if (image == null) {
//            return new ResponseObjectResult(false, 400, "Image is not exist", null);
//        }
//        imageRepository.delete(image);
//        return new ResponseObjectResult(true, 200, "OK", null);
//    }
}
