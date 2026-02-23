package raisetech.StudentManagement;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("raisetech.StudentManagement")
@RestController
public class StudentManagementApplication {

  //自動でインスタンスを作成
  //Springが管理しているインスタンスやクラスを自動で入れる
  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/student")
  public List<Student> getStudentList() {
    return repository.search();
  }

  @GetMapping("/studentsCoursesList")
  public List<StudentsCourses> getStudentsCorsesList() {
    return repository.searchCourses();
  }
}