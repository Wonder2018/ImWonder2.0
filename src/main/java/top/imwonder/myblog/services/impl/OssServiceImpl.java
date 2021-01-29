package top.imwonder.myblog.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qiniu.util.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import top.imwonder.myblog.Env;
import top.imwonder.myblog.dao.OssResourceDAO;
import top.imwonder.myblog.domain.OssResource;
import top.imwonder.myblog.services.OssService;

@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private OssResourceDAO orDAO;

    @Autowired
    private Env env;

    @Autowired
    private Auth auth;

    private static Map<String, OssResourceInfo> resource;

    public String getUrlById(String orId) {
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
            String finalUrl = auth.privateDownloadUrl(getBaseUrl(prefix, fileName), env.getOssExpireInSeconds());
            res.setPath(finalUrl);
            res.setLastUpdate(System.currentTimeMillis());
            res.setTimeout(3300000);
            switch (or.getCategory()) {
                case "bg":
                    fileName = fileName.replace(".webp", "blur.webp");
                    String blurUrl = auth.privateDownloadUrl(getBaseUrl(prefix, fileName), env.getOssExpireInSeconds());
                    res.setBz(blurUrl);
                    break;
                default:
                    break;
            }
        }
        or.setPath(res.getPath());
        or.setBz(res.getBz());
    }

    private String getBaseUrl(String prefix, String fileName) {
        return String.format("%s://%s.%s/%s", env.getOssProtocol(), prefix, env.getOssHostSuffix(), fileName);
    }
}

@Data
class OssResourceInfo {
    private String path;
    private long lastUpdate;
    private long timeout;
    private String bz;
}