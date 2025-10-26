# Assignment 3: Optimization of a City Transportation Network (Minimum Spanning Tree)

**Student Name:** Jaxygaliyev Artur  
**Group:** SE-2429

---

## Objective

The purpose of this assignment is to apply **Prim’s** and **Kruskal’s** algorithms to optimize a city’s transportation network by determining the minimum set of roads that connect all city districts with the lowest possible total construction cost.  
Both algorithms were implemented in Java, tested on datasets of varying sizes, and compared in terms of execution time and operation count.

---

## 1. Introduction

This project implements and compares two **Minimum Spanning Tree (MST)** algorithms — **Prim’s** and **Kruskal’s**, applied to a simulated city transportation network.  
The goal is to minimize the total construction cost while ensuring all districts remain connected.  
Both algorithms were implemented in Java and evaluated using multiple datasets (small, medium, large, and extralarge) under identical testing conditions.

---

## 2. Methodology

- Graph data is stored in JSON format (`data/*.json`), representing city districts as vertices and roads as weighted edges.
- Both algorithms read the same input and output:
    - MST edge list
    - Total MST cost
    - Operation count (comparisons, unions, etc.)
    - Execution time (in milliseconds)
- Results are automatically saved to:
    - `reports/output.json`
    - `reports/result.csv`

---

## 3. Comparison of Prim’s and Kruskal’s Algorithms

| Feature | Prim’s Algorithm | Kruskal’s Algorithm |
|----------|------------------|--------------------|
| **Approach** | Vertex-based, grows the MST from a single vertex | Edge-based, adds edges in increasing order of weight |
| **Data Structure** | Priority queue (min-heap) | Union-Find (disjoint set) |
| **Graph Representation** | Adjacency matrix or list | Edge list |
| **Cycle Management** | Implicitly handled | Managed using Union-Find |
| **Complexity** | O(V²) or O((V + E) log V) | O(E log E) or O(E log V) |
| **Suitable For** | Dense graphs | Sparse graphs |
| **Implementation Complexity** | Simpler in dense graphs | More complex (cycle detection) |
| **Starting Point** | Requires a start vertex | No specific start point |
| **Optimal For** | Dense city networks | Sparse road networks |

---

## 4. Experimental Results

| ID | Algorithm | Vertices | Total MST Cost | Execution Time (ms) | Operation Count |
|----|------------|-----------|----------------|----------------------|-----------------|
| 1 | Kruskal | 18 | 96.000 | 1 | 113 |
| 1 | Prim | 18 | 96.000 | 0 | 27 |
| 2 | Kruskal | 27 | 157.000 | 0 | 190 |
| 2 | Prim | 27 | 157.000 | 0 | 40 |
| 3 | Kruskal | 22 | 170.000 | 0 | 149 |
| 3 | Prim | 22 | 170.000 | 0 | 33 |
| 4 | Kruskal | 27 | 166.000 | 0 | 187 |
| 4 | Prim | 27 | 166.000 | 0 | 40 |
| 5 | Kruskal | 13 | 105.000 | 0 | 72 |
| 5 | Prim | 13 | 105.000 | 0 | 19 |

---

## 5. Analysis and Interpretation

### Correctness
Both algorithms consistently produced identical MST costs, confirming correctness.

### Performance
Prim’s algorithm required fewer operations — roughly **4–5× fewer** than Kruskal’s for larger graphs.

### Scalability
- Kruskal’s complexity: **O(E log E)**
- Prim’s complexity (with heap): **O((V + E) log V)**  
  Prim scales better on dense graphs.

### Algorithm Suitability
- **Kruskal:** Best for sparse graphs (few edges).
- **Prim:** Best for dense graphs (many edges).

---

## 6. Conclusion

- Both algorithms produce identical MST weights.
- **Prim’s algorithm** performs faster on dense networks.
- **Kruskal’s algorithm** is simpler and effective on sparse graphs.
- For real-world city transportation networks, **Prim’s** is more practical.

---

## 7. Bonus Section - Graph Design and Visualization in Java

A visualization feature was implemented using **Graphviz**, allowing automatic generation of MST diagrams.

### Implementation
- Added `GraphVizExporter.java` — exports `.dot` files for both original graphs and MSTs.
- Integrated into `Runner.java`, saving visualizations under `reports/images/`.
- `.dot` files can be converted to `.png` via:
  ```bash
  for f in reports/images/*.dot; do dot -Tpng "$f" -o "${f%.dot}.png"; done

Results
•	Graph images visualize both the original graph and MST (highlighted in red).
•	Demonstrates integration between custom Graph, Edge, and MST computation.
•	Improves understanding, reproducibility, and presentation quality.

⸻

## 8. Project Structure
```bash

daa-3-new/
├── pom.xml
├── data/
│   ├── ass_3_input_small.json
│   ├── ass_3_input_medium.json
│   ├── ass_3_input_large.json
│   └── ass_3_input_extralarge.json
├── reports/
│   ├── result.csv
│   ├── output.json
│   └── images/
│       ├── graph_1.dot
│       ├── mst_kruskal_1.dot
│       └── mst_prim_1.dot
├── src/
│   ├── main/java/algorithms/
│   │   ├── Edge.java
│   │   ├── EdgeWeightedGraph.java
│   │   ├── PrimMST.java
│   │   ├── KruskalMST.java
│   │   ├── JSONHandler.java
│   │   ├── MetricsTracker.java
│   │   ├── GraphVizExporter.java
│   │   └── mst/Runner.java
│   └── test/java/mst/
│       └── MSTTests.java

```
⸻
## 9. How to Run

### 1. Build the Project
Compile the entire project using Maven:
```bash
mvn clean compile
```

### 2. Run Algorithms
Execute the program with your desired dataset:
```bash
mvn exec:java -Dexec.mainClass="algorithms.mst.Runner" \
-Dexec.args="data/ass_3_input_small.json reports/output.json reports/result.csv"
```
### 3. Run Tests
To verify correctness and algorithmic consistency:
```bash
mvn test
```
### 4. Generate Visualizations
After installing Graphviz, convert .dot files to images:
```bash
for f in reports/images/*.dot; do dot -Tpng "$f" -o "${f%.dot}.png"; done
```

⸻

## 10. References
    1.	Baeldung — Kruskal’s vs Prim’s Algorithm - Analytic Report
    2.	GeeksforGeeks — Prim’s MST (Greedy Algorithm) - Analytic Report
    3.	GeeksforGeeks — Kruskal’s MST (Greedy Algorithm) - Analytic Report
    4.	GeeksforGeeks — Difference Between Prim’s and Kruskal’s Algorithm - Analytic Report
    5.	ChatGPT - Algorithmic Analysis 