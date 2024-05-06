package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<Student,Long> {

}
