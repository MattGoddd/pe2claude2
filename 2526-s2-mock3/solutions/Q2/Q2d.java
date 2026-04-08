import cs2030s.fp.Maybe;
import java.util.List;
import java.util.stream.Collectors;

public class Q2d {

  // 2d. Returns all ordered pairs of student IDs sharing the same non-None tutor.
  public static List<List<String>> getSharedTutorPairs(List<Record> records) {
    return records.stream()
        .flatMap(r1 -> records.stream()
            .filter(r2 -> records.indexOf(r2) > records.indexOf(r1))
            .filter(r2 -> !r1.getTutor().equals(Maybe.none()))
            .filter(r2 -> r1.getTutor().equals(r2.getTutor()))
            .map(r2 -> List.of(r1.getStudentId(), r2.getStudentId())))
        .collect(Collectors.toList());
  }
}
