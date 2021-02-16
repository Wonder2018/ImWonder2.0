package top.imwonder.myblog.services;

import java.io.File;
import java.io.InputStream;

import lombok.Data;

public interface OssApiService {
    FileInfo fetchByUrl(String url, String bucket, String name);

    FileInfo uploadFile(byte[] file, String bucket, String name);

    FileInfo uploadFile(InputStream is, String bucket, String name);

    FileInfo uploadFile(File file, String bucket, String name);

    FileInfo getFileInfo(String bucket, String name);

    void deleteFile(String bucket, String name);

    String authUrl(String baseUrl, long expires);

    @Data
    class FileInfo {
        /**
         * 文件名
         */
        private String key;
        /**
         * 文件hash值
         */
        private String hash;
        /**
         * 文件大小，单位：字节
         */
        private long fsize;
        /**
         * 文件上传时间，单位为：100纳秒
         */
        private long putTime;
        /**
         * 文件的mimeType
         */
        private String mimeType;
        /**
         * 文件上传时设置的endUser
         */
        private String endUser;
        /**
         * 文件的存储类型，0为普通存储，1为低频存储
         */
        private int type;
        /**
         * 文件的状态，0表示启用，1表示禁用
         */
        private int status;
        /**
         * 文件的md5值
         */
        private String md5;
    }
}
