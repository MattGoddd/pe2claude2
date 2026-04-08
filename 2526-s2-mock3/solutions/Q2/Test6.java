import cs2030s.fp.Maybe;
import java.util.List;

/**
 * Automated test for CS2030S AY25/26 Sem 2 PE2 Mock 3, Question 2 Part (c).
 * Tests: Q2c.getModuleTutors()
 *
 * Run from Q2/ with:  java -cp ../test.jar:. Test6
 */
public class Test6 extends CS2030STest {

  public static void main(String[] args) {
    reset();
    System.out.println("=== Test6: Q2c - getModuleTutors ===");
    System.out.println();

    List<Record> records = Test4.makeRecords();

    // CS2030S: Alice (A001,A002,A003), Carol (A007,A008) — no tutor-less records
    check("getModuleTutors: CS2030S → [Prof Alice, Prof Carol]",
        List.of("Prof Alice", "Prof Carol"),
        Q2c.getModuleTutors(records, "CS2030S"));

    // CS2101: Bob (A004,A006), A005 has no tutor → only Bob
    check("getModuleTutors: CS2101 → [Prof Bob]",
        List.of("Prof Bob"),
        Q2c.getModuleTutors(records, "CS2101"));

    // Module not in list → []
    check("getModuleTutors: unknown module → []",
        List.of(), Q2c.getModuleTutors(records, "CS9999"));

    // All records in module have no tutor → []
    List<Record> noTutors = List.of(
        new Record("X1", "MA1", Maybe.some(80.0), Maybe.none(), 1),
        new Record("X2", "MA1", Maybe.none(),     Maybe.none(), 1));
    check("getModuleTutors: all None tutors → []",
        List.of(), Q2c.getModuleTutors(noTutors, "MA1"));

    // Duplicates are removed
    List<Record> dupes = List.of(
        new Record("X1", "M1", Maybe.some(70.0), Maybe.some("Tutor Z"), 1),
        new Record("X2", "M1", Maybe.some(80.0), Maybe.some("Tutor Z"), 1),
        new Record("X3", "M1", Maybe.some(90.0), Maybe.some("Tutor A"), 1));
    check("getModuleTutors: duplicates removed and sorted",
        List.of("Tutor A", "Tutor Z"),
        Q2c.getModuleTutors(dupes, "M1"));

    // Sorted alphabetically, not by order of appearance
    List<Record> unsorted = List.of(
        new Record("X1", "M2", Maybe.some(70.0), Maybe.some("Zara"), 1),
        new Record("X2", "M2", Maybe.some(80.0), Maybe.some("Alice"), 1),
        new Record("X3", "M2", Maybe.some(90.0), Maybe.some("Mike"), 1));
    check("getModuleTutors: sorted alphabetically",
        List.of("Alice", "Mike", "Zara"),
        Q2c.getModuleTutors(unsorted, "M2"));

    // Mixed modules — only target module's tutors returned
    List<Record> mixed = List.of(
        new Record("X1", "M1", Maybe.some(70.0), Maybe.some("T1"), 1),
        new Record("X2", "M2", Maybe.some(80.0), Maybe.some("T2"), 1),
        new Record("X3", "M1", Maybe.some(90.0), Maybe.some("T3"), 1));
    check("getModuleTutors: only target module tutors",
        List.of("T1", "T3"),
        Q2c.getModuleTutors(mixed, "M1"));

    report();
  }
}
