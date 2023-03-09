package pl.javastart.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UniversityAppTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final UniversityApp universityApp = new UniversityApp();

    @Test
    void shouldCreateLecturer() {
        // when
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");

        // then - no exception is thrown
    }

    @Test
    void shouldNotCreateLecturersWithSameId() {
        // when
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createLecturer(1, "prof.", "Zbigniew", "Mazurek");

        // then
        assertThat(outContent.toString()).contains("Prowadzący z id 1 już istnieje");
    }

    @Test
    void shouldCreate100Lecturers() {
        // when
        for (int i = 1; i < 101; i++) {
            universityApp.createLecturer(i, "dr", "Janusz", "Rataj");
        }

        // then - no exception is thrown
    }

    @Test
    void shouldCreateGroup() {
        // given
        String groupCode = "pp-2022";

        // when
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup(groupCode, "Podstawy Programowania", 1);
        universityApp.printGroupInfo(groupCode);

        // then
        assertAll(
                () -> assertThat(outContent.toString()).contains("Kod: pp-2022"),
                () -> assertThat(outContent.toString()).contains("Nazwa: Podstawy Programowania"),
                () -> assertThat(outContent.toString()).contains("Prowadzący: dr Janusz Rataj")
        );
    }

    @Test
    void shouldCreate100Groups() {
        // given
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");

        // when
        for (int i = 0; i < 100; i++) {
            universityApp.createGroup("pp-" + i, "Podstawy programowania", 1);
        }

        // then - no exception is thrown
    }

    @Test
    void shouldDisplayMessageWhenGroupNotFound() {
        // when
        universityApp.printGroupInfo("angebra-2022");

        // then
        assertThat(outContent.toString()).contains("Grupa angebra-2022 nie znaleziona");
    }

    @Test
    void shouldCreateTwoGroups() {
        // given
        String groupCode1 = "pp-2022";
        String groupCode2 = "db-2022";
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createLecturer(2, "prof.", "Zbigniew", "Mazurek");

        // when
        universityApp.createGroup(groupCode1, "Podstawy Programowania", 1);
        universityApp.createGroup(groupCode2, "Bazy Danych", 2);
        universityApp.printGroupInfo(groupCode1);
        universityApp.printGroupInfo(groupCode2);

        // then
        assertAll(
                () -> assertThat(outContent.toString()).contains("Kod: pp-2022"),
                () -> assertThat(outContent.toString()).contains("Nazwa: Podstawy Programowania"),
                () -> assertThat(outContent.toString()).contains("Prowadzący: dr Janusz Rataj"),
                () -> assertThat(outContent.toString()).contains("Kod: db-2022"),
                () -> assertThat(outContent.toString()).contains("Nazwa: Bazy Danych"),
                () -> assertThat(outContent.toString()).contains("Prowadzący: prof. Zbigniew Mazurek")
        );
    }

    @Test
    void shouldNotAddGroupTwice() {
        // given
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup("pp-2022", "Podstawy Programowania", 1);

        // when
        universityApp.createGroup("pp-2022", "Podstawy Programowania", 1);

        // then
        assertThat(outContent.toString()).contains("Grupa pp-2022 już istnieje");
    }

    @Test
    void shouldAddStudentToGroup() {
        // given
        String groupCode = "pp-2022";
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup(groupCode, "Podstawy Programowania", 1);

        // when
        universityApp.addStudentToGroup(179128, groupCode, "Marcin", "Abacki");
        universityApp.addStudentToGroup(179129, groupCode, "Adam", "Browarski");
        universityApp.printGroupInfo(groupCode);

        // then
        assertAll(
                () -> assertThat(outContent.toString()).contains("Marcin Abacki"),
                () -> assertThat(outContent.toString()).contains("Adam Browarski")
        );
    }

    @Test
    void shouldNotAddStudentToGroupTwice() {
        // given
        String groupCode = "pp-2022";
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup(groupCode, "Podstawy Programowania", 1);

        // when
        universityApp.addStudentToGroup(179128, groupCode, "Marcin", "Abacki");
        universityApp.addStudentToGroup(179128, groupCode, "Marcin", "Browarski");
        universityApp.printGroupInfo(groupCode);

        // then
        assertAll(
                () -> assertThat(outContent.toString()).contains("Student o indeksie 179128 jest już w grupie pp-2022"),
                () -> assertThat(outContent.toString()).contains("Marcin Abacki")
        );
    }

    @Test
    void shouldNotAddStudentToGroupIfItDoesntExit() {
        // when
        universityApp.addStudentToGroup(179128, "po-2022", "Marcin", "Abacki");

        // then
        assertThat(outContent.toString()).contains("Grupa po-2022 nie istnieje");
    }

    @Test
    void shouldAddGradeAndPrintForStudent() {
        // given
        String groupCode = "pp-2022";
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup(groupCode, "Podstawy Programowania", 1);
        universityApp.addStudentToGroup(179128, groupCode, "Marcin", "Abacki");

        // when
        universityApp.addGrade(179128, groupCode, 5.0);
        universityApp.printGradesForStudent(179128);

        // then
        assertThat(outContent.toString()).contains("Podstawy Programowania: 5.0");
    }

    @Test
    void shouldAddGradeAndPrintForGroup() {
        // given
        String groupCode = "pp-2022";
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup(groupCode, "Podstawy Programowania", 1);
        universityApp.addStudentToGroup(179128, groupCode, "Marcin", "Abacki");

        // when
        universityApp.addGrade(179128, groupCode, 5);
        universityApp.printGradesForGroup("pp-2022");

        // then
        assertThat(outContent.toString()).contains("Marcin Abacki: 5");
    }

    @Test
    void shouldNotAddGradeWhenGroupDoesntExist() {
        // when
        universityApp.addGrade(179128, "pp-2022", 5);

        // then
        assertThat(outContent.toString()).contains("Grupa pp-2022 nie istnieje");
    }

    @Test
    void shouldAddMultipleGradesForStudent() {
        // given
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup("pp-2022", "Podstawy Programowania", 1);
        universityApp.createGroup("po-2022", "Programowanie Obiektowe", 1);
        universityApp.addStudentToGroup(179128, "pp-2022", "Marcin", "Abacki");
        universityApp.addStudentToGroup(179128, "po-2022", "Marcin", "Abacki");
        universityApp.addGrade(179128, "pp-2022", 5.0);
        universityApp.addGrade(179128, "po-2022", 5.5);

        // when
        universityApp.printGradesForStudent(179128);

        // then
        assertThat(outContent.toString()).contains("Podstawy Programowania: 5.0");
        assertThat(outContent.toString()).contains("Programowanie Obiektowe: 5.5");
    }

    @Test
    void shouldAddMultipleGradesForGroup() {
        // given
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup("pp-2022", "Podstawy Programowania", 1);
        universityApp.addStudentToGroup(179128, "pp-2022", "Marcin", "Abacki");
        universityApp.addStudentToGroup(179129, "pp-2022", "Adam", "Browarski");
        universityApp.addGrade(179128, "pp-2022", 5);
        universityApp.addGrade(179129, "pp-2022", 4.5);

        // when
        universityApp.printGradesForGroup("pp-2022");

        // then
        assertThat(outContent.toString()).contains("Marcin Abacki: 5");
        assertThat(outContent.toString()).contains("Adam Browarski: 4.5");
    }

    @Test
    void shouldNotAddGradeTwice() {
        // given
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup("pp-2022", "Podstawy Programowania", 1);
        universityApp.createGroup("po-2022", "Programowanie obiektowe", 1);
        universityApp.addStudentToGroup(179128, "pp-2022", "Marcin", "Abacki");
        universityApp.addGrade(179128, "pp-2022", 5);

        // when
        universityApp.addGrade(179128, "pp-2022", 4);

        // then
        assertThat(outContent.toString()).contains("Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022");
    }

    @Test
    void shouldNotAddWhenStudentNotInGroup() {
        // given
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup("pp-2022", "Podstawy Programowania", 1);
        universityApp.createGroup("po-2022", "Programowanie obiektowe", 1);

        // when
        universityApp.addGrade(179128, "pp-2022", 4);

        // then
        assertThat(outContent.toString()).contains("Student o indeksie 179128 nie jest zapisany do grupy pp-2022");
    }

    @Test
    void shouldNotPrintGradesWhenGroupDoesntExits() {
        // when
        universityApp.printGradesForGroup("pp-2022");

        // then
        assertThat(outContent.toString()).contains("Grupa pp-2022 nie istnieje");
    }

    @Test
    void shouldPrintAllStudentsWithoutDuplicates() {
        // given
        universityApp.createLecturer(1, "dr", "Janusz", "Rataj");
        universityApp.createGroup("pp-2022", "Podstawy Programowania", 1);
        universityApp.createGroup("po-2022", "Programowanie Obiektowe", 1);
        universityApp.addStudentToGroup(179128, "pp-2022", "Marcin", "Abacki");
        universityApp.addStudentToGroup(179128, "po-2022", "Marcin", "Abacki");
        universityApp.addGrade(179128, "pp-2022", 5.0);
        universityApp.addGrade(179128, "po-2022", 5.5);

        // when
        universityApp.printAllStudents();

        // then
        String resultString = outContent.toString();
        List<String> resultRows = Arrays.stream(resultString.split("\r[\n]?")).toList();
        assertThat(resultString).contains("179128 Marcin Abacki");
        assertThat(resultRows).doesNotHaveDuplicates();
    }

    @BeforeEach
    void init() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void cleanup() {
        System.setOut(originalOut);
    }

}
