package cn.bdqn.springbootproject.controller;

import cn.bdqn.springbootproject.dao.RoleDao;
import cn.bdqn.springbootproject.entity.Role;
import cn.bdqn.springbootproject.service.RoleService;
import cn.bdqn.springbootproject.service.RoleServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class RoleController {
    Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Value("${web.upload-path}")
    private static String filePath; //从全局配置文件中根据键读取数据，注入到属性中
    @Resource
    private RoleService rser;
    @RequestMapping("/roleIndex")
    public String roleIndex(Model m){
        m.addAttribute("msg","hello");
        Role role = new Role(100,"guanliyuan","管理员",12,new Date());
        m.addAttribute("role",role);
        List<Role> list = new ArrayList<Role>();
        list.add(new Role(101,"guanliyuan","管理员",12,new Date()));
        list.add(new Role(102,"jingli","经理",12,new Date()));
        list.add(new Role(103,"caiwu","财务",12,new Date()));
        m.addAttribute("list",list);
        return"RoleIndex";
    }

    @RequestMapping("/getAllRole")
    public String getAllRole(Model m){
        List<Role> list = rser.findAllRole();
        m.addAttribute("roleList",list);
        return "allRole";
    }
    @RequestMapping("/showRole")
    public String updateRole1(Integer id,Model m) {
        Role role = rser.findRole(id);
        m.addAttribute("r",role);
        return "role";
    }

    @RequestMapping("/showRole1")
    public String updateRole(Role role) throws ParseException {
        //int rel=rser.updateRole(id,"员工",new Date());
        /*Role role = new Role();
        role.setId(id);
        role.setRoleName("员工经理");
        String date = "2020-11-15";
        Date date1 = null;
        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        role.setModifyDate(date1);*/
       /* Role role = new Role();
        role.setId(id);
        role.setModifyDate(new SimpleDateFormat("yyyy-MM-dd").parse(modifyDate));*/
        role.setRoleName("员工经理");
        int rel = rser.updateRole1(role);
        System.out.println(rel==1?"修改成功":"修改失败");
        return "redirect:/allRole";
    }

    //显示添加页面
    @RequestMapping("/showAdd")
    public String addRole(Model model){
        model.addAttribute("role",new Role());
        return "addRole";
    }
    //处理添加
    @RequestMapping("/addRole")
    public String addRole(@Valid Role role, BindingResult result,@RequestParam(required = false) MultipartFile myPic) {
        File file = new File(filePath+myPic.getOriginalFilename());
        try {
            myPic.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result.hasErrors()){
            return "addRole";
        }
        System.out.println(role.getRoleName());

        return "redirect:/getAllRole";
    }

    //异步处理
    @RequestMapping("/rolelist")
    @ResponseBody
    public Map<String,Object> getListRole(@RequestParam(required = false,defaultValue = "0") Integer from,
                                          @RequestParam(required = false,defaultValue = "2") Integer pageSize,
                                          @RequestParam(required = false) String roleName){
        logger.info("异步处理====================================");
        Map<String,Object> map = new HashMap<String, Object>();
        int rel = rser.findRoleCount(roleName);
        List<Role> list = rser.findRolePaging(from, pageSize, roleName);
        map.put("total",rel);
        map.put("data",list);
        return map;
    }

}
