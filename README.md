# **Chromatic3D**  

## Overview  
**Chromatic3D** is a **Java-based** 3D visualization application designed to represent **RGB color spaces**. Originally developed to assist in **high school chemical experiments**, this project provides an intuitive way to visualize and interpret color data. It utilizes the **Jzy3d** library for generating and manipulating 3D charts and objects.  

---  

## Features  
- **3D RGB Cube Visualization** – Display RGB color spaces in a three-dimensional cube.  
- **Experiment Set Management** – Add and manage multiple experiment sets with distinct colors and points.  
- **Customizable Display** – Enable or disable elements such as background, equations, and gravity points.  
- **Detailed Data Analysis** – Generate reports containing polygon area calculations, distance from the origin, and brightness percentages.  

---  

## Project Structure  
```
Chromatic3D/
├─ README.md
├─ pictures
│  ├─ a.png
│  └─ b.png
├─ pom.xml
├─ src
│  └─ main
│     ├─ java
│     │  └─ com
│     │     └─ njdge
│     │        └─ chromatic3d
│     │           ├─ CoordDemo.java
│     │           ├─ Utils.java
│     │           ├─ object
│     │           │  ├─ EnvironmentManager.java
│     │           │  └─ impl
│     │           │     └─ ExperimentSet.java
│     │           └─ spaces
│     │              └─ RGBCubeDemo.java
│     └─ resources
│        ├─ META-INF
│        │  └─ maven
│        │     └─ archetype.xml
│        └─ archetype-resources
│           ├─ pom.xml
│           └─ src
│              ├─ main
│              │  └─ java
│              │     └─ App.java
│              └─ test
│                 └─ java
│                    └─ AppTest.java
└─ target
   └─ classes
      ├─ META-INF
      │  └─ maven
      │     └─ archetype.xml
      ├─ archetype-resources
      │  ├─ pom.xml
      │  └─ src
      │     ├─ main
      │     │  └─ java
      │     │     └─ App.java
      │     └─ test
      │        └─ java
      │           └─ AppTest.java
      └─ com
         └─ njdge
            └─ chromatic3d
               ├─ CoordDemo.class
               ├─ Utils.class
               ├─ object
               │  ├─ EnvironmentManager.class
               │  └─ impl
               │     └─ ExperimentSet.class
               └─ spaces
                  └─ RGBCubeDemo.class
```  

---  

## Dependencies  
- **[Jzy3d](http://www.jzy3d.org/)** – A Java library for 3D plotting.  

---  

## Preview  

### Mapping of Real Data  
<img src="pictures/a.png" width="600"/>  

### RGB Cube Demo  
<img src="pictures/b.png" width="600"/>  
