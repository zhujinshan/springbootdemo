package com.zhujs.controller;

import com.github.pagehelper.PageHelper;
import com.zhujs.mapper.UserMapper;
import com.zhujs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jinshan.zhu@luckincoffee.com
 * Date: 2018/9/13
 * Time: 17:04
 */
@RestController
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/{id}/{age}")
    public Object index(@PathVariable("id") Long id, @PathVariable("age") Integer age) {
        User user = new User();
        user.setId(id);
        user.setAge(age);
        user.setName("test" + id);
        userMapper.insert(user);
        PageHelper.startPage(1, 1);
        return userMapper.findAll();
    }
}
