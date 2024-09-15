package com.ibm.training.UserAuthAndProfile.service;



import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

;


@Service
public class CloudStorageService {

    private final Storage storage;
    private final String bucketName = "myimages-jitu";  // Static bucket name
    private final String objectNamePrefix = "image_"; // Prefix for object names (if needed)

    public CloudStorageService() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public String uploadImage(MultipartFile file) throws IOException {
        // Generate a unique object name by appending timestamp
        String objectNameWithTimestamp = objectNamePrefix + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, objectNameWithTimestamp);

        // Determine the content type of the file
        String contentType = file.getContentType();
        if (contentType == null) {
            contentType = Files.probeContentType(Paths.get(file.getOriginalFilename()));
        }

        // Create BlobInfo with the correct content type
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(contentType)
                .build();

        // Temporary file to store the multipart data
        Path tempFile = Files.createTempFile(Paths.get(System.getProperty("java.io.tmpdir")), "upload-", file.getOriginalFilename());
        file.transferTo(tempFile.toFile());

        // Upload the file
        try {
            Storage.BlobWriteOption precondition;
            if (storage.get(bucketName, objectNameWithTimestamp) == null) {
                precondition = Storage.BlobWriteOption.doesNotExist();
            } else {
                precondition = Storage.BlobWriteOption.generationMatch(storage.get(bucketName, objectNameWithTimestamp).getGeneration());
            }
            storage.createFrom(blobInfo, tempFile, precondition);

            // Make the uploaded file public
            makeObjectPublic(objectNameWithTimestamp);
        } finally {
            // Clean up the temporary file
            Files.delete(tempFile);
        }

        // Construct the file URL
        String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, objectNameWithTimestamp);
        return fileUrl;
    }

    private void makeObjectPublic(String objectName) {
        BlobId blobId = BlobId.of(bucketName, objectName);
        Blob blob = storage.get(blobId);

        if (blob == null) {
            throw new RuntimeException("Object not found: " + objectName);
        }

        try {
            // Setting ACL for the object
            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        } catch (StorageException e) {
            System.err.println("Failed to make object public: " + e.getMessage());
            throw new RuntimeException("Failed to make object public", e);
        }
    }
}
