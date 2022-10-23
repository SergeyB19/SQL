
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<FacultyRecord> create(@RequestBody FacultyRecord facultyRecord) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facultyService.create(facultyRecord));
    }

    @GetMapping("/{id}")
    public FacultyRecord read(@PathVariable("id") long id) {
        return facultyService.read(id);
    }

    @PutMapping
    public FacultyRecord update(@RequestBody FacultyRecord facultyRecord) {
        return facultyService.update(facultyRecord);
    }

    @DeleteMapping("/{id}")
    public FacultyRecord delete(@PathVariable("id") long id) {
        return facultyService.delete(id);
    }

    @GetMapping(params = "!colorOrName")
    public List<FacultyRecord> facultiesByColor(@RequestParam("color") String color) {
        return facultyService.facultiesByColor(color);
    }

    @GetMapping(params = "colorOrName")
    public List<FacultyRecord> facultiesByColorOrName(@RequestParam("colorOrName") String colorOrName) {
        return facultyService.facultiesByColorOrName(colorOrName);
    }

    @GetMapping("/{id}/students")
    public List<StudentRecord> getFacultyStudents(@PathVariable("id") Long id) {
        return facultyService.getFacultyStudents(id);
    }

}
