package com.ifuture.demo.repository;

import com.ifuture.demo.domain.Student;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeBetween(Integer age1, Integer age2);

    List<Student> findByName(String stuName);

    List<Student> findBySex(String stuSex);
}
