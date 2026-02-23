package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentsCourses;
import raisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  public StudentController(StudentService service) {
    this.service = service;
  }
  //リクエストの加工処理、入力チェックをする処理を加える場合もある

  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentList/ThirtyAge")
  public List<Student> getThirtyAgeList() {
    return service.searchThirtyAgeList();
  }

  @GetMapping("/studentsCoursesList")
  public List<StudentsCourses> getStudentsCoursesList() {
    return service.searchStudentsCoursesList();
  }

  @GetMapping("/studentsCoursesList/Java")
  public List<StudentsCourses> getJavaCoursesList() {
    return service.searchJavaCoursesList();

  }
}
