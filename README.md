# numerical-interpolation-visualizer

Polynomial interpolation using Chebyshev nodes with adaptive order selection, point merging for numerical stability, and graphical visualization using JFreeChart

---

**Project_3**  
**Numerical Methods For Digital Computing**  
Oklahoma State University  
Due: 11-07-2025

**Joshua Price**  
joshua.price@okstate.edu

---

## Running the Program

Navigate to the src directory:
```bash
cd numerical-interpolation-visualizer/src
```

Compile and run:

**Windows (Command Prompt or PowerShell):**
```bash
javac -cp ".;../lib/*" *.java
java -cp ".;../lib/*" NIV
```

**Mac/Linux:**
```bash
javac -cp ".:../lib/*" *.java
java -cp ".:../lib/*" NIV
```

---

## Program Overview

This program demonstrates Chebyshev polynomial interpolation with the following features:

**Data Generation:**
- Generates 6-8 random data points at Chebyshev nodes
- Chebyshev nodes are special x-coordinates on the interval [-1, 1] that minimize interpolation error
- Random y-values are generated in the range [-5, 5]

**Point Merging:**
- Points closer than a threshold distance are averaged together for numerical stability
- Uses Euclidean distance to detect close points
- Outputs which points were merged and the resulting averaged point

**Adaptive Order Testing:**
- Tests polynomial orders from 1 up to n-1 (where n is the number of points)
- Lower orders use a subset of points and approximate the full dataset
- Higher orders use more points and fit the data more closely
- The highest order passes through all points exactly
- Calculates and displays approximation error for each order

**Graphical Visualization:**
- Displays all data points as black dots
- Shows multiple colored curves representing different polynomial orders
- Each curve demonstrates how that polynomial order fits the data

---

## Graphics Library Selection

This project uses **JFreeChart** for graphical representation of numerical data and function plotting.

### Rationale

JFreeChart was selected for two primary reasons:

1. **Familiarity and reliability** - I have extensive prior experience with JFreeChart for data visualization, though this is my first implementation of its graphical display capabilities for function plotting.

2. **Precision and customization** - The library provides the level of control necessary for a numerically sensitive application. JFreeChart allows fine-grained customization of data sampling, axis configuration, and display formatting, which is essential for accurately representing the results of numerical methods where precision is critical.

While alternative libraries exist for 2D graphing in Java (such as XChart, JMathPlot, and JavaFX), JFreeChart's combination of proven stability and extensive customization options makes it well-suited for this project's requirements.