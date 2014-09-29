package org.ping.study.spring.controller;

import java.util.List;
import java.util.Set;

import org.ping.study.spring.mvc.bean.DataBinderTestModel;
import org.ping.study.spring.mvc.bean.UserModel;
import org.ping.study.spring.mvc.bind.annotation.RequestJsonParam;
import org.ping.study.spring.mvc.util.MapWapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * jsonparam测试
 * @author ping.zhu
 *
 */
@Controller
@RequestMapping("/jsonparam")
public class JsonParamController {

    //ok   http://localhost:8080/opera/jsonparam/list.do?list=[1,2,34]
    //fail http://localhost:8080/opera/jsonparam/list.do?list=[1,2,a]
    @RequestMapping("/list")
    @ResponseBody
    public List<Integer> list(@RequestJsonParam("list") List<Integer> list) {
        return list;
    }
    
    //ok   http://localhost:8080/opera/jsonparam/set.do?set=[1,2,34]
    //fail http://localhost:8080/opera/jsonparam/set.do?set=[1,2,a]
    @RequestMapping("/set")
    @ResponseBody
    public Set<Integer> set(@RequestJsonParam("set") Set<Integer> set) {
        return set;
    }
    
    //ok   http://localhost:8080/opera/jsonparam/array.do?array=[1,2,3]
    //fail http://localhost:8080/opera/jsonparam/array.do?array=[1,2,a]
    @RequestMapping("/array")
    @ResponseBody
    public int[] list(@RequestJsonParam("array") int[] array) {
        return array;
    }
    
    //ok   http://localhost:8080/opera/jsonparam/map.do?map={"a":1, "b":2}
    //fail http://localhost:8080/opera/jsonparam/map.do?map={"a":1, "b":a}
    @RequestMapping("/map")
    @ResponseBody
    public MapWapper<String, Integer> map(@RequestJsonParam(value = "map", required=false) MapWapper<String, Integer> map) {
        return map;
    }
    
    //UserModel[]
    //ok   http://localhost:8080/opera/jsonparam/array2.do?array=[{"username":"123"},{"username":"234"}]
    @RequestMapping("/array2")
    @ResponseBody
    public UserModel[] array2(@RequestJsonParam(value = "array") UserModel[] array) {
        return array;
    }

    //List<UserModel>
    //ok   http://localhost:8080/opera/jsonparam/list2.do?list=[{"username":"123"},{"username":"234"}]
    @RequestMapping("/list2")
    @ResponseBody
    public List<UserModel> list2(@RequestJsonParam(value = "list") List<UserModel> list) {
        return list;
    }

    //Set<UserModel>
    //ok   http://localhost:8080/opera/jsonparam/set2.do?set=[{"username":"123"},{"username":"234"}]
    @RequestMapping("/set2")
    @ResponseBody
    public Set<UserModel> set2(@RequestJsonParam(value = "set") Set<UserModel> set) {
        return set;
    }
    
    //Map<void, UserModel>
    //ok   http://localhost:8080/opera/jsonparam/map2.do?map={"a":{"username":"123"},"b":{"username":"234"}}
    //暂不支持 Map<UserModel, UserModel>
    @RequestMapping("/map2")
    @ResponseBody
    public MapWapper<String, UserModel> map2(@RequestJsonParam(value = "map") MapWapper<String, UserModel> map) {
        return map;
    }
    
    //ok   http://localhost:8080/opera/jsonparam/model1.do?model={"username":123,"password":234,"realname":"zhang","workInfo":{"city":"abc","job":"abc","year":"abc"}, "schoolInfo":{"schoolType":"1","schoolName":"1","specialty":"1"}}
    //没有realname1
    //fail http://localhost:8080/opera/jsonparam/model1.do?model={"username":123,"password":234,"realname1":123}
    @RequestMapping("/model1")  
    @ResponseBody
    public UserModel model1(@RequestJsonParam(value = "model", required=true) UserModel user) {
        return user;
    }
    
    //ENUM
    //ok   http://localhost:8080/opera/jsonparam/model2.do?model={"state":"normal"}
    //List<基本类型>
    //ok   http://localhost:8080/opera/jsonparam/model2.do?model={"hobbyList":["film", "music"]}
    //Map<基本类型，基本类型>
    //ok   http://localhost:8080/opera/jsonparam/model2.do?model={"map":{"key":"value", "a":"b"}}    
    @RequestMapping("/model2")  
    @ResponseBody
    public DataBinderTestModel model2(@RequestJsonParam(value = "model", required=true) DataBinderTestModel model) {
        return model;
    }
    
    //List<UserModel>
    //ok   http://localhost:8080/opera/jsonparam/model3.do?model={"userList":[{"username":"1"},{"username":"2"}]}
    //Map<void，UserModel>
    //ok   http://localhost:8080/opera/jsonparam/model3.do?model={"userMap":{"1":{"username":"1"},"2":{"username":"2"}}}

    //暂不支持 类似于 Map<UserModel, UserModel> 形式
    @RequestMapping("/model3")  
    @ResponseBody
    public DataBinderTestModel model3(@RequestJsonParam(value = "model") DataBinderTestModel model) {
        return model;
    }
    
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
    	return "Hello World!!!!";
    }
}
