package com.techjagannath.digitalidentification.utils.s3fileupload;

import com.techjagannath.digitalidentification.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Utils {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public String uploadStudentProfileImage(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();

            String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

            String fileName = UUID.randomUUID() + "." + extension;

            String s3Key = "student-profile-photo/" + fileName;

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Thumbnails.of(file.getInputStream())
                    .size(500, 500)
                    .outputQuality(0.7)
                    .toOutputStream(outputStream);

            PutObjectRequest putObjectRequest =
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Key)
                            .contentType(file.getContentType())
                            .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(
                            outputStream.toByteArray()
                    )
            );
            return s3Key;

        } catch (Exception e) {
            throw new FileUploadException("Failed to upload image");
        }
    }


    public String generatePreSignedUrl(String s3Key) {

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(s3Key)
                        .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();

        PresignedGetObjectRequest presignedRequest =
                s3Presigner.presignGetObject(
                        presignRequest
                );

        return presignedRequest.url().toString();
    }

    public void deleteFile(String s3Key) {
        try {
            DeleteObjectRequest deleteObjectRequest =
                    DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Key)
                            .build();

            s3Client.deleteObject(deleteObjectRequest);

        } catch (Exception e) {
            throw new FileUploadException("Failed to delete file");
        }
    }

}