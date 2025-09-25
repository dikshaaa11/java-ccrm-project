# Campus Course & Records Manager (CCRM)

## 1. Project Overview

The Campus Course & Records Manager (CCRM) is a console-based Java application designed to manage students, courses, enrollments, and grades for an educational institution. It is built with Java SE and demonstrates a wide range of core Java and Object-Oriented Programming concepts.

**Key Features:**
- Student Management (Add, List, Update)
- Course Management (Add, List, Update)
- Student Enrollment and Grading
- Data Persistence via CSV file import/export
- Timestamped Data Backups

## 2. How to Run

1.  **Prerequisites:** JDK 11 or higher must be installed and configured on your system.
2.  **Clone the Repository:** `git clone <your-repo-link>`
3.  **Navigate to the Source Directory:** `cd CCRM_Project/src`
4.  **Compile:** `javac edu/ccrm/Main.java`
5.  **Run:** `java edu.ccrm.Main`

## 3. Core Java Concepts Demonstrated

[cite_start]This table maps the required syllabus topics to where they are implemented in the source code[cite: 136].

| Concept | File(s) / Location |
| :--- | :--- |
| **OOP Pillars** | |
| Encapsulation | `domain/Person.java` (private fields, getters/setters) |
| Inheritance | `domain/Student.java` (extends Person) |
| Abstraction | `domain/Person.java` (abstract class), `service/EnrollmentService.java` (interface) |
| Polymorphism | `Main.java` (using Person references), `cli/CliHandler.java` (calling service methods) |
| **Design Patterns** | |
| Singleton | `config/DataStore.java` |
| Builder | `domain/Course.java` (static nested Builder class) |
| **Java Features** | |
| Lambdas & Streams | `service/EnrollmentServiceImpl.java` (filtering/mapping), `io/DataPersistenceService.java` (file processing) |
| NIO.2 File I/O | `io/DataPersistenceService.java`, `io/BackupService.java` |
| Date/Time API | `domain/Person.java` (LocalDate), `io/BackupService.java` (LocalDateTime) |
| Custom Exceptions | `exception/MaxCreditLimitExceededException.java` |
| Recursion | `util/FileUtils.java` (calculateDirectorySize method) |
| Enums w/ fields | `domain/Grade.java`, `domain/Semester.java` |

---

## 4. Java Platform Fundamentals

### [cite_start]Evolution of Java (Briefly) [cite: 132]
- **1995:** Java 1.0 released by Sun Microsystems.
- **2004:** Java 5 (J2SE 5.0) released, introducing major features like generics, enums, and annotations.
- **2014:** Java 8 released, a landmark version with Lambdas, Streams API, and a new Date/Time API.
- **2018:** Java 11 released as a Long-Term Support (LTS) version.
- **Present:** Java continues with a faster release cycle (every 6 months), with recent LTS versions being 17 and 21.

### [cite_start]Java ME vs SE vs EE [cite: 133]

| Feature | Java ME (Micro Edition) | Java SE (Standard Edition) | Java EE (Enterprise Edition) |
| :--- | :--- | :--- | :--- |
| **Target** | Mobile, embedded devices | Desktop, servers | Large-scale, distributed enterprise applications |
| **Core API** | Subset of SE API | Core Java language, JVM, core libraries | Superset of SE API |
| **Includes** | CLDC, MIDP | JDK, JRE, JVM | Servlets, JSPs, EJB, Web Services |

### [cite_start]JDK vs JRE vs JVM [cite: 134]
- **JVM (Java Virtual Machine):** An abstract machine that provides the runtime environment in which Java bytecode can be executed. It's the component that makes Java "platform-independent".
- **JRE (Java Runtime Environment):** A software package that contains the JVM, standard libraries, and other components needed to *run* Java applications.
- **JDK (Java Development Kit):** A superset of the JRE. It contains everything in the JRE, plus the tools needed to *develop* Java applications, such as the compiler (`javac`) and debugger.

---

## [cite_start]5. Setup and Installation (Windows) 

1.  **Download JDK:** Download a Java SE Development Kit (JDK) installer (e.g., from Oracle or Adoptium).
2.  **Install JDK:** Run the installer and follow the on-screen instructions.
3.  **Configure Environment Variables:**
    -   Set `JAVA_HOME` to your JDK installation directory (e.g., `C:\Program Files\Java\jdk-17`).
    -   Add the JDK's `bin` folder to the system `Path` variable (e.g., `%JAVA_HOME%\bin`).
4.  **Verify Installation:** Open a new Command Prompt and run `java -version` and `javac -version`.

****

## 6. Notes

- [cite_start]**Enabling Assertions:** To run the program with assertions enabled, use the `-ea` flag: `java -ea edu.ccrm.Main`[cite: 137].