package com.zz.startup.controller;

import com.zz.startup.entity.Role;
import com.zz.startup.entity.User;
import com.zz.startup.service.RoleService;
import com.zz.startup.service.UserService;
import com.zz.startup.util.Constants;
import com.zz.startup.util.SearchFilter;
import com.zz.startup.util.Servlets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Validated
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, Model model) {
        List<SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX));
        Page<User> users = userService.findPage(filters, pageable);
        model.addAttribute("users", users);
        return "user/list";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        User user = userService.find(id);
        model.addAttribute("user", user);
        return "user/show";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String _new() {
        return "user/new";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "user/new";
        }
        userService.createUser(user);

        redirectAttributes.addFlashAttribute("msg", "新增用户成功");
        return "redirect:/user/";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userService.find(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "user/edit";
        }

        user.setUpdateTime(new Date());
        userService.update(id, user);

        redirectAttributes.addFlashAttribute("msg", "更新用户成功");
        return "redirect:/user/";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes, HttpServletRequest request) {

        userService.deleteUser(id);

        redirectAttributes.addFlashAttribute("msg", "删除用户成功");
        redirectAttributes.addAllAttributes(Servlets.getParametersStartingWith(request, ""));
        return "redirect:/user/";
    }

    @RequestMapping(value = "edit/{id}/role", method = RequestMethod.GET)
    public String editRole(@PathVariable("id") Long id, Model model) {
        User user = userService.find(id);
        List<Role> userRoles = roleService.queryUserRoles(id);
        List<Role> roles = roleService.findAll();

        for (Role userRole : userRoles) {
            for (Role role : roles) {
                if (userRole.equals(role)) {
                    role.setChecked(true);
                    break;
                }
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "user/edit_role";
    }

    @RequestMapping(value = "update/{id}/role", method = RequestMethod.POST)
    public String updateRole(@PathVariable("id") Long id, Long[] roleIds, RedirectAttributes redirectAttributes) {
        userService.updateUserRoles(id, roleIds);

        redirectAttributes.addFlashAttribute("msg", "更新用户角色成功");
        return "redirect:/user/";
    }
}
