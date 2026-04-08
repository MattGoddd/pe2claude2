import cs2030s.fp.Maybe;
import java.util.List;

/**
 * Automated test for CS2030S AY25/26 Sem 2 PE2 Mock 3, Question 2 Part (d).
 * Tests: Q2d.getSharedTutorPairs()
 *
 * Run from Q2/ with:  java -cp ../test.jar:. Test7
 */
public class Test7 extends CS2030STest {

  public static void main(String[] args) {
    reset();
    System.out.println("=== Test7: Q2d - getSharedTutorPairs ===");
    System.out.println();

    List<Record> records = Test4.makeRecords();

    // Full case: see Demo expected output
    check("getSharedTutorPairs: full list of 5 pairs",
        List.of(
            List.of("A001", "A002"),
            List.of("A001", "A003"),
            List.of("A002", "A003"),
            List.of("A004", "A006"),
            List.of("A007", "A008")),
        Q2d.getSharedTutorPairs(records));

    // No shared tutors → []
    List<Record> noShared = List.of(
        new Record("X1", "M", Maybe.some(70.0), Maybe.some("T1"), 1),
        new Record("X2", "M", Maybe.some(80.0), Maybe.some("T2"), 1),
        new Record("X3", "M", Maybe.some(90.0), Maybe.some("T3"), 1));
    check("getSharedTutorPairs: no shared tutors → []",
        List.of(), Q2d.getSharedTutorPairs(noShared));

    // All None tutors → []
    List<Record> noTutors = List.of(
        new Record("X1", "M", Maybe.some(70.0), Maybe.none(), 1),
        new Record("X2", "M", Maybe.some(80.0), Maybe.none(), 1));
    check("getSharedTutorPairs: all None tutors → []",
        List.of(), Q2d.getSharedTutorPairs(noTutors));

    // One None tutor among same-tutor group: None does not match None
    List<Record> mixed = List.of(
        new Record("X1", "M", Maybe.some(70.0), Maybe.some("T1"), 1),
        new Record("X2", "M", Maybe.some(80.0), Maybe.none(),     1),
        new Record("X3", "M", Maybe.some(90.0), Maybe.some("T1"), 1));
    check("getSharedTutorPairs: None tutors excluded from pairs",
        List.of(List.of("X1", "X3")),
        Q2d.getSharedTutorPairs(mixed));

    // Empty list → []
    check("getSharedTutorPairs: empty list → []",
        List.of(), Q2d.getSharedTutorPairs(List.of()));

    // Single record → no pairs
    check("getSharedTutorPairs: single record → []",
        List.of(),
        Q2d.getSharedTutorPairs(List.of(
            new Record("X1", "M", Maybe.some(70.0), Maybe.some("T1"), 1))));

    // Pair order: earlier in list comes first in pair
    List<Record> pairOrder = List.of(
        new Record("B", "M", Maybe.some(70.0), Maybe.some("T1"), 1),
        new Record("A", "M", Maybe.some(80.0), Maybe.some("T1"), 1));
    List<List<String>> pairResult = Q2d.getSharedTutorPairs(pairOrder);
    check("getSharedTutorPairs: B appears before A in input → B first in pair",
        List.of("B", "A"), pairResult.get(0));

    // Each pair appears exactly once
    check("getSharedTutorPairs: exactly one pair produced (not duplicated)",
        1, Q2d.getSharedTutorPairs(pairOrder).size());

    report();
  }
}
