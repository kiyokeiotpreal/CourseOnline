package com.example.BaiTest.repository;

import com.example.BaiTest.model.CourseType;
import com.example.BaiTest.responses.CourseTypeResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.print.Pageable;
import java.util.List;


@Repository

public interface CourseTypeRepo extends JpaRepository<CourseType, Integer> {

    List<CourseType> findByNameContaining(String name);


    @Query(nativeQuery = true,
            value = "WITH RankedCourses AS (" +
                    "    SELECT" +
                    "        c.image_course," +
                    "        c.name_of_course," +
                    "        c.lesson_count," +
                    "        c.time_lesson_total," +
                    "        ct.name AS course_type_name," +
                    "        c.register_count," +
                    "        ROW_NUMBER() OVER (PARTITION BY c.course_type_id ORDER BY c.register_count DESC) AS rn" +
                    "    FROM" +
                    "        baitestok.course c" +
                    "    JOIN" +
                    "        baitestok.course_type ct ON c.course_type_id = ct.id" +
                    ")," +
                    "Top3CourseTypes AS (" +
                    "    SELECT" +
                    "        ct.name AS course_type_name," +
                    "        SUM(c.register_count) AS total_register_count" +
                    "    FROM" +
                    "        baitestok.course c" +
                    "    JOIN" +
                    "        baitestok.course_type ct ON c.course_type_id = ct.id" +
                    "    GROUP BY" +
                    "        ct.name" +
                    "    ORDER BY" +
                    "        total_register_count DESC" +
                    "    LIMIT 3" +
                    ")" +
                    "SELECT" +
                    "    rc.image_course," +
                    "    rc.name_of_course," +
                    "    rc.course_type_name," +
                    "    rc.lesson_count," +
                    "    rc.time_lesson_total" +
                    " FROM" +
                    "    RankedCourses rc" +
                    " JOIN" +
                    "    Top3CourseTypes tt ON rc.course_type_name = tt.course_type_name" +
                    " WHERE" +
                    "    rc.rn <= 15" +
                    " ORDER BY" +
                    "    rc.register_count DESC;")
    List< Object[] > getTop3CourseTypes();

    @Query("SELECT ct FROM CourseType ct")
    List<CourseType> getAll();


}
