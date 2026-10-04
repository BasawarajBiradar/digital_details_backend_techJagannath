package com.techjagannath.digitalidentification.utils.s3fileupload;

import com.techjagannath.digitalidentification.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
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
import java.util.List;
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


    public String uploadNoticeAttachments(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isBlank()) {
                throw new FileUploadException("Invalid file name");
            }

            String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID() + "." + extension;
            String s3Key = "notice-attachments/" + fileName;
            byte[] fileBytes;

            if (file.getContentType() != null &&
                    file.getContentType().startsWith("image/")) {

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                Thumbnails.of(file.getInputStream())
                        .scale(1.0)
                        .outputQuality(0.90)
                        .toOutputStream(outputStream);

                fileBytes = outputStream.toByteArray();

            } else {
                // PDF / other non-image files
                fileBytes = file.getBytes();
            }

            PutObjectRequest putObjectRequest =
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Key)
                            .contentType(file.getContentType())
                            .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(fileBytes)
            );

            return s3Key;

        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException("Failed to upload image");
        }
    }

    public String uploadHomeworkImage(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();

            String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

            String fileName = UUID.randomUUID() + "." + extension;

            String s3Key = "homework-images/" + fileName;

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Thumbnails.of(file.getInputStream())
                    .scale(1.0)
                    .outputQuality(0.90)
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

    public String uploadSchoolLogo(MultipartFile file) {

        try {

            if (file == null || file.isEmpty()) {
                throw new FileUploadException("File is empty");
            }

            String contentType = file.getContentType();

            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null ||
                    !originalFileName.contains(".")) {

                throw new FileUploadException(
                        "Invalid file name"
                );
            }

            String extension =
                    originalFileName.substring(
                            originalFileName.lastIndexOf(".") + 1
                    );

            String fileName =
                    UUID.randomUUID() + "." + extension;

            String s3Key =
                    "school-logo/" + fileName;

            PutObjectRequest putObjectRequest =
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Key)
                            .contentType(contentType)
                            .build();

            if ("image/svg+xml".equals(contentType)) {
                s3Client.putObject(
                        putObjectRequest,
                        RequestBody.fromBytes(
                                file.getBytes()
                        )
                );

            } else {

                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream();

                Thumbnails.of(file.getInputStream())
                        .size(500, 500)
                        .outputQuality(0.7)
                        .toOutputStream(outputStream);

                s3Client.putObject(
                        putObjectRequest,
                        RequestBody.fromBytes(
                                outputStream.toByteArray()
                        )
                );
            }

            return s3Key;

        } catch (Exception e) {

            throw new FileUploadException(
                    "Failed to upload image"
            );
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


    public String uploadTapRecordImage(MultipartFile file) {
        try {

            if (file == null || file.isEmpty()) {
                throw new FileUploadException("File is empty ");
            }

            String contentType = file.getContentType();
            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null || !originalFileName.contains(".")) {
                throw new FileUploadException("Invalid file name");
            }

            String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID()+"."+extension;

            String s3Key = "attendance-tap-image/"+ fileName;

            PutObjectRequest putObjectRequest =
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Key)
                            .contentType(contentType)
                            .build();

            if ("image/svg+xml".equals(contentType)) {
                s3Client.putObject(
                        putObjectRequest,
                        RequestBody.fromBytes(
                                file.getBytes()
                        )
                );

            } else {

                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream();

                Thumbnails.of(file.getInputStream())
                        .size(500, 500)
                        .outputQuality(0.7)
                        .toOutputStream(outputStream);

                s3Client.putObject(
                        putObjectRequest,
                        RequestBody.fromBytes(
                                outputStream.toByteArray()
                        )
                );
            }

            return s3Key;

        } catch (Exception e) {

            throw new FileUploadException(
                    "Failed to upload image"
            );
        }
    }

}