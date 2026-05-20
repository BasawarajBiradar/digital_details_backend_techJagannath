package com.techjagannath.digitalidentification.utils.s3fileupload;

import com.techjagannath.digitalidentification.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Utils {

    private final S3Client s3Client;

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
            return getUrl(s3Key);

        } catch (Exception e) {
            throw new FileUploadException("Failed to upload image");
        }
    }

    public String getUrl(String s3Key) {

        return String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                region,
                s3Key
        );
    }
}