import cs2030s.fp.Maybe;
import java.util.List;

/**
 * Automated test for CS2030S AY25/26 Sem 2 PE2 Mock 3, Question 2 Part (b).
 * Tests: Q2b.getGradeSummaries()
 *
 * Run from Q2/ with:  java -cp ../test.jar:. Test5
 */
public class Test5 extends CS2030STest {

  public static void main(String[] args) {
    reset();
    System.out.println("=== Test5: Q2b - getGradeSummaries ===");
    System.out.println();

    List<Record> records = Test4.makeRecords();

    // Full list — check every element
    List<String> result = Q2b.getGradeSummaries(records);
    check("getGradeSummaries: A001 grade present",  "A001: 85.0",       result.get(0));
    check("getGradeSummaries: A002 grade present",  "A002: 72.0",       result.get(1));
    check("getGradeSummaries: A003 incomplete",      "A003: incomplete", result.get(2));
    check("getGradeSummaries: A004 grade present",  "A004: 90.0",       result.get(3));
    check("getGradeSummaries: A005 grade present",  "A005: 65.0",       result.get(4));
    check("getGradeSummaries: A006 incomplete",      "A006: incomplete", result.get(5));
    check("getGradeSummaries: A007 grade present",  "A007: 78.0",       result.get(6));
    check("getGradeSummaries: A008 grade present",  "A008: 91.0",       result.get(7));
    check("getGradeSummaries: size == 8", 8, result.size());

    // All incomplete
    List<Record> allNone = List.of(
        new Record("S1", "M", Maybe.none(), Maybe.none(), 1),
        new Record("S2", "M", Maybe.none(), Maybe.none(), 1));
    check("getGradeSummaries: all incomplete",
        List.of("S1: incomplete", "S2: incomplete"),
        Q2b.getGradeSummaries(allNone));

    // All have grades
    List<Record> allGrades = List.of(
        new Record("S1", "M", Maybe.some(100.0), Maybe.none(), 1),
        new Record("S2", "M", Maybe.some(50.5),  Maybe.none(), 1));
    check("getGradeSummaries: all grades → formatted to 1 decimal",
        List.of("S1: 100.0", "S2: 50.5"),
        Q2b.getGradeSummaries(allGrades));

    // Order preserved
    List<Record> ordered = List.of(
        new Record("B", "M", Maybe.some(60.0), Maybe.none(), 1),
        new Record("A", "M", Maybe.none(),     Maybe.none(), 1));
    List<String> orderedResult = Q2b.getGradeSummaries(ordered);
    check("getGradeSummaries: order preserved (B before A)",
        "B: 60.0", orderedResult.get(0));
    check("getGradeSummaries: A comes second",
        "A: incomplete", orderedResult.get(1));

    // Empty list
    check("getGradeSummaries: empty list → []",
        List.of(), Q2b.getGradeSummaries(List.of()));

    report();
  }
}
