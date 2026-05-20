package raisetech.StudentManagement.controller;

import exception.TestException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.executable.ValidateOnExecution;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentDetail;
import raisetech.StudentManagement.StudentsCourses;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.service.StudentService;
import org.springframework.ui.Model;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるcontrollerです。
 */
@Validated
@RestController
public class StudentController {


  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索機能、全件検索を行う。
   *
   * @return 受講生詳細一覧（全件）
   */
  //リクエストの加工処理、入力チェックをする処理を加える場合もある
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索 IDに紐づく任意の受講生の情報を取得 studentId 受講生ID
   *
   * @return 受講生
   */
  @Operation(summary = "一覧検索",description = "受講生一覧を検索します。")
  @GetMapping("student/{studentId}")
  public StudentDetail getStudent(@PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String studentId) {
    return service.searchStudentDetail(studentId);
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


  /**
   * 受講生情報の新規登録 studentDetail 受講生詳細
   *
   * @return 実行結果
   */
  @Operation(summary = "受講生登録",description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentdetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentdetail);
  }

  /**
   * 受講生詳細の更新を行う キャンセルフラグの更新もここで行う（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  //PutMappingは全体更新
  //BindingResult result、if (result.hasErrors())は画面用
  //ResponseEntity:処理が成功したのか、失敗したのかを判定
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  //受講生情報の更新
  @GetMapping("/updateStudent/{studentId}")
  public String updateStudent(@PathVariable String studentId, Model model) {
    StudentDetail studentDetail = service.searchStudentDetail(studentId);
    model.addAttribute("studentDetail", studentDetail);

    return "updateStudent";
  }

  @GetMapping("/studentDetail/{studentId}")
  public String studentDetail(@PathVariable String studentId, Model model) {
    StudentDetail studentDetail = service.searchStudentDetail(studentId);
    model.addAttribute("studentDetail", studentDetail);

    return "informationStudent";
  }

  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
