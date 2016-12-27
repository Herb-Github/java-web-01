package com.zz.startup.controller;

import com.zz.startup.annotation.ValidatorId;
import com.zz.startup.entity.Role;
import com.zz.startup.service.RoleService;
import com.zz.startup.util.Constants;
import com.zz.startup.util.SearchFilter;
import com.zz.startup.util.Servlets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, Model model) {
        List<SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX));
        Page<Role> roles = roleService.findPage(filters, pageable);
        model.addAttribute("roles", roles);

        return "role/list";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@ValidatorId @PathVariable("id") Long id, Model model) {
        Role role = roleService.find(id);
        model.addAttribute("role", role);
        return "role/show";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String _new() {
        return "role/new";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "role/new";
        }

        roleService.save(role);

        redirectAttributes.addFlashAttribute("msg", "新增角色成功");
        return "redirect:/role/";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@ValidatorId @PathVariable("id") Long id, Model model) {
        Role role = roleService.find(id);
        model.addAttribute("role", role);

        return "role/edit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, Role role, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "role/edit";
        }

        roleService.update(id, role);

        redirectAttributes.addFlashAttribute("msg", "更新角色成功");
        return "redirect:/role/";
    }
}
