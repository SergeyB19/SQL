package pro.sky.java.course3.sql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.sky.java.course3.sql.entity.Student;

import java.util.List;

@Service
@Profile("getPort")
public class InfoService {

    @Value("${server.port}")
    private String serverPort;

    public String getServerPort() {
        return serverPort;
    }

    public void printStudents() {
        List<Student> students = studentRepository.findAll(PageRequest.of(0, 6)).getContent();

        printStudents(students.subList(0,2));
        new Thread(()->printStudents(students.subList(2,4))).start();
        new Thread(()->printStudents(students.subList(4,6))).start();
    }

    private void printStudents(List<Student> students) {
        for (Student student : students) {
            LOG.info(student.getName());
        }
    }

    private  synchronized void printStudents(List<Student> students) {
        for (Student student : students) {
            LOG.info(student.getName());
        }
    }

    public void printStudentSync() {
        List<Student> students = studentRepository.findAll(PageRequest.of(0, 6)).getContent();
        printStudents(students.subList(0,2));
        new Thread(()->printStudents(students.subList(2,4))).start();
        new Thread(()->printStudents(students.subList(4,6))).start();
    }
}
