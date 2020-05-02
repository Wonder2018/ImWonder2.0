/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:59:25 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:59:25 
 */
package top.imwonder.myblog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import top.imwonder.myblog.Env;

public abstract class AbstractController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Env env;

    // protected void list(int rows, int page, Search search, DAOTemplate<?> dao, Model model){
    //     Page p = PageFactory.getPage();
    //     p.setRecordNum(rows);
    //     p.setPageNum(page);
    //     List<?> result = p.getOnePage(search.buildSQL(dao), search.getParams(), dao);
    //     model.addAttribute("retCode", "OK");
    //     model.addAttribute("result", result);
    //     model.addAttribute("page", p);
    // }
}
