package raisetech.StudentManagement.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentDetail;
import raisetech.StudentManagement.StudentRepository;
import raisetech.StudentManagement.StudentsCourses;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
//HTTPリクエストの疑似実行
@AutoConfigureMockMvc
class StudentControllerTest {

  @MockBean
  private StudentService service;

  @MockBean
  private StudentRepository repository;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  //HTTPリクエストを送る
  @Autowired
  MockMvc mockMvc;

  //JSON変換、POSTとPUT
  @Autowired
  ObjectMapper objectMapper;

  //student、studentCourses、studentDetailの作成
  private StudentDetail createStudentDetail(String id, String name) {
    Student student = new Student();
    student.setStudentId(id);
    student.setName(name);
    student.setFurigana("タロウ");
    student.setNickname("たろう");
    student.setGender("男性");
    student.setAge(20);
    student.setMailAddress("taro@test.com");
    student.setRegion("東京");
    student.setRemark("テスト");
    student.setDeleted(false);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentsCourses(new ArrayList<>());

    return detail;
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setStudentId("1");
    student.setName("山田太郎");
    student.setFurigana("タロウ");
    student.setNickname("たろう");
    student.setGender("男性");
    student.setAge(20);
    student.setMailAddress("taro@test.com");
    student.setRegion("東京");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いたときに入力チェックに掛かること() {
    Student student = new Student();
    student.setStudentId("テストです。");
    student.setName("山田太郎");
    student.setFurigana("タロウ");
    student.setNickname("たろう");
    student.setGender("男性");
    student.setAge(20);
    student.setMailAddress("taro@test.com");
    student.setRegion("東京");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    //assertThat(violations).extracting("message").containsOnly("数字のみ入力するようにしてください。");
  }

  @Test
  void 受講生のコース情報が空欄の場合に入力チェックに掛かること() {
    StudentsCourses course = new StudentsCourses();
    course.setCourseName("");
    course.setStudentId("1");
    course.setStudentsCoursesId("SC001");
    course.setStartDate("2026-02-01");
    course.setPlannedEndDate("2026-08-31");

    Set<ConstraintViolation<StudentsCourses>> violations = validator.validate(course);

    assertThat(violations.size()).isEqualTo(1);
  }

  //受講生一覧検索のテスト
  @Test
  void 受講生情報の一覧検索が成功すること() throws Exception {
    List<StudentDetail> studentDetailList = new ArrayList<>();
    studentDetailList.add(createStudentDetail("1", "Taro"));

    when(service.searchStudentList()).thenReturn(studentDetailList);

    //JSONの配列を指定する場合は"$[0]…"
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].student.studentId").value("1"))
        .andExpect(jsonPath("$[0].student.name").value("Taro"));

    verify(service).searchStudentList();
  }

  @Test
  void 受講生詳細検索が成功すること() throws Exception {
    StudentDetail detail = createStudentDetail("1", "Taro");

    when(service.searchStudentDetail("1")).thenReturn(detail);

    //JSONの単体オブジェクトを指定する場合は"$…"
    mockMvc.perform(get("/student/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.studentId").value("1"))
        .andExpect(jsonPath("$.student.name").value("Taro"));

    verify(service).searchStudentDetail("1");
  }

  //受講生情報の登録のテスト
  @Test
  void 受講生情報の登録が成功すること() throws Exception {
    StudentDetail detail = createStudentDetail("1", "Taro");

    mockMvc.perform(post("/registerStudent")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(detail)))
        .andExpect(status().isOk());

    verify(service).registerStudent(org.mockito.ArgumentMatchers.any(StudentDetail.class));
  }

  //受講生情報の更新のテスト
  @Test
  void 受講生情報の更新が成功すること() throws Exception {
    StudentDetail detail = createStudentDetail("1", "Taro");

    mockMvc.perform(put("/updateStudent")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(detail)))
        .andExpect(status().isOk());

    verify(service).updateStudent(org.mockito.ArgumentMatchers.any(StudentDetail.class));
  }

}