package com.ibm.training.Content.Microservice.Controller;




import com.ibm.training.Content.Microservice.Service.CloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/cloud-storage")
public class ImageUploadController {

    @Autowired
    private CloudStorageService cloudStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Delegate the upload logic to the service layer
            String objectName = cloudStorageService.uploadImage(file);
            return ResponseEntity.ok("File uploaded successfully and url is:" + objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
