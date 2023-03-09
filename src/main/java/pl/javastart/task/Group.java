package pl.javastart.task;

public class Group {
    private String code;
    private String name;
    private Lecturer lecturer;

    public Group(String code, String name, Lecturer lecturer) {
        this.code = code;
        this.name = name;
        this.lecturer = lecturer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public void printInfo() {
        System.out.println("Kod: " + code);
        System.out.println("Nazwa: " + name);
        System.out.println("Prowadzący: " + lecturer.getDegree() +
                " " + lecturer.getFirstName() +
                " " + lecturer.getLastName());
    }
}
