/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:59:25 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-10 10:13:15
 */
package top.imwonder.myblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import top.imwonder.myblog.services.FriendlyLinkService;
import top.imwonder.myblog.services.OssService;
import top.imwonder.myblog.util.SpiderUtil;

public abstract class AbstractController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private OssResourceDAO orDAO;

    @Autowired
    private OssService ossService;

    @Autowired
    private FriendlyLinkService flService;

    @Autowired
    protected Env env;

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private SystemProperties sp;

    private static List<OssResource> orList;

    private static long lastUpdateBg = 0;

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
                ossService.calcPath(item);
                item.setPath("/assets/img/bg/img2.jpg");
                item.setBz("/assets/img/bg/img2blur.webp");
            }
        }
        model.addAttribute("bgList", AbstractController.orList);
    }

    protected void listTag(Model model) {
        String sql = "select b.w_id as tagId, b.w_name as text, a.count as weight, concat('/blog/search?tag=', b.w_id) as link from (select c.w_tag_id, count(c.w_article_id) as count from w_articl_tag c group by c.w_tag_id) a left join w_tag b on a.w_tag_id = b.w_id order by a.count desc;";
        List<Map<String, Object>> tagList = jt.queryForList(sql);
        model.addAttribute("tagList", tagList);
    }

    protected void listFriendlyLink(Model model) {
        model.addAttribute("friendlyLinkList", flService.listFriendlyLinks(1, 6));
    }

}
