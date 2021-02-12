package top.imwonder.myblog.services;

import java.io.File;
import java.io.InputStream;

public interface OssApiService {
    void fetchByUrl(String url,String target,String name);

    void uploadFile(byte[] file,String target,String name);

    void uploadFile(InputStream is,String target,String name);

    void uploadFile(File file,String target,String name);

    String authUrl(String baseUrl, long expires);
}
