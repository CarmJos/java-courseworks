package cc.carm.study.courseworks.course4;

public class Course {

    private String courseName;
    private int credit;

    public Course() {
    }

    public Course(String courseName, int credit) {
        this.courseName = courseName;
        this.credit = credit;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void showCourse() {
        System.out.println("课程：" + courseName + "，学分：" + credit);
    }

}
