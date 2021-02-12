package top.imwonder.myblog.services.impl;

import java.io.File;
import java.io.InputStream;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.imwonder.myblog.services.OssApiService;

@Service
public class QiniuApiServiceImpl implements OssApiService {

    @Autowired
    private Auth auth;

    @Override
    public void fetchByUrl(String url, String target, String name) {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        // ...其他参数参考类注释
        String bucket = "your bucket name";
        String key = "your file key";
        BucketManager bucketManager = new BucketManager(auth, cfg);
        // 抓取网络资源到空间
        try {
            FetchRet fetchRet = bucketManager.fetch(url, bucket, key);
            System.out.println(fetchRet.hash);
            System.out.println(fetchRet.key);
            System.out.println(fetchRet.mimeType);
            System.out.println(fetchRet.fsize);
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
    }

    @Override
    public void uploadFile(byte[] file, String target, String name) {// 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // ...生成上传凭证，然后准备上传
        String bucket = "your bucket name";
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "null";
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file, key, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
    }

    @Override
    public void uploadFile(InputStream is, String target, String name) {// 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String bucket = "your bucket name";
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(is, key, upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
    }

    @Override
    public void uploadFile(File file, String target, String name) {// 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String bucket = "your bucket name";
        // 如果是Windows情况下，格式是 D:\\qiniu\\test.png
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.getAbsolutePath(), key, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
    }

    @Override
    public String authUrl(String baseUrl, long expires) {
        return auth.privateDownloadUrl(baseUrl, expires);
    }

}
