package org.ping.study.spring.controller;

import java.util.List;

import org.ping.study.spring.mvc.bean.UserModel;
import org.ping.study.spring.mvc.bind.annotation.FormModel;
import org.ping.study.spring.mvc.util.MapWapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/formmodel")  
public class FormModelController {

    //ok   http://localhost:8080/opera/formmodel/user.do?user.username=zhang&user.password=123
    @RequestMapping("/user/{user.realname}")
    @ResponseBody
    public UserModel user(@FormModel("user") UserModel user) {
        return user;
    }
    
    //ok   http://localhost:8080/opera/formmodel/array1.do?array[0]=zhang&array[1]=li
    @RequestMapping("/array1")
    @ResponseBody
    public String[] array1(@FormModel("array") String[] array) {
        return array;
    }
    
    //ok   http://localhost:8080/opera/formmodel/array2.do?array[0].username=zhang&array[0].password=123&array[1].username=li
    @RequestMapping("/array2")
    @ResponseBody
    public UserModel[] array2(@FormModel("array") UserModel[] array) {
        return array;  
    }
    
    
  //ok   http://localhost:8080/opera/formmodel/list1.do?list[0]=123&list[1]=234
    @RequestMapping("/list1")
    @ResponseBody
    public List<Integer> list1(@FormModel("list") List<Integer> list) {
        return list;
    }
    
    //ok   http://localhost:8080/opera/formmodel/list2.do?list[0].username=zhang&list[1].username=li
    @RequestMapping("/list2")
    @ResponseBody
    public List<UserModel> list2(@FormModel("list") List<UserModel> list) {
        return list;
    }
    
    //ok   http://localhost:8080/opera/formmodel/map1.do?map['0']=123&map["1"]=234
    @RequestMapping("/map1")
    @ResponseBody
    public MapWapper<String, Integer> map1(@FormModel("map") MapWapper<String, Integer> map) {
        return map;   
    }
    
  //ok   http://localhost:8080/opera/formmodel/map2.do?map['0'].password=123&map['0'].username=123&map["1"].username=234
    @RequestMapping("/map2")
    @ResponseBody
    public MapWapper<Integer, UserModel> map2(@FormModel("map") MapWapper<Integer, UserModel> map) {
        return map;
    }
}
