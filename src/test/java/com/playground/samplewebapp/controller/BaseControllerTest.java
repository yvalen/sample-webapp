package com.playground.samplewebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.playground.samplewebapp.BaseTest;
import org.junit.Ignore;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;


@Ignore
public abstract class BaseControllerTest extends BaseTest {
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected MappingJackson2HttpMessageConverter jacksonConverter() {
        objectMapper.registerModule(new JodaModule());
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }


    protected MockHttpServletResponse successfulRequest(RequestBuilder httpRequest) throws Exception {
        return mockMvc.perform(httpRequest)
                .andExpect(MockMvcResultMatchers.status().is(HttpServletResponse.SC_OK))
                .andReturn()
                .getResponse();
    }

    protected void unsuccessfulRequest(RequestBuilder httpRequest, int status)
            throws Exception {
        mockMvc.perform(httpRequest)
                .andExpect(MockMvcResultMatchers.status().is(status));
    }


}
