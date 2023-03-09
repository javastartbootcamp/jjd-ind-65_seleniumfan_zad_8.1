package pl.javastart.task;

public class Student extends Person {
    private int index;
    private Group group;
    private double grade;

    public Student(int index, Group group, String firstName, String lastName) {
        super(firstName, lastName);
        this.index = index;
        this.group = group;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void printDetails() {
        System.out.print(index + " ");
        super.printDetails();
    }

    public void printInfo() {
        printDetails();
        System.out.println(": " + grade);
    }
}
