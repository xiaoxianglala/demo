package com.ifuture.demo.repository;

import com.ifuture.demo.domain.Score;
import com.ifuture.demo.service.dto.ScoreDTO;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Score entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScoreRepository extends JpaRepository<Score, Long>,JpaSpecificationExecutor<Score>{
    //@Query("SELECT a FROM Score a WHERE a.student.name=?1 AND a.course.courseName=?2")
    @Query(nativeQuery = true,value = "SELECT a.* FROM SCORE a,STUDENT b,TB_COURSE c WHERE   a.student_id=b.id and a.course_id=c.id AND b.name=?1 and c.course_name=?2")
    List<Score> searchByscore(String stuName, String cname);
    @Query("SELECT new com.ifuture.demo.service.dto.ScoreDTO(a.student.name,a.course.courseName,a.score) FROM Score a WHERE a.student.name=?1 AND a.course.courseName=?2")
    List<ScoreDTO> query3StuScore(String stuName, String cname);
}
