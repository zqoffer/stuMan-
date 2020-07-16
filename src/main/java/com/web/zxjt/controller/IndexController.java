package com.web.zxjt.controller;

import com.web.zxjt.mapper.StudentMapper;
import com.web.zxjt.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    StudentMapper studentMapper;

    @GetMapping("/")
    @ResponseBody
    public List<Student> index(Model model){
        List<Student> students;
        students = studentMapper.findAll();
        model.addAttribute("students",students);
        System.out.println("index");
        return students;//thymeleaf自动到templates下找，自动加.html后缀
    }


}
