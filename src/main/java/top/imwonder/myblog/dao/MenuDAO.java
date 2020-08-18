package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.Menu;
import top.imwonder.util.DAOTemplate;

@Component
public class MenuDAO extends DAOTemplate<Menu>{
    
    public MenuDAO(){
        domainType = Menu.class;
        tableName = "w_menu";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_icon", "w_name", "w_href", "w_perm", "w_order"};
        init();
    }
}