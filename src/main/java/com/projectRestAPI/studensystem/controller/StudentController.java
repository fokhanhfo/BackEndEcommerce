package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Student;
import com.projectRestAPI.studensystem.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@CrossOrigin("http://localhost:3000")
public class StudentController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public String add(@RequestBody Student student){
        studentService.createNew(student);
        return "new student is add";
    }

    @GetMapping("getAll")
    public List<Student> getAllStudents(){
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getID(@PathVariable Long id){
        Optional<Student> otp = studentService.findById(id);
        if (otp.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject("Fail", "Không tìm thấy id " + id, 1, null), HttpStatus.BAD_REQUEST);
        }
        Student student = otp.map(pro -> mapper.map(pro, Student.class)).orElseThrow(IllegalArgumentException::new);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Student> update(@RequestBody Student student,@PathVariable Integer id){
//        try{
//            Student existedStudent=studentService.getId(id);
//            existedStudent.setName(student.getName());
//            existedStudent.setAddress(student.getAddress());
//            studentService.saveStudent(existedStudent);
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        }catch (NoSuchElementException e){
//            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable Integer id){
//        studentService.deleteId(id);
//        return "Deleted Student with id" +id;
//    }

}
