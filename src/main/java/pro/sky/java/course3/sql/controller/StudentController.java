
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentRecord> create(@RequestBody StudentRecord studentRecord) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.create(studentRecord));
    }

    @GetMapping("/{id}")
    public StudentRecord read(@PathVariable("id") long id) {
        return studentService.read(id);
    }

    @PutMapping
    public StudentRecord update(@RequestBody StudentRecord studentRecord) {
        return studentService.update(studentRecord);
    }

    @DeleteMapping("/{id}")
    public StudentRecord delete(@PathVariable("id") long id) {
        return studentService.delete(id);
    }

    @GetMapping
    public List<StudentRecord> studentsByAge(@RequestParam("age") int age) {
        return studentService.studentsByAge(age);
    }

    @GetMapping(params = {"min", "max"})
    public List<StudentRecord> studentsBetweenAges(@RequestParam("min") int min,
                                                   @RequestParam("max") int max) {
        return studentService.studentsBetweenAges(min, max);
    }

    @GetMapping("/{id}/faculty")
    public FacultyRecord getStudentFaculty(@PathVariable("id") long id) {
        return studentService.getStudentFaculty(id);
    }

    @GetMapping("/totalCount")
    public int totalCountOfStudents() {
        return studentService.totalCountOfStudents();
    }

    @GetMapping("/averageAge")
    public double averageAgeOfStudents() {
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/lastStudents")
    public List<StudentRecord> lastStudents(@RequestParam @Min(1) @Max(10) int count) {
        return studentService.lastStudents(count);
    }

}
