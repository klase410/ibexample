import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Class representing a subject
class Subject {
    private String title;
    private Teacher teacher;

    public Subject(String title) {
        this.title = title;
        this.teacher = null;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getTeacherName() {
        if(teacher != null) {
            return this.teacher.getName();
        } else {
            return "N/A";
        }
    }
}

class StudentSubject extends Subject {
    private int studentId;
    private String level;

    public StudentSubject(int studentId, String title, String level) {
        super(title);
        this.studentId = studentId;
        this.level = level;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getLevel() {
        return level;
    }
}

// Base class for all students
class Student {
    protected int id;
    protected String name;
    protected int age;
    protected Teacher assignedTeacher;
    protected ArrayList<StudentSubject> assignedSubjects;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.assignedSubjects = new ArrayList<>();
    }

    public void displayInfo() {
        System.out.println("Name: " + name);

        if (assignedTeacher != null) {
            System.out.println("Assigned Teacher: " + assignedTeacher.getName());
        }

        if (!assignedSubjects.isEmpty()) {
            System.out.println("Assigned Subjects:");
            for (StudentSubject subject : assignedSubjects) {
                System.out.println("\t" + subject.getTitle() + " " + subject.getLevel());
                System.out.println("\t\t" + "Teacher: " + subject.getTeacher().getName());
            }
        }
    }

    // Method to assign a teacher to the student
    public void assignTeacher(Teacher teacher) {
        assignedTeacher = teacher;
    }

    // Method to assign a subject to the student
    public void assignSubject(StudentSubject subject) {
        assignedSubjects.add(subject);
    }

    public void assignSubjects(ArrayList<StudentSubject> subjects, ArrayList<Teacher> teachers) {
        for (StudentSubject subject: subjects) {
            if(subject.getStudentId() == this.id) {

                for(Teacher teacher: teachers) {
                    if(teacher.getSubject().getTitle().equals(subject.getTitle())) {
                        subject.setTeacher(teacher);
                    }
                }

                assignSubject(subject);
            }
        }
    }

    // Method to be overridden by subclasses
    public void study() {
        System.out.println(name + " is studying.");
    }
}

// Subclass representing undergraduate students
class UndergraduateStudent extends Student {
    private int year;

    public UndergraduateStudent(int id, String name, int age, int year) {
        super(id, name, age);
        this.year = year;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Year: " + year);
    }

    // Overloaded study method
    public void study() {
        System.out.println(name + " is studying ");
    }
}

// Subclass representing graduate students
class GraduateStudent extends Student {
    private String researchTopic;

    public GraduateStudent(int id, String name, int age, String researchTopic) {
        super(id, name, age);
        this.researchTopic = researchTopic;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Research Topic: " + researchTopic);
    }

    public void study() {
        System.out.println(name + " is finished studying " + this.researchTopic);
    }
}

// Class representing a teacher
class Teacher {
    private Subject subject;
    private String name;

    public Teacher(Subject subject, String name) {
        this.subject = subject;
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getName() {
        return name;
    }
}

// Class to read student data from a CSV file
class StudentDataReader {

    public static ArrayList<Student> readData(String fileName) throws IOException {

        ArrayList<Student> students = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        // Skip the first line (CSV header)
        br.readLine();

        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 5) {
                int id = Integer.parseInt(data[0]);
                String type = data[1];
                String name = data[2];
                int age = Integer.parseInt(data[3]);
                if (type.equals("UG")) {
                    int year = Integer.parseInt(data[4]);
                    UndergraduateStudent student = new UndergraduateStudent(id, name, age, year);
                    students.add(student);
                } else if (type.equals("Grad")) {
                    String researchTopic = data[4];
                    GraduateStudent student = new GraduateStudent(id, name, age, researchTopic);
                    students.add(student);
                }
            }
        }
        br.close();
        return students;
    }

    public static ArrayList<StudentSubject> readSubjectsFromFile(String fileName) {
        ArrayList<StudentSubject> subjects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("Student ID")) {
                    continue;
                }
                String[] data = line.split(",");
                int studentId = Integer.parseInt(data[0]);
                String title = data[1];
                String level = data[2];
                StudentSubject subject = new StudentSubject(studentId, title, level);
                subjects.add(subject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public static ArrayList<Teacher> readTeachersFromFile(String fileName) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("Subject")) {
                    continue;
                }
                String[] data = line.split(",");
                String subject = data[0];
                String name = data[1];
                Teacher teacher = new Teacher(new Subject(subject), name);
                teachers.add(teacher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teachers;
    }
}


public class Main {

    public static void main(String[] args) {
        try {

            // Creating an ArrayList to store both undergraduate and graduate students
            ArrayList<Student> allStudents = StudentDataReader.readData("students.csv");

            ArrayList<StudentSubject> allSubjects = StudentDataReader.readSubjectsFromFile("subjects.csv");

            ArrayList<Teacher> allTeachers = StudentDataReader.readTeachersFromFile("teachers.csv");

            // Creating a teacher object for undergraduate students
            Teacher teacher = new Teacher(new Subject("Test"), "Mr. Smith");

            for (Student student: allStudents) {
                if (student instanceof UndergraduateStudent) {
                    student.assignTeacher(teacher);
                    student.assignSubjects(allSubjects, allTeachers);
                }
                student.displayInfo();
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
    }
}