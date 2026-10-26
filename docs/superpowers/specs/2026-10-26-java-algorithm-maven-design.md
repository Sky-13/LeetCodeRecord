# Java Algorithm Study Repo Maven Standardization Design

**Date:** 2026-10-26

## Goal
Turn the repository into a standard Maven-based Java project that supports direct run/debug of algorithm examples, keeps each algorithm area paired with a Markdown note, and provides starter folders for the main algorithm families the user wants to study.

## Current State
- The repo already contains a few algorithm notes and Java files.
- The Java files are not organized as a standard Java source tree yet.
- The user wants three things:
  1. a Maven project layout,
  2. a reusable template for each algorithm folder,
  3. starter folders for additional algorithm families.

## Proposed Repository Layout

```text
LeetCodeRecord/
├── pom.xml
├── README.md
├── docs/
│   └── superpowers/
│       ├── specs/
│       └── plans/
└── src/
    └── main/
        └── java/
            └── algorithm/
                ├── sort/
                │   ├── Quickselect.java
                │   └── Quickselect.md
                ├── greedy/
                │   ├── Greedy.java
                │   └── Greedy.md
                ├── dp/
                │   ├── DpTemplate.java
                │   └── DpTemplate.md
                ├── tree/
                │   ├── TreeTemplate.java
                │   └── TreeTemplate.md
                ├── graph/
                │   ├── GraphTemplate.java
                │   └── GraphTemplate.md
                └── two_pointers/
                    ├── TwoPointersTemplate.java
                    └── TwoPointersTemplate.md
```

## Design Decisions

### 1) Maven as the build system
- Add a root `pom.xml` so the repository can be opened as a standard Maven Java project in IntelliJ IDEA.
- Use Maven defaults for source and output directories.
- Keep dependencies minimal; only the JDK is required for now.

### 2) One algorithm folder = one package area
- Each algorithm family gets its own package under `src/main/java/algorithm`.
- Package names are all lower-case and match the directory name.
- Each family keeps two paired files:
  - `.java` for runnable code,
  - `.md` for explanation, examples, complexity, and notes.

### 3) Runnable examples by default
- Every starter `.java` file includes a `main(String[] args)` method.
- The `main` method demonstrates the algorithm with a small sample input so the file can be run and debugged immediately from the IDE.
- Each file also contains a small, self-contained algorithm implementation that compiles without extra project setup.

### 4) Reusable markdown template
Each `.md` file will follow the same structure:
- problem overview,
- key idea,
- algorithm steps,
- time/space complexity,
- example,
- common pitfalls.

### 5) Starter families to create now
Create the following categories as the initial batch:
- `sort`
- `greedy`
- `dp`
- `tree`
- `graph`
- `two_pointers`

These are starters only; future algorithm families can be added using the same pattern.

## Constraints
- Java files must compile under Maven.
- No external dependencies are required.
- Package names must remain valid Java identifiers; use `two_pointers` instead of `two-pointers` for the Java package and directory name.
- Files should stay focused: one algorithm example per Java file.

## Validation
- `mvn test` should succeed once the project structure is in place.
- Each starter Java file should compile and be directly runnable from the IDE.
- The repository layout should be easy to extend with more algorithm families later.
