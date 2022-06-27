package com.onlineshop.theshop.minio;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.onlineshop.theshop.configproperties.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    ConfigProperties configProperties;

    @Autowired
    AmazonS3 s3Client;

    final Logger logger = LoggerFactory.getLogger(FileService.class);

    public String uploadFileAndGetURL(String fileName, String fileEnding, MultipartFile multipartFile) {
        uploadFile(fileName, fileEnding, multipartFile);
        return "http://"+configProperties.getMinio().getOuterHostname()+":"+configProperties.getMinio().getPort()+"/"+configProperties.getMinio().getBucket()+"/"+fileName+fileEnding;
    }
    public UUID uploadImageAndGetId(MultipartFile multipartFile) {
        UUID fileId = UUID.randomUUID();
        uploadFile(fileId.toString(), ".png", multipartFile);
        return fileId;
    }
    public void removeImage(UUID imageId) {
        s3Client.deleteObject(configProperties.getMinio().getBucket() ,imageId.toString()+".png");
    }

    public void uploadFile(String filename, String fileEnding, MultipartFile multipartFile) {
        try {
            logger.info("Uploading a new object to S3 from a file");
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            s3Client.putObject(configProperties.getMinio().getBucket(), filename+fileEnding, multipartFile.getInputStream(), metadata);
        } catch (AmazonServiceException ase) {
            logger.error("Caught an AmazonServiceException, which " + "means your request made it "
                    + "to Amazon S3, but was rejected with an error response" + " for some reason.");
            logger.error("Error Message: " + ase.getMessage());
            logger.error("HTTP Status Code: " + ase.getStatusCode());
            logger.error("AWS Error Code: " + ase.getErrorCode());
            logger.error("Error Type: " + ase.getErrorType());
            logger.error("Request ID: " + ase.getRequestId());

        } catch (AmazonClientException ace) {
            logger.error("Caught an AmazonClientException, which " + "means the client encountered " + "an internal error while trying to "
                    + "communicate with S3, " + "such as not being able to access the network.");
            logger.error("Error Message: " + ace.getMessage());
            logger.error("Cause: " + ace.getCause());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
