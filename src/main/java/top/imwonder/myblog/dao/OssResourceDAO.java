package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.OssResource;
import top.imwonder.util.AbstractDAO;

@Component
public class OssResourceDAO extends AbstractDAO<OssResource> {

    public OssResourceDAO() {
        domainType = OssResource.class;
        tableName = "w_oss_resource";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_name", "w_prefix", "w_category", "w_order", "w_path", "w_create_time" };
        init();
    }

}