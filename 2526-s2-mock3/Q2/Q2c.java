import cs2030s.fp.Maybe;
import java.util.ArrayList;
import java.util.List;

public class Q2c {

  // 2c. Given a list of records and a module code, returns a sorted list of
  //     all DISTINCT tutor names assigned to records for that module.
  //     Records with no tutor (Maybe.none()) are excluded.
  public static List<String> getModuleTutors(List<Record> records, String module) {
    List<String> tutors = new ArrayList<>();
    for (Record r : records) {
      if (r.getModule().equals(module)) {
        String tutor = r.getTutor().orElse(null);
        if (tutor != null && !tutors.contains(tutor)) {
          tutors.add(tutor);
        }
      }
    }
    tutors.sort(String::compareTo);
    return tutors;
  }
}
