import cs2030s.fp.Maybe;
import java.util.ArrayList;
import java.util.List;

public class Q2a {

  // 2a. Returns a sorted list of all numeric grades (as Double) from records
  //     where the grade is present (i.e. not None), sorted in ascending order.
  public static List<Double> getCompletedGrades(List<Record> records) {
    List<Double> grades = new ArrayList<>();
    for (Record r : records) {
      if (!r.getGrade().equals(Maybe.none())) {
        grades.add(r.getGrade().orElse(0.0));
      }
    }
    grades.sort(Double::compare);
    return grades;
  }
}
