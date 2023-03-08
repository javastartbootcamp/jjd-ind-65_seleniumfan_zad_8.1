package pl.javastart.task;

public class Group {
    private String code;
    private String name;
    private int lecturerId;

    public Group(String code, String name, int lecturerId) {
        this.code = code;
        this.name = name;
        this.lecturerId = lecturerId;
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

    public int getLecturer() {
        return lecturerId;
    }

    public void setLecturer(int lecturerId) {
        this.lecturerId = lecturerId;
    }
}
