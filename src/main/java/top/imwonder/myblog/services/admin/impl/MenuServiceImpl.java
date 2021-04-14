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
        mDAO.update(menu);
        return menu;
    }

    @Override
    public void deleteMenu(String id) {
        mDAO.delete(id);
    }

}
