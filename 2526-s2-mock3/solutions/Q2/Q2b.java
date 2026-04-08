import java.util.List;
import java.util.stream.Collectors;

public class Q2b {

  // 2b. Returns grade summaries using Maybe.map + orElse — no if statements.
  public static List<String> getGradeSummaries(List<Record> records) {
    return records.stream()
        .map(r -> r.getStudentId() + ": "
            + r.getGrade().map(g -> String.format("%.1f", g)).orElse("incomplete"))
        .collect(Collectors.toList());
  }
}
