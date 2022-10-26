package pro.sky.java.course3.sql.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course3.sql.component.RecordMapper;
import pro.sky.java.course3.sql.entity.Faculty;
import pro.sky.java.course3.sql.exception.FacultyNotFoundException;
import pro.sky.java.course3.sql.record.FacultyRecord;
import pro.sky.java.course3.sql.repository.FacultyRepository;
import pro.sky.java.course3.sql.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository,
                          RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord create(FacultyRecord facultyRecord) {
        logger.info("Was invoked method for create faculty");
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord update(FacultyRecord faculty) {
        logger.info("Was invoked method for update faculty");
        Faculty oldFaculty = facultyRepository.findById(faculty.getId())
                .orElseThrow(FacultyNotFoundException::new);
        oldFaculty.setName(faculty.getName());
        oldFaculty.setColor(faculty.getColor());
        return recordMapper.toRecord(facultyRepository.save(oldFaculty));
    }

    public FacultyRecord read(long id) {
        logger.info("Was invoked method for read faculty");
        return facultyRepository.findById(id)
                .map(recordMapper::toRecord)
                .orElseThrow(FacultyNotFoundException::new);
    }

    public FacultyRecord delete(long id) {
        logger.info("Was invoked method for delete faculty");
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(FacultyNotFoundException::new);
        facultyRepository.delete(faculty);
        return recordMapper.toRecord(faculty);
    }

    public List<FacultyRecord> facultiesByColor(String color) {
        return facultyRepository.findAllByColor(color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public List<FacultyRecord> facultiesByColorOrName(String colorOrName) {
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(colorOrName, colorOrName).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public List<StudentRecord> getFacultyStudents(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(FacultyNotFoundException::new)
                .getStudents().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

}
