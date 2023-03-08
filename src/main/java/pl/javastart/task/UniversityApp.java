package pl.javastart.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UniversityApp {
    List<Group> groups = new ArrayList<>();
    List<Lecturer> lecturers = new ArrayList<>();
    List<Student> students = new ArrayList<>();

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */
    public void createLecturer(int id, String degree, String firstName, String lastName) {
        if (Objects.nonNull(getLecturer(id))) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
        } else {
            lecturers.add(new Lecturer(id, degree, firstName, lastName));
        }
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int, String, String, String)}
     */
    public void createGroup(String code, String name, int lecturerId) {
        checkGroupExists(code);

        if (Objects.isNull(getLecturer(lecturerId))) {
            System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
            return;
        }
        groups.add(new Group(code, name, lecturerId));
    }

    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */
    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {
        checkGroupNotExists(groupCode);

        if (Objects.nonNull(getStudent(index)) && getStudent(index).getGroup().getCode().equals(groupCode)) {
            System.out.println("Student o indeksie " + index + " jest już w grupie " + groupCode);
            return;
        }

        Group group = getGroup(groupCode);
        boolean isStudentExists = false;
        for (Student student : students) {
            if (student.getGroup() == group && student.getIndex() == index) {
                isStudentExists = true;
                break;
            }
        }
        if (!isStudentExists) {
            students.add(new Student(index, group, firstName, lastName));
        }
    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */
    public void printGroupInfo(String groupCode) {
        if (Objects.isNull(getGroup(groupCode))) {
            System.out.println("Grupa " + groupCode + " nie znaleziona");
        } else {
            Group group = getGroup(groupCode);
            Lecturer lecturer = getLecturer(group.getLecturer());
            System.out.println("Kod: " + group.getCode());
            System.out.println("Nazwa: " + group.getName());
            System.out.println("Prowadzący: " + lecturer.getDegree() +
                    " " + lecturer.getFirstName() +
                    " " + lecturer.getLastName());
        }

        for (Student student : students) {
            student.printDetails();
        }
    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */
    public void addGrade(int studentIndex, String groupCode, double grade) {
        checkGroupNotExists(groupCode);

        if (Objects.isNull(getStudent(studentIndex, groupCode)) ||
                !Objects.equals(getStudent(studentIndex, groupCode).getGroup().getCode(), groupCode)) {
            System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            return;
        }

        if (getStudent(studentIndex, groupCode).getGrade() > 0) {
            System.out.println("Student o indeksie " + studentIndex + " ma już wystawioną ocenę dla grupy " + groupCode);
            return;
        }

        getStudent(studentIndex, groupCode).setGrade(grade);
    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */
    public void printGradesForStudent(int index) {
        for (Student student : students) {
            if (student.getIndex() == index) {
                System.out.println(student.getGroup().getName() + ": " + student.getGrade());
            }
        }
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */
    public void printGradesForGroup(String groupCode) {
        boolean isExist = false;
        for (Student student : students) {
            if (student.getGroup().getCode().equals(groupCode)) {
                System.out.println(student.getIndex() + " " + student.getFirstName() + " " + student.getLastName() + ": " + student.getGrade());
                isExist = true;
            }
        }
        if (!isExist) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */
    public void printAllStudents() {
        List<Student> uniqueStudents = students.stream()
                .map(Student::getIndex)
                .distinct()
                .flatMap(index -> students.stream()
                        .filter(student -> student.getIndex() == index)
                        .limit(1))
                .toList();

        for (Student student : uniqueStudents) {
            student.printDetails();
        }
    }

    private void checkGroupExists(String code) {
        if (Objects.nonNull(getGroup(code))) {
            System.out.println("Grupa " + code + " już istnieje");
        }
    }

    private void checkGroupNotExists(String code) {
        if (Objects.isNull(getGroup(code))) {
            System.out.println("Grupa " + code + " nie istnieje");
        }
    }

    private Group getGroup(String groupCode) {
        for (Group group : groups) {
            if (group.getCode().equals(groupCode)) {
                return group;
            }
        }
        return null;
    }

    private Lecturer getLecturer(int lecturerId) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getId() == lecturerId) {
                return lecturer;
            }
        }
        return null;
    }

    private Student getStudent(int index, String groupCode) {
        for (Student student : students) {
            if (student.getIndex() == index && student.getGroup().getCode().equals(groupCode)) {
                return student;
            }
        }
        return null;
    }

    private Student getStudent(int index) {
        for (Student student : students) {
            if (student.getIndex() == index) {
                return student;
            }
        }
        return null;
    }
}
