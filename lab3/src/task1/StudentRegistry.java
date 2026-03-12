package task1;
import java.util.*;

public class StudentRegistry {

        private Map<Integer, Student> students = new HashMap<>();

        public void addStudent(Student s) {
            students.put(s.getId(), s);
        }

        public void removeStudent(int id) {
            students.remove(id);
        }

        public Student findStudent(int id) {
            return students.get(id);
        }

        public void showAllStudents() {
            for (Student s : students.values()) {
                System.out.println(s);
            }
        }
    }

