package pro.sky.java.course3.sql.component;

import org.springframework.stereotype.Component;
import pro.sky.java.course3.sql.entity.Avatar;
import pro.sky.java.course3.sql.entity.Faculty;
import pro.sky.java.course3.sql.entity.Student;
import pro.sky.java.course3.sql.record.AvatarRecord;
import pro.sky.java.course3.sql.record.FacultyRecord;
import pro.sky.java.course3.sql.record.StudentRecord;

@Component
public class RecordMapper {

    public StudentRecord toRecord(Student student) {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setId(student.getId());
        studentRecord.setName(student.getName());
        studentRecord.setAge(student.getAge());
        if (student.getFaculty() != null) {
            studentRecord.setFaculty(toRecord(student.getFaculty()));
        }
        return studentRecord;
    }

    public FacultyRecord toRecord(Faculty faculty) {
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setId(faculty.getId());
        facultyRecord.setName(faculty.getName());
        facultyRecord.setColor(faculty.getColor());
        return facultyRecord;
    }

    public AvatarRecord toRecord(Avatar avatar) {
        AvatarRecord avatarRecord = new AvatarRecord();
        avatarRecord.setId(avatar.getId());
        avatarRecord.setStudent(toRecord(avatar.getStudent()));
        avatarRecord.setUrl("http://localhost:8080/avatar/fromFs/" + avatarRecord.getStudent().getId());
        return avatarRecord;
    }

    public Student toEntity(StudentRecord studentRecord) {
        Student student = new Student();
        student.setAge(studentRecord.getAge());
        student.setName(studentRecord.getName());
        return student;
    }

    public Faculty toEntity(FacultyRecord facultyRecord) {
        Faculty faculty = new Faculty();
        faculty.setColor(facultyRecord.getColor());
        faculty.setName(facultyRecord.getName());
        return faculty;
    }

}
