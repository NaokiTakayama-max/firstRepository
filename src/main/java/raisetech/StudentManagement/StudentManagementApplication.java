package raisetech.StudentManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  private String name = "Naoki Takayama";
  private String age = "23";

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/name")
  public String getName() {
    return name;
  }

  @GetMapping("/age")
  public String getAge() {
    return age;
  }

  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    return name + " " + age + "歳";
  }


  @PostMapping("/name")
  public void setName(String name) {
    this.name = name;
  }

  @PostMapping("/age")
  public void setAge(String age) {
    this.age = age;
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(String name, String age) {
    this.name = name;
    this.age = age;
  }

}
