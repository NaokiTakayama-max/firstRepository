package raisetech.StudentManagement;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentsCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;


}
