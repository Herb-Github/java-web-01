package com.zz.startup.test.controller;

import com.zz.startup.test.BaseControllerTest;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UserControllerTest extends BaseControllerTest {

    @Test
    public void list() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andReturn();

        System.out.println(result.getModelAndView().getViewName());
    }
}
