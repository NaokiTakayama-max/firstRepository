package raisetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentsCourses;

//データベースを操作するもの

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student")
  List<Student> search();

  @Select("SELECT * FROM student WHERE age BETWEEN 30 AND 39")
  List<Student> searchThirtyAge();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  @Select("SELECT * FROM students_courses WHERE course_name = 'Java基礎コース'")
  List<StudentsCourses> searchJavaCourse();

  @Insert("""
      INSERT INTO student
      (studentId, name, furigana, nickname, gender, age, mail_address, region, remark, isDelete)
      VALUES
      (#{studentId}, #{name}, #{furigana}, #{nickname}, #{gender}, #{age}, #{mailAddress}, #{region}, #{remark}, #{isDeleted})
      """)
  void insertStudent(Student student);

  @Insert("""
      INSERT INTO students_courses
      (student_id, course_name, start_date, planned_end_date, remark, is_deleted)
      VALUES
      (#{studentId}, #{courseName}, #{startDate}, #{plannedEndDate}, #{remark}, #{isDeleted})
      """)
  void insertStudentCourses(StudentsCourses studentsCourses);
}
