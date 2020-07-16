package com.web.zxjt.mapper;

import com.web.zxjt.model.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Mapper
@Component
public interface StudentMapper {
    @Select("select * from student")
    public ArrayList<Student> findAll();

    @Insert("insert into student(id,name,age,gender,email,major) "+
    "values(#{id},#{name},#{age},#{gender},#{email},#{major})")
    void insert(Student student);

    @Select("select * from student where id=#{id}")
    Student findById(@Param("id") Integer id);

    @Delete("delete from student where id=#{id}")
    void delete(@Param("id")Integer id);

    @Update("update student set id=#{Student.id},name=#{Student.name},age=#{Student.age},gender=#{Student.gender},email=#{Student.email},major=#{Student.major} "+
            "where id=#{originalId}")
    void update(@Param("Student")Student student,@Param("originalId") Integer originalId);

    @Select("select * from student where name=#{name}")
    List<Student> findByName(@Param("name") String name);
}
