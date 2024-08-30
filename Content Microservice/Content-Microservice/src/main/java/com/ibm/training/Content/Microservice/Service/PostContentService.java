package com.ibm.training.Content.Microservice.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.training.Content.Microservice.Entity.PostContent;


@Service
public interface PostContentService {

	
	 PostContent uploadImagePost(String contentType, String imageFile, String caption) throws Exception;

	    PostContent uploadTextPost(String contentType, String contentText);

	    PostContent uploadImageTextPost(String contentType, String contentText, MultipartFile imageFile) throws Exception;

}
