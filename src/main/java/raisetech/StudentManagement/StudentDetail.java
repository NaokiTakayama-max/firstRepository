package raisetech.StudentManagement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentsCourses;

//どういうデータ構造なのか
@Schema(description = "受講生詳細")
//StudentとStudentsCoursesが取得したデータを細かく処理するための場
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;

  @Valid
  private List<StudentsCourses> studentsCourses;
}
