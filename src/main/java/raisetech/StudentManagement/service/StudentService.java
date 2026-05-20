package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentDetail;
import raisetech.StudentManagement.StudentRepository;
import raisetech.StudentManagement.StudentsCourses;
import raisetech.StudentManagement.controller.converter.StudentConverter;

/**
 * 受講生情報を取り扱うサービス 受講生の検索、登録、更新処理を行う
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  //自動でインスタンスを作成
  //Springが管理しているインスタンスやクラスを自動で入れる
  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  //ここに処理を追加する、最初に整理する
  public List<Student> searchThirtyAgeList() {

    return repository.searchThirtyAge();
  }

  public List<StudentsCourses> searchJavaCoursesList() {

    return repository.searchJavaCourse();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchAllStudentsCourses();
  }

  /**
   * 受講生詳細の登録を行う 受講生と受講生コース情報を個別に登録し、受講生コース情報には、受講生情報を紐づける値や日付情報（コース開始日など）
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.insertStudent(student);
    studentDetail.getStudentsCourses().forEach(studentsCourse -> {
      initStudentsCourses(studentsCourse, student);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の書記情報を設定する
   *
   * @param studentsCourse 受講生コース情報
   * @param student        受講生
   */
  private void initStudentsCourses(StudentsCourses studentsCourse, Student student) {
    LocalDate now = LocalDate.now();

    studentsCourse.setStudentId(student.getStudentId());
    studentsCourse.setStartDate(now.toString());
    studentsCourse.setPlannedEndDate(now.plusYears(1).toString());
    studentsCourse.setDeleted(false);
    repository.insertStudentCourses(studentsCourse);
  }

  /**
   * 受講生詳細の一覧検索、全件検索を行う、条件指定なし
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchAllStudentsCourses();
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  /**
   * 受講生検索 IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定 studentId 受講生ID
   *
   * @return 受講生
   */


  public StudentDetail searchStudentDetail(String studentId) {
    Student student = repository.searchStudent(studentId);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCoursesByStudentId(studentId);
    return new StudentDetail(student, studentsCourses);
  }


  /**
   * 受講生詳細の更新を行う 受講生と受講生コース情報をそれぞれ更新する
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentsCourses().stream().filter(StudentsCourses::isDeleted)
        .forEach(course -> repository.updateStudentsCourses(course));
  }
}