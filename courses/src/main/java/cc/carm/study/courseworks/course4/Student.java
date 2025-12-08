package cc.carm.study.courseworks.course4;

import java.util.Arrays;
import java.util.Objects;

public class Student {
    public static int total = 0; // 按理说这里应该用全大写来命名才对吧?

    private String stuId;
    private String name;
    private Course[] courses;

    public Student() {
        this.courses = new Course[3]; // 初始数组长度为3
    }

    // 带学号、姓名、初始课程数组的有参构造方法
    public Student(String stuId, String name, Course[] courses) {
        this.stuId = stuId;
        this.name = name;
        this.courses = courses;
        total++; // 总数自增
    }

    // getter/setter 方法
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCourse(Course c) {
        for (int i = 0; i < courses.length; i++) {
            if (courses[i] == null) {
                courses[i] = c;
                break;
            }
        }
    }

    public int calcTotalCredit() {
        return Arrays.stream(courses).filter(Objects::nonNull).mapToInt(Course::getCredit).sum();
    }

    // showStudent方法
    public void showStudent() {
        System.out.println("学号：" + stuId + "，姓名：" + name);
        System.out.println("所选课程：");
        for (Course course : courses) {
            if (course != null) {
                course.showCourse();
            }
        }
        System.out.println("总学分：" + calcTotalCredit());
        System.out.println();
    }

}
