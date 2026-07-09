package com.training.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 30 MCQs per level for Java skill tests (Pre-Intermediate, Intermediate, Advanced). */
public final class JavaQuizBank {

    private JavaQuizBank() {
    }

    public static List<Map<String, Object>> preIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(QuizQuestion.of("What is the correct syntax for a Java main method?",
                List.of("public static void main(String[] args)", "public void main(String args[])",
                        "static public void main(String[] args)", "void main(String[] args)"), 0));
        list.add(QuizQuestion.of("Which of these is NOT a valid Java identifier?",
                List.of("myVariable", "_private", "123number", "$dollar"), 2));
        list.add(QuizQuestion.of("What does the 'public' access modifier mean?",
                List.of("Accessible only within the same class", "Accessible within the same package",
                        "Accessible from anywhere", "Accessible only by subclasses"), 2));
        list.add(QuizQuestion.of("Which operator is used for string concatenation in Java?",
                List.of("+", "&", ".", "||"), 0));
        list.add(QuizQuestion.of("What is the default value of a boolean variable in Java?",
                List.of("true", "false", "null", "0"), 1));
        list.add(QuizQuestion.of("Which keyword is used to create an object in Java?",
                List.of("new", "create", "object", "instance"), 0));
        list.add(QuizQuestion.of("What is the size of an int data type in Java?",
                List.of("8 bits", "16 bits", "32 bits", "64 bits"), 2));
        list.add(QuizQuestion.of("Which of these is not a Java keyword?",
                List.of("class", "interface", "extends", "function"), 3));
        list.add(QuizQuestion.of("What does JVM stand for?",
                List.of("Java Virtual Machine", "Java Variable Memory", "Java Virtual Method",
                        "Java Variable Manager"), 0));
        list.add(QuizQuestion.of("Which symbol is used for single-line comments in Java?",
                List.of("//", "/*", "*/", "#"), 0));
        list.add(QuizQuestion.of("Which primitive type stores whole numbers without decimals?",
                List.of("int", "double", "boolean", "char"), 0));
        list.add(QuizQuestion.of("Which keyword is used for class inheritance in Java?",
                List.of("extends", "implements", "inherits", "super"), 0));
        list.add(QuizQuestion.of("Which loop is guaranteed to execute at least once?",
                List.of("do-while", "for", "while", "foreach"), 0));
        list.add(QuizQuestion.of("Which access modifier is the most restrictive?",
                List.of("private", "protected", "public", "default"), 0));
        list.add(QuizQuestion.of("What is the purpose of a package in Java?",
                List.of("Organize related classes and avoid naming conflicts",
                        "Compile source code faster", "Store database connections", "Run unit tests"), 0));
        list.add(QuizQuestion.of("Which primitive type is used for single Unicode characters?",
                List.of("char", "byte", "short", "String"), 0));
        list.add(QuizQuestion.of("Which keyword prevents a class from being subclassed?",
                List.of("final", "static", "abstract", "private"), 0));
        list.add(QuizQuestion.of("Which is a valid declaration of an int array?",
                List.of("int[] numbers = new int[5]", "int numbers[] = int(5)",
                        "array int numbers[5]", "int numbers = new array[5]"), 0));
        list.add(QuizQuestion.of("What does the import statement do?",
                List.of("Brings classes or packages into the current compilation unit",
                        "Copies bytecode into the JVM heap", "Starts the main method",
                        "Creates a new thread"), 0));
        list.add(QuizQuestion.of("Which wrapper class corresponds to the int primitive?",
                List.of("Integer", "Int", "Number", "Long"), 0));
        list.add(QuizQuestion.of("What is autoboxing in Java?",
                List.of("Automatic conversion from primitive to wrapper object",
                        "Automatic garbage collection", "Automatic method overloading",
                        "Automatic interface implementation"), 0));
        list.add(QuizQuestion.of("Which operator compares object references by default?",
                List.of("==", "equals()", "compareTo()", "instanceof"), 0));
        list.add(QuizQuestion.of("What is a constructor in Java?",
                List.of("A special method used to initialize a new object",
                        "A static utility method", "A method that cannot throw exceptions",
                        "A method that runs after main"), 0));
        list.add(QuizQuestion.of("Which keyword refers to the current object instance?",
                List.of("this", "self", "current", "super"), 0));
        list.add(QuizQuestion.of("What does the static keyword mean for a method?",
                List.of("It belongs to the class rather than a specific instance",
                        "It cannot be called from main", "It always runs in a new thread",
                        "It cannot access fields"), 0));
        list.add(QuizQuestion.of("Which syntax is used for multi-line comments?",
                List.of("/* comment */", "// comment //", "# comment #", "-- comment --"), 0));
        list.add(QuizQuestion.of("What file extension do compiled Java class files use?",
                List.of(".class", ".java", ".jar", ".exe"), 0));
        list.add(QuizQuestion.of("Which method is the entry point of a standalone Java application?",
                List.of("main", "start", "run", "init"), 0));
        list.add(QuizQuestion.of("Which keyword is used to implement an interface?",
                List.of("implements", "extends", "interface", "inherits"), 0));
        list.add(QuizQuestion.of("What is the default value of a reference type field?",
                List.of("null", "0", "false", "undefined"), 0));
        return list;
    }

    public static List<Map<String, Object>> intermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(QuizQuestion.of("What is method overloading in Java?",
                List.of("Having multiple methods with the same name but different parameters",
                        "Calling a method from another method", "Overriding a method in a subclass",
                        "Creating multiple instances of a method"), 0));
        list.add(QuizQuestion.of("Which collection class maintains insertion order?",
                List.of("HashSet", "HashMap", "LinkedHashSet", "TreeSet"), 2));
        list.add(QuizQuestion.of("What is the purpose of the 'finally' block?",
                List.of("To catch exceptions", "To execute cleanup code regardless of exceptions",
                        "To throw exceptions", "To ignore exceptions"), 1));
        list.add(QuizQuestion.of("Which interface should be implemented for custom sorting?",
                List.of("Runnable", "Comparable", "Serializable", "Cloneable"), 1));
        list.add(QuizQuestion.of("Which keyword is used to define a constant in Java?",
                List.of("final", "static", "const", "immutable"), 0));
        list.add(QuizQuestion.of("What does the 'instanceof' operator do?",
                List.of("Creates a new instance", "Checks type compatibility", "Invokes a method",
                        "Compares primitive values"), 1));
        list.add(QuizQuestion.of("What value does String.length() return?",
                List.of("Character count", "Byte size", "Hash code", "Memory address"), 0));
        list.add(QuizQuestion.of("Which exception is thrown when accessing an array out of bounds?",
                List.of("NullPointerException", "ArrayIndexOutOfBoundsException", "IllegalArgumentException",
                        "ClassCastException"), 1));
        list.add(QuizQuestion.of("What is the default value of an int instance variable?",
                List.of("0", "null", "false", "undefined"), 0));
        list.add(QuizQuestion.of("Which statement is used to stop a loop immediately?",
                List.of("continue", "break", "return", "stop"), 1));
        list.add(QuizQuestion.of("Which collection allows duplicate elements and maintains insertion order?",
                List.of("ArrayList", "HashSet", "TreeSet", "PriorityQueue"), 0));
        list.add(QuizQuestion.of("What is method overriding?",
                List.of("Providing a subclass-specific implementation of an inherited method",
                        "Defining two methods with the same name in one class",
                        "Calling a private method from outside the class",
                        "Creating a static copy of a method"), 0));
        list.add(QuizQuestion.of("Which keyword calls a superclass constructor or method?",
                List.of("super", "this", "base", "parent"), 0));
        list.add(QuizQuestion.of("What is encapsulation in OOP?",
                List.of("Hiding internal state and exposing controlled access through methods",
                        "Creating many subclasses", "Using only static methods",
                        "Storing all data in public fields"), 0));
        list.add(QuizQuestion.of("Which interface is implemented by classes that can be run on a thread?",
                List.of("Runnable", "Comparable", "Iterable", "Closeable"), 0));
        list.add(QuizQuestion.of("What does the throws keyword declare?",
                List.of("Checked exceptions a method may propagate",
                        "Exceptions that are always caught", "Runtime-only errors",
                        "Methods that cannot fail"), 0));
        list.add(QuizQuestion.of("Which Map implementation does NOT guarantee iteration order?",
                List.of("HashMap", "LinkedHashMap", "TreeMap", "SortedMap"), 0));
        list.add(QuizQuestion.of("What is polymorphism in Java?",
                List.of("One interface, many implementations or one reference, many forms",
                        "Using only primitive types", "Preventing inheritance",
                        "Compiling to native code"), 0));
        list.add(QuizQuestion.of("Which keyword skips the current loop iteration and continues?",
                List.of("continue", "break", "skip", "next"), 0));
        list.add(QuizQuestion.of("What is an abstract class?",
                List.of("A class that cannot be instantiated and may contain abstract methods",
                        "A class with only private constructors", "A class without fields",
                        "A class compiled to bytecode only"), 0));
        list.add(QuizQuestion.of("Which collection stores unique elements with no guaranteed order?",
                List.of("HashSet", "ArrayList", "LinkedList", "Vector"), 0));
        list.add(QuizQuestion.of("What does String.equals() compare?",
                List.of("Character sequence content", "Memory addresses only",
                        "Object hash codes only", "Class loaders"), 0));
        list.add(QuizQuestion.of("Which exception occurs when calling a method on a null reference?",
                List.of("NullPointerException", "IOException", "ClassNotFoundException",
                        "StackOverflowError"), 0));
        list.add(QuizQuestion.of("What is the purpose of an interface in Java?",
                List.of("Define a contract of methods a class must implement",
                        "Store static constants only", "Replace the need for classes",
                        "Compile source without a JVM"), 0));
        list.add(QuizQuestion.of("Which List implementation is best for frequent random access by index?",
                List.of("ArrayList", "LinkedList", "Stack", "Queue"), 0));
        list.add(QuizQuestion.of("What does the @Override annotation indicate?",
                List.of("The method replaces a superclass or interface method",
                        "The method is static", "The method cannot throw exceptions",
                        "The method is deprecated"), 0));
        list.add(QuizQuestion.of("Which block runs when a matching exception is thrown in try?",
                List.of("catch", "finally", "else", "switch"), 0));
        list.add(QuizQuestion.of("What is boxing in Java?",
                List.of("Converting a primitive value to its wrapper object",
                        "Packaging classes into JAR files", "Encrypting strings",
                        "Creating anonymous inner classes"), 0));
        list.add(QuizQuestion.of("Which collection is synchronized and legacy but thread-safe for lists?",
                List.of("Vector", "ArrayList", "LinkedList", "HashSet"), 0));
        list.add(QuizQuestion.of("What does immutability mean for String objects?",
                List.of("Their content cannot change after creation",
                        "They cannot be compared", "They are stored on the stack only",
                        "They cannot be passed to methods"), 0));
        return list;
    }

    public static List<Map<String, Object>> advanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(QuizQuestion.of("What is the difference between HashMap and ConcurrentHashMap?",
                List.of("HashMap is synchronized, ConcurrentHashMap is not",
                        "ConcurrentHashMap allows concurrent access, HashMap does not",
                        "They are identical", "HashMap is thread-safe, ConcurrentHashMap is not"), 1));
        list.add(QuizQuestion.of("What does the 'volatile' keyword ensure?",
                List.of("The variable cannot be changed", "Changes to the variable are visible to all threads",
                        "The variable is stored in volatile memory", "The variable cannot be garbage collected"), 1));
        list.add(QuizQuestion.of("Which design pattern uses a single instance shared across the application?",
                List.of("Factory", "Observer", "Singleton", "Decorator"), 2));
        list.add(QuizQuestion.of("What is the primary purpose of the 'transient' keyword?",
                List.of("To make a method thread-safe", "To exclude a field from serialization",
                        "To create a temporary variable", "To synchronize access"), 1));
        list.add(QuizQuestion.of("What does the 'finalize' method do?",
                List.of("Runs before object construction", "May run before garbage collection of an object",
                        "Makes a class immutable", "Prevents inheritance"), 1));
        list.add(QuizQuestion.of("Which Java construct is used to manage multiple threads?",
                List.of("ThreadPoolExecutor", "ArrayList", "HashMap", "Stream"), 0));
        list.add(QuizQuestion.of("What is the output of System.out.println(3 + 4 + \"5\");?",
                List.of("75", "345", "12", "Error"), 0));
        list.add(QuizQuestion.of("Which memory area stores method call frames in Java?",
                List.of("Heap", "Stack", "PermGen", "Code segment"), 1));
        list.add(QuizQuestion.of("What is a lambda expression used for?",
                List.of("Creating anonymous classes", "Defining a new data type",
                        "Providing inline implementation of functional interfaces", "Handling exceptions"), 2));
        list.add(QuizQuestion.of("What is the purpose of the 'try-with-resources' statement?",
                List.of("To catch runtime exceptions", "To automatically close resources",
                        "To declare variables", "To pause execution"), 1));
        list.add(QuizQuestion.of("What is the Java Stream API primarily used for?",
                List.of("Declarative processing of collections with map/filter/reduce operations",
                        "Low-level socket programming", "Compiling source files",
                        "Managing database transactions only"), 0));
        list.add(QuizQuestion.of("Which garbage collector generation typically holds long-lived objects?",
                List.of("Old generation", "Young generation", "Stack", "Program counter"), 0));
        list.add(QuizQuestion.of("What does synchronized do on a method?",
                List.of("Ensures only one thread executes the method on the same monitor at a time",
                        "Makes the method run faster", "Prevents exceptions",
                        "Converts the method to static"), 0));
        list.add(QuizQuestion.of("Which interface represents a function that takes one argument and returns a value?",
                List.of("Function", "Runnable", "Comparable", "Iterable"), 0));
        list.add(QuizQuestion.of("What is a deadlock?",
                List.of("Two or more threads blocked forever waiting on each other's locks",
                        "A method that never returns", "An uncaught runtime exception",
                        "A completed garbage collection cycle"), 0));
        list.add(QuizQuestion.of("What does Optional help prevent?",
                List.of("NullPointerException from absent values when used idiomatically",
                        "Memory leaks in all cases", "Checked exceptions",
                        "Compilation errors"), 0));
        list.add(QuizQuestion.of("Which collection is sorted by natural ordering of keys?",
                List.of("TreeMap", "HashMap", "LinkedHashMap", "Hashtable"), 0));
        list.add(QuizQuestion.of("What is the difference between fail-fast and fail-safe iterators?",
                List.of("Fail-fast throws ConcurrentModificationException; fail-safe works on a snapshot",
                        "Fail-safe always throws; fail-fast never throws",
                        "They are identical", "Fail-fast is only for arrays"), 0));
        list.add(QuizQuestion.of("What does the default keyword do in an interface (Java 8+)?",
                List.of("Provides a concrete method implementation in the interface",
                        "Makes all methods private", "Prevents inheritance",
                        "Marks fields as constants"), 0));
        list.add(QuizQuestion.of("Which JVM component executes bytecode?",
                List.of("Execution engine", "Class loader only", "Garbage collector only",
                        "JIT compiler only"), 0));
        list.add(QuizQuestion.of("What is escape analysis used for in the JVM?",
                List.of("Determining if objects can be stack-allocated or synchronized elided",
                        "Parsing XML documents", "Encrypting strings",
                        "Resolving DNS names"), 0));
        list.add(QuizQuestion.of("Which stream operation produces a single aggregated result?",
                List.of("reduce", "map", "filter", "peek"), 0));
        list.add(QuizQuestion.of("What is the happens-before relationship in Java concurrency?",
                List.of("A guarantee that memory written by one action is visible to another",
                        "The order methods appear in source code", "A database isolation level",
                        "A Git merge strategy"), 0));
        list.add(QuizQuestion.of("Which annotation marks a method as deprecated?",
                List.of("@Deprecated", "@Override", "@SuppressWarnings", "@FunctionalInterface"), 0));
        list.add(QuizQuestion.of("What is a ClassLoader responsible for?",
                List.of("Loading class bytecode into the JVM", "Executing SQL queries",
                        "Rendering HTML templates", "Managing HTTP sessions"), 0));
        list.add(QuizQuestion.of("Which pattern decouples an abstraction from its implementation?",
                List.of("Bridge", "Singleton", "Prototype", "Flyweight"), 0));
        list.add(QuizQuestion.of("What does CompletableFuture enable?",
                List.of("Asynchronous computation with composable callbacks",
                        "Synchronous-only file I/O", "Manual memory management",
                        "Direct bytecode editing"), 0));
        list.add(QuizQuestion.of("Which modifier makes a nested class tied to an outer class instance?",
                List.of("Inner (non-static) class", "Static nested class", "Local class only",
                        "Anonymous enum"), 0));
        list.add(QuizQuestion.of("What is the metaspace used for in modern JVMs?",
                List.of("Storing class metadata", "Storing object instances",
                        "Storing local variables", "Storing thread stacks"), 0));
        list.add(QuizQuestion.of("Which collection offers O(1) average-time get/put for unsynchronized maps?",
                List.of("HashMap", "TreeMap", "LinkedList", "CopyOnWriteArrayList"), 0));
        return list;
    }
}
