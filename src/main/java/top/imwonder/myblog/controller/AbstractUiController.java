/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:59:25 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-02-16 16:28:47
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
import top.imwonder.myblog.services.OssResourceService;
import top.imwonder.myblog.util.SpiderUtil;

public abstract class AbstractUiController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private OssResourceDAO orDAO;

    @Autowired
    private OssResourceService ors;

    @Autowired
    private FriendlyLinkService flService;

    @Autowired
    protected Env env;

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private SystemProperties sp;

    @ModelAttribute
    public void initModel(HttpServletRequest req, Model model) {
        String ua = req.getHeader("User-Agent");
        model.addAttribute("isRobot", SpiderUtil.isSpider(ua));
        model.addAttribute("iconfontUrl", sp.getIconfontUrl());
        listFriendlyLink(model);
    }

    protected void initBg(Model model) {
        List<OssResource> orList = orDAO.loadMore(" where w_category = ? order by w_order asc", "bg");
        for (OssResource item : orList) {
            ors.calcPath(item);
        }
        model.addAttribute("bgList", orList);
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
