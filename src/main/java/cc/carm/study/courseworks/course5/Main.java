package cc.carm.study.courseworks.course5;

public class Main {

    public static void main(String[] args) {

        Student student = new Student("S001", "MYX", "IOT Engineering");
        student.study();
        student.displayInfo();

        System.out.println();
        System.out.println("---------------------");
        System.out.println();

        PostgraduateStudent pgStudent = new PostgraduateStudent(
                "P001", "PYG", "IOT Engineering",
                "Dr. Smith", "Artificial Intelligence OF IOT"
        );

        pgStudent.study();
        pgStudent.displayInfo();


    }
}
