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
        registerAi();
        registerFullstack();
    }

    private TechQuizCatalog() {
    }

    public static final int QUIZ_QUESTION_COUNT = 10;
    /** Question bank size per level for subjects that randomize across attempts (e.g. DSA, Git). */
    public static final int QUIZ_BANK_SIZE = 80;
    public static final int DSA_MAX_ATTEMPTS = 3;
    public static final int FRONTEND_MAX_ATTEMPTS = 5;
    public static final int BASE_FRAMEWORK_MAX_ATTEMPTS = 5;
    public static final int AI_MAX_ATTEMPTS = 5;
    public static final int FULLSTACK_MAX_ATTEMPTS = 5;

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
        Object correctOption = question.get("correctOption");
        if (correctOption != null) {
            copy.put("correctOption", correctOption);
        } else if (question.get("answer") instanceof Integer idx) {
            @SuppressWarnings("unchecked")
            List<String> opts = (List<String>) question.get("options");
            if (opts != null && idx >= 0 && idx < opts.size()) {
                copy.put("correctOption", opts.get(idx));
            }
        }
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
        question.put("correctOption", correctText);
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
        levels.put("Pre-Intermediate", JavaQuizBank.preIntermediate());
        levels.put("Intermediate", JavaQuizBank.intermediate());
        levels.put("Advanced", JavaQuizBank.advanced());
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

    private static void registerAi() {
        Map<String, List<Map<String, Object>>> levels = new LinkedHashMap<>();
        levels.put("Pre-Intermediate", AiQuizBank.preIntermediate());
        levels.put("Intermediate", AiQuizBank.intermediate());
        levels.put("Advanced", AiQuizBank.advanced());
        QUESTIONS.put("AI", levels);
    }

    private static void registerFullstack() {
        Map<String, List<Map<String, Object>>> levels = new LinkedHashMap<>();
        levels.put("Pre-Intermediate", FullstackQuizBank.preIntermediate());
        levels.put("Intermediate", FullstackQuizBank.intermediate());
        levels.put("Advanced", FullstackQuizBank.advanced());
        QUESTIONS.put("Fullstack", levels);
    }

    private static Map<String, Object> q(String question, List<String> options, int answer) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("question", question);
        item.put("options", options);
        item.put("answer", answer);
        return item;
    }

    private static List<Map<String, Object>> pythonPreIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(q("What is the correct way to declare a function in Python?",
                List.of("function myFunc():", "def myFunc():", "func myFunc():", "declare myFunc():"), 1));
        list.add(q("Which of these is a valid Python list?",
                List.of("{1, 2, 3}", "[1, 2, 3]", "(1, 2, 3)", "<1, 2, 3>"), 1));
        list.add(q("What is the output of print(type(3.14))?",
                List.of("<class 'float'>", "<class 'int'>", "<class 'str'>", "<class 'double'>"), 0));
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
        list.add(q("Which property sets the JDBC driver class for the datasource?",
                List.of("spring.datasource.driver-class-name", "spring.jpa.driver", "jdbc.class", "db.driver"), 0));
        list.add(q("What format do REST APIs commonly use to exchange data with clients?",
                List.of("JSON", "HTML only", "Binary EXE files", "Word documents"), 0));
        list.add(q("Which property changes the HTTP port of a Spring Boot application?",
                List.of("server.port", "spring.port", "http.port", "tomcat.port"), 0));
        list.add(q("Which layer should handle HTTP requests and return responses in a Spring app?",
                List.of("Controller layer", "Repository layer", "Database layer", "CSS layer"), 0));
        list.add(q("What does spring.jpa.hibernate.ddl-auto=create-drop typically do?",
                List.of("Creates schema on startup and drops it on shutdown (dev/test)", "Never changes the database",
                        "Encrypts all tables", "Disables JPA"), 0));
        list.add(q("Which annotation is a generic Spring-managed component (broader than @Service)?",
                List.of("@Component", "@Table", "@Query", "@GetMapping"), 0));
        list.add(q("Which in-memory database is often used for quick Spring Boot prototypes?",
                List.of("H2", "MongoDB only", "Redis only", "SQLite driver only"), 0));
        list.add(q("What is the role of DispatcherServlet in Spring MVC?",
                List.of("Routes incoming HTTP requests to the correct controller method", "Generates SQL queries",
                        "Compiles Java source code", "Stores JWT tokens"), 0));
        list.add(q("Which file usually lists Spring Boot dependencies and plugins?",
                List.of("pom.xml (Maven) or build.gradle (Gradle)", "index.html", "application.js",
                        "database.sql only"), 0));
        list.add(q("Besides application.properties, Spring Boot can load config from:",
                List.of("application.yml or application.yaml", "index.html only", "style.css only",
                        "package-lock.json"), 0));
        list.add(q("What does CRUD stand for in database-backed APIs?",
                List.of("Create, Read, Update, Delete", "Copy, Run, Upload, Download",
                        "Connect, Route, Use, Deploy", "Cache, Render, Undo, Debug"), 0));
        list.add(q("Which HTTP status code usually means a GET request succeeded?",
                List.of("200 OK", "201 Created", "204 No Content", "400 Bad Request"), 0));
        list.add(q("Which Accept header value tells the server the client prefers JSON?",
                List.of("application/json", "text/html", "image/png", "multipart/form-data"), 0));
        list.add(q("What is the Spring IoC container responsible for?",
                List.of("Creating and wiring Spring beans (dependency injection)", "Writing SQL queries only",
                        "Rendering HTML templates only", "Compiling TypeScript"), 0));
        list.add(q("Which method boots a Spring Boot application from the main class?",
                List.of("SpringApplication.run(...)", "SpringBoot.start(...)", "Application.launch(...)",
                        "Main.execute(...)"), 0));
        list.add(q("@RestController is equivalent to combining which two annotations?",
                List.of("@Controller and @ResponseBody", "@Entity and @Table", "@Service and @Repository",
                        "@GetMapping and @PostMapping"), 0));
        list.add(q("Which starter is commonly used to write unit tests in Spring Boot?",
                List.of("spring-boot-starter-test", "spring-boot-starter-web only", "spring-boot-starter-mail",
                        "spring-boot-starter-jdbc only"), 0));
        list.add(q("In a relational database, what does a foreign key enforce?",
                List.of("A link between rows in related tables", "Automatic JSON serialization",
                        "HTTP routing rules", "JWT expiration"), 0));
        list.add(q("Why do many Spring REST APIs use an /api prefix in URLs?",
                List.of("To separate API routes from web pages or static resources", "Because JPA requires it",
                        "To disable validation", "To connect to MySQL only"), 0));
        list.add(q("Which dependency is commonly used to connect Spring Boot to PostgreSQL?",
                List.of("org.postgresql:postgresql", "mysql-connector-j only", "h2 only", "mongodb-driver only"), 0));
        list.add(q("Which annotation marks a class that defines Spring @Bean methods?",
                List.of("@Configuration", "@Entity", "@Repository", "@GetMapping"), 0));
        list.add(q("What does each JPA entity instance typically represent?",
                List.of("One row in a database table", "One HTTP request", "One HTML page", "One JWT token"), 0));
        list.add(q("Which property file location does Spring Boot load automatically from src/main/resources?",
                List.of("application.properties or application.yml", "pom.xml", "README.md", "index.html"), 0));
        list.add(q("What does ORM stand for?",
                List.of("Object-Relational Mapping", "Online Resource Manager", "Open Runtime Module",
                        "Object Request Method"), 0));
        list.add(q("Which HTTP methods are considered safe because they should not change server data?",
                List.of("GET (and HEAD)", "POST and PUT", "DELETE and PATCH", "POST only"), 0));
        list.add(q("Which Spring Boot starter adds health and monitoring endpoints such as /actuator/health?",
                List.of("spring-boot-starter-actuator", "spring-boot-starter-mail", "spring-boot-starter-aop",
                        "spring-boot-starter-json"), 0));
        list.add(q("What is the main purpose of a primary key in a relational table?",
                List.of("Uniquely identify each row", "Store passwords securely", "Speed up HTTP routing",
                        "Serialize objects to JSON"), 0));
        list.add(q("Which annotation on @SpringBootApplication enables automatic component scanning?",
                List.of("@ComponentScan", "@EntityScan only", "@WebMvcTest", "@Query"), 0));
        list.add(q("In Maven, which parent POM typically manages Spring Boot dependency versions?",
                List.of("spring-boot-starter-parent", "spring-boot-starter-web", "maven-compiler-plugin only",
                        "junit-jupiter"), 0));
        list.add(q("What does spring.jpa.hibernate.ddl-auto=validate do?",
                List.of("Checks that the database schema matches entities without changing it",
                        "Drops all tables on startup", "Creates tables and deletes them on shutdown",
                        "Disables Hibernate completely"), 0));
        list.add(q("Which Maven dependency scope is typical for spring-boot-starter-test?",
                List.of("test", "compile", "provided", "runtime only"), 0));
        list.add(q("In REST, a URI is best described as:",
                List.of("An identifier for a resource (e.g. /api/courses/5)", "Only the domain name of a server",
                        "A Java class name", "A database column name"), 0));
        list.add(q("Which annotation can map a controller method to multiple HTTP methods on the same path?",
                List.of("@RequestMapping", "@GetMapping only", "@Entity", "@Table"), 0));
        list.add(q("Which Java type is commonly used for auto-generated primary key fields in JPA entities?",
                List.of("Long", "boolean", "char", "void"), 0));
        list.add(q("Why add an index to a frequently searched database column?",
                List.of("To improve query lookup performance", "To encrypt the column", "To disable foreign keys",
                        "To convert rows to JSON"), 0));
        list.add(q("Which HTTP method is generally NOT idempotent (repeating it may create multiple resources)?",
                List.of("POST", "GET", "PUT", "DELETE"), 0));
        list.add(q("Which property sets the logical application name used in logs and monitoring?",
                List.of("spring.application.name", "spring.boot.name", "app.title", "server.name"), 0));
        list.add(q("What is database normalization mainly intended to reduce?",
                List.of("Redundant and duplicated data", "HTTP response size", "JWT token length",
                        "Number of REST endpoints"), 0));
        list.add(q("Which annotation declares a method that returns an object managed by the Spring container?",
                List.of("@Bean", "@Entity", "@GetMapping", "@Column"), 0));
        list.add(q("In GET /api/courses?page=2&size=10, what are page and size?",
                List.of("Query parameters", "Path variables", "Request headers", "Entity fields"), 0));
        list.add(q("What is a common purpose of spring-boot-devtools in development?",
                List.of("Automatic restart when classpath files change", "Encrypt database passwords",
                        "Generate JWT tokens", "Replace the service layer"), 0));
        list.add(q("Which HTTP status code indicates the server encountered an unexpected error?",
                List.of("500 Internal Server Error", "200 OK", "201 Created", "204 No Content"), 0));
        list.add(q("What is the main benefit of the repository pattern in Spring Data JPA?",
                List.of("Abstracts data access behind a clean interface", "Replaces the need for a database",
                        "Renders HTML templates", "Signs JWT tokens"), 0));
        list.add(q("How does JPA relate to JDBC?",
                List.of("JPA is a higher-level ORM API; JDBC is lower-level SQL access", "They are identical",
                        "JDBC replaces Hibernate", "JPA cannot use relational databases"), 0));
        list.add(q("Where does an HTTP client typically send JSON payload data?",
                List.of("Request body", "URL path only", "DNS record", "Database trigger"), 0));
        list.add(q("Which annotation on a controller method returns the method result directly as the HTTP body?",
                List.of("@ResponseBody", "@Entity", "@Repository", "@Table"), 0));
        list.add(q("Which SQL data type is commonly used for short text such as a course title?",
                List.of("VARCHAR", "BLOB", "BOOLEAN only", "TIMESTAMP only"), 0));
        list.add(q("In a one-to-many relationship, one parent record can relate to:",
                List.of("Many child records", "Exactly one child only", "No other records", "Only HTTP headers"), 0));
        list.add(q("Which HTTP status means the requested resource was not found?",
                List.of("404 Not Found", "200 OK", "201 Created", "101 Switching Protocols"), 0));
        list.add(q("Why use layered architecture (Controller → Service → Repository)?",
                List.of("Separates concerns and improves testability", "Eliminates the need for JSON",
                        "Forces all logic into one class", "Disables database connections"), 0));
        list.add(q("Which folder under src/main/resources holds application.properties by convention?",
                List.of("src/main/resources (classpath root)", "src/main/java only", "src/test/java",
                        "public/ on the web server"), 0));
        list.add(q("Which Spring Boot feature auto-configures beans based on classpath dependencies?",
                List.of("Spring Boot auto-configuration", "Manual XML wiring only", "HTML templating only",
                        "Git version control"), 0));
        list.add(q("When designing REST URLs, which style is recommended for multi-word resource names?",
                List.of("Plural nouns with consistent casing (e.g. /api/order-items)", "Verb-based paths (/getOrderItems)",
                        "SQL queries in the URL", "Java method names as paths"), 0));
        list.add(q("Which HTTP status code means a resource was successfully created?",
                List.of("201 Created", "200 OK", "204 No Content", "404 Not Found"), 0));
        list.add(q("Which Spring annotation combines @Configuration and @EnableAutoConfiguration?",
                List.of("@SpringBootApplication", "@Entity", "@RestController only", "@Table"), 0));
        list.add(q("What is the default embedded servlet container port unless changed in config?",
                List.of("8080", "443", "3306", "5432"), 0));
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
        list.add(q("Which repository method returns a paginated result using Pageable?",
                List.of("findAll(Pageable pageable)", "paginateAll()", "getPage()", "listPaged()"), 0));
        list.add(q("Which JPA annotation defines a one-to-one relationship between two entities?",
                List.of("@OneToOne", "@OneToMany", "@Table", "@Query"), 0));
        list.add(q("What does FetchType.LAZY mean for a JPA association?",
                List.of("Related data is loaded only when accessed", "Data is always loaded immediately",
                        "The association is read-only", "The field cannot be null"), 0));
        list.add(q("Which annotation marks a repository method that executes an UPDATE or DELETE query?",
                List.of("@Modifying", "@UpdateOnly", "@SqlDelete", "@Transactional only on entity"), 0));
        list.add(q("Which @Column attribute prevents null values at the database column level?",
                List.of("nullable = false", "unique = false", "lazy = true", "fetch = eager"), 0));
        list.add(q("Which repository method returns the total number of entities?",
                List.of("count()", "size()", "total()", "length()"), 0));
        list.add(q("Which JPA annotation enables optimistic locking with a version field?",
                List.of("@Version", "@Lock", "@Secure", "@Revision"), 0));
        list.add(q("Which Spring Data feature supports sorting with Sort.by(\"title\")?",
                List.of("Passing Sort into repository query methods", "Using @SortMapping on entities only",
                        "Writing HTML sort buttons only", "Calling ORDER BY in controllers"), 0));
        list.add(q("What does cascade = CascadeType.ALL typically do on a parent-child relationship?",
                List.of("Propagates persist/merge/remove operations to related entities", "Disables foreign keys",
                        "Encrypts child records", "Creates read-only children"), 0));
        list.add(q("Which annotation maps an HTTP PATCH request for partial updates?",
                List.of("@PatchMapping", "@PutMapping only", "@PostMapping", "@DeleteMapping"), 0));
        list.add(q("Which JPA annotation defines a many-to-many relationship with a join table?",
                List.of("@ManyToMany", "@OneToOne only", "@Column only", "@Id only"), 0));
        list.add(q("What does FetchType.EAGER mean for a JPA association?",
                List.of("Related data is loaded immediately with the parent entity", "Data is never loaded",
                        "The field is excluded from JSON", "The relationship is read-only"), 0));
        list.add(q("In a bidirectional @OneToMany relationship, mappedBy belongs on:",
                List.of("The inverse (non-owning) side of the relationship", "The database primary key only",
                        "The controller class", "The DTO class"), 0));
        list.add(q("Which Optional method throws an exception when no entity is found?",
                List.of("orElseThrow()", "orElse(null) only", "isEmpty() only", "getClass()"), 0));
        list.add(q("Which @Column attribute enforces uniqueness at the database level?",
                List.of("unique = true", "nullable = true", "lazy = true", "fetch = eager"), 0));
        list.add(q("Which repository method saves multiple entities in one call?",
                List.of("saveAll(entities)", "saveMany()", "insertBatch() only", "persistAllSQL()"), 0));
        list.add(q("Which ResponseEntity factory method builds a 404 Not Found response?",
                List.of("ResponseEntity.notFound().build()", "ResponseEntity.ok().build()",
                        "ResponseEntity.created(null)", "ResponseEntity.accepted()"), 0));
        list.add(q("What does @Transactional(readOnly = true) suggest to the persistence provider?",
                List.of("The transaction should not modify data (optimization hint)", "The method must delete rows",
                        "HTTP caching is disabled", "JWT validation is skipped"), 0));
        list.add(q("Which derived query method ignores case when matching a title?",
                List.of("findByTitleIgnoreCase(...)", "findByTitleCase(...)", "findTitleExact(...)",
                        "getTitleEquals(...)"), 0));
        list.add(q("Which HTTP status fits invalid client input such as a negative price?",
                List.of("400 Bad Request", "201 Created", "204 No Content", "302 Found"), 0));
        list.add(q("What is the owning side of a JPA association responsible for?",
                List.of("Managing the foreign key column in the database", "Rendering HTML templates",
                        "Signing JWT tokens", "Parsing JSON only"), 0));
        list.add(q("Which annotation specifies the join table for a @ManyToMany relationship?",
                List.of("@JoinTable", "@JoinColumn only", "@PathVariable", "@RequestParam"), 0));
        list.add(q("When should a service call repository.deleteById(id) instead of only returning 200?",
                List.of("After confirming the record exists or handling not-found appropriately",
                        "Before checking if the id exists", "Only for GET requests", "Never in REST APIs"), 0));
        list.add(q("Which JPA lifecycle callback runs before an entity is first persisted?",
                List.of("@PrePersist", "@PostRemove", "@GetMapping", "@Autowired"), 0));
        list.add(q("What is the N+1 query problem in JPA?",
                List.of("One query loads parents and N extra queries load each child collection",
                        "One query deletes N tables", "N controllers call one repository", "N JWT tokens per request"), 0));
        list.add(q("Which @Query attribute runs native SQL instead of JPQL?",
                List.of("nativeQuery = true", "jpql = false only", "sqlMode = raw", "useJdbc = true"), 0));
        list.add(q("Which Spring Data type combines pagination with a list of content and page metadata?",
                List.of("Page<T>", "Optional<T> only", "Stream<T> only", "Map<String, Object> only"), 0));
        list.add(q("How do you pass sorting to a repository method that accepts Pageable?",
                List.of("PageRequest.of(page, size, Sort.by(\"title\"))", "Sort.deleteAll()", "@Sort on entity only",
                        "ORDER BY in the controller"), 0));
        list.add(q("Which annotation maps a Java enum field to a database column?",
                List.of("@Enumerated", "@EnumColumn", "@JavaEnum", "@Type"), 0));
        list.add(q("What does orphanRemoval = true typically do on a parent-child association?",
                List.of("Deletes child entities removed from the parent's collection", "Prevents DELETE requests",
                        "Encrypts child rows", "Disables lazy loading"), 0));
        list.add(q("Which annotation can reduce N+1 queries by fetching associations in one query?",
                List.of("@EntityGraph", "@JsonIgnore", "@CrossOrigin", "@Valid"), 0));
        list.add(q("Which ResponseEntity factory returns 201 Created with a Location-style response?",
                List.of("ResponseEntity.status(HttpStatus.CREATED).body(dto)", "ResponseEntity.notFound().build()",
                        "ResponseEntity.noContent().build() only", "ResponseEntity.ok(null)"), 0));
        list.add(q("Which annotation binds an HTTP request header to a method parameter?",
                List.of("@RequestHeader", "@PathVariable", "@RequestBody", "@Column"), 0));
        list.add(q("Which derived query method finds courses by both title and category?",
                List.of("findByTitleAndCategory(...)", "findTitleOrCategory(...)", "getByTitleCategorySQL()",
                        "searchTitleCategory()"), 0));
        list.add(q("What does repository.deleteById(id) return in Spring Data JPA?",
                List.of("void", "Optional<Entity>", "boolean always", "Page<Entity>"), 0));
        list.add(q("What does EntityManager.flush() do?",
                List.of("Synchronizes persistence context changes to the database immediately",
                        "Clears all entities from memory", "Deletes the database schema",
                        "Validates JWT tokens"), 0));
        list.add(q("What does EntityManager.clear() do?",
                List.of("Detaches all managed entities from the persistence context", "Commits a transaction",
                        "Creates a new database connection", "Generates OpenAPI docs"), 0));
        list.add(q("What is EntityManager used for in JPA?",
                List.of("Managing entity lifecycle and executing queries", "Rendering Thymeleaf templates",
                        "Parsing JSON in the browser", "Configuring Tomcat ports"), 0));
        list.add(q("Which annotations model a reusable group of columns embedded in an entity?",
                List.of("@Embeddable and @Embedded", "@Controller and @Service", "@GetMapping and @PostMapping",
                        "@Query and @Modifying"), 0));
        list.add(q("Which mapping attribute specifies the media type a controller method produces?",
                List.of("produces = \"application/json\"", "consumes = \"text/html\" only", "format = xml",
                        "media = binary"), 0));
        list.add(q("Which mapping attribute specifies the request content type a method accepts?",
                List.of("consumes = \"application/json\"", "produces = \"text/html\" only", "accept = xml",
                        "body = raw"), 0));
        list.add(q("Which annotation formats a request parameter as a date in a controller method?",
                List.of("@DateTimeFormat", "@Email", "@NotBlank", "@Version"), 0));
        list.add(q("Which annotation formats date/time fields during JSON serialization?",
                List.of("@JsonFormat", "@Table", "@JoinColumn", "@Modifying"), 0));
        list.add(q("In a bidirectional relationship, why add helper methods on both sides?",
                List.of("To keep both sides of the association in sync", "To replace @Transactional",
                        "To skip validation", "To avoid using repositories"), 0));
        list.add(q("Which JPA lock mode requests a pessimistic write lock on selected rows?",
                List.of("LockModeType.PESSIMISTIC_WRITE", "FetchType.LAZY", "CascadeType.ALL", "GenerationType.IDENTITY"), 0));
        list.add(q("Which property formats SQL logged by Hibernate for easier reading?",
                List.of("spring.jpa.properties.hibernate.format_sql=true", "spring.sql.pretty=true",
                        "database.log.format=all", "hibernate.print.json=true"), 0));
        list.add(q("Which HTTP status indicates a redirect to another URL?",
                List.of("302 Found", "201 Created", "204 No Content", "409 Conflict"), 0));
        list.add(q("Which interface extends JpaRepository and adds pagination support?",
                List.of("PagingAndSortingRepository", "HttpServlet", "UserDetailsService", "PasswordEncoder"), 0));
        list.add(q("What does @Transactional(propagation = Propagation.REQUIRES_NEW) create?",
                List.of("A new independent transaction even if one already exists", "No transaction at all",
                        "A read-only HTTP cache", "A JWT refresh token"), 0));
        list.add(q("Which repository method deletes all entities in a single batch operation?",
                List.of("deleteAllInBatch()", "removeEveryRowSQL()", "truncateViaController()", "dropSchema()"), 0));
        list.add(q("Which JPA annotation marks a field updated automatically on each entity update?",
                List.of("@PreUpdate (lifecycle callback)", "@PostPersist only", "@GetMapping", "@RequestParam"), 0));
        list.add(q("When returning a list from GET /api/courses?category=java, category is bound with:",
                List.of("@RequestParam", "@PathVariable", "@RequestBody", "@Id"), 0));
        list.add(q("Which Optional method supplies a default value when no entity is present?",
                List.of("orElse(defaultValue)", "orElseThrow() only", "isPresent() only", "getClass()"), 0));
        list.add(q("Which Spring Data method returns the first entity matching a sort order?",
                List.of("findFirstBy...OrderBy... (derived query)", "getOneSQL()", "firstOrNull()", "head()"), 0));
        list.add(q("What does @CreatedDate (Spring Data auditing) typically store?",
                List.of("Timestamp when the entity was first saved", "JWT expiration time",
                        "HTTP response code", "Database port number"), 0));
        list.add(q("Which HTTP method should be idempotent when updating a full resource?",
                List.of("PUT", "POST", "PATCH only always", "CONNECT"), 0));
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
        list.add(q("Which starter adds Bean Validation support for DTOs?",
                List.of("spring-boot-starter-validation", "spring-boot-starter-json", "spring-boot-starter-mail",
                        "spring-boot-starter-thymeleaf"), 0));
        list.add(q("Which validation annotation checks that a numeric field is within a min/max range?",
                List.of("@Min / @Max (or @DecimalMin / @DecimalMax)", "@Range only in HTML", "@Between",
                        "@Limit"), 0));
        list.add(q("What is the main purpose of BCrypt in a Spring Security login flow?",
                List.of("Hash passwords securely before storing them", "Encrypt JWT tokens in the browser",
                        "Compress JSON responses", "Validate SQL syntax"), 0));
        list.add(q("Which annotation restricts a controller method to users with a specific role?",
                List.of("@PreAuthorize(\"hasRole('ADMIN')\")", "@GetMapping only", "@Entity", "@Table"), 0));
        list.add(q("Why are REST APIs described as stateless?",
                List.of("Each request contains all info needed; server does not store client session between calls",
                        "They never use databases", "They cannot return JSON", "They run without HTTP"), 0));
        list.add(q("Which tool is recommended for production database schema changes instead of ddl-auto=update?",
                List.of("Flyway or Liquibase migrations", "Manual DROP DATABASE", "Editing entities only",
                        "Deleting application.properties"), 0));
        list.add(q("What does @Email validate on a DTO field?",
                List.of("That the string follows a valid email format", "That the email exists in the database",
                        "That the user is logged in", "That SMTP is configured"), 0));
        list.add(q("Which HTTP status indicates the client is authenticated but not allowed to perform the action?",
                List.of("403 Forbidden", "401 Unauthorized", "404 Not Found", "201 Created"), 0));
        list.add(q("What is authentication vs authorization?",
                List.of("Authentication verifies who you are; authorization decides what you can do",
                        "They mean the same thing", "Authorization happens before login only",
                        "Authentication is only for databases"), 0));
        list.add(q("Which approach helps keep controllers thin and testable?",
                List.of("Move business logic into the service layer", "Put SQL inside @RestController methods",
                        "Return entities directly without services", "Disable validation"), 0));
        list.add(q("Which exception is commonly handled when @Valid fails on a request DTO?",
                List.of("MethodArgumentNotValidException", "FileNotFoundException", "OutOfMemoryError",
                        "ClassNotFoundException"), 0));
        list.add(q("What is the difference between a Request DTO and a Response DTO?",
                List.of("Request DTO carries input from client; Response DTO shapes output to client",
                        "They must be identical classes", "Only entities can be used", "DTOs replace databases"), 0));
        list.add(q("Which HTTP status should be returned when the user is not authenticated?",
                List.of("401 Unauthorized", "403 Forbidden", "404 Not Found", "201 Created"), 0));
        list.add(q("Which HTTP status is appropriate for unexpected server failures?",
                List.of("500 Internal Server Error", "200 OK", "204 No Content", "301 Moved Permanently"), 0));
        list.add(q("What does a CORS preflight request typically use as the HTTP method?",
                List.of("OPTIONS", "GET", "POST", "DELETE"), 0));
        list.add(q("Which validation annotation limits the maximum length of a string field?",
                List.of("@Size(max = ...)", "@Length only in HTML", "@MaxLength on entities only", "@Count"), 0));
        list.add(q("Why should passwords never be returned in API response DTOs?",
                List.of("Security risk — credentials must not be exposed to clients", "JSON cannot serialize strings",
                        "JPA forbids String fields", "It makes SQL slower only"), 0));
        list.add(q("Which layer should NOT contain SQL or persistence logic?",
                List.of("Controller layer", "Repository layer", "Entity mapping", "Database"), 0));
        list.add(q("What is a common purpose of API versioning such as /api/v1/courses?",
                List.of("Allow breaking changes without disrupting existing clients", "Improve database indexes only",
                        "Replace JWT authentication", "Disable validation"), 0));
        list.add(q("Which annotation can hide a sensitive entity field from JSON serialization?",
                List.of("@JsonIgnore", "@Hide", "@Secret", "@Password"), 0));
        list.add(q("What does a refresh token typically allow in JWT-based auth?",
                List.of("Obtain a new access token without logging in again", "Delete database tables",
                        "Skip service-layer validation", "Compile Java code"), 0));
        list.add(q("Which Spring Security class is often used to encode user passwords?",
                List.of("BCryptPasswordEncoder", "JsonPasswordEncoder", "HtmlPasswordEncoder", "SqlPasswordEncoder"), 0));
        list.add(q("When mapping entity to response DTO manually, where should that mapping usually live?",
                List.of("Service layer or dedicated mapper component", "Database trigger", "HTML template",
                        "Browser JavaScript only"), 0));
        list.add(q("Which principle says each layer should have one clear responsibility?",
                List.of("Single Responsibility Principle", "Random Placement Principle",
                        "Controller-Only Principle", "No-Repository Principle"), 0));
        list.add(q("What is the difference between @ExceptionHandler on a method and @RestControllerAdvice?",
                List.of("@RestControllerAdvice applies handlers globally across controllers; @ExceptionHandler is local",
                        "They are identical", "@ExceptionHandler only works for HTML", "@RestControllerAdvice disables JSON"), 0));
        list.add(q("Which RFC-style structure helps APIs return machine-readable error details?",
                List.of("ProblemDetail (RFC 7807)", "HTML table layout", "Raw stack trace only", "JWT payload"), 0));
        list.add(q("Which tools are commonly used to map Entity objects to DTOs automatically?",
                List.of("MapStruct or ModelMapper", "Git or SVN", "Tomcat or Jetty", "BCrypt only"), 0));
        list.add(q("In Spring Security 6, which bean type configures HTTP security rules?",
                List.of("SecurityFilterChain", "JpaRepository", "EntityManagerFactory", "DispatcherServlet only"), 0));
        list.add(q("Where is a JWT usually validated in a Spring Security setup?",
                List.of("In a filter (e.g. JwtAuthenticationFilter) before the controller", "Inside every @Entity class",
                        "In the HTML template", "In application.yml only"), 0));
        list.add(q("Which annotation enables Spring Security web configuration?",
                List.of("@EnableWebSecurity", "@EnableJwt", "@EnableDatabase", "@EnableCors only"), 0));
        list.add(q("What is the difference between hasRole('ADMIN') and hasAuthority('ROLE_ADMIN') in expressions?",
                List.of("hasRole adds the ROLE_ prefix automatically; hasAuthority uses the exact string",
                        "They are unrelated", "hasAuthority only works on entities", "hasRole disables JWT"), 0));
        list.add(q("Why is CSRF protection often disabled for stateless JWT REST APIs?",
                List.of("Clients use Bearer tokens instead of browser cookie sessions", "JWT cannot be sent over HTTPS",
                        "CSRF is required for all REST APIs", "It improves SQL performance"), 0));
        list.add(q("What is rate limiting used for in public APIs?",
                List.of("Restrict how many requests a client can make in a time window", "Encrypt database columns",
                        "Generate primary keys", "Replace DTO validation"), 0));
        list.add(q("In Clean Architecture, where should business rules live?",
                List.of("Domain / use-case layer at the center", "Only in HTML templates", "Only in SQL triggers",
                        "Only in the browser"), 0));
        list.add(q("What is a port in hexagonal (ports and adapters) architecture?",
                List.of("An interface the application exposes or depends on", "A Tomcat HTTP port number",
                        "A database index", "A JWT claim"), 0));
        list.add(q("Which validation annotation enforces a string matches a regular expression pattern?",
                List.of("@Pattern", "@Email only", "@Size only", "@Table"), 0));
        list.add(q("Which validation annotation ensures a numeric field is greater than zero?",
                List.of("@Positive", "@NotBlank", "@Email", "@CrossOrigin"), 0));
        list.add(q("How do you create a custom Bean Validation rule?",
                List.of("Implement ConstraintValidator and annotate with @Constraint", "Add @Entity on the DTO",
                        "Use @GetMapping only", "Write SQL in the controller"), 0));
        list.add(q("Which annotation limits @ControllerAdvice exception handlers to specific controller classes?",
                List.of("assignableTypes or basePackageClasses on @ControllerAdvice", "@Entity only",
                        "@RequestMapping on the exception", "@Table on the handler"), 0));
        list.add(q("What is content negotiation in REST?",
                List.of("Choosing response format (JSON/XML) based on Accept header or produces attribute",
                        "Negotiating database passwords", "Splitting JWT into three parts", "Sharding tables"), 0));
        list.add(q("Which library commonly documents Spring REST APIs with Swagger UI?",
                List.of("springdoc-openapi", "spring-boot-starter-mail", "hibernate-validator only", "lombok only"), 0));
        list.add(q("Which annotation activates a @Configuration class only in the dev profile?",
                List.of("@Profile(\"dev\")", "@Entity", "@Transactional", "@GetMapping"), 0));
        list.add(q("How should database passwords be supplied in production Spring Boot apps?",
                List.of("Environment variables or a secrets manager — not hard-coded in source", "In a public GitHub repo",
                        "Inside JWT payload", "In HTML comments"), 0));
        list.add(q("Which OWASP recommendation applies to storing user passwords?",
                List.of("Use strong one-way hashing (e.g. BCrypt), never store plain text", "Store passwords in JWT",
                        "Email passwords to admins", "Reuse one password for all users"), 0));
        list.add(q("What is a common approach to invalidate JWTs on logout in stateless APIs?",
                List.of("Token blacklist or short-lived access tokens with refresh rotation", "Delete the database",
                        "Disable HTTPS", "Remove @Valid from DTOs"), 0));
        list.add(q("What does the principle of least privilege mean for API authorization?",
                List.of("Grant only the minimum permissions required for each role", "Give all users admin access",
                        "Disable authentication", "Return stack traces to clients"), 0));
        list.add(q("Which HTTP header helps browsers enforce HTTPS for future requests?",
                List.of("Strict-Transport-Security (HSTS)", "Accept-Language", "Content-Type only", "X-JWT-Token"), 0));
        list.add(q("Why separate infrastructure code (DB, web) from domain logic in Clean Architecture?",
                List.of("Domain rules stay independent of frameworks and are easier to test", "To remove all DTOs",
                        "To put SQL in controllers", "To avoid using HTTP"), 0));
        list.add(q("Which Spring Security interface loads user details during login?",
                List.of("UserDetailsService", "JpaRepository", "PasswordEncoder only", "EntityManager"), 0));
        list.add(q("Which component encodes and matches raw passwords against stored hashes?",
                List.of("PasswordEncoder (e.g. BCryptPasswordEncoder)", "JwtAuthenticationFilter only",
                        "DispatcherServlet", "JpaRepository"), 0));
        list.add(q("What should the login endpoint return after successful authentication?",
                List.of("JWT access token (and optionally refresh token) — not the raw password", "The user's password",
                        "Database connection string", "Entity with all fields unfiltered"), 0));
        list.add(q("Which validation annotation ensures a collection is not empty?",
                List.of("@NotEmpty", "@NotNull only (allows empty collection)", "@Email", "@CrossOrigin"), 0));
        list.add(q("What is the purpose of API documentation (OpenAPI/Swagger) for teams?",
                List.of("Describes endpoints, parameters, and schemas for consumers and testers", "Replaces the database",
                        "Encrypts JWT automatically", "Generates HTML pages only"), 0));
        list.add(q("Which environment-specific file might override settings for local development?",
                List.of("application-dev.properties or application-dev.yml", "pom.xml only", "index.html",
                        ".gitignore"), 0));
        list.add(q("Which Spring annotation loads configuration properties from a prefix into a Java class?",
                List.of("@ConfigurationProperties", "@Entity", "@GetMapping", "@Query"), 0));
        list.add(q("What is the main purpose of separating Command and Query responsibilities (CQRS concept)?",
                List.of("Optimize read and write models independently for complex domains", "Remove all DTOs",
                        "Disable JWT authentication", "Store passwords in query strings"), 0));
        return list;
    }
}
