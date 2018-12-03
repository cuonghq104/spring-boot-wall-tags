package ptit.cuonghq.walltag.controller;

import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.requestmodels.AddNewImageRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.UploadFileRequestBody;
import ptit.cuonghq.walltag.models.responsemodels.UploadFileResponse;
import ptit.cuonghq.walltag.services.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ptit.cuonghq.walltag.services.ProviderService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Api(value = "common", description = "Common Apis", produces = "application/json", tags = {"Common"})
@RequestMapping("common")
public class CommonController {
//
//    @Autowired
//    private CommonService service;
//
//    @Autowired
//    private ProviderService providerService;
//
//    @PostMapping(value = "/image")
//    private ResponseEntity<ResponseObjectResult> addNewImage(@RequestHeader("Authorization") int id, @RequestBody AddNewImageRequestBody requestBody) {
//        User user = providerService.checkProvider(id);
//        if (user == null) {
//            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization", null), HttpStatus.UNAUTHORIZED);
//        }
//
//        ResponseObjectResult result = service.addNewImage(requestBody);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @DeleteMapping(value = "/image/{id_image}")
//    private ResponseEntity<ResponseObjectResult> deleteImage(@RequestHeader("Authorization") int id, @PathVariable("id_image") int idImage) {
//        User user = providerService.checkProvider(id);
//        if (user == null) {
//            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization", null), HttpStatus.UNAUTHORIZED);
//        }
//
//        ResponseObjectResult result = service.deleteImage(idImage);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/upload")
//    @ApiOperation(value = "Upload image")
//    public ResponseEntity<ResponseObjectResult> uploadFile(@RequestHeader(value = "Authorization") int id, @RequestBody UploadFileRequestBody requestBody) {
//        String fileName = service.storeFile(requestBody, id);
//        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/common/image/")
//                .path(fileName)
//                .toUriString();
//        UploadFileResponse response = new UploadFileResponse(fileName, fileDownloadUrl);
//        return new ResponseEntity<>(new ResponseObjectResult(true, 200, "OK", response), HttpStatus.OK);
//
//    }
//
//    @GetMapping("/images/{fileName:.+}")
//    @ApiOperation(value = "Get images")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//        Resource resource = service.loadFileAsResource(fileName);
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
}
