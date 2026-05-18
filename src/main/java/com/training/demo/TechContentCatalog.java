package com.training.demo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class TechContentCatalog {

    private static final List<TechTopic> TOPICS = List.of(
            topic("java", "Java", "fab fa-java",
                    "Object-oriented programming with Java — syntax, OOP, collections, and enterprise patterns.",
                    List.of(
                            material("Java Tutorial (PDF-style guide)", "pdf", "W3Schools", "https://www.w3schools.com/java/"),
                            material("The Java™ Tutorials", "pdf", "Oracle", "https://docs.oracle.com/javase/tutorial/"),
                            material("Java Programming Full Course", "video", "YouTube · freeCodeCamp", "https://www.youtube.com/watch?v=xk4_1vDrvgk"),
                            material("Java Beginner to Advanced", "video", "YouTube · Programming with Mosh", "https://www.youtube.com/watch?v=eIrMbAQSU34")
                    )),
            topic("sql", "SQL", "fas fa-database",
                    "Query relational databases — SELECT, JOINs, aggregates, indexing, and design basics.",
                    List.of(
                            material("SQL Tutorial", "pdf", "W3Schools", "https://www.w3schools.com/sql/"),
                            material("SQL Reference", "pdf", "MDN / Mozilla", "https://developer.mozilla.org/en-US/docs/Glossary/SQL"),
                            material("SQL for Beginners", "video", "YouTube · Programming with Mosh", "https://www.youtube.com/watch?v=7S_tz1z_5bA"),
                            material("Advanced SQL Tutorial", "video", "YouTube · freeCodeCamp", "https://www.youtube.com/watch?v=HXV3zeQKqGY")
                    )),
            topic("git", "Git", "fab fa-git-alt",
                    "Version control workflows — commits, branches, merges, and team collaboration.",
                    List.of(
                            material("Pro Git Book", "pdf", "Git SCM", "https://git-scm.com/book/en/v2"),
                            material("Git Cheat Sheet", "pdf", "Atlassian", "https://www.atlassian.com/git/tutorials/atlassian-git-cheatsheet"),
                            material("Git & GitHub Crash Course", "video", "YouTube · Traversy Media", "https://www.youtube.com/watch?v=SWYqp7iY_Tc"),
                            material("Git Tutorial for Beginners", "video", "YouTube · Programming with Mosh", "https://www.youtube.com/watch?v=8JJ101D3knE")
                    )),
            topic("dsa", "DSA", "fas fa-brain",
                    "Data structures and algorithms — arrays, trees, graphs, sorting, and complexity analysis.",
                    List.of(
                            material("Data Structures Easy to Advanced", "pdf", "GeeksforGeeks", "https://www.geeksforgeeks.org/data-structures/"),
                            material("Big-O Cheat Sheet", "pdf", "Big-O Cheat Sheet", "https://www.bigocheatsheet.com/"),
                            material("Data Structures Full Course", "video", "YouTube · freeCodeCamp", "https://www.youtube.com/watch?v=RBSGKlAvoiM"),
                            material("Algorithms Specialization Overview", "video", "Coursera · Stanford", "https://www.coursera.org/specializations/algorithms")
                    )),
            topic("spring", "Spring", "fas fa-leaf",
                    "Spring Framework and Spring Boot — DI, REST APIs, security, and microservices basics.",
                    List.of(
                            material("Spring Boot Reference", "pdf", "Spring.io", "https://docs.spring.io/spring-boot/docs/current/reference/html/"),
                            material("Spring Guides", "pdf", "Spring.io", "https://spring.io/guides"),
                            material("Spring Boot Tutorial for Beginners", "video", "YouTube · Amigoscode", "https://www.youtube.com/watch?v=9SGDpanrc8U"),
                            material("Spring Framework 6 / Boot 3", "video", "YouTube · Telusko", "https://www.youtube.com/watch?v=Qt9yGDCn4ok")
                    )),
            topic("mock-project", "Mock Project", "fas fa-project-diagram",
                    "End-to-end sample projects to practice full-stack delivery and portfolio building.",
                    List.of(
                            material("Build a REST API with Spring Boot", "pdf", "Spring.io Guide", "https://spring.io/guides/tutorials/rest/"),
                            material("Full Stack Open", "pdf", "University of Helsinki", "https://fullstackopen.com/en/"),
                            material("Backend Project Ideas", "video", "YouTube · freeCodeCamp", "https://www.youtube.com/watch?v=OeEHJgzqS1k"),
                            material("Full Stack Project Tutorial", "video", "YouTube · JavaScript Mastery", "https://www.youtube.com/watch?v=7CqJlxBYj-M")
                    )),
            topic("python", "Python", "fab fa-python",
                    "Python fundamentals — syntax, data types, scripting, libraries, and automation.",
                    List.of(
                            material("Python Tutorial", "pdf", "Python.org", "https://docs.python.org/3/tutorial/"),
                            material("Python for Everybody (book site)", "pdf", "py4e.com", "https://www.py4e.com/book"),
                            material("Python for Beginners", "video", "YouTube · Programming with Mosh", "https://www.youtube.com/watch?v=_uQrJ0TkZlc"),
                            material("Python Full Course", "video", "YouTube · freeCodeCamp", "https://www.youtube.com/watch?v=rfscVS0vtbw")
                    )),
            topic("cpp", "C++", "fas fa-code",
                    "C++ programming — memory, pointers, OOP, STL, and performance-oriented design.",
                    List.of(
                            material("Learn C++", "pdf", "LearnCpp.com", "https://www.learncpp.com/"),
                            material("C++ Reference", "pdf", "cppreference.com", "https://en.cppreference.com/w/"),
                            material("C++ Programming Course", "video", "YouTube · freeCodeCamp", "https://www.youtube.com/watch?v=vLnPwxZdW4Y"),
                            material("C++ Full Course", "video", "YouTube · Bro Code", "https://www.youtube.com/watch?v=-TkoO8H07kI")
                    )),
            topic("dotnet", ".NET", "fab fa-microsoft",
                    "Microsoft .NET and C# — desktop, web, and cloud application development.",
                    List.of(
                            material(".NET Documentation", "pdf", "Microsoft Learn", "https://learn.microsoft.com/en-us/dotnet/"),
                            material("C# Documentation", "pdf", "Microsoft Learn", "https://learn.microsoft.com/en-us/dotnet/csharp/"),
                            material("C# Tutorial for Beginners", "video", "YouTube · Programming with Mosh", "https://www.youtube.com/watch?v=gfkTfOVW_Y8"),
                            material("ASP.NET Core Web API", "video", "YouTube · Les Jackson", "https://www.youtube.com/watch?v=fmvcAzHpsk8")
                    ))
    );

    private static final Map<String, TechTopic> BY_ID = TOPICS.stream()
            .collect(Collectors.toMap(TechTopic::getId, t -> t));

    private TechContentCatalog() {
    }

    public static List<TechTopic> getAllTopics() {
        return TOPICS;
    }

    public static Optional<TechTopic> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(BY_ID.get(id.toLowerCase().trim()));
    }

    private static TechTopic topic(String id, String title, String icon, String description, List<LearningMaterial> materials) {
        return new TechTopic(id, title, icon, description, materials);
    }

    private static LearningMaterial material(String title, String type, String platform, String url) {
        return new LearningMaterial(title, type, platform, url);
    }
}
