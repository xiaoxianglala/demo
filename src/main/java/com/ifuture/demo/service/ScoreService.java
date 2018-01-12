package com.ifuture.demo.service;

import com.ifuture.demo.domain.Score;
import com.ifuture.demo.repository.ScoreRepository;
import com.ifuture.demo.service.dto.ScoreDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing Score.
 */

@Service
@Transactional
public class ScoreService {
    @Autowired
    @PersistenceContext
    private EntityManager entityManager;
    private final Logger log = LoggerFactory.getLogger(ScoreService.class);

    private final ScoreRepository scoreRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    /**
     * Save a score.
     *
     * @param score the entity to save
     * @return the persisted entity
     */
    public Score save(Score score) {
        log.debug("Request to save Score : {}", score);
        return scoreRepository.save(score);
    }

    /**
     * Get all the scores.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Score> findAll(Pageable pageable) {
        log.debug("Request to get all Scores");
        return scoreRepository.findAll(pageable);
    }

    /**
     * Get one score by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Score findOne(Long id) {
        log.debug("Request to get Score : {}", id);
        return scoreRepository.findOne(id);
    }

    /**
     * Delete the  score by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Score : {}", id);
        scoreRepository.delete(id);
    }

    public List<Score> searchStuScore(String stuName, String cname) {
        return scoreRepository.searchByscore(stuName, cname);
    }


    public List<Score> queryStuScore(String stuName, String cname) {
        return scoreRepository.findAll(new Specification<Score>() {
            @Override
            public Predicate toPredicate(Root<Score> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank("stuName")) {
                    predicates.add(criteriaBuilder.equal(root.get("student").get("name"), stuName));
                }
                if (StringUtils.isNotBlank("cname")) {
                    predicates.add(criteriaBuilder.equal(root.get("course").get("courseName"), cname));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    public List<Score> query1StuScore(String stuName, String cname) {
        Query query = entityManager.createQuery("select a FROM Score a WHERE a.student.name=:stuName AND a.course.courseName=:cname", Score.class);
        query.setParameter("stuName", stuName);
        query.setParameter("cname", cname);
        return query.getResultList();
       /*Query query=entityManager.createNativeQuery("SELECT a.* FROM SCORE a,STUDENT b,TB_COURSE c WHERE a.student_id=b.id and a.course_id=c.id and b.name=?1 and c.course_name=?2",Score.class);
       query.setParameter(1,stuName);
       query.setParameter(2,cname);
       return query.getResultList();*/
    }

    public List<Score> query2StuScore(String stuName, String cname) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();//获得builder 让他创建SQL语句
        CriteriaQuery<Score> query = builder.createQuery(Score.class);//获得query 通过其查询数据库
        Root<Score> root = query.from(Score.class);//构建Root 相当于SQL语句
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(stuName)) {
            predicates.add(builder.equal(root.get("student").get("name"), stuName));
        }
        if (StringUtils.isNotBlank(cname)) {
            predicates.add(builder.equal(root.get("course").get("courseName"), cname));
        }
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }

    public List<ScoreDTO> query3StuScore(String stuName, String cname) {
        return scoreRepository.query3StuScore(stuName, cname);
    }

    public List<ScoreDTO> query4StuScore(String stuName, String cname) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(ScoreDTO.class);
        Root<Score> root = query.from(Score.class);
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(stuName)) {
            predicates.add(builder.equal(root.get("student").get("name"), stuName));
        }
        if (StringUtils.isNotBlank(cname)) {
            predicates.add(builder.equal(root.get("course").get("courseName"), cname));
        }
        query.multiselect(root.get("student").get("name"), root.get("course").get("courseName"),
            root.get("score")).where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }


    public List<ScoreDTO> query5StuScore(String stuName, String cname) {
        StringBuilder sql = new StringBuilder("SELECT b.name as stuName,c.course_name as courseName,a.score as score " +
            "FROM SCORE a,STUDENT b,TB_COURSE c " +
            "WHERE a.student_id=b.id AND a.course_id=c.id ");
        List<Object> params = new ArrayList<Object>();
        StringBuilder where = new StringBuilder("");
        if (StringUtils.isNotBlank(stuName)) {
            where.append(" and b.name=?");
            params.add(stuName);
        }
        if (StringUtils.isNotBlank(cname)) {
            where.append(" and c.course_name like ?");
            params.add("%" + cname + "%");
        }
        sql = sql.append(where);
        Query query = entityManager.createNativeQuery(sql.toString(), "ScoreDTO");
        int index = 1;
        for (Object param : params) {
            query.setParameter(index, param);
            index++;
        }
        return query.getResultList();

    }

    public List<ScoreDTO> query6StuScore(String stuName, String cname) {
        StringBuilder sql = new StringBuilder("SELECT b.name as stuName,c.course_name as courseName," +
            "a.score as score FROM SCORE a,STUDENT b," +
            "TB_COURSE c WHERE a.student_id=b.id AND a.course_id=c.id");
        List<Object> params = new ArrayList<Object>();
        StringBuilder where = new StringBuilder("");
        if (StringUtils.isNotBlank(stuName)) {
            where.append(" and b.name=?");
            params.add(stuName);
        }
        if (StringUtils.isNotBlank(cname)) {
            where.append(" and c.course_name like ?");
            params.add("%" + cname + "%");
        }
        sql = sql.append(where);
        return jdbcTemplate.query(sql.toString(), params.toArray(new Object[params.size()]),
            new BeanPropertyRowMapper(ScoreDTO.class));
    }
}
