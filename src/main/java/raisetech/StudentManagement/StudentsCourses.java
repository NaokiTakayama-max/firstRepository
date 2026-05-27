package raisetech.StudentManagement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentsCourses {

  @NotBlank
  private String studentId;

  @NotBlank
  private String studentsCoursesId;

  @NotBlank
  private String courseName;

  @NotBlank
  private String startDate;

  @NotBlank
  private String plannedEndDate;

  private String remark;
  private boolean deleted;
}
