package top.imwonder.myblog.services;

import top.imwonder.myblog.domain.OssResource;

public interface OssService {
    String getUrlById(String id);

    void calcPath(OssResource or);
}