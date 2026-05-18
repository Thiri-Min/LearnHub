package com.training.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Quiz questions aligned with tech.html skill sets (Java & Python × 3 levels).
 */
public final class TechQuizCatalog {

    private static final Map<String, Map<String, List<Map<String, Object>>>> QUESTIONS = new LinkedHashMap<>();

    static {
        registerJava();
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
}
