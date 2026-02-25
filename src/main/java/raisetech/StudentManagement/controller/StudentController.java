package raisetech.StudentManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentDetail;
import raisetech.StudentManagement.StudentsCourses;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  //リクエストの加工処理、入力チェックをする処理を加える場合もある
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCoursesList();

    //コンバーター・コンバート
    return converter.convertStudentDetails(students, studentsCourses);
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
