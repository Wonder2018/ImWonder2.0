/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:59:25 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-05 23:52:31
 */
package top.imwonder.myblog.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qiniu.util.Auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import top.imwonder.myblog.Env;
import top.imwonder.myblog.dao.OssResourceDAO;
import top.imwonder.myblog.domain.OssResource;

public abstract class AbstractController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Env env;

    @Autowired
    private OssResourceDAO orDAO;

    private static List<OssResource> orList;

    private static long lastUpdateBg = 0;
    private static Map<String, Map<String, Object>> resource;

    @ModelAttribute
    public void initModel(Model model) {
        log.info("in---");
        model.addAttribute("teststr", "have fun!");
    }

    protected void initBg(Model model) {
        long lub = AbstractController.lastUpdateBg;
        if (lub == 0 || System.currentTimeMillis() - lub > 3300000) {
            AbstractController.lastUpdateBg = System.currentTimeMillis();
            AbstractController.orList = orDAO.loadMore(" where w_category = ? order by w_order asc",
                    new Object[] { "bg" });
            for (OssResource item : AbstractController.orList) {
                calcOne(item);
            }
        }
        model.addAttribute("bgList", AbstractController.orList);
    }

    // protected void list(int rows, int page, Search search, DAOTemplate<?> dao,
    // Model model){
    // Page p = PageFactory.getPage();
    // p.setRecordNum(rows);
    // p.setPageNum(page);
    // List<?> result = p.getOnePage(search.buildSQL(dao), search.getParams(), dao);
    // model.addAttribute("retCode", "OK");
    // model.addAttribute("result", result);
    // model.addAttribute("page", p);
    // }

    protected void calcOne(OssResource or) {
        if (resource == null) {
            resource = new HashMap<>();
        }
        Map<String, Object> res = resource.get(or.getId());
        if (res == null || System.currentTimeMillis() - (Long) res.get("lu") > (Long) res.get("timeout")) {
            if (res == null) {
                resource.put(or.getId(), new HashMap<>());
                res = resource.get(or.getId());
            }
            String ossProtocol = env.getOssProtocol();
            String prefix = or.getPrefix();
            String ossHostSuffix = env.getOssHostSuffix();
            String fileName = or.getPath();
            String accessKey = env.getOssAccessKey();
            String secretKey = env.getOssSecretKey();
            Auth auth = Auth.create(accessKey, secretKey);
            long expireInSeconds = 3600;
            String finalUrl = auth.privateDownloadUrl( String.format("%s://%s.%s/%s", ossProtocol, prefix, ossHostSuffix, fileName), expireInSeconds);
            res.put("path", finalUrl);
            res.put("lu", new Long(System.currentTimeMillis()));
            res.put("timeout", new Long(3300000));
            switch (or.getCategory()) {
                case "bg":
                    String blurBg = fileName.replace(".webp", "blur.webp");
                    // String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+",
                    // "%20");
                    String blurUrl = auth.privateDownloadUrl(String.format("%s://%s.%s/%s", ossProtocol, prefix, ossHostSuffix, blurBg),
                            expireInSeconds);
                    res.put("bz", blurUrl);
                    break;
                default:
                    break;
            }
        }
        or.setPath((String) res.get("path"));
        or.setBz((String) res.get("bz"));
    }
}
