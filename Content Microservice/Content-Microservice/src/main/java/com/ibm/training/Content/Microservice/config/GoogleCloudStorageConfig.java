package com.ibm.training.Content.Microservice.config;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleCloudStorageConfig {

    @Bean
    public Storage googleCloudStorage() {
        // Use default credentials and project ID, or specify them explicitly
        return StorageOptions.getDefaultInstance().getService();
    }
}
