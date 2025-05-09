# CPT212 Assignment I  
**Analysis of Algorithms & Sorting Methods**

> Java implementation of a step-by-step sorting algorithm, its string-sorting variant, and an empirical complexity analysis.

---

## 📚 Overview

This project includes:
- ✅ Integer sorting based on the provided algorithm
- 🔤 Word sorting using modified logic
- 📊 Operation count tracking and complexity graphing

Developed as part of the CPT212 course (Sem II, 2024/2025). Group-based assignment.

---

## 🛠️ Setup

### Requirements
- Java 11+
- Maven
- (Optional) Python 3.8+ with `matplotlib` for graphs

### Build & Run
```bash
# Clone and build
git clone https://github.com/<your-username>/cpt212-assignment1.git
cd cpt212-assignment1
mvn package

# Run number sorter
java -jar target/sorter.jar --mode numbers --input data/numbers.txt

# Run word sorter
java -jar target/sorter.jar --mode words --input data/words.txt

# Generate graph (optional)
python scripts/benchmark.py --out results/ops_vs_n.png
📁 Structure
bash
Copy
Edit
├─ src/               # Java source code
├─ data/              # Input files
├─ results/           # Output graphs and logs
├─ scripts/benchmark.py
├─ README.md
📈 Results
The sorting algorithms were benchmarked for n = 100–1000.
Graph:

👥 Authors
Leader: <Your Name>

Member 2: <Name>

Member 3: <Name>
