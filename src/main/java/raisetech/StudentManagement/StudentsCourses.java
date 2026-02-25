package raisetech.StudentManagement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  private String studentId;
  private String studentsCoursesId;
  private String courseName;
  private String startDate;
  private String plannedEndDate;
  private String remark;
  private String isDeleted;
}
