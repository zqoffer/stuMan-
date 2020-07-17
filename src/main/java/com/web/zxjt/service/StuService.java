package com.web.zxjt.service;

import com.web.zxjt.mapper.StudentMapper;
import com.web.zxjt.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StuService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    //查找所有用户
    public List<Student> findAll(){
        return studentMapper.findAll();
    }

    //查找姓名用户
    public List<Student> findByName(String name){
        List<Student> list = studentMapper.findByName(name);
        for(Student temp:list){
            String key = "stu_"+temp.getId();
            ValueOperations<String,Student> operations=redisTemplate.opsForValue();
            boolean hasKey = redisTemplate.hasKey(key);
            if(!hasKey){
                operations.set(key,temp,5, TimeUnit.HOURS);
            }
        }
        return list;
    }

    public Student findById(Integer id){
        String key = "stu"+id;
        ValueOperations<String,Student> valueOperations =redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        Student student = null;
        if (hasKey){
            student=valueOperations.get(key);
        }else{
            student = studentMapper.findById(id);
            valueOperations.set(key,student,5, TimeUnit.HOURS);
        }
        return student;
    }

    public void delete(Integer id){
        studentMapper.delete(id);

        String key = "stu_"+id;
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            redisTemplate.delete(key);
        }
    }

    public void update(Student student,Integer id){
         ValueOperations<String,Student> operations=redisTemplate.opsForValue();
         studentMapper.update(student,id);

         String key = "stu_"+id;
         boolean hasKey = redisTemplate.hasKey(key);
         if(hasKey){
             redisTemplate.delete(key);
         }

         Student stuNew = studentMapper.findById(student.getId());
         if(stuNew!=null){
             operations.set(key, stuNew, 3, TimeUnit.HOURS);
         }
    }

    public void insert(Student student) {
        studentMapper.insert(student);
        String key = "stu_"+student.getId();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey){
            redisTemplate.delete(key);
        }
        redisTemplate.opsForValue().set(key, student, 3, TimeUnit.HOURS);
    }
}
