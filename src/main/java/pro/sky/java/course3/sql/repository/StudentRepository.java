
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByAge(int age);

    List<Student> findAllByAgeBetween(int min, int max);

    @Query(value = "SELECT count(id) FROM students", nativeQuery = true)
    int totalCountOfStudents();

    @Query(value = "SELECT avg(age) FROM students", nativeQuery = true)
    double averageAgeOfStudents();

    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT :count", nativeQuery = true)
    List<Student> lastStudents(@Param("count") int count);

}
