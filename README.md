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

## Graphics Library Selection

This project uses **JFreeChart** for graphical representation of numerical data and function plotting.

### Rationale

JFreeChart was selected for two primary reasons:

1. **Familiarity and reliability** - I have extensive prior experience with JFreeChart for data visualization, though this is my first implementation of its graphical display capabilities for function plotting.

2. **Precision and customization** - The library provides the level of control necessary for a numerically sensitive application. JFreeChart allows fine-grained customization of data sampling, axis configuration, and display formatting, which is essential for accurately representing the results of numerical methods where precision is critical.

While alternative libraries exist for 2D graphing in Java (such as XChart, JMathPlot, and JavaFX), JFreeChart's combination of proven stability and extensive customization options makes it well-suited for this project's requirements.