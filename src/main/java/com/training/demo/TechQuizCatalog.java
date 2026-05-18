package com.training.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Quiz questions aligned with tech.html skill sets (Java, SQL, Python × 3 levels).
 */
public final class TechQuizCatalog {

    private static final Map<String, Map<String, List<Map<String, Object>>>> QUESTIONS = new LinkedHashMap<>();

    static {
        registerJava();
        registerSql();
        registerPython();
    }

    private TechQuizCatalog() {
    }

    public static List<Map<String, Object>> getQuestions(String subject, String level) {
        Map<String, List<Map<String, Object>>> byLevel = QUESTIONS.get(subject);
        if (byLevel == null) {
            return genericQuestions(subject, level);
        }
        List<Map<String, Object>> set = byLevel.get(level);
        if (set == null || set.isEmpty()) {
            return genericQuestions(subject, level);
        }
        return set;
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
        list.add(q("Which keyword is used to handle exceptions in Python?",
                List.of("try", "catch", "except", "error"), 2));
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
        list.add(q("Which SQL statement is used to retrieve data from a table?",
                List.of("SELECT", "GET", "FETCH", "READ"), 0));
        list.add(q("What does a PRIMARY KEY do?",
                List.of("Uniquely identifies each row", "Encrypts the table", "Sorts rows automatically",
                        "Links two tables"), 0));
        list.add(q("Which clause filters rows in a SELECT query?",
                List.of("WHERE", "FILTER", "HAVING", "ORDER"), 0));
        list.add(q("Which statement adds new rows to a table?",
                List.of("INSERT", "ADD", "CREATE ROW", "APPEND"), 0));
        list.add(q("What does COUNT(*) return?",
                List.of("The number of rows", "The sum of all columns", "The largest value",
                        "The table size in MB"), 0));
        list.add(q("Which keyword removes duplicate rows from a result set?",
                List.of("DISTINCT", "UNIQUE", "ONLY", "SINGLE"), 0));
        list.add(q("Which statement modifies existing data?",
                List.of("UPDATE", "CHANGE", "MODIFY", "ALTER ROW"), 0));
        list.add(q("Which clause sorts query results?",
                List.of("ORDER BY", "SORT BY", "GROUP BY", "ARRANGE"), 0));
        list.add(q("What does DELETE do?",
                List.of("Removes rows from a table", "Drops the entire table", "Clears the database",
                        "Hides columns"), 0));
        list.add(q("In SQL, NULL means:",
                List.of("Unknown or missing value", "Zero", "Empty string", "False"), 0));
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
}
