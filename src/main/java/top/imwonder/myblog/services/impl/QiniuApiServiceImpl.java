package top.imwonder.myblog.services.impl;

import java.io.File;
import java.io.InputStream;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.myblog.exception.WonderException;
import top.imwonder.myblog.services.OssApiService;

@Slf4j
@Service
public class QiniuApiServiceImpl implements OssApiService {

    @Autowired
    private Auth auth;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private Gson gson;

    @Override
    public FileInfo fetchByUrl(String url, String bucket, String name) {
        // 抓取网络资源到空间
        try {
            FetchRet fetchRet = bucketManager.fetch(url, bucket, name);
            FileInfo fi = new FileInfo();
            fi.setHash(fetchRet.hash);
            fi.setKey(fetchRet.key);
            fi.setMimeType(fetchRet.mimeType);
            fi.setFsize(fetchRet.fsize);
            log.info(fi.toString());
            return fi;
        } catch (QiniuException ex) {
            log.error(ex.response.toString());
            throw new WonderException("500", "抓取文件失败！");
        }
    }

    @Override
    public FileInfo uploadFile(byte[] file, String bucket, String name) {
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file, name, upToken);
            // 解析上传成功的结果
            FileInfo putRet = gson.fromJson(response.bodyString(), FileInfo.class);
            log.info(putRet.toString());
            return putRet;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
            throw new WonderException("500", "上传文件失败！");
        }
    }

    @Override
    public FileInfo uploadFile(InputStream is, String bucket, String name) {
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(is, name, upToken, null, null);
            // 解析上传成功的结果
            FileInfo putRet = gson.fromJson(response.bodyString(), FileInfo.class);
            log.info(putRet.toString());
            return putRet;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
            throw new WonderException("500", "上传文件失败！");
        }
    }

    @Override
    public FileInfo uploadFile(File file, String bucket, String name) {
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.getAbsolutePath(), name, upToken);
            // 解析上传成功的结果
            FileInfo putRet = new Gson().fromJson(response.bodyString(), FileInfo.class);
            log.info(putRet.toString());
            return putRet;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
            throw new WonderException("500", "上传文件失败！");
        }
    }

    @Override
    public void deleteFile(String bucket, String name) {
        try {
            bucketManager.delete(bucket, name);
        } catch (QiniuException ex) {
            // 如果遇到异常，说明删除失败
            log.error("{}", ex.code());
            log.error(ex.response.toString());
            if (ex.code() != 612) {
                throw new WonderException("500", "资源删除失败！");
            }
        }
    }

    @Override
    public FileInfo getFileInfo(String bucket, String name) {
        try {
            com.qiniu.storage.model.FileInfo fileInfo = bucketManager.stat(bucket, name);
            FileInfo fi = new FileInfo();
            fi.setHash(fileInfo.hash);
            fi.setFsize(fileInfo.fsize);
            fi.setMimeType(fileInfo.mimeType);
            fi.setPutTime(fileInfo.putTime);
            return fi;
        } catch (QiniuException ex) {
            log.error(ex.response.toString());
            throw new WonderException("500", "获取文件信息失败!");
        }
    }

    @Override
    public String authUrl(String baseUrl, long expires) {
        return auth.privateDownloadUrl(baseUrl, expires);
    }

}
