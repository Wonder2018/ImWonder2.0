package top.imwonder.myblog.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import top.imwonder.myblog.dao.friendlylinkdao.FriendlyLinkDisplayDAO;
import top.imwonder.myblog.dao.friendlylinkdao.FriendlyLinkFullDAO;
import top.imwonder.myblog.domain.FriendlyLink;
import top.imwonder.myblog.domain.OssResource;
import top.imwonder.myblog.exception.WonderException;
import top.imwonder.myblog.pojo.FriendlyLinkForm;
import top.imwonder.myblog.services.FriendlyLinkService;
import top.imwonder.myblog.services.OssApiService.FileInfo;
import top.imwonder.myblog.services.OssResourceService;
import top.imwonder.util.IdUtil;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FriendlyLinkServiceImpl implements FriendlyLinkService {

    @Autowired
    private FriendlyLinkDisplayDAO fldDAO;

    @Autowired
    private FriendlyLinkFullDAO flfDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OssResourceService ors;

    private static final Pattern BILI_HEAD_PTN = Pattern.compile(
            "[\\s\\S]*\"face\":\"(http(s)?://([\\w\\d-]+)(.[\\w\\d-]+)+/?([\\w\\d-]+/)*([\\w\\d.]+))\"[\\S\\s]*");

    private static final String PAGE_CAUSE_FORMAT = " where w_disabled = 0 order by w_order asc, w_protocol asc, w_add_time asc limit %d, %d";

    @Override
    public List<FriendlyLink> listFriendlyLinks(int page, int rows) {
        if (rows == -1) {
            return fldDAO.loadMore(" where w_disabled = 0 order by w_order asc, w_protocol asc, w_add_time asc");
        }
        int offset = (page - 1) * rows;
        return fldDAO.loadMore(String.format(PAGE_CAUSE_FORMAT, offset, rows));
    }

    @Override
    public void addFriendlyLink(FriendlyLink friendlyLink) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional
    public FriendlyLink addFriendlyLinkOpen(FriendlyLinkForm flf) {
        FriendlyLink fl = parseFromOpenForm(flf);
        if (flf.getIconType() == null) {
            flfDAO.insert(fl);
            return fl;
        }
        String url = null;
        switch (flf.getIconType()) {
            case BILI:
                url = biliHeaderImgUrl((String) flf.getIcon());
                break;
            case QQ:
                url = qqHeaderImgUrl((String) flf.getIcon());
                break;
            case GITHUB:
                url = githubHeaderImgUrl((String) flf.getIcon());
                break;
            case URL:
                url = (String) flf.getIcon();
                break;
            default:
                break;
        }
        OssResource or = creatOssResource(fl);
        FileInfo fi = null;
        fl.setIcon(or.getId());
        if (url == null) {
            MultipartFile icon = (MultipartFile) flf.getIcon();
            try {
                byte[] ib = icon.getBytes();
                fi = ors.uploadResource(ib, or);
            } catch (IOException e) {
                throw new WonderException("500", "保存文件时发生错误！");
            }
        } else {
            ors.fetchResourceByUrl(url, or);
        }
        if (fi.getMimeType() == null) {
            fi = ors.getFileInfo(or);
        }
        if (!fi.getMimeType().startsWith("image")) {
            ors.deleteFile(or);
            throw new WonderException("412", "获取到的文件不是可用图片！");
        }
        flfDAO.insert(fl);
        flf.setIcon(String.format("/blog/resource/%s", or.getId()));
        return fl;

    }

    @Override
    public void updateFriendlyLink(FriendlyLink friendlyLink) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteFriendlyLink(String id) {
        // TODO Auto-generated method stub

    }

    private FriendlyLink parseFromOpenForm(FriendlyLinkForm flf) {
        FriendlyLink fl = new FriendlyLink();
        fl.setId(IdUtil.uuid());
        fl.setAddTime(new Date());
        fl.setDisabled(false);
        fl.setEmail(flf.getEmail());
        fl.setPassword(flf.getPwd());
        fl.setProtocol(flf.getUrl().startsWith("https") ? 1 : 2);
        fl.setVarified(false);
        fl.setWebmaster(flf.getMaster());
        fl.setSiteName(flf.getSiteName());
        fl.setUrl(flf.getUrl());
        return fl;

    }

    private OssResource creatOssResource(FriendlyLink fl) {
        OssResource or = new OssResource();
        or.setCategory("fl-icon");
        or.setCreateTime(new Date());
        or.setId(IdUtil.uuid());
        or.setName(fl.getId());
        or.setOrder(ors.countType(or.getCategory()) + 1);
        or.setPath(String.format("img/fl/%s", fl.getId()));
        or.setPrefix("image");
        return or;
    }

    private String biliHeaderImgUrl(String uid) {
        if (uid == null) {
            return null;
        }
        String userInfoApi = String.format("http://api.bilibili.com/x/space/acc/info?mid=%s", uid);
        String userInfo = restTemplate.getForObject(userInfoApi, String.class);
        Matcher matcher = BILI_HEAD_PTN.matcher(userInfo);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String qqHeaderImgUrl(String qq) {
        if (qq == null)
            return null;
        return String.format("http://q1.qlogo.cn/g?b=qq&nk=%s&s=640", qq);
    }

    private String githubHeaderImgUrl(String username) {
        if (username == null)
            return null;
        return String.format("https://avatars.githubusercontent.com/%s", username);
    }

}
