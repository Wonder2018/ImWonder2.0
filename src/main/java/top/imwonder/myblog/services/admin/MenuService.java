package top.imwonder.myblog.services.admin;

import java.util.List;

import top.imwonder.myblog.domain.Menu;

public interface MenuService {

    List<Menu> listMenus();
    
    void addMenu(Menu menu);

    Menu updateMenu(Menu menu);

    void deleteMenu(String id);
}
