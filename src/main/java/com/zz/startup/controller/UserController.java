package com.zz.startup.controller;

import com.zz.startup.entity.User;
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

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, Model model) {
        return "user/list";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") String id, Model model) {
        return "user/show";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String _new(Model model) {
        return "user/new";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "新增用户成功");
        return "redirect:/user/";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id, Model model) {
        return "user/edit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") String id, @Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "更新用户成功");
        return "redirect:/user/";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "删除用户成功");
        return "redirect:/user/";
    }
}
