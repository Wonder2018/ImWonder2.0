package top.imwonder.myblog.services.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

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
import top.imwonder.myblog.services.OssApiService.FileInfo;
import top.imwonder.myblog.services.OssResourceService;
import top.imwonder.myblog.services.RedisService;

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

    @Resource
    private RedisService<OssResourceInfo> redisService;

    @Override
    public String getUrlById(String orId) {
        return tryToFindOssResourceInfo(orId).getPath();
    }

    @Override
    public OssResource calcPath(OssResource or) {
        OssResourceInfo ori = tryToFindOssResourceInfo(or);
        or.setPath(ori.getPath());
        or.setBz(ori.getBz());
        return or;
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

    private OssResourceInfo tryToFindOssResourceInfo(String orId) {
        OssResourceInfo res = redisService.get(orId);
        if (res != null) {
            return res;
        } else {
            return loadOssResourceInfo(orId);
        }
    }

    private OssResourceInfo tryToFindOssResourceInfo(OssResource or) {
        OssResourceInfo res = redisService.get(or.getId());
        if (res != null) {
            return res;
        } else {
            return loadOssResourceInfo(or);
        }
    }

    private OssResourceInfo loadOssResourceInfo(String orId) {
        List<OssResource> ors = orDAO.loadMore("where w_id =?", orId);
        if (!ors.isEmpty()) {
            return loadOssResourceInfo(ors.get(0));
        }
        return new OssResourceInfo();
    }

    private OssResourceInfo loadOssResourceInfo(OssResource or) {
        OssResourceInfo res = new OssResourceInfo();
        String prefix = or.getPrefix();
        String fileName = or.getPath();
        long oeis = env.getOssExpireInSeconds();
        String finalUrl = getAuthedUrl(prefix, fileName, oeis);
        switch (or.getCategory()) {
        case "bg":
            fileName = fileName.replace(".webp", "blur.webp");
            String blurUrl = getAuthedUrl(prefix, fileName, oeis);
            res.setBz(blurUrl);
            break;
        default:
            break;
        }
        res.setPath(finalUrl);
        redisService.set(or.getId(), res, env.getResourceExpireInSeconds());
        return res;
    }

    private String getAuthedUrl(String prefix, String fileName, long expires) {
        String localAssetsPrefix = env.getLocalAssetsPrefix();
        if (localAssetsPrefix != null && localAssetsPrefix.equals(prefix)) {
            return "/blog-assets/" + fileName;
        } else {
            String baseUrl = String.format("%s://%s.%s/%s", env.getOssProtocol(), prefix, env.getOssHostSuffix(),
                    fileName);
            return oas.authUrl(baseUrl, expires);
        }
    }

    @Data
    private static class OssResourceInfo {
        private String path;
        private String bz;
    }
}