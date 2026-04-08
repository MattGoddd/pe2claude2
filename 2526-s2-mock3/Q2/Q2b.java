import cs2030s.fp.Maybe;
import java.util.ArrayList;
import java.util.List;

public class Q2b {

  // 2b. Returns a list of strings, one per record in input order, of the form:
  //     "<studentId>: <grade>" if the grade is present (formatted to 1 decimal),
  //     "<studentId>: incomplete" if the grade is None.
  //     Use Maybe.map and Maybe.orElse — no if statements allowed in your solution.
  public static List<String> getGradeSummaries(List<Record> records) {
    List<String> result = new ArrayList<>();
    for (Record r : records) {
      String grade = r.getGrade().map(g -> String.format("%.1f", g)).orElse("incomplete");
      result.add(r.getStudentId() + ": " + grade);
    }
    return result;
  }
}
