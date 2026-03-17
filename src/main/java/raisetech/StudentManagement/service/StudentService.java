package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

  public void registerStudent(StudentDetail studentDetail) {

    Student student = studentDetail.getStudent();

    repository.insertStudent(student);
  }
}
