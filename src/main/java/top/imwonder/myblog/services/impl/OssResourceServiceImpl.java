package top.imwonder.myblog.services.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Data;
import top.imwonder.myblog.Env;
import top.imwonder.myblog.SystemProperties;
import top.imwonder.myblog.dao.OssResourceDAO;
import top.imwonder.myblog.domain.OssResource;
import top.imwonder.myblog.services.OssApiService;
import top.imwonder.myblog.services.OssResourceService;
import top.imwonder.myblog.services.OssApiService.FileInfo;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OssResourceServiceImpl implements OssResourceService {

    @Autowired
    private OssResourceDAO orDAO;

    @Autowired
    private Env env;

    @Autowired
    private OssApiService oas;

    @Autowired
    private SystemProperties sp;

    private static Map<String, OssResourceInfo> resource;

    public String getUrlById(String orId) {
        if (resource == null) {
            resource = new HashMap<>();
        }
        OssResourceInfo res = resource.get(orId);
        if (res != null) {
            return res.getPath();
        }
        List<OssResource> ors = orDAO.loadMore("where w_id =?", new Object[] { orId });
        if (!ors.isEmpty()) {
            calcPath(ors.get(0));
            return ors.get(0).getPath();
        }
        return "";
    }

    public void calcPath(OssResource or) {
        if (resource == null) {
            resource = new HashMap<>();
        }
        OssResourceInfo res = resource.get(or.getId());
        if (res == null || System.currentTimeMillis() - res.getLastUpdate() > res.getTimeout()) {
            if (res == null) {
                res = new OssResourceInfo();
            }
            String prefix = or.getPrefix();
            String fileName = or.getPath();
            String finalUrl = oas.authUrl(getBaseUrl(prefix, fileName), env.getOssExpireInSeconds());
            res.setPath(finalUrl);
            res.setLastUpdate(System.currentTimeMillis());
            res.setTimeout(3300000);
            switch (or.getCategory()) {
                case "bg":
                    fileName = fileName.replace(".webp", "blur.webp");
                    String blurUrl = oas.authUrl(getBaseUrl(prefix, fileName), env.getOssExpireInSeconds());
                    res.setBz(blurUrl);
                    break;
                default:
                    break;
            }
            resource.put(or.getId(), res);
        }
        or.setPath(res.getPath());
        or.setBz(res.getBz());
    }

    private String getBaseUrl(String prefix, String fileName) {
        return String.format("%s://%s.%s/%s", env.getOssProtocol(), prefix, env.getOssHostSuffix(), fileName);
    }

    @Override
    @Transactional
    public FileInfo uploadResource(File file, OssResource or) {
        FileInfo fi = oas.uploadFile(file, getBucketName(or), or.getPath());
        orDAO.insert(or);
        return fi;
    }

    @Override
    @Transactional
    public FileInfo uploadResource(byte[] file, OssResource or) {
        FileInfo fi = oas.uploadFile(file, getBucketName(or), or.getPath());
        orDAO.insert(or);
        return fi;
    }

    @Override
    @Transactional
    public FileInfo fetchResourceByUrl(String url, OssResource or) {
        FileInfo fi = oas.fetchByUrl(url, getBucketName(or), or.getPath());
        orDAO.insert(or);
        return fi;
    }

    @Override
    public FileInfo getFileInfo(OssResource or) {
        return oas.getFileInfo(getBucketName(or), or.getPath());
    }

    @Override
    public void deleteFile(OssResource or) {
        oas.deleteFile(getBucketName(or), or.getPath());
        orDAO.delete(or.getId());
    }

    @Override
    public int countType(String category) {
        return orDAO.countBy(" where w_category=?", "w_id", category);
    }

    private String getBucketName(OssResource or) {
        return sp.getBucket(or.getPrefix());
    }

    @Data
    private class OssResourceInfo {
        private String path;
        private long lastUpdate;
        private long timeout;
        private String bz;
    }
}