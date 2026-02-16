package raisetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

//データベースを操作するもの

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchCourses();
}
