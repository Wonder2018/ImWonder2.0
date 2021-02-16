package top.imwonder.myblog.services;

import java.io.File;

import top.imwonder.myblog.domain.OssResource;
import top.imwonder.myblog.services.OssApiService.FileInfo;

public interface OssResourceService {

    /**
     * 通过数据库中的资源 ID ，获取资源对应的 URL
     * 
     * @param id
     * @return
     */
    String getUrlById(String id);

    /**
     * 通过资源模型，获取资源对应的 URL，并存入原对象的 path 属性
     * 
     * @param or
     */
    void calcPath(OssResource or);

    FileInfo uploadResource(File file, OssResource or);

    FileInfo uploadResource(byte[] file, OssResource or);

    FileInfo fetchResourceByUrl(String url, OssResource or);

    FileInfo getFileInfo(OssResource or);

    void deleteFile(OssResource or);

    int countType(String category);

}