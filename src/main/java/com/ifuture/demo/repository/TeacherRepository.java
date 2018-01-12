package com.ifuture.demo.repository;

    import com.ifuture.demo.domain.Teacher;
    import org.springframework.stereotype.Repository;

    import org.springframework.data.jpa.repository.*;

    import java.util.List;


/**
 * Spring Data JPA repository for the Teacher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByName(String teaName);

    void deleteByName(String name);
}
