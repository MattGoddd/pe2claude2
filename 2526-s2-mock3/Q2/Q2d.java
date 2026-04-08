import cs2030s.fp.Maybe;
import java.util.ArrayList;
import java.util.List;

public class Q2d {

  // 2d. Returns all distinct ordered pairs of student IDs (as List<List<String>>)
  //     where both students have the SAME tutor (and that tutor is Some, not None).
  //     A pair [a, b] appears only once (a appears before b in the input list).
  //     Do not create a custom class to represent a pair.
  //     The order of pairs must follow the order of students in the input list.
  public static List<List<String>> getSharedTutorPairs(List<Record> records) {
    List<List<String>> pairs = new ArrayList<>();
    for (int i = 0; i < records.size(); i++) {
      Record r1 = records.get(i);
      String t1 = r1.getTutor().orElse(null);
      if (t1 == null) continue;
      for (int j = i + 1; j < records.size(); j++) {
        Record r2 = records.get(j);
        String t2 = r2.getTutor().orElse(null);
        if (t1.equals(t2)) {
          pairs.add(List.of(r1.getStudentId(), r2.getStudentId()));
        }
      }
    }
    return pairs;
  }
}
