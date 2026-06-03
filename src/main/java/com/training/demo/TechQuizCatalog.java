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
    }

    private TechQuizCatalog() {
    }

    public static final int QUIZ_QUESTION_COUNT = 10;
    /** Question bank size per level for subjects that randomize across attempts (e.g. DSA, Git). */
    public static final int QUIZ_BANK_SIZE = 15;
    public static final int DSA_MAX_ATTEMPTS = 3;

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
        question.put("answer", options.indexOf(correctText));
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
}
