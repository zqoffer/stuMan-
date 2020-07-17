package com.web.zxjt.controller;

import com.web.zxjt.dto.userDto;
import com.web.zxjt.mapper.StudentMapper;
import com.web.zxjt.model.Student;
import com.web.zxjt.service.StuService;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AddController {
    @Autowired
    private StuService stuService;
    private Integer originalId=null;
    @GetMapping("/add")
    public String add(Student student){
        return"add";
    }

    //对返回页面也要加student对象参数，来进行BindingResult校验
//    @PostMapping("/add")
//    public String addStu(@Valid Student student, BindingResult result,Model model){
//
//        model.addAttribute("stu_id",student);
//        if(result.hasErrors())
//            return "/add";
//        if(studentMapper.findById(student.getId())!=null){
//            model.addAttribute("msg","该ID已存在，请重新输入");
//            return"/add";
//        }
//        else
//            studentMapper.insert(student);
//        return"redirect:/";
//    }

    @PostMapping("/search")
    @ResponseBody
    //后台传数据时，
    //1、后台javaBean，前端传jason转换后的数据封装
    //2、@RequestParam，前端传jason转换后的数据封装
    //前端只能传封装，传具体成员数据时以上两种方式都接收不到
    public List<Student> search(@RequestParam("name") String name){//@RequestParam("name") String name
        //System.out.println(name);
        List<Student> students=stuService.findByName(name);;
        //odel.addAttribute("students",students);
       // System.out.println(students);
        return students;
    }

    @GetMapping("/add/{id}")
    @ResponseBody
    public Student updateStu(@PathVariable("id") Integer id){
        //System.out.println(id);
        originalId=id;
        Student stu_id = stuService.findById(id);
        //model.addAttribute("stu_id",stu_id);
        return stu_id;
    }

    @GetMapping("/test/{id}")
    @ResponseBody
    public Student testStu(@PathVariable("id") Integer id){
        System.out.println(id);
        Student stu_id = stuService.findById(id);
        //model.addAttribute("stu_id",stu_id);
        return stu_id;
    }

    @PostMapping("/edit")
    @ResponseBody
    public void editStu(@Validated Student student, BindingResult result){// BindingResult result
        if(result.hasErrors()){
           return ;
        }
//        //修改时是否修改了ID
//        if(originalStu==null || student.getId()!=originalStu.getId()){
//            //检测新ID是否已存在
//            if(studentMapper.findById(student.getId())!=null){
//                //model.addAttribute("msg","该ID已存在，请重新输入");
//                System.out.println(2);
//                return;
//            }
//        }
        System.out.println("ss"+originalId);
        //studentMapper.delete(originalId);
        //studentMapper.insert(student);
        stuService.update(student,originalId);
    }

    @PostMapping("/add")
    @ResponseBody
    public void insertStu(@Validated Student student, BindingResult result){// BindingResult result
        if(result.hasErrors()){
            return ;
        }
        stuService.insert(student);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") Integer id){
        stuService.delete(id);
        System.out.println(id);
    }

}
