/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:59:25 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-10 10:13:15
 */
package top.imwonder.myblog.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.qiniu.util.Auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import top.imwonder.myblog.Env;
import top.imwonder.myblog.SystemProperties;
import top.imwonder.myblog.dao.OssResourceDAO;
import top.imwonder.myblog.domain.OssResource;

public abstract class AbstractController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Env env;

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private OssResourceDAO orDAO;

    @Autowired
    private SystemProperties sp;

    protected static Object[] emptyObj = new Object[0];

    private static List<OssResource> orList;

    private static long lastUpdateBg = 0;
    private static Map<String, Map<String, Object>> resource;

    @ModelAttribute
    public void initModel(HttpServletRequest req, Model model) {
        String ua = req.getHeader("User-Agent");
        model.addAttribute("isRobot", SpiderUtil.isSpider(ua));
        model.addAttribute("iconfontUrl", sp.getIconfontUrl());
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

    protected void listTag(Model model) {
        String sql = "select b.w_id as tagId, b.w_name as text, a.count as weight, concat('/blog/search?tag=', b.w_id) as link from (select c.w_tag_id, count(c.w_article_id) as count from w_articl_tag c group by c.w_tag_id) a left join w_tag b on a.w_tag_id = b.w_id order by a.count desc;";
        List<Map<String, Object>> tagList = jt.queryForList(sql);
        model.addAttribute("tagList", tagList);
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

    protected String calcOne(String orId) {
        List<OssResource> ors = orDAO.loadMore("where w_id =?", new Object[] { orId });
        if (!ors.isEmpty()) {
            calcOne(ors.get(0));
            return ors.get(0).getPath();
        }
        return "";
    }

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
            String finalUrl = auth.privateDownloadUrl(String.format("%s://%s.%s/%s", ossProtocol, prefix, ossHostSuffix, fileName), expireInSeconds);
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
