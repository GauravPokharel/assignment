package util;

import entity.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class SortingUtil {
    JDBCUtil jdbcUtil;

    public SortingUtil() {
        this.jdbcUtil = new JDBCUtil();
    }

    public void fetchAndUpdateSortedStudents() {
        List<Student> students = jdbcUtil.selectQuery();
        //Grouping by class
        Map<Integer, List<Student>> studentsGroupedByClass = students.stream().collect(groupingBy(Student::getClassLevel));
        //Sorting by class and rollNumber
        Map<Integer, List<Student>> sortedStudentsByClass = new HashMap<>();
        studentsGroupedByClass.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()).forEachOrdered(entry -> {
                    sortedStudentsByClass.put(entry.getKey(), entry.getValue());
                    jdbcUtil.updateRollNumberQuery(entry.getValue());
                });
        jdbcUtil.closeConnection();
    }
}
