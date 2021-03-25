package top.imwonder.myblog.services.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.imwonder.myblog.dao.MenuDAO;
import top.imwonder.myblog.domain.Menu;
import top.imwonder.myblog.services.admin.MenuService;
import top.imwonder.util.IdUtil;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDAO mDAO;

    @Override
    public List<Menu> listMenus() {
        return mDAO.loadMore(" order by w_order");
    }

    @Override
    public void addMenu(Menu menu) {
        menu.setId(IdUtil.uuid());
        mDAO.insert(menu);
    }

    @Override
    public Menu updateMenu(Menu menu) {
        Menu old = mDAO.loadOneByPrimaryKey(menu.getId());
        old.setHref(menu.getHref());
        old.setIcon(menu.getIcon());
        old.setName(menu.getName());
        old.setOrder(menu.getOrder());
        old.setPerm(menu.getPerm());
        mDAO.update(old);
        return old;
    }

    @Override
    public void deleteMenu(String id) {
        mDAO.delete(id);
    }

}
