package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.Tag;
import top.imwonder.util.AbstractDAO;

@Component
public class TagDAO extends AbstractDAO<Tag> {
    
    public TagDAO(){
        domainType = Tag.class;
        tableName = "w_tag";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_name", "w_icon"};
        init();
    }

}