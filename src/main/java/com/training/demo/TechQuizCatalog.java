package com.training.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Quiz questions aligned with tech.html skill sets (Java, SQL, Python, Git, DSA × 3 levels).
 */
public final class TechQuizCatalog {

    private static final Map<String, Map<String, List<Map<String, Object>>>> QUESTIONS = new LinkedHashMap<>();

    static {
        registerJava();
        registerSql();
        registerPython();
        registerGit();
        registerDsa();
        registerFrontEnd();
        registerBaseFramework();
    }

    private TechQuizCatalog() {
    }

    public static final int QUIZ_QUESTION_COUNT = 10;
    /** Question bank size per level for subjects that randomize across attempts (e.g. DSA, Git). */
    public static final int QUIZ_BANK_SIZE = 25;
    public static final int DSA_MAX_ATTEMPTS = 3;
    public static final int FRONTEND_MAX_ATTEMPTS = 5;
    public static final int BASE_FRAMEWORK_MAX_ATTEMPTS = 5;

    public static List<Map<String, Object>> getQuestions(String subject, String level) {
        Map<String, List<Map<String, Object>>> byLevel = QUESTIONS.get(subject);
        if (byLevel == null) {
            return genericQuestions(subject, level);
        }
        List<Map<String, Object>> set = byLevel.get(level);
        if (set == null || set.isEmpty()) {
            return genericQuestions(subject, level);
        }
        return List.copyOf(set);
    }

    /** Shuffles question order and option order; returns up to {@code count} questions per attempt. */
    public static List<Map<String, Object>> getRandomizedQuestions(String subject, String level, int count) {
        List<Map<String, Object>> source = new ArrayList<>(getQuestions(subject, level));
        if (source.isEmpty()) {
            return source;
        }
        Collections.shuffle(source);
        int take = Math.min(count, source.size());
        List<Map<String, Object>> picked = new ArrayList<>(source.subList(0, take));
        List<Map<String, Object>> result = new ArrayList<>(picked.size());
        for (Map<String, Object> question : picked) {
            result.add(shuffleQuestionOptions(copyQuestion(question)));
        }
        return result;
    }

    private static Map<String, Object> copyQuestion(Map<String, Object> question) {
        Map<String, Object> copy = new LinkedHashMap<>();
        copy.put("question", question.get("question"));
        @SuppressWarnings("unchecked")
        List<String> options = (List<String>) question.get("options");
        copy.put("options", new ArrayList<>(options));
        copy.put("answer", question.get("answer"));
        return copy;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> shuffleQuestionOptions(Map<String, Object> question) {
        List<String> options = new ArrayList<>((List<String>) question.get("options"));
        int answer = (Integer) question.get("answer");
        String correctText = options.get(answer);
        Collections.shuffle(options);
        question.put("options", options);
        int newAnswer = options.indexOf(correctText);
        question.put("answer", newAnswer >= 0 ? newAnswer : answer);
        return question;
    }

    private static List<Map<String, Object>> genericQuestions(String subject, String level) {
        List<Map<String, Object>> questions = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            questions.add(q(
                    "Sample question " + i + " for " + subject + " " + level,
                    List.of("Option A", "Option B", "Option C", "Option D"),
                    0));
        }
        return questions;
    }

    private static void registerJava() {
        Map<String, List<Map<String, Object>>> levels = new LinkedHashMap<>();
        levels.put("Pre-Intermediate", javaPreIntermediate());
        levels.put("Intermediate", javaIntermediate());
        levels.put("Advanced", javaAdvanced());
        QUESTIONS.put("Java", levels);
    }

    private static void registerSql() {
        Map<String, List<Map<String, Object>>> levels = new LinkedHashMap<>();
        levels.put("Pre-Intermediate", sqlPreIntermediate());
        levels.put("Intermediate", sqlIntermediate());
        levels.put("Advanced", sqlAdvanced());
        QUESTIONS.put("SQL", levels);
    }

    private static void registerPython() {
        Map<String, List<Map<String, Object>>> levels = new LinkedHashMap<>();
        levels.put("Pre-Intermediate", pythonPreIntermediate());
        levels.put("Intermediate", pythonIntermediate());
        levels.put("Advanced", pythonAdvanced());
        QUESTIONS.put("Python", levels);
    }

    private static void registerGit() {
        Map<String, List<Map<String, Object>>> levels = new LinkedHashMap<>();
        levels.put("Pre-Intermediate", gitPreIntermediate());
        levels.put("Intermediate", gitIntermediate());
        levels.put("Advanced", gitAdvanced());
        QUESTIONS.put("Git", levels);
    }

    private static void registerDsa() {
        Map<String, List<Map<String, Object>>> levels = new LinkedHashMap<>();
        levels.put("Pre-Intermediate", dsaPreIntermediate());
        levels.put("Intermediate", dsaIntermediate());
        levels.put("Advanced", dsaAdvanced());
        QUESTIONS.put("DSA", levels);
    }

    private static void registerFrontEnd() {
        Map<String, List<Map<String, Object>>> levels = new LinkedHashMap<>();
        levels.put("Pre-Intermediate", frontEndPreIntermediate());
        levels.put("Intermediate", frontEndIntermediate());
        levels.put("Advanced", frontEndAdvanced());
        QUESTIONS.put("FrontEnd", levels);
    }

    private static void registerBaseFramework() {
        Map<String, List<Map<String, Object>>> levels = new LinkedHashMap<>();
        levels.put("Pre-Intermediate", baseFrameworkPreIntermediate());
        levels.put("Intermediate", baseFrameworkIntermediate());
        levels.put("Advanced", baseFrameworkAdvanced());
        QUESTIONS.put("BaseFramework", levels);
    }

    private static Map<String, Object> q(String question, List<String> options, int answer) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("question", question);
        item.put("options", options);
        item.put("answer", answer);
        return item;
    }

    private static List<Map<String, Object>> javaPreIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What is the correct syntax for a Java main method?",
                List.of("public static void main(String[] args)", "public void main(String args[])",
                        "static public void main(String[] args)", "void main(String[] args)"), 0));
        list.add(q("Which of these is NOT a valid Java identifier?",
                List.of("myVariable", "_private", "123number", "$dollar"), 2));
        list.add(q("What does the 'public' access modifier mean?",
                List.of("Accessible only within the same class", "Accessible within the same package",
                        "Accessible from anywhere", "Accessible only by subclasses"), 2));
        list.add(q("Which operator is used for string concatenation in Java?",
                List.of("+", "&", ".", "||"), 0));
        list.add(q("What is the default value of a boolean variable in Java?",
                List.of("true", "false", "null", "0"), 1));
        list.add(q("Which keyword is used to create an object in Java?",
                List.of("new", "create", "object", "instance"), 0));
        list.add(q("What is the size of an int data type in Java?",
                List.of("8 bits", "16 bits", "32 bits", "64 bits"), 2));
        list.add(q("Which of these is not a Java keyword?",
                List.of("class", "interface", "extends", "function"), 3));
        list.add(q("What does JVM stand for?",
                List.of("Java Virtual Machine", "Java Variable Memory", "Java Virtual Method",
                        "Java Variable Manager"), 0));
        list.add(q("Which symbol is used for single-line comments in Java?",
                List.of("//", "/*", "*/", "#"), 0));
        return list;
    }

    private static List<Map<String, Object>> javaIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What is method overloading in Java?",
                List.of("Having multiple methods with the same name but different parameters",
                        "Calling a method from another method", "Overriding a method in a subclass",
                        "Creating multiple instances of a method"), 0));
        list.add(q("Which collection class maintains insertion order?",
                List.of("HashSet", "HashMap", "LinkedHashSet", "TreeSet"), 2));
        list.add(q("What is the purpose of the 'finally' block?",
                List.of("To catch exceptions", "To execute cleanup code regardless of exceptions",
                        "To throw exceptions", "To ignore exceptions"), 1));
        list.add(q("Which interface should be implemented for custom sorting?",
                List.of("Runnable", "Comparable", "Serializable", "Cloneable"), 1));
        list.add(q("Which keyword is used to define a constant in Java?",
                List.of("final", "static", "const", "immutable"), 0));
        list.add(q("What does the 'instanceof' operator do?",
                List.of("Creates a new instance", "Checks type compatibility", "Invokes a method",
                        "Compares primitive values"), 1));
        list.add(q("What value does String.length() return?",
                List.of("Character count", "Byte size", "Hash code", "Memory address"), 0));
        list.add(q("Which exception is thrown when accessing an array out of bounds?",
                List.of("NullPointerException", "ArrayIndexOutOfBoundsException", "IllegalArgumentException",
                        "ClassCastException"), 1));
        list.add(q("What is the default value of an int instance variable?",
                List.of("0", "null", "false", "undefined"), 0));
        list.add(q("Which statement is used to stop a loop immediately?",
                List.of("continue", "break", "return", "stop"), 1));
        return list;
    }

    private static List<Map<String, Object>> javaAdvanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What is the difference between HashMap and ConcurrentHashMap?",
                List.of("HashMap is synchronized, ConcurrentHashMap is not",
                        "ConcurrentHashMap allows concurrent access, HashMap does not",
                        "They are identical", "HashMap is thread-safe, ConcurrentHashMap is not"), 1));
        list.add(q("What does the 'volatile' keyword ensure?",
                List.of("The variable cannot be changed", "Changes to the variable are visible to all threads",
                        "The variable is stored in volatile memory", "The variable cannot be garbage collected"), 1));
        list.add(q("Which design pattern uses a single instance shared across the application?",
                List.of("Factory", "Observer", "Singleton", "Decorator"), 2));
        list.add(q("What is the primary purpose of the 'transient' keyword?",
                List.of("To make a method thread-safe", "To exclude a field from serialization",
                        "To create a temporary variable", "To synchronize access"), 1));
        list.add(q("What does the 'finalize' method do?",
                List.of("Runs before object construction", "Releases resources before garbage collection",
                        "Makes a class immutable", "Prevents inheritance"), 1));
        list.add(q("Which Java construct is used to manage multiple threads?",
                List.of("ThreadPoolExecutor", "ArrayList", "HashMap", "Stream"), 0));
        list.add(q("What is the output of System.out.println(3 + 4 + \"5\");?",
                List.of("75", "345", "12", "Error"), 0));
        list.add(q("Which memory area stores method call frames in Java?",
                List.of("Heap", "Stack", "PermGen", "Code segment"), 1));
        list.add(q("What is a lambda expression used for?",
                List.of("Creating anonymous classes", "Defining a new data type",
                        "Providing inline implementation of functional interfaces", "Handling exceptions"), 2));
        list.add(q("What is the purpose of the 'try-with-resources' statement?",
                List.of("To catch runtime exceptions", "To automatically close resources",
                        "To declare variables", "To pause execution"), 1));
        return list;
    }

    private static List<Map<String, Object>> pythonPreIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What is the correct way to declare a function in Python?",
                List.of("function myFunc():", "def myFunc():", "func myFunc():", "declare myFunc():"), 1));
        list.add(q("Which of these is a valid Python list?",
                List.of("{1, 2, 3}", "[1, 2, 3]", "(1, 2, 3)", "<1, 2, 3>"), 1));
        list.add(q("What is the output of print(type(3.14))?",
                List.of("<class 'int'>", "<class 'float'>", "<class 'str'>", "<class 'double'>"), 1));
        list.add(q("Which symbol is used for comments in Python?",
                List.of("//", "#", "/*", "--"), 1));
        list.add(q("How do you create a dictionary in Python?",
                List.of("[1: 'a']", "{1: 'a'}", "(1: 'a')", "<1: 'a'>"), 1));
        list.add(q("What keyword starts a conditional block in Python?",
                List.of("when", "if", "cond", "check"), 1));
        list.add(q("Which function prints output to the console in Python?",
                List.of("echo()", "print()", "console.log()", "output()"), 1));
        list.add(q("How do you access the first element of a list named items?",
                List.of("items[1]", "items[0]", "items.first", "items.get(0)"), 1));
        list.add(q("Which data type is immutable in Python?",
                List.of("list", "set", "tuple", "dictionary"), 2));
        list.add(q("What does len('hello') return?",
                List.of("4", "5", "6", "hello"), 1));
        return list;
    }

    private static List<Map<String, Object>> pythonIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What is list comprehension used for in Python?",
                List.of("Creating lists using loops in a single expression", "Declaring classes",
                        "Handling exceptions", "Defining functions"), 0));
        list.add(q("Which keyword catches an exception after a try block in Python?",
                List.of("except", "catch", "finally", "throw"), 0));
        list.add(q("How do you import a module named math?",
                List.of("import math", "include math", "using math", "require math"), 0));
        list.add(q("What does the strip() method do on a string?",
                List.of("Removes whitespace from both ends", "Converts to lowercase",
                        "Reverses the string", "Splits the string"), 0));
        list.add(q("Which of these is a tuple?",
                List.of("[1, 2, 3]", "(1, 2, 3)", "{1, 2, 3}", "<1, 2, 3>"), 1));
        list.add(q("What is the output of print('5' + '2')?",
                List.of("7", "52", "Error", "5 2"), 1));
        list.add(q("Which built-in type is used for ordered key-value data?",
                List.of("list", "tuple", "set", "dict"), 3));
        list.add(q("How do you define a class named Person?",
                List.of("class Person:", "Person class:", "def Person:", "create Person:"), 0));
        list.add(q("What is the result of 3 == 3.0 in Python?",
                List.of("True", "False", "None", "Error"), 0));
        list.add(q("Which function generates a sequence of numbers?",
                List.of("range()", "list()", "numbers()", "sequence()"), 0));
        list.add(q("Which list method adds a single item to the end of a list?",
                List.of("append()", "add()", "push()", "insertEnd()"), 0));
        list.add(q("Which method opens a file for reading in Python?",
                List.of("open()", "read()", "file()", "load()"), 0));
        list.add(q("Which string method splits text into a list by a separator?",
                List.of("split()", "divide()", "break()", "separate()"), 0));
        list.add(q("Which method joins list elements into one string?",
                List.of("join()", "merge()", "combine()", "concat()"), 0));
        list.add(q("What does the finally block do in exception handling?",
                List.of("Runs cleanup code whether or not an exception occurred", "Catches all exceptions only",
                        "Stops the program immediately", "Imports a module"), 0));
        list.add(q("Which keyword is used to raise an exception manually?",
                List.of("raise", "throw", "error", "except"), 0));
        list.add(q("What does enumerate() provide when looping over a list?",
                List.of("Index and value pairs", "Only sorted values", "Unique keys only",
                        "A reversed copy of the list"), 0));
        list.add(q("Which method safely returns a default when a dictionary key is missing?",
                List.of("get()", "find()", "lookup()", "fetch()"), 0));
        list.add(q("What is the output of len([10, 20, 30])?",
                List.of("3", "30", "6", "Error"), 0));
        list.add(q("Which symbol is used to define a lambda function?",
                List.of("lambda", "fn", "func", "def lambda"), 0));
        list.add(q("Which module is commonly used to work with JSON data?",
                List.of("json", "csv", "xml", "pickle"), 0));
        list.add(q("What does pip install do?",
                List.of("Installs a Python package from PyPI", "Runs unit tests", "Compiles Python to Java",
                        "Deletes unused imports"), 0));
        list.add(q("Which method returns dictionary keys as a view object?",
                List.of("keys()", "items()", "values()", "entries()"), 0));
        list.add(q("What is the result of 'hello'.upper()?",
                List.of("HELLO", "hello", "Hello", "Error"), 0));
        return list;
    }

    private static List<Map<String, Object>> pythonAdvanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What does the @staticmethod decorator do?",
                List.of("Defines a class method", "Defines a method that does not receive self",
                        "Creates a new object", "Imports a module"), 1));
        list.add(q("Which protocol is used for object serialization in Python?",
                List.of("pickle", "json", "xml", "yaml"), 0));
        list.add(q("What is a generator in Python?",
                List.of("A list of values", "A function returning an iterator", "A class method",
                        "A type of exception"), 1));
        list.add(q("Which statement is used to create a context manager?",
                List.of("with", "use", "using", "context"), 0));
        list.add(q("What is the output of len({'a': 1, 'b': 2})?",
                List.of("1", "2", "3", "Error"), 1));
        list.add(q("Which method is used to add an item to a set?",
                List.of("add()", "append()", "insert()", "push()"), 0));
        list.add(q("What is the purpose of __init__ in a Python class?",
                List.of("To define a class method", "To initialize object state", "To delete an object",
                        "To create a static method"), 1));
        list.add(q("How do you catch multiple exceptions in one except block?",
                List.of("except (TypeError, ValueError):", "except TypeError, ValueError:",
                        "except TypeError | ValueError:", "except [TypeError, ValueError]:"), 0));
        list.add(q("Which statement is true about Python's GIL?",
                List.of("It allows true parallel threads for CPU-bound tasks",
                        "It prevents multiple native threads from executing Python bytecodes at the same time",
                        "It optimizes memory usage", "It is only active in Python 2"), 1));
        list.add(q("What is the output of [x*x for x in range(3)]?",
                List.of("[0, 1, 4]", "[1, 4, 9]", "[0, 1, 2]", "Error"), 0));
        return list;
    }

    private static List<Map<String, Object>> sqlPreIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("Which SQL clause groups rows that share the same values?",
                List.of("GROUP BY", "ORDER BY", "JOIN", "PARTITION"), 0));
        list.add(q("What is the purpose of a FOREIGN KEY?",
                List.of("Ensures referential integrity", "Creates a unique index", "Encrypts sensitive data",
                        "Automatically sorts rows"), 0));
        list.add(q("Which type of JOIN returns all rows from both tables, matching rows where possible?",
                List.of("FULL OUTER JOIN", "INNER JOIN", "LEFT JOIN", "CROSS JOIN"), 0));
        list.add(q("What does the HAVING clause do?",
                List.of("Filters groups after aggregation", "Filters rows before grouping",
                        "Sorts aggregated results", "Limits query output"), 0));
        list.add(q("Which SQL statement creates a new table?",
                List.of("CREATE TABLE", "MAKE TABLE", "NEW TABLE", "ADD TABLE"), 0));
        list.add(q("What is the difference between CHAR and VARCHAR?",
                List.of("CHAR is fixed-length, VARCHAR is variable-length",
                        "CHAR stores numbers, VARCHAR stores text", "CHAR is faster, VARCHAR is slower",
                        "CHAR is case-sensitive, VARCHAR is not"), 0));
        list.add(q("Which index type improves query performance by avoiding full table scans?",
                List.of("B-Tree Index", "Hash Index", "Clustered Index", "Bitmap Index"), 0));
        list.add(q("What does the SQL UNION operator do?",
                List.of("Combines results of two queries and removes duplicates", "Joins two tables on a key",
                        "Aggregates multiple columns", "Merges rows into one"), 0));
        list.add(q("Which constraint ensures that a column cannot have NULL values?",
                List.of("NOT NULL", "PRIMARY KEY", "UNIQUE", "CHECK"), 0));
        list.add(q("What is the purpose of the EXPLAIN command in SQL?",
                List.of("Shows query execution plan", "Runs the query step by step", "Explains syntax errors",
                        "Displays table schema"), 0));
        return list;
    }

    private static List<Map<String, Object>> sqlIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("Which JOIN returns only matching rows from both tables?",
                List.of("INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "CROSS JOIN"), 0));
        list.add(q("Which clause filters groups after GROUP BY?",
                List.of("HAVING", "WHERE", "FILTER", "GROUP FILTER"), 0));
        list.add(q("What does GROUP BY do?",
                List.of("Groups rows for aggregate functions", "Sorts the table", "Creates a new table",
                        "Joins tables"), 0));
        list.add(q("Which function calculates the average of numeric values?",
                List.of("AVG()", "MEAN()", "AVERAGE()", "MID()"), 0));
        list.add(q("A FOREIGN KEY is used to:",
                List.of("Reference a primary key in another table", "Encrypt a column", "Index a table",
                        "Rename a column"), 0));
        list.add(q("Which subquery type returns a single value?",
                List.of("Scalar subquery", "Correlated list", "Table scan", "Outer join"), 0));
        list.add(q("LEFT JOIN returns:",
                List.of("All rows from the left table and matches from the right", "Only matching rows",
                        "Only the left table's columns", "Rows with no nulls"), 0));
        list.add(q("Which constraint ensures a column cannot be NULL?",
                List.of("NOT NULL", "UNIQUE", "CHECK", "DEFAULT"), 0));
        list.add(q("What does the LIKE operator do?",
                List.of("Pattern matching with wildcards", "Exact equality only", "Numeric comparison",
                        "Date arithmetic"), 0));
        list.add(q("UNION combines result sets and:",
                List.of("Removes duplicates by default in standard SQL", "Always keeps duplicates",
                        "Sorts automatically", "Creates a permanent table"), 0));
        return list;
    }

    private static List<Map<String, Object>> sqlAdvanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("Which property is NOT part of ACID?",
                List.of("Distribution", "Atomicity", "Consistency", "Isolation"), 0));
        list.add(q("What is the main purpose of an index?",
                List.of("Speed up data retrieval", "Encrypt data", "Backup tables", "Validate passwords"), 0));
        list.add(q("Which normal form removes partial dependency on a composite key?",
                List.of("2NF", "1NF", "3NF", "BCNF"), 0));
        list.add(q("What does a transaction COMMIT do?",
                List.of("Permanently saves changes", "Rolls back changes", "Locks the database forever",
                        "Deletes the log"), 0));
        list.add(q("Which isolation level prevents dirty reads?",
                List.of("READ COMMITTED or higher", "READ UNCOMMITTED only", "No isolation",
                        "SERIALIZABLE only"), 0));
        list.add(q("A covering index means:",
                List.of("The index contains all columns needed by the query", "The index covers the whole disk",
                        "The table has no primary key", "The query uses no WHERE clause"), 0));
        list.add(q("What is a deadlock?",
                List.of("Two transactions waiting on each other", "A deleted table", "A failed backup",
                        "An invalid JOIN"), 0));
        list.add(q("Which statement is true about VARCHAR vs CHAR?",
                List.of("VARCHAR uses variable length storage", "CHAR is always slower", "They are identical",
                        "VARCHAR cannot store text"), 0));
        list.add(q("A VIEW is:",
                List.of("A virtual table based on a query", "A physical copy of data", "An index type",
                        "A backup file"), 0));
        list.add(q("EXPLAIN (or similar) is used to:",
                List.of("Analyze query execution plan", "Insert sample data", "Grant permissions",
                        "Drop constraints"), 0));
        return list;
    }

    private static List<Map<String, Object>> gitPreIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What type of version control system is Git?",
                List.of("Distributed", "Centralized only", "Local only", "Manual file copy"), 0));
        list.add(q("Which command creates a new local Git repository?",
                List.of("git init", "git start", "git new", "git create"), 0));
        list.add(q("Which command copies a remote repository to your machine?",
                List.of("git clone", "git copy", "git download", "git pull only"), 0));
        list.add(q("What does git add do?",
                List.of("Stages changes for the next commit", "Commits changes immediately",
                        "Pushes to remote", "Creates a new branch"), 0));
        list.add(q("What does git commit do?",
                List.of("Saves a snapshot of staged changes with a message", "Uploads to GitHub only",
                        "Deletes a branch", "Merges two branches"), 0));
        list.add(q("What is the default name of the primary remote repository?",
                List.of("origin", "main", "master", "remote"), 0));
        list.add(q("Which command shows the state of your working directory?",
                List.of("git status", "git show", "git list", "git state"), 0));
        list.add(q("What is a branch in Git?",
                List.of("An independent line of development", "A remote server", "A backup folder",
                        "A type of commit message"), 0));
        list.add(q("Which command sends local commits to a remote repository?",
                List.of("git push", "git send", "git upload", "git publish"), 0));
        list.add(q("What does git pull do?",
                List.of("Fetches and merges changes from remote", "Only downloads without merging",
                        "Deletes local commits", "Creates a tag"), 0));
        list.add(q("Which command stages all modified tracked files in the current directory?",
                List.of("git add .", "git stage all", "git commit -a", "git track ."), 0));
        list.add(q("What is the common default name for the primary branch today?",
                List.of("main", "origin", "master only", "head"), 0));
        list.add(q("Who created Git?",
                List.of("Linus Torvalds", "Microsoft", "Apache Foundation", "Richard Stallman"), 0));
        list.add(q("Where are Git's version history and metadata stored locally?",
                List.of("In the .git directory", "In every source file", "On GitHub only",
                        "In package.json"), 0));
        list.add(q("Before committing, new or changed files must usually be:",
                List.of("Staged with git add", "Pushed with git push", "Tagged with git tag",
                        "Merged with git merge"), 0));
        return list;
    }

    private static List<Map<String, Object>> gitIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What does git merge do?",
                List.of("Combines changes from another branch into the current branch",
                        "Deletes a remote branch", "Renames a repository", "Stages all files"), 0));
        list.add(q("What is the difference between git fetch and git pull?",
                List.of("fetch downloads only; pull downloads and merges", "They are identical",
                        "pull does not contact remote", "fetch deletes local branches"), 0));
        list.add(q("What causes a merge conflict?",
                List.of("The same lines were changed differently on two branches",
                        "The repository is empty", "You forgot git add", "The remote is named origin"), 0));
        list.add(q("Which command lists local branches?",
                List.of("git branch", "git list-branch", "git branches", "git show-branch"), 0));
        list.add(q("What does git checkout (or git switch) do?",
                List.of("Changes the active branch or restores files", "Commits staged files",
                        "Adds a remote", "Shows commit history"), 0));
        list.add(q("What does HEAD usually refer to?",
                List.of("The current commit/branch you are on", "The first commit ever made",
                        "The remote server URL", "The .gitignore file"), 0));
        list.add(q("What is the purpose of a .gitignore file?",
                List.of("Specifies files Git should not track", "Stores commit messages",
                        "Lists all branches", "Encrypts the repository"), 0));
        list.add(q("What does git diff show?",
                List.of("Differences between working directory and staged/committed files",
                        "Only remote branches", "User permissions", "Repository size"), 0));
        list.add(q("Which command adds a connection to a remote repository?",
                List.of("git remote add", "git connect", "git link", "git origin set"), 0));
        list.add(q("What does git log display?",
                List.of("Commit history", "Only current branch name", "Remote URLs only",
                        "Staged file list"), 0));
        list.add(q("What does git stash pop do?",
                List.of("Reapplies the most recent stash and removes it from the stash list",
                        "Deletes all stashes", "Pushes stashed commits to remote", "Creates a new branch"), 0));
        list.add(q("A fast-forward merge happens when:",
                List.of("The target branch has no new commits since the branch being merged",
                        "There is a merge conflict", "You use git rebase only", "The remote is offline"), 0));
        list.add(q("What is a detached HEAD state?",
                List.of("HEAD points to a specific commit instead of a branch name",
                        "The repository has no commits", "Git cannot connect to remote",
                        "All branches were deleted"), 0));
        list.add(q("Which command creates a new branch and switches to it (modern Git)?",
                List.of("git switch -c branch-name", "git branch only", "git new branch-name",
                        "git checkout --delete"), 0));
        list.add(q("git commit -m is used to:",
                List.of("Create a commit with a message in one step", "Rename a remote",
                        "Show diff statistics only", "Reset the last commit"), 0));
        return list;
    }

    private static List<Map<String, Object>> gitAdvanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What does git rebase do?",
                List.of("Replays commits on top of another branch for a linear history",
                        "Deletes all branches", "Pushes tags only", "Creates a submodule"), 0));
        list.add(q("When should you avoid git rebase?",
                List.of("On commits already pushed to a shared remote branch",
                        "On a new local feature branch", "Before git add", "When cloning a repo"), 0));
        list.add(q("What is a Git submodule?",
                List.of("A Git repository embedded inside another repository",
                        "A type of merge conflict", "A backup of .git folder", "A remote named origin"), 0));
        list.add(q("In Git Flow, which branch is reserved for production releases?",
                List.of("main (or master)", "develop", "feature", "hotfix only"), 0));
        list.add(q("In Git Flow, which branch is used to integrate completed features?",
                List.of("develop", "main", "release only", "staging"), 0));
        list.add(q("GitHub Flow is best described as:",
                List.of("Branch from main, open PR, merge to main, deploy",
                        "Only use git rebase for all merges", "No branches allowed",
                        "Multiple production branches per environment only"), 0));
        list.add(q("What does git stash do?",
                List.of("Temporarily saves uncommitted changes for later",
                        "Permanently deletes commits", "Pushes to remote", "Creates a release tag"), 0));
        list.add(q("What is trunk-based development?",
                List.of("Developers commit frequently to the main branch with strong CI",
                        "No commits allowed on main", "Only Git submodules", "Centralized CVS workflow"), 0));
        list.add(q("What does git cherry-pick do?",
                List.of("Applies a specific commit from one branch to another",
                        "Deletes a remote branch", "Renames a repository", "Stages all files"), 0));
        list.add(q("git reset --soft HEAD~1 typically:",
                List.of("Undoes the last commit but keeps changes staged",
                        "Deletes the repository", "Force-pushes to remote", "Removes all branches"), 0));
        list.add(q("What does git reflog help you do?",
                List.of("Recover commits or HEAD positions after mistakes",
                        "List only remote branches", "Encrypt the repository", "Run CI pipelines"), 0));
        list.add(q("Why is git push --force dangerous on a shared branch?",
                List.of("It can overwrite teammates' commits on the remote",
                        "It only affects local files", "It disables .gitignore", "It cannot be undone locally"), 0));
        list.add(q("git bisect is primarily used to:",
                List.of("Find which commit introduced a bug using binary search",
                        "Merge two release branches", "Sign commits", "Clone submodules"), 0));
        list.add(q("A squash merge on a pull request typically:",
                List.of("Combines multiple commits into one before merging",
                        "Deletes the main branch", "Rebases onto every feature branch daily",
                        "Removes the remote origin"), 0));
        list.add(q("git tag is commonly used to:",
                List.of("Mark a specific commit (e.g. a release version)",
                        "Stage all modified files", "Resolve merge conflicts automatically",
                        "Rename a branch"), 0));
        return list;
    }

    private static List<Map<String, Object>> dsaPreIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What is the time complexity of accessing an element by index in an array?",
                List.of("O(1)", "O(n)", "O(log n)", "O(n²)"), 0));
        list.add(q("Which structure stores elements in contiguous memory?",
                List.of("Array", "Linked list", "Tree", "Graph"), 0));
        list.add(q("A stack follows which principle?",
                List.of("LIFO (Last In, First Out)", "FIFO (First In, First Out)", "LILO", "Random access"), 0));
        list.add(q("A queue follows which principle?",
                List.of("FIFO (First In, First Out)", "LIFO", "Sorted order", "Heap order"), 0));
        list.add(q("What does Big-O notation describe?",
                List.of("Upper bound of algorithm growth rate", "Exact runtime in seconds",
                        "Memory address layout", "Number of lines of code"), 0));
        list.add(q("Linear search on an unsorted array of size n has time complexity:",
                List.of("O(n)", "O(1)", "O(log n)", "O(n log n)"), 0));
        list.add(q("Binary search requires the input to be:",
                List.of("Sorted", "Unsorted", "A linked list only", "At least size 100"), 0));
        list.add(q("Which Java collection allows duplicate elements and maintains insertion order?",
                List.of("ArrayList", "HashSet", "HashMap keys only", "TreeSet"), 0));
        list.add(q("In a singly linked list, each node contains:",
                List.of("Data and a reference to the next node", "Only data", "Data and two pointers only",
                        "Index and value in an array"), 0));
        list.add(q("What is the main disadvantage of an array compared to a linked list?",
                List.of("Fixed or costly resize; slow insert in middle", "Cannot store integers",
                        "No indexing", "Uses more memory always"), 0));
        list.add(q("Recursion always needs:",
                List.of("A base case to stop", "A loop variable only", "Two arrays", "Global variables"), 0));
        list.add(q("HashMap provides average-case lookup time of:",
                List.of("O(1)", "O(n²)", "O(log n) only for strings", "O(n) always"), 0));
        list.add(q("Which traversal visits root, then left subtree, then right subtree?",
                List.of("Preorder", "Inorder", "Postorder", "Level order"), 0));
        list.add(q("An ArrayList in Java is best described as:",
                List.of("A resizable dynamic array", "A fixed-size array", "A hash table",
                        "A binary search tree"), 0));
        list.add(q("Which operation is typically O(1) for a hash table (average case)?",
                List.of("Get by key", "Sort all keys", "Find minimum key", "Print in sorted order"), 0));
        return list;
    }

    private static List<Map<String, Object>> dsaIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("In a binary tree, each node has at most how many children?",
                List.of("2", "1", "3", "Unlimited"), 0));
        list.add(q("Inorder traversal of a Binary Search Tree (BST) prints keys in:",
                List.of("Ascending sorted order", "Descending order only", "Random order",
                        "Level-by-level order"), 0));
        list.add(q("Binary search on a sorted array of n elements runs in:",
                List.of("O(log n)", "O(n)", "O(1)", "O(n²)"), 0));
        list.add(q("Bubble sort has worst-case time complexity:",
                List.of("O(n²)", "O(n)", "O(log n)", "O(1)"), 0));
        list.add(q("Which structure is ideal for BFS (breadth-first search) on a graph?",
                List.of("Queue", "Stack", "Priority queue only", "Array list tail only"), 0));
        list.add(q("Which structure is commonly used for DFS using an explicit structure?",
                List.of("Stack", "Queue", "HashMap", "Array of booleans only"), 0));
        list.add(q("A HashSet in Java guarantees:",
                List.of("No duplicate elements", "Sorted iteration order", "Key-value pairs",
                        "FIFO ordering"), 0));
        list.add(q("Merging two sorted linked lists of lengths n and m can be done in:",
                List.of("O(n + m)", "O(n × m)", "O(1)", "O(n²)"), 0));
        list.add(q("Level-order traversal of a tree is also called:",
                List.of("BFS traversal", "Preorder", "Postorder", "Inorder"), 0));
        list.add(q("Selection sort repeatedly selects:",
                List.of("The minimum element and places it at the front", "Random swaps",
                        "The middle element", "Two sorted halves"), 0));
        list.add(q("A graph can be represented using:",
                List.of("Adjacency list or adjacency matrix", "Only a single array",
                        "Only linked lists", "Stack alone"), 0));
        list.add(q("Inserting at the head of a singly linked list (when head pointer exists) is:",
                List.of("O(1)", "O(n)", "O(log n)", "O(n²)"), 0));
        list.add(q("Which is true about a stack?",
                List.of("Push and pop happen at the same end", "Enqueue at both ends",
                        "Elements are always sorted", "Used only for sorting"), 0));
        list.add(q("Two-dimensional arrays in Java are best thought of as:",
                List.of("Array of arrays (rows)", "Always 10×10", "Linked list of rows",
                        "Hash map of columns"), 0));
        list.add(q("Finding an element in an unsorted linked list of n nodes requires:",
                List.of("O(n) time in the worst case", "O(1) time", "O(log n) time",
                        "O(n log n) time"), 0));
        return list;
    }

    private static List<Map<String, Object>> dsaAdvanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("Dynamic programming is applicable when a problem has:",
                List.of("Optimal substructure and overlapping subproblems",
                        "Only greedy choice property", "No recursion", "Constant input size only"), 0));
        list.add(q("Memoization in DP is an example of:",
                List.of("Top-down approach", "Bottom-up tabulation only", "Divide and conquer without overlap",
                        "Brute force"), 0));
        list.add(q("The 0/1 knapsack problem means:",
                List.of("Each item can be taken at most once", "Unlimited copies of each item",
                        "Items must be sorted", "Only one item total"), 0));
        list.add(q("Dijkstra's algorithm finds:",
                List.of("Shortest paths from a source in graphs with non-negative weights",
                        "Minimum spanning tree only", "Topological order only", "Maximum flow"), 0));
        list.add(q("Compared to divide and conquer, DP typically:",
                List.of("Stores results of overlapping subproblems", "Never uses tables",
                        "Always splits into independent non-overlapping parts only", "Uses only recursion"), 0));
        list.add(q("Fibonacci with naive recursion (no memo) has time complexity about:",
                List.of("O(2^n)", "O(n)", "O(log n)", "O(1)"), 0));
        list.add(q("Coin change (minimum coins) with DP often uses state:",
                List.of("dp[amount] = min coins to make amount", "dp[tree height] only",
                        "dp[string length] for sorting", "No state array"), 0));
        list.add(q("A binary heap is commonly used to implement:",
                List.of("Priority queue", "Hash table", "Stack only", "Graph adjacency list"), 0));
        list.add(q("Quick sort average-case time complexity is:",
                List.of("O(n log n)", "O(n²) always", "O(n)", "O(log n)"), 0));
        list.add(q("Topological sort is defined on:",
                List.of("Directed acyclic graph (DAG)", "Any undirected graph", "Binary tree only",
                        "Circular linked list"), 0));
        list.add(q("In a BST, for every node:",
                List.of("Left subtree keys < node < right subtree keys (typical convention)",
                        "All keys equal", "No left child allowed", "Height is always log n"), 0));
        list.add(q("Space complexity of recursive DFS on a tree with height h is often:",
                List.of("O(h) due to call stack", "O(1)", "O(n²)", "O(log log n)"), 0));
        list.add(q("Tabulation (bottom-up) DP usually fills a table:",
                List.of("From smaller subproblems to larger ones", "Random order only",
                        "Only after greedy pass", "Without base cases"), 0));
        list.add(q("Which problem is a classic DP example?",
                List.of("Longest common subsequence", "Binary search on sorted array",
                        "Stack push/pop", "Array index access"), 0));
        list.add(q("Graph BFS time complexity with adjacency list is often:",
                List.of("O(V + E)", "O(V²) only", "O(E²)", "O(1)"), 0));
        return list;
    }

    private static List<Map<String, Object>> frontEndPreIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("Which HTML element is used for the largest heading?",
                List.of("h1 element", "heading element", "head element", "title element"), 0));
        list.add(q("Which attribute provides alternative text for an image?",
                List.of("alt", "src", "href", "title-only"), 0));
        list.add(q("Which CSS property changes text color?",
                List.of("color", "font-style", "background-color", "text-size"), 0));
        list.add(q("Which tag links an external CSS file?",
                List.of("link tag", "style tag", "script tag", "css tag"), 0));
        list.add(q("Which JavaScript method writes a message to the browser console?",
                List.of("console.log()", "print()", "System.out.println()", "echo()"), 0));
        list.add(q("What does CSS stand for?",
                List.of("Cascading Style Sheets", "Creative Style Syntax", "Computer Styled Sections",
                        "Client Side Script"), 0));
        list.add(q("Which HTML element creates a clickable link?",
                List.of("anchor (a) element", "button-link element", "href element", "url element"), 0));
        list.add(q("Which CSS selector targets an element with id='main'?",
                List.of("#main", ".main", "main()", "*main"), 0));
        list.add(q("Which input type is best for entering an email address?",
                List.of("email", "text-email", "mail", "address"), 0));
        list.add(q("Where should visible page content usually be placed in HTML?",
                List.of("Inside the body element", "Inside the head element", "Inside the meta element",
                        "Inside the doctype declaration"), 0));
        list.add(q("Which HTML element creates an unordered list?",
                List.of("ul", "ol", "li", "dl"), 0));
        list.add(q("Which CSS property adds space inside an element between content and border?",
                List.of("padding", "margin", "border", "gap"), 0));
        list.add(q("How do you correctly include JavaScript in an HTML page?",
                List.of("Using a script element", "Using a java element", "Using a link element",
                        "Using a style element"), 0));
        list.add(q("Which CSS property makes text bold?",
                List.of("font-weight", "font-bold", "text-style", "font-size"), 0));
        list.add(q("Which attribute opens a hyperlink in a new browser tab?",
                List.of("target=\"_blank\"", "rel=\"new\"", "href=\"_blank\"", "open=\"tab\""), 0));
        list.add(q("What is the purpose of the class attribute in HTML?",
                List.of("To assign a CSS styling hook shared by multiple elements", "To make an element unique",
                        "To store JavaScript code", "To hide content from users"), 0));
        list.add(q("Which keyword declares a block-scoped variable in modern JavaScript?",
                List.of("let", "int", "define", "static"), 0));
        list.add(q("Which attribute on an img element specifies the image file URL?",
                List.of("src", "href", "link", "path"), 0));
        list.add(q("Which HTML form element is used for multi-line text input?",
                List.of("textarea", "input type=\"text\"", "select", "label"), 0));
        list.add(q("Which CSS property sets space outside an element's border?",
                List.of("margin", "padding", "border", "outline"), 0));
        list.add(q("Which Bootstrap 5 classes create a primary-styled button?",
                List.of("btn btn-primary", "button-primary", "btn-main", "primary-btn"), 0));
        return list;
    }

    private static List<Map<String, Object>> frontEndIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("Which CSS layout model is designed for one-dimensional rows or columns?",
                List.of("Flexbox", "CSS Grid", "Float layout", "Table layout"), 0));
        list.add(q("Which CSS layout model is best for two-dimensional row and column layouts?",
                List.of("CSS Grid", "Inline layout", "Float layout", "Position static"), 0));
        list.add(q("What does event.preventDefault() usually do?",
                List.of("Stops the browser's default action for an event", "Deletes the event listener",
                        "Refreshes the page immediately", "Makes the event run twice"), 0));
        list.add(q("Which method selects the first matching element in the DOM?",
                List.of("document.querySelector()", "document.querySelectorAll()", "document.getAll()",
                        "window.selectFirst()"), 0));
        list.add(q("Which Web API is commonly used to make HTTP requests in modern JavaScript?",
                List.of("fetch()", "alert()", "prompt()", "setTimeout()"), 0));
        list.add(q("What is the purpose of a media query in CSS?",
                List.of("Apply styles based on viewport or device conditions", "Query a database",
                        "Load a video file", "Create a JavaScript promise"), 0));
        list.add(q("Which attribute helps associate a label with a form control?",
                List.of("for", "name", "value", "placeholder"), 0));
        list.add(q("In JavaScript, what does === check?",
                List.of("Value and type equality", "Value equality with type conversion", "Assignment",
                        "Object inheritance"), 0));
        list.add(q("Which storage API persists data after the browser tab is closed?",
                List.of("localStorage", "sessionStorage", "WeakMap", "history.state"), 0));
        list.add(q("What is semantic HTML mainly used for?",
                List.of("Giving meaningful structure to content", "Making all text bold",
                        "Replacing CSS completely", "Disabling browser defaults"), 0));
        list.add(q("Which CSS unit is relative to the root element font size?",
                List.of("rem", "px", "vh", "pt"), 0));
        list.add(q("What does the async attribute on a script tag do?",
                List.of("Downloads the script asynchronously without blocking HTML parsing",
                        "Runs the script before any HTML loads", "Deletes other scripts on the page",
                        "Blocks all JavaScript until parsing finishes"), 0));
        list.add(q("Which DOM method appends a new child element to a parent?",
                List.of("appendChild()", "removeChild()", "getAttribute()", "querySelectorAll()"), 0));
        list.add(q("What does JSON.parse() do?",
                List.of("Converts a JSON string into a JavaScript value", "Converts an object into a JSON string",
                        "Validates HTML markup", "Sends an HTTP request"), 0));
        list.add(q("Which HTML5 element represents self-contained content such as a blog post?",
                List.of("article", "span", "b", "nav"), 0));
        list.add(q("Which CSS Flexbox property aligns items along the cross axis?",
                List.of("align-items", "justify-content", "flex-direction", "order"), 0));
        list.add(q("What HTTP method does fetch() use by default when no options are specified?",
                List.of("GET", "POST", "PUT", "DELETE"), 0));
        list.add(q("Which semantic HTML element is intended for major navigation links?",
                List.of("nav", "menu", "links", "header"), 0));
        list.add(q("Which CSS pseudo-class applies styles when the user hovers over an element?",
                List.of(":hover", ":active", ":focus", ":visited"), 0));
        list.add(q("In Bootstrap's grid system, which class wraps a horizontal group of columns?",
                List.of("row", "col", "container-fluid", "grid"), 0));
        list.add(q("Which JavaScript method attaches a handler that runs when an event occurs?",
                List.of("addEventListener()", "onload()", "attachEvent() only", "bindEvent()"), 0));
        return list;
    }

    private static List<Map<String, Object>> frontEndAdvanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What is hydration in a server-rendered frontend app?",
                List.of("Attaching client-side JavaScript behavior to server-rendered HTML",
                        "Compressing images before upload", "Clearing the browser cache",
                        "Converting CSS into inline styles"), 0));
        list.add(q("Which technique can improve initial load by splitting JavaScript bundles?",
                List.of("Code splitting", "CSS reset", "DOM polling", "Inline event attributes"), 0));
        list.add(q("What does ARIA primarily help with?",
                List.of("Accessibility semantics for assistive technologies", "API caching",
                        "Image compression", "Database indexing"), 0));
        list.add(q("Which metric measures visual loading performance for the largest visible content element?",
                List.of("Largest Contentful Paint (LCP)", "First Input Delay only", "Cumulative Layout Shift",
                        "Time to First Byte only"), 0));
        list.add(q("What causes Cumulative Layout Shift (CLS)?",
                List.of("Unexpected movement of visible elements during page load", "Large JavaScript functions only",
                        "Using semantic HTML", "A successful CSS transition after click"), 0));
        list.add(q("What is the benefit of using a virtual DOM in libraries like React?",
                List.of("Efficiently calculating UI updates before touching the real DOM",
                        "Removing the need for HTML", "Running JavaScript on the database",
                        "Preventing all runtime errors"), 0));
        list.add(q("Which HTTP header is commonly used by browsers to enforce content security rules?",
                List.of("Content-Security-Policy", "Content-Type", "Accept-Language", "X-Page-Color"), 0));
        list.add(q("What is tree shaking in frontend builds?",
                List.of("Removing unused code from the final bundle", "Animating nested menus",
                        "Sorting DOM nodes alphabetically", "Refreshing CSS variables"), 0));
        list.add(q("Which pattern helps avoid repeated DOM work during rapid resize or scroll events?",
                List.of("Debouncing or throttling handlers", "Adding more event listeners",
                        "Using alert() inside the handler", "Disabling CSS media queries"), 0));
        list.add(q("Why should critical images often include width and height attributes?",
                List.of("To reserve layout space and reduce layout shift", "To make them private",
                        "To convert them into SVG", "To disable lazy loading"), 0));
        list.add(q("What is the main benefit of lazy loading images?",
                List.of("Defers loading off-screen images until needed, improving performance",
                        "Makes images private", "Converts PNG files to JPG", "Removes the need for alt text"), 0));
        list.add(q("Why use rel=\"noopener\" with target=\"_blank\" on links?",
                List.of("Prevents the new page from accessing window.opener for security",
                        "Opens the link faster", "Improves SEO ranking", "Disables browser caching"), 0));
        list.add(q("What does code minification do in production builds?",
                List.of("Removes unnecessary whitespace and characters to reduce file size",
                        "Deletes all HTML comments visible to users", "Removes unused DOM nodes at runtime",
                        "Encrypts JavaScript source code"), 0));
        list.add(q("Which HTML feature serves different image files based on screen size?",
                List.of("srcset on img", "meta viewport only", "CSS float", "onclick handler"), 0));
        list.add(q("What does the Same-Origin Policy restrict?",
                List.of("How scripts from one origin access data from another origin",
                        "CSS inheritance between parent and child", "Supported image formats",
                        "Which fonts a page may load"), 0));
        list.add(q("In frontend architecture, what does SSR stand for?",
                List.of("Server-Side Rendering", "Single Source Repository", "Secure Socket Relay",
                        "Static Style Rules"), 0));
        list.add(q("Which Bootstrap class provides a centered, responsive page-width wrapper?",
                List.of("container", "row", "col-12", "wrapper"), 0));
        list.add(q("Which JavaScript array method returns a new array by transforming each element?",
                List.of("map()", "push()", "sort()", "join()"), 0));
        return list;
    }

    private static List<Map<String, Object>> baseFrameworkPreIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("Which Spring Boot starter is required for JPA and database access?",
                List.of("spring-boot-starter-data-jpa", "spring-boot-starter-webflux", "spring-boot-starter-mail",
                        "spring-boot-starter-security"), 0));
        list.add(q("Which dependency connects a Spring Boot project to MySQL?",
                List.of("mysql-connector-j", "postgresql-driver", "h2-console", "spring-boot-starter-jdbc-only"), 0));
        list.add(q("Which property in application.properties sets the database connection URL?",
                List.of("spring.datasource.url", "spring.jpa.url", "database.connection", "jdbc.url"), 0));
        list.add(q("Which annotation marks a Java class as a JPA entity mapped to a database table?",
                List.of("@Entity", "@Component", "@Service", "@Repository"), 0));
        list.add(q("Which HTTP method is typically used to retrieve data from a REST API?",
                List.of("GET", "POST", "PUT", "DELETE"), 0));
        list.add(q("Which HTTP method is typically used to create a new resource?",
                List.of("POST", "GET", "DELETE", "PATCH"), 0));
        list.add(q("What does JPA stand for?",
                List.of("Java Persistence API", "Java Programming Application", "Joint Project Architecture",
                        "Java Package Annotation"), 0));
        list.add(q("What is Hibernate in a Spring Boot JPA project?",
                List.of("The JPA provider that generates and runs SQL", "A frontend framework",
                        "A build tool like Maven", "A JSON serializer only"), 0));
        list.add(q("Which annotation defines a class as a REST controller that returns JSON?",
                List.of("@RestController", "@Controller only", "@Entity", "@Configuration"), 0));
        list.add(q("Which annotation maps an HTTP GET request to a controller method?",
                List.of("@GetMapping", "@PostMapping", "@RequestBody", "@Autowired"), 0));
        list.add(q("Which Spring Data interface provides built-in CRUD methods such as save() and findById()?",
                List.of("JpaRepository", "HttpServlet", "JdbcTemplate only", "EntityManagerFactory"), 0));
        list.add(q("Which layer should contain business rules in a layered Spring application?",
                List.of("Service layer", "Controller layer", "Repository layer", "HTML template layer"), 0));
        list.add(q("Which annotation is used for dependency injection in Spring?",
                List.of("@Autowired (or constructor injection)", "@InjectSQL", "@Database", "@Route"), 0));
        list.add(q("What is the default port for a Spring Boot web application?",
                List.of("8080", "3000", "5432", "80"), 0));
        list.add(q("Which property sets the database username in application.properties?",
                List.of("spring.datasource.username", "spring.jpa.username", "db.user", "jdbc.user"), 0));
        list.add(q("Which annotation marks the main Spring Boot application entry class?",
                List.of("@SpringBootApplication", "@Entity", "@RestController", "@Configuration only"), 0));
        list.add(q("Which starter adds Spring MVC and an embedded web server for REST APIs?",
                List.of("spring-boot-starter-web", "spring-boot-starter-test", "spring-boot-starter-mail",
                        "spring-boot-starter-actuator only"), 0));
        list.add(q("Which annotation maps an HTTP POST request to a controller method?",
                List.of("@PostMapping", "@GetMapping", "@PutMapping", "@DeleteMapping"), 0));
        list.add(q("Which Content-Type header is commonly used when sending JSON in a REST request?",
                List.of("application/json", "text/html", "multipart/form-data", "application/xml only"), 0));
        list.add(q("Which JPA annotation specifies the database table name for an entity?",
                List.of("@Table", "@Column", "@Database", "@SqlTable"), 0));
        list.add(q("Which property sets the database password in application.properties?",
                List.of("spring.datasource.password", "spring.jpa.password", "db.pass", "jdbc.secret"), 0));
        list.add(q("Which embedded server does Spring Boot use by default for web applications?",
                List.of("Tomcat", "Jetty only", "Node.js", "Nginx"), 0));
        list.add(q("In REST, resources should be named using:",
                List.of("Nouns (e.g. /api/courses)", "Verbs (e.g. /getCourses)", "SQL queries",
                        "Java method names"), 0));
        list.add(q("Which build tool manages dependencies and packages a Spring Boot project?",
                List.of("Maven (or Gradle)", "npm", "pip", "Composer"), 0));
        return list;
    }

    private static List<Map<String, Object>> baseFrameworkIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("Which annotation maps an HTTP PUT request for updating a resource?",
                List.of("@PutMapping", "@GetMapping", "@PostMapping", "@PatchMapping only"), 0));
        list.add(q("Which annotation maps an HTTP DELETE request?",
                List.of("@DeleteMapping", "@RemoveMapping", "@DropMapping", "@EraseMapping"), 0));
        list.add(q("In GET /api/courses/{id}, what does {id} represent?",
                List.of("A path variable identifying the course", "A query parameter named id",
                        "A request header", "A JSON body field"), 0));
        list.add(q("Which annotation binds a URL path segment to a method parameter?",
                List.of("@PathVariable", "@RequestParam", "@RequestBody", "@ResponseBody only"), 0));
        list.add(q("Which annotation binds JSON from the request body to a Java object?",
                List.of("@RequestBody", "@PathVariable", "@GetMapping", "@Column"), 0));
        list.add(q("What does repository.findById(id) return in Spring Data JPA?",
                List.of("Optional<Entity>", "Entity always (never empty)", "List<Entity>", "void"), 0));
        list.add(q("Which annotation marks the data access layer interface?",
                List.of("@Repository", "@RestController", "@Entity", "@GetMapping"), 0));
        list.add(q("Which annotation marks a service class containing business logic?",
                List.of("@Service", "@Repository", "@Entity", "@Table"), 0));
        list.add(q("What does @Transactional ensure in a service method?",
                List.of("Database operations succeed or fail together as one unit", "The method runs faster only",
                        "The method is public", "HTTP caching is enabled"), 0));
        list.add(q("Which annotation maps an entity field to a database column?",
                List.of("@Column", "@Field", "@Property", "@DbColumn"), 0));
        list.add(q("Which annotation marks the primary key field in a JPA entity?",
                List.of("@Id", "@Key", "@Primary", "@PK"), 0));
        list.add(q("What does @GeneratedValue(strategy = GenerationType.IDENTITY) typically do?",
                List.of("Auto-generates primary key values (e.g. auto-increment)", "Encrypts the primary key",
                        "Creates a foreign key", "Validates the ID format"), 0));
        list.add(q("Which route and method pair is correct for listing all courses?",
                List.of("GET /api/courses", "POST /api/courses/list", "DELETE /api/courses", "PUT /api/courses/all"), 0));
        list.add(q("Which route and method pair is correct for creating a new course?",
                List.of("POST /api/courses", "GET /api/courses", "PUT /api/courses", "DELETE /api/courses"), 0));
        list.add(q("What does spring.jpa.hibernate.ddl-auto=update do in development?",
                List.of("Updates database schema to match entities automatically", "Deletes all tables on startup",
                        "Disables Hibernate", "Encrypts database columns"), 0));
        list.add(q("Which annotation binds a query string parameter to a method argument?",
                List.of("@RequestParam", "@PathVariable", "@RequestBody", "@Column"), 0));
        list.add(q("Which repository method returns all records of an entity type?",
                List.of("findAll()", "findOne()", "getAllSQL()", "listTables()"), 0));
        list.add(q("Which Spring Data JPA feature lets you define methods like findByTitleContaining?",
                List.of("Derived query methods from method names", "Only raw SQL files",
                        "HTML templates", "JWT parsing"), 0));
        list.add(q("Which JPA annotation defines a many-to-one relationship to another entity?",
                List.of("@ManyToOne", "@OneToMany only", "@ManyToMany only", "@JoinTable only"), 0));
        list.add(q("When findById returns Optional.empty(), the API should typically respond with:",
                List.of("404 Not Found", "200 OK with null body", "201 Created", "500 always"), 0));
        list.add(q("Which HTTP status is appropriate after a successful DELETE with no response body?",
                List.of("204 No Content", "201 Created", "302 Found", "400 Bad Request"), 0));
        list.add(q("Which annotation maps a foreign-key column in a JPA relationship?",
                List.of("@JoinColumn", "@ForeignKey only", "@PathVariable", "@RequestParam"), 0));
        list.add(q("Which repository method checks whether a record exists by primary key?",
                List.of("existsById(id)", "hasId(id)", "contains(id)", "isPresent(id)"), 0));
        list.add(q("Which property helps Hibernate generate correct SQL for your database dialect?",
                List.of("spring.jpa.properties.hibernate.dialect", "spring.web.dialect",
                        "database.sql.mode", "jdbc.driver.only"), 0));
        return list;
    }

    private static List<Map<String, Object>> baseFrameworkAdvanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("In REST APIs, what is the main difference between PUT and PATCH?",
                List.of("PUT replaces the full resource; PATCH updates part of it", "PUT is read-only; PATCH is write-only",
                        "PATCH always deletes data", "They are identical"), 0));
        list.add(q("Why use ResponseEntity in a controller instead of returning the object directly?",
                List.of("To control HTTP status code and headers", "To avoid using JSON",
                        "To replace the service layer", "To disable validation"), 0));
        list.add(q("What is the purpose of @RestControllerAdvice?",
                List.of("Centralized exception handling for REST controllers", "Defining database tables",
                        "Creating JWT tokens only", "Mapping entities to DTOs automatically"), 0));
        list.add(q("Why should APIs use DTOs instead of exposing JPA entities directly?",
                List.of("To control the API contract and hide internal persistence details", "To make SQL faster only",
                        "Because entities cannot be serialized", "To remove validation"), 0));
        list.add(q("Which property logs generated SQL statements in the console (useful for debugging)?",
                List.of("spring.jpa.show-sql=true", "spring.sql.debug=true", "hibernate.log=all",
                        "database.print=true"), 0));
        list.add(q("In Clean Architecture, dependency direction should point:",
                List.of("Inward toward domain/business rules", "Outward toward the database only",
                        "Randomly between layers", "Only to the controller"), 0));
        list.add(q("Which HTTP status should be returned when a requested resource does not exist?",
                List.of("404 Not Found", "200 OK", "201 Created", "500 Internal Server Error"), 0));
        list.add(q("Which HTTP status is appropriate for a successful POST that creates a resource?",
                List.of("201 Created", "204 No Content", "404 Not Found", "301 Moved Permanently"), 0));
        list.add(q("What does @RequestMapping(\"/api/courses\") on a controller class define?",
                List.of("A base route prefix for all handler methods in that controller", "The database table name",
                        "The entity package", "The JWT secret key"), 0));
        list.add(q("Which repository method persists a new or updated entity?",
                List.of("save()", "findAll()", "count()", "deleteAllInBatch() only"), 0));
        list.add(q("Which repository method removes an entity by primary key?",
                List.of("deleteById(id)", "removeByKey(id)", "drop(id)", "erase(id)"), 0));
        list.add(q("What is the correct layered call order for a create-course API?",
                List.of("Controller -> Service -> Repository -> Database", "Repository -> Controller -> Service",
                        "Database -> Controller -> Service", "Controller -> Database -> Service"), 0));
        list.add(q("Which annotation runs a custom JPQL query on a repository method?",
                List.of("@Query", "@Sql", "@Select", "@JpqlMapping"), 0));
        list.add(q("What does @OneToMany represent in JPA?",
                List.of("One parent entity relates to many child entities", "One column maps to many tables",
                        "One HTTP request maps to many controllers", "One user has one role only"), 0));
        list.add(q("Which combination correctly maps CRUD to REST routes for /api/courses/{id}?",
                List.of("GET read, PUT update, DELETE remove; POST on /api/courses for create",
                        "GET create, POST read, PUT delete", "DELETE read, GET update, POST remove",
                        "All operations use GET only"), 0));
        list.add(q("Which annotation triggers Bean Validation on a request DTO in a controller?",
                List.of("@Valid", "@Validated only on class", "@Entity", "@Transactional"), 0));
        list.add(q("Which validation annotation ensures a string field is not null and not blank?",
                List.of("@NotBlank", "@NotNull only (allows empty string)", "@Size only", "@Email only"), 0));
        list.add(q("Which HTTP status should be returned when request validation fails?",
                List.of("400 Bad Request", "200 OK", "201 Created", "404 Not Found"), 0));
        list.add(q("Which HTTP status fits a duplicate email or unique constraint violation?",
                List.of("409 Conflict", "204 No Content", "301 Moved Permanently", "101 Switching Protocols"), 0));
        list.add(q("Where should a client send a JWT access token in a secured REST API?",
                List.of("Authorization: Bearer <token>", "Cookie: token=<token> only", "X-Body-Token header only",
                        "Query string ?jwt= always"), 0));
        list.add(q("Why map Entity objects to Response DTOs before returning JSON?",
                List.of("To expose only safe fields and decouple API from persistence model",
                        "Because JPA cannot serialize objects", "To disable validation", "To avoid using HTTP"), 0));
        list.add(q("Which annotation enables cross-origin requests from a separate frontend origin?",
                List.of("@CrossOrigin", "@CorsFilter only in HTML", "@Entity", "@Table"), 0));
        list.add(q("Which injection style is generally preferred in Spring for required dependencies?",
                List.of("Constructor injection", "Field injection only", "Static injection", "Manual new in controller"), 0));
        list.add(q("What should a global exception handler return for API errors?",
                List.of("Consistent JSON error structure with message and status", "Raw stack trace to clients always",
                        "Empty 200 OK", "HTML error page only"), 0));
        return list;
    }
}
