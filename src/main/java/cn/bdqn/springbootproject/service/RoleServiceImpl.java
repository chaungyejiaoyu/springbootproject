package cn.bdqn.springbootproject.service;

import cn.bdqn.springbootproject.dao.RoleDao;
import cn.bdqn.springbootproject.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Resource
    private RoleDao rdao;
    @Override
    public int updateRole(Integer id, String roleName, Date modifyDate) {
        return rdao.updateRole(id, roleName,modifyDate);
    }

    @Override
    public List<Role> findAllRole() {
        return rdao.findAllRole();
    }

    @Override
    public int updateRole1(Role role) {
        return rdao.updateRole1(role);
    }

    @Override
    public Role findRole(Integer id) {
        return rdao.findRole(id);
    }

    @Override
    public List<Role> findRolePaging(Integer from, Integer pageSize, String roleName) {
        return rdao.findRolePaging(from, pageSize, roleName);
    }

    @Override
    public int findRoleCount(String roleName) {
        return rdao.findRoleCount(roleName);
    }
}
