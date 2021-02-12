package top.imwonder.myblog.services;

import java.io.File;

import top.imwonder.myblog.domain.OssResource;

public interface OssResourceService {

    /**
     * 通过数据库中的资源 ID ，获取资源对应的 URL
     * @param id
     * @return
     */
    String getUrlById(String id);

    /**
     * 通过资源模型，获取资源对应的 URL，并存入原对象的 path 属性
     * @param or
     */
    void calcPath(OssResource or);

    void uploadResource(File file,OssResource or);

    void uploadResource(byte[] file,OssResource or);

    void fetchResourceByUrl(String url,OssResource or);

    int countType(String category);

    
}