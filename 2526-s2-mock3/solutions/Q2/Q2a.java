import cs2030s.fp.Maybe;
import java.util.List;
import java.util.stream.Collectors;

public class Q2a {

  // 2a. Returns a sorted list of all numeric grades where grade is Some.
  public static List<Double> getCompletedGrades(List<Record> records) {
    return records.stream()
        .filter(r -> !r.getGrade().equals(Maybe.none()))
        .map(r -> r.getGrade().orElse(0.0))
        .sorted()
        .collect(Collectors.toList());
  }
}
