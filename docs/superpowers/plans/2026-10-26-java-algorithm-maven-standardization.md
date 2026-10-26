# Java Algorithm Study Repo Maven Standardization Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Convert the repository into a standard Maven Java project with directly runnable algorithm examples, paired Markdown notes, and starter folders for the main algorithm families.

**Architecture:** Use Maven defaults under `src/main/java` and keep one small runnable Java class plus one Markdown note per algorithm family. Standardize package names to lower-case Java identifiers and keep the Java examples self-contained so they can be run and debugged immediately from IntelliJ IDEA or `mvn`-backed tooling.

**Tech Stack:** Java 8, Maven, IntelliJ IDEA, JDK 1.8 from the local machine

**Spec:** `docs/superpowers/specs/2026-10-26-java-algorithm-maven-design.md`

## Global Constraints

- Use Maven defaults for source and output directories: `src/main/java`, `src/test/java`, `target/`.
- No external dependencies are required for the project.
- Package names must be valid Java identifiers and lower-case.
- Use `two_pointers` instead of `two-pointers` for the Java package and directory name.
- Each algorithm family must keep exactly one runnable Java example file and one Markdown note file in the same package directory.
- Each starter Java file must include a `main(String[] args)` method so it can be run and debugged directly.

---

### Task 1: Add Maven project metadata

**Files:**
- Create: `pom.xml`
- Modify: `README.md`

**Interfaces:**
- Consumes: the current repository root and the existing Java source tree under `src/main/java`
- Produces: a Maven project that IntelliJ IDEA and `mvn test` can recognize immediately

- [ ] **Step 1: Write the Maven POM**

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.yudemac</groupId>
    <artifactId>leetcode-record</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>LeetCodeRecord</name>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>${maven.compiler.source}</source>
                    <target>${maven.compiler.target}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

- [ ] **Step 2: Update the root README with Maven usage**

~~~markdown
# Welcome !

笔试-面试总结

## Build and run

```bash
mvn test
mvn -q -DskipTests compile
java -cp target/classes algorithm.sort.Quickselect
```

## Run another example

```bash
java -cp target/classes algorithm.greedy.Greedy
```
~~~

- [ ] **Step 3: Verify Maven recognizes the project**

Run: `mvn test`

Expected: Maven completes successfully, even if there are no test classes yet.

---

### Task 2: Standardize the existing algorithm examples

**Files:**
- Modify: `src/main/java/algorithm/sort/Quickselect.java`
- Modify: `src/main/java/algorithm/sort/Quickselect.md`
- Modify: `src/main/java/algorithm/greedy/Greedy.java`
- Modify: `src/main/java/algorithm/greedy/Greedy.md`

**Interfaces:**
- Consumes: the current Quickselect and Greedy example implementations
- Produces: two Maven-ready, directly runnable example classes with paired notes

- [ ] **Step 1: Keep Quickselect runnable and package-correct**

```java
package algorithm.sort;

import java.util.Arrays;

public class Quickselect {

    public static void main(String[] args) {
        int[] nums = {7, 2, 1, 6, 8, 5, 3, 4};
        int k = 3;
        int result = quickselect(nums, k);
        System.out.println("The " + k + "-th smallest element is: " + result);
        System.out.println("Array after selection: " + Arrays.toString(nums));
    }

    public static int quickselect(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("nums must not be empty");
        }
        if (k < 1 || k > nums.length) {
            throw new IllegalArgumentException("k out of range");
        }
        return select(nums, 0, nums.length - 1, k - 1);
    }

    private static int select(int[] nums, int left, int right, int targetIndex) {
        if (left == right) {
            return nums[left];
        }

        int pivotIndex = partition(nums, left, right);
        if (pivotIndex == targetIndex) {
            return nums[pivotIndex];
        }
        if (pivotIndex < targetIndex) {
            return select(nums, pivotIndex + 1, right, targetIndex);
        }
        return select(nums, left, pivotIndex - 1, targetIndex);
    }

    private static int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] <= pivot) {
                swap(nums, storeIndex++, i);
            }
        }
        swap(nums, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

- [ ] **Step 2: Keep Quickselect notes aligned with the code**

```markdown
# Quickselect

## Overview
Quickselect finds the k-th smallest element in an unsorted array.

## Key Idea
Partition the array around a pivot and recurse only into the side that can still contain the answer.

## Algorithm Steps
1. Choose a pivot.
2. Partition the array.
3. Compare the pivot index with the target index.
4. Recurse into the matching side only.

## Complexity
- Time: average O(n), worst O(n^2)
- Space: O(log n) average recursion depth, O(n) worst case

## Example
Use the sample array in `Quickselect.java` with `k = 3`.

## Pitfalls
- Forgetting that `k` is 1-based in the demo method.
- Not validating empty arrays or out-of-range `k`.
```

- [ ] **Step 3: Keep Greedy runnable and package-correct**

```java
package algorithm.greedy;

public class Greedy {

    public static void main(String[] args) {
        int[] start = {1, 3, 0, 5, 8, 5};
        int[] end = {2, 4, 6, 7, 9, 9};
        System.out.println("Max non-overlapping meetings: " + maxMeetings(start, end));
    }

    public static int maxMeetings(int[] start, int[] end) {
        if (start == null || end == null || start.length != end.length) {
            throw new IllegalArgumentException("start and end must be non-null and same length");
        }
        if (start.length == 0) {
            return 0;
        }

        int n = start.length;
        int[][] meetings = new int[n][2];
        for (int i = 0; i < n; i++) {
            meetings[i][0] = start[i];
            meetings[i][1] = end[i];
        }

        java.util.Arrays.sort(meetings, java.util.Comparator.comparingInt(a -> a[1]));

        int count = 1;
        int lastEnd = meetings[0][1];
        for (int i = 1; i < n; i++) {
            if (meetings[i][0] >= lastEnd) {
                count++;
                lastEnd = meetings[i][1];
            }
        }
        return count;
    }
}
```

- [ ] **Step 4: Keep Greedy notes aligned with the code**

```markdown
# Greedy

## Overview
This folder records greedy algorithm examples.

## Key Idea
Choose the locally optimal option first, then continue with the remaining work.

## Algorithm Steps
1. Sort or order candidates by the greedy rule.
2. Pick the first valid candidate.
3. Skip candidates that conflict.
4. Repeat until no candidates remain.

## Complexity
- Time: depends on the sorting step and the problem constraints
- Space: depends on the representation of the candidates

## Example
Use the meeting-scheduling example in `Greedy.java`.

## Pitfalls
- Mixing the greedy choice with a dynamic programming strategy.
- Forgetting to define a clear ordering rule.
```

- [ ] **Step 5: Verify both files compile**

Run: `mvn -q -DskipTests compile`

Expected: both `algorithm.sort.Quickselect` and `algorithm.greedy.Greedy` compile cleanly.

---

### Task 3: Add starter templates for the remaining algorithm families

**Files:**
- Create: `src/main/java/algorithm/dp/DpTemplate.java`
- Create: `src/main/java/algorithm/dp/DpTemplate.md`
- Create: `src/main/java/algorithm/tree/TreeTemplate.java`
- Create: `src/main/java/algorithm/tree/TreeTemplate.md`
- Create: `src/main/java/algorithm/graph/GraphTemplate.java`
- Create: `src/main/java/algorithm/graph/GraphTemplate.md`
- Create: `src/main/java/algorithm/two_pointers/TwoPointersTemplate.java`
- Create: `src/main/java/algorithm/two_pointers/TwoPointersTemplate.md`

**Interfaces:**
- Consumes: the naming pattern established by the sort and greedy examples
- Produces: four new algorithm family folders with runnable Java starters and matching Markdown notes

- [ ] **Step 1: Create the `dp` starter pair**

```java
package algorithm.dp;

public class DpTemplate {

    public static void main(String[] args) {
        int n = 5;
        System.out.println("Fibonacci(" + n + ") = " + fibonacci(n));
    }

    public static int fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        if (n <= 1) {
            return n;
        }
        int prev2 = 0;
        int prev1 = 1;
        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        return prev1;
    }
}
```

```markdown
# DP

## Overview
This folder stores dynamic programming examples.

## Key Idea
Break a problem into overlapping subproblems and build the answer from smaller states.

## Algorithm Steps
1. Define the state.
2. Define the transition.
3. Define the base case.
4. Compute states in dependency order.

## Complexity
- Time: depends on the number of states and transitions
- Space: depends on the storage for the state table

## Example
Use the Fibonacci example in `DpTemplate.java`.

## Pitfalls
- Missing base cases.
- Using a recurrence without defining what each state means.
```

- [ ] **Step 2: Create the `tree` starter pair**

```java
package algorithm.tree;

public class TreeTemplate {

    private static final class Node {
        private final int value;
        private Node left;
        private Node right;

        private Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        System.out.println("Tree node count = " + countNodes(root));
    }

    public static int countNodes(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}
```

```markdown
# Tree

## Overview
This folder stores binary tree and tree traversal examples.

## Key Idea
Represent hierarchical data with node links and solve problems recursively or with a traversal stack.

## Algorithm Steps
1. Define the node structure.
2. Choose a traversal or recursion strategy.
3. Visit each node once in the desired order.

## Complexity
- Time: usually O(n)
- Space: depends on recursion depth or explicit stack size

## Example
Use the node-count example in `TreeTemplate.java`.

## Pitfalls
- Forgetting null checks.
- Recursing without a clear base case.
```

- [ ] **Step 3: Create the `graph` starter pair**

```java
package algorithm.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class GraphTemplate {

    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            graph.add(new ArrayList<>());
        }
        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(3);
        graph.get(2).add(3);
        System.out.println("Reachable from 0 = " + bfsCount(graph, 0));
    }

    public static int bfsCount(List<List<Integer>> graph, int start) {
        boolean[] visited = new boolean[graph.size()];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        visited[start] = true;
        queue.add(start);
        int count = 0;

        while (!queue.isEmpty()) {
            int node = queue.removeFirst();
            count++;
            for (int next : graph.get(node)) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.addLast(next);
                }
            }
        }
        return count;
    }
}
```

```markdown
# Graph

## Overview
This folder stores graph traversal and shortest-path examples.

## Key Idea
Model nodes and edges explicitly, then use BFS, DFS, or other graph-specific techniques.

## Algorithm Steps
1. Choose an adjacency representation.
2. Track visited nodes.
3. Traverse from a start node.

## Complexity
- Time: usually O(V + E)
- Space: usually O(V)

## Example
Use the BFS reachability example in `GraphTemplate.java`.

## Pitfalls
- Visiting nodes more than once.
- Building the adjacency list incorrectly.
```

- [ ] **Step 4: Create the `two_pointers` starter pair**

```java
package algorithm.two_pointers;

import java.util.Arrays;

public class TwoPointersTemplate {

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 4, 4, 5};
        int uniqueCount = removeDuplicatesInPlace(nums);
        System.out.println("Unique prefix length = " + uniqueCount);
        System.out.println("Array after compaction = " + Arrays.toString(nums));
    }

    public static int removeDuplicatesInPlace(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("nums must not be null");
        }
        if (nums.length == 0) {
            return 0;
        }

        int slow = 1;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - 1]) {
                nums[slow++] = nums[fast];
            }
        }
        return slow;
    }
}
```

```markdown
# Two Pointers

## Overview
This folder stores examples that use two pointers to scan or modify data in linear time.

## Key Idea
Move two indices through the data while maintaining an invariant about the region they delimit.

## Algorithm Steps
1. Choose the pointer roles.
2. Define the invariant.
3. Move one or both pointers until the condition changes.

## Complexity
- Time: usually O(n)
- Space: usually O(1)

## Example
Use the duplicate-compaction example in `TwoPointersTemplate.java`.

## Pitfalls
- Moving both pointers without preserving the invariant.
- Forgetting to return the usable prefix length after in-place compaction.
```

- [ ] **Step 5: Verify all starter files compile**

Run: `mvn -q -DskipTests compile`

Expected: all new starter files compile successfully.

---

### Task 4: Clean up repository layout and document the new convention

**Files:**
- Modify: `README.md`
- Modify: `docs/superpowers/specs/2026-10-26-java-algorithm-maven-design.md` only if a wording mismatch is discovered during implementation
- Remove if still present: legacy top-level `Algorithm/` directory contents that are superseded by `src/main/java`

**Interfaces:**
- Consumes: the completed Maven layout and the new algorithm folder structure
- Produces: a repository that clearly explains where to add future algorithm notes and examples

- [ ] **Step 1: Add a short folder convention section to the README**

```markdown
## Folder convention

Each algorithm family lives under `src/main/java/algorithm/<family>/` and contains exactly two files:
- `<Name>.java` for the runnable example
- `<Name>.md` for the explanation and notes
```

- [ ] **Step 2: Remove or archive the old `Algorithm/` tree if it is no longer needed**

Run: `git status --short`

Expected: only the Maven source tree remains as the active location for algorithm material.

- [ ] **Step 3: Run a final project validation pass**

Run:

```bash
mvn test
mvn -q -DskipTests compile
```

Expected: both commands succeed on the final layout.
