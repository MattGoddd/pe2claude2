import cs2030s.fp.Maybe;
import java.util.List;

public class Demo {
  public static void main(String[] args) {
    List<Record> records = List.of(
        new Record("A001", "CS2030S", Maybe.some(85.0), Maybe.some("Prof Alice"), 1),
        new Record("A002", "CS2030S", Maybe.some(72.0), Maybe.some("Prof Alice"), 1),
        new Record("A003", "CS2030S", Maybe.none(),     Maybe.some("Prof Alice"), 1),
        new Record("A004", "CS2101",  Maybe.some(90.0), Maybe.some("Prof Bob"),   1),
        new Record("A005", "CS2101",  Maybe.some(65.0), Maybe.none(),             1),
        new Record("A006", "CS2101",  Maybe.none(),     Maybe.some("Prof Bob"),   2),
        new Record("A007", "CS2030S", Maybe.some(78.0), Maybe.some("Prof Carol"), 2),
        new Record("A008", "CS2030S", Maybe.some(91.0), Maybe.some("Prof Carol"), 2));

    System.out.println("== Q2a ==");
    System.out.println(Q2a.getCompletedGrades(records));
    // Expected: [65.0, 72.0, 78.0, 85.0, 90.0, 91.0]

    System.out.println("== Q2b ==");
    System.out.println(Q2b.getGradeSummaries(records));
    // Expected: [A001: 85.0, A002: 72.0, A003: incomplete,
    //            A004: 90.0, A005: 65.0, A006: incomplete,
    //            A007: 78.0, A008: 91.0]

    System.out.println("== Q2c ==");
    System.out.println(Q2c.getModuleTutors(records, "CS2030S"));
    // Expected: [Prof Alice, Prof Carol]
    System.out.println(Q2c.getModuleTutors(records, "CS2101"));
    // Expected: [Prof Bob]

    System.out.println("== Q2d ==");
    System.out.println(Q2d.getSharedTutorPairs(records));
    // Expected: [[A001, A002], [A001, A003], [A002, A003],
    //            [A004, A006], [A007, A008]]
  }
}
