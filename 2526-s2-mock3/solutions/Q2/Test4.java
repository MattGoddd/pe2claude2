import cs2030s.fp.Maybe;
import java.util.List;

/**
 * Automated test for CS2030S AY25/26 Sem 2 PE2 Mock 3, Question 2 Part (a).
 * Tests: Q2a.getCompletedGrades()
 *
 * Run from Q2/ with:  java -cp ../test.jar:. Test4
 */
public class Test4 extends CS2030STest {

  static List<Record> makeRecords() {
    return List.of(
        new Record("A001", "CS2030S", Maybe.some(85.0), Maybe.some("Prof Alice"), 1),
        new Record("A002", "CS2030S", Maybe.some(72.0), Maybe.some("Prof Alice"), 1),
        new Record("A003", "CS2030S", Maybe.none(),     Maybe.some("Prof Alice"), 1),
        new Record("A004", "CS2101",  Maybe.some(90.0), Maybe.some("Prof Bob"),   1),
        new Record("A005", "CS2101",  Maybe.some(65.0), Maybe.none(),             1),
        new Record("A006", "CS2101",  Maybe.none(),     Maybe.some("Prof Bob"),   2),
        new Record("A007", "CS2030S", Maybe.some(78.0), Maybe.some("Prof Carol"), 2),
        new Record("A008", "CS2030S", Maybe.some(91.0), Maybe.some("Prof Carol"), 2));
  }

  public static void main(String[] args) {
    reset();
    System.out.println("=== Test4: Q2a - getCompletedGrades ===");
    System.out.println();

    List<Record> records = makeRecords();

    // Standard case: 6 completed grades, 2 incomplete
    check("getCompletedGrades: sorted list of 6 grades",
        List.of(65.0, 72.0, 78.0, 85.0, 90.0, 91.0),
        Q2a.getCompletedGrades(records));

    // All incomplete
    List<Record> allNone = List.of(
        new Record("X1", "M1", Maybe.none(), Maybe.none(), 1),
        new Record("X2", "M1", Maybe.none(), Maybe.none(), 1));
    check("getCompletedGrades: all incomplete → []",
        List.of(), Q2a.getCompletedGrades(allNone));

    // All have grades
    List<Record> allGrades = List.of(
        new Record("X1", "M1", Maybe.some(50.0), Maybe.none(), 1),
        new Record("X2", "M1", Maybe.some(30.0), Maybe.none(), 1),
        new Record("X3", "M1", Maybe.some(70.0), Maybe.none(), 1));
    check("getCompletedGrades: all have grades → sorted [30.0, 50.0, 70.0]",
        List.of(30.0, 50.0, 70.0), Q2a.getCompletedGrades(allGrades));

    // Single grade
    check("getCompletedGrades: single completed record",
        List.of(88.0),
        Q2a.getCompletedGrades(List.of(
            new Record("X", "M", Maybe.some(88.0), Maybe.none(), 1))));

    // Empty list
    check("getCompletedGrades: empty list → []",
        List.of(), Q2a.getCompletedGrades(List.of()));

    // Grades in reverse order become sorted
    List<Record> reversed = List.of(
        new Record("X1", "M", Maybe.some(90.0), Maybe.none(), 1),
        new Record("X2", "M", Maybe.some(80.0), Maybe.none(), 1),
        new Record("X3", "M", Maybe.some(70.0), Maybe.none(), 1));
    check("getCompletedGrades: reversed input → ascending output",
        List.of(70.0, 80.0, 90.0), Q2a.getCompletedGrades(reversed));

    // None records mixed in should not appear
    List<Record> mixed = List.of(
        new Record("X1", "M", Maybe.none(),     Maybe.none(), 1),
        new Record("X2", "M", Maybe.some(55.0), Maybe.none(), 1),
        new Record("X3", "M", Maybe.none(),     Maybe.none(), 1));
    check("getCompletedGrades: None values excluded",
        List.of(55.0), Q2a.getCompletedGrades(mixed));

    report();
  }
}
