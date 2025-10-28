package cc.carm.study.courseworks.course5;

public class PostgraduateStudent extends Student {

    protected String supervisor;
    protected String researchArea;

    public PostgraduateStudent(String id, String name, String major, String supervisor, String researchArea) {
        super(id, name, major);
        this.supervisor = supervisor;
        this.researchArea = researchArea;
    }


    public String getSupervisor() {
        return supervisor;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void research() {
        System.out.println(name + " is researching in the area of " + researchArea + ".");
    }

    @Override
    public void study() {
        System.out.println(name + " is  researching in the area of " + major + " under the supervision of " + supervisor + ".");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Supervisor: " + supervisor);
        System.out.println("Research Area: " + researchArea);
    }
}
