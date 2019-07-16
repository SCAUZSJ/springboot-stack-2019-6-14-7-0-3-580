package com.tw.apistackbase.controller;

import com.tw.apistackbase.po.Employee;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRequestTest() throws Exception {
        String content = this.mockMvc.perform(get("/employees")).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONArray json = new JSONArray(content);
        Assertions.assertEquals(20,json.getJSONObject(0).getInt("age"));
    }
    @Test
    public void postRequestTest() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("id","2");
        map.put("name","B");
        map.put("age","18");
        map.put("gender","male");
        String objectJson = JSONObject.toJSONString(map);
        Object o = new Object();
        this.mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isCreated());


    }
    @Test
    public void putRequestTest() throws Exception {
        //success
        postRequestTest();
        Map<String, String> map = new HashMap<>();
        map.put("id","2");
        map.put("name","B");
        map.put("age","20");
        map.put("gender","male");
        String objectJson = JSONObject.toJSONString(map);
        String content = this.mockMvc.perform(put("/employees/2").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONObject obj = (JSONObject) JSONValue.parse(content);

        Assertions.assertEquals(20,obj.get("age"));

        //fail
        this.mockMvc.perform(put("/employees/3").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isBadRequest());
    }
    @Test
    public void deleteRequestTest() throws Exception {
        //success
        postRequestTest();
        this.mockMvc.perform(delete("/employees/2")).andExpect(status().isOk());
        //fail
        this.mockMvc.perform(delete("/employees/2")).andExpect(status().isBadRequest());
    }


}
