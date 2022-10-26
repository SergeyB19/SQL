package pro.sky.java.course3.sql.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course3.sql.component.RecordMapper;
import pro.sky.java.course3.sql.entity.Faculty;
import pro.sky.java.course3.sql.entity.Student;
import pro.sky.java.course3.sql.exception.FacultyNotFoundException;
import pro.sky.java.course3.sql.exception.StudentNotFoundException;
import pro.sky.java.course3.sql.record.FacultyRecord;
import pro.sky.java.course3.sql.record.StudentRecord;
import pro.sky.java.course3.sql.repository.FacultyRepository;
import pro.sky.java.course3.sql.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository,
                          RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public StudentRecord create(StudentRecord studentRecord) {
        logger.info("Was invoked method for create student");
        Student student = recordMapper.toEntity(studentRecord);
        if (studentRecord.getFaculty() != null) {
            Faculty faculty = facultyRepository.findById(studentRecord.getFaculty().getId()).orElseThrow(FacultyNotFoundException::new);
            student.setFaculty(faculty);
        }
        return recordMapper.toRecord(studentRepository.save(student));
    }

    public StudentRecord update(StudentRecord studentRecord) {
        logger.info("Was invoked method for update student");
        Student oldStudent = studentRepository.findById(studentRecord.getId())
                .orElseThrow(StudentNotFoundException::new);
        oldStudent.setAge(studentRecord.getAge());
        oldStudent.setName(studentRecord.getName());
        return recordMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord read(long id) {
        logger.info("Was invoked method for read student");
        return studentRepository.findById(id)
                .map(recordMapper::toRecord)
                .orElseThrow(StudentNotFoundException::new);
        logger.error("There is not student with id = " + id);
    }

    public StudentRecord delete(long id) {
        logger.info("Was invoked method for delete student");
        Student student = studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(student);
        return recordMapper.toRecord(student);
    }

    public List<StudentRecord> studentsByAge(int age) {
        return studentRepository.findAllByAge(age).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public List<StudentRecord> studentsBetweenAges(int min, int max) {
        return studentRepository.findAllByAgeBetween(min, max).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord getStudentFaculty(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);
        if (student.getFaculty() == null) {
            throw new FacultyNotFoundException();
        }
        return recordMapper.toRecord(student.getFaculty());
    }

    public int totalCountOfStudents() {
        return studentRepository.totalCountOfStudents();
    }

    public double averageAgeOfStudents() {
        return studentRepository.averageAgeOfStudents();
    }

    public List<StudentRecord> lastStudents(int count) {
        return studentRepository.lastStudents(count).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

}
