package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentDetail;
import raisetech.StudentManagement.StudentRepository;
import raisetech.StudentManagement.StudentsCourses;
import raisetech.StudentManagement.controller.converter.StudentConverter;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  //テストのたびに処理を行う、メソッド全体でやりたいこと
  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  //受講生検索、受講生コース情報の検索、コンバーターを呼び出すことのテスト
  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    //事前準備
    //StudentService sut = new StudentService(repository, converter);
    List<Student> studentList = new ArrayList<>();
    List<StudentsCourses> studentsCoursesList = new ArrayList<>();

    //返り値を任意に設定
    when(repository.search()).thenReturn(studentList);
    when(repository.searchAllStudentsCourses()).thenReturn(studentsCoursesList);

    //実行
    List<StudentDetail> actual = sut.searchStudentList();
    //List<StudentDetail> expected = new ArrayList<>();

    //検証
    //expectedとactualが同じならテストが完了する
    //Assertions.assertEquals(expected, actual);
    verify(repository, times(1)).search();
    verify(repository, times(1)).searchAllStudentsCourses();
    verify(converter, times(1)).convertStudentDetails(studentList, studentsCoursesList);

    //(後続処理)
    //(ここでDBを元に戻す)
  }

  @Test
  void 受講生情報登録_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    Student student = new Student();
    student.setStudentId("1");

    StudentsCourses course = new StudentsCourses();

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(List.of(course));

    // 実行
    sut.registerStudent(studentDetail);

    // 検証
    verify(repository, times(1)).insertStudent(student);
    verify(repository, times(1)).insertStudentCourses(course);
  }

  @Test
  void 受講生情報更新_リポジトリの処理が適切に呼び出せていること() {
    // 事前準備
    Student student = new Student();
    student.setStudentId("1");

    StudentsCourses course = new StudentsCourses();
    course.setDeleted(true);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(List.of(course));

    // 実行
    sut.updateStudent(studentDetail);

    // 検証
    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentsCourses(course);
  }

}
