package com.encore.thecatch.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.encore.thecatch.common.CatchException;
import com.encore.thecatch.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public S3Service(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    //다중파일 업로드
    public List<String> uploadList(String fileType, List<MultipartFile> multipartFile) {
        List<String> imgUrlList = new ArrayList<>();

        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        for (MultipartFile file : multipartFile) {
            if (file.isEmpty()) {
                return null;
            }
            String uploadFilePath = fileType + "/" + getFolderName();
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                String keyName = uploadFilePath + "/" + fileName;
                amazonS3Client.putObject(new PutObjectRequest(bucket, keyName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgUrlList.add(amazonS3Client.getUrl(bucket + "/Post/" + getFolderName(), fileName).toString());
            } catch (IOException e) {
                throw new NoSuchElementException("업로드 실패");
            }
        }
        return imgUrlList;
    }

    //개인 파일 업로드
    public String upload(String fileType, MultipartFile multipartFile) {
        String imgUrl = "";

        MultipartFile file = multipartFile;

        if (file.isEmpty()) {
            return null;
        }
        String uploadFilePath = fileType + "/" + getFolderName();
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            String keyName = uploadFilePath + "/" + fileName;
            amazonS3Client.putObject(new PutObjectRequest(bucket, keyName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imgUrl = amazonS3Client.getUrl(bucket + "/Post/" + getFolderName(), fileName).toString();
        } catch (IOException e) {
            throw new NoSuchElementException("업로드 실패");
        }

        return imgUrl;
    }

    // 이미지파일명 중복 방지
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 유효성 검사
    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new NoSuchElementException("파일명이 없음");
        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new NoSuchElementException("유효성 미적합");
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // 년/월/일 폴더명 반환
    private String getFolderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);

        return str.replace("-", "/");
    }

    // S3에 업로드된 파일 삭제
    public void deleteFile(String fileUrl) {
//        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
//        amazonS3Client.deleteObject(deleteObjectRequest);
        try{
            String fileKey = fileUrl.substring(49);
            amazonS3Client.deleteObject(bucket, fileKey);
        } catch (Exception exception) {
            throw new CatchException(ResponseCode.S3_DELETE_ERROR);
        }
    }
}
