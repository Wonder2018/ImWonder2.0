package top.imwonder.myblog.services.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.imwonder.myblog.dao.PermissionDAO;
import top.imwonder.myblog.domain.Permission;
import top.imwonder.myblog.services.admin.PermService;
import top.imwonder.util.IdUtil;

@Service
public class PermServiceImpl implements PermService {

    @Autowired
    private PermissionDAO pDAO;

    @Override
    public List<Permission> listPerms() {
        return pDAO.loadMore(" order by w_id");
    }

    @Override
    public void addPerm(Permission perm) {
        perm.setId(IdUtil.uuid());
        pDAO.insert(perm);
    }

    @Override
    public Permission updatePerm(Permission perm) {
        pDAO.update(perm);
        return perm;
    }

    @Override
    public void deletePerm(String id) {
        pDAO.delete(id);
    }

}
