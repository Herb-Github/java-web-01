package com.zz.startup.test;


import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(locations = {"classpath:application-config-mvc.xml"})
@WebAppConfiguration
public class BaseControllerTest extends BaseContext {

    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockHttpServletRequest request;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .alwaysDo(print())
                .alwaysExpect(status().isOk())
                .build();
    }
}
