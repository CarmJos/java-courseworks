package cc.carm.study.courseworks.course5;

public class Student {

    protected String id;
    protected String name;

    protected String major;

    public Student(String id, String name, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public void study() {
        System.out.println(name + " is studying " + major);
    }

    public void displayInfo() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Major: " + major);
    }


}
