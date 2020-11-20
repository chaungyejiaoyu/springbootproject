package cn.bdqn.springbootproject.service;

import cn.bdqn.springbootproject.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RoleService {
    List<Role> findAllRole();
    int updateRole(Integer id, String roleName, Date modifyDate);
    int updateRole1(Role role);
    Role findRole(Integer id);
    List<Role> findRolePaging(Integer from,Integer pageSize,String roleName);
    int findRoleCount(String roleName);
}
