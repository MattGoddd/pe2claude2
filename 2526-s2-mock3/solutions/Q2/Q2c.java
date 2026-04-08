import cs2030s.fp.Maybe;
import java.util.List;
import java.util.stream.Collectors;

public class Q2c {

  // 2c. Returns sorted distinct tutors for a module, excluding records with no tutor.
  public static List<String> getModuleTutors(List<Record> records, String module) {
    return records.stream()
        .filter(r -> r.getModule().equals(module))
        .flatMap(r -> r.getTutor()
            .map(java.util.stream.Stream::of)
            .orElse(java.util.stream.Stream.empty()))
        .distinct()
        .sorted()
        .collect(Collectors.toList());
  }
}
