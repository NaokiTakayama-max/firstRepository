package raisetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentsCourses;

//データベースを操作するもの

/**
 * 受講生テーブルと受講生テーブルと紐づくrepository
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索
   *
   * @return 受講生一覧（全件）
   */
  List<Student> search();

  /**
   * 30代の受講生のみを検索
   *
   * @return 30代の受講生情報
   */
  List<Student> searchThirtyAge();

  /**
   * 受講生のコース情報の全件検索
   *
   * @return 受講生コース情報（全件）
   */
  List<StudentsCourses> searchAllStudentsCourses();

  /**
   * 受講生コース情報検索
   *
   * @return 受講生コース情報一覧（全件）
   */
  List<StudentsCourses> searchJavaCourse();


  /**
   * 受講生の新規登録 IDは各自設定
   *
   * @param student 受講生
   */
  void insertStudent(Student student);

  /**
   * 受講生コース情報の新規登録 IDは各自設定
   *
   * @param studentsCourses 受講生コース情報
   */
  void insertStudentCourses(StudentsCourses studentsCourses);


  /**
   * 受講生の検索を行う ID 受講生ID
   *
   * @return 受講生
   */
  Student searchStudent(String studentId);


  /**
   * 受講生を更新します
   *
   * @param student 受講生
   */
  void updateStudent(Student student);


  /**
   * 受講生IDに紐づく受講生コース情報を検索
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentsCourses> searchStudentsCoursesByStudentId(String studentId);


  /**
   * 受講生コース情報のコース名を更新します
   *
   * @param course 受講生コース情報
   */
  void updateStudentsCourses(StudentsCourses course);
}
