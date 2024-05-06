package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.model.Student;
import com.projectRestAPI.studensystem.repository.StudentRepository;
import com.projectRestAPI.studensystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student,Long,StudentRepository> implements StudentService {

}
