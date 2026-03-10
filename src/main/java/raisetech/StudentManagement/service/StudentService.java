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

@Service
public class StudentService {

  private StudentRepository repository;

  //自動でインスタンスを作成
  //Springが管理しているインスタンスやクラスを自動で入れる
  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  //ここに処理を追加する、最初に整理する
  public List<Student> searchStudentList() {

    return repository.search();
  }

  public List<Student> searchThirtyAgeList() {

    return repository.searchThirtyAge();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {

    return repository.searchStudentsCourses();
  }

  public List<StudentsCourses> searchJavaCoursesList() {

    return repository.searchJavaCourse();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());

    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      studentsCourse.setStudentId(studentDetail.getStudent().getStudentId());
      studentsCourse.setStartDate(LocalDate.now().toString());
      studentsCourse.setPlannedEndDate(LocalDate.now().plusYears(1).toString());
      repository.insertStudentCourses(studentsCourse);
    }
  }
}
