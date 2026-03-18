package raisetech.StudentManagement.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentDetail;
import raisetech.StudentManagement.StudentsCourses;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.service.StudentService;
import org.springframework.ui.Model;

@Controller
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
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCoursesList();

    //コンバーター
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses)
    );
    //テンプレートエンジンのファイル名
    return "studentList";
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

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }

    //新規受講生情報を登録するシステムを実装
    service.registerStudent(studentDetail);

    //コース情報も一緒に登録できるように実装（コースは単体）
    return "redirect:/studentList";
  }


  @GetMapping("/updateStudent/{studentId}")
  public String updateStudent(@PathVariable String studentId, Model model) {
    StudentDetail studentDetail = service.searchStudentDetail(studentId);
    model.addAttribute("studentDetail", studentDetail);

    return "updateStudent";
  }

  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "updateStudent";
    }

    //受講生情報を更新するシステムを実装
    service.updateStudent(studentDetail);

    return "redirect:/studentList";
  }


  @GetMapping("/studentDetail/{studentId}")
  public String studentDetail(@PathVariable String studentId, Model model) {
    StudentDetail studentDetail = service.searchStudentDetail(studentId);
    model.addAttribute("studentDetail", studentDetail);

    return "informationStudent";
  }
}
