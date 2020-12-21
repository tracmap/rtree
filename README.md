rtree
=========

R-tree library for TML.

This is our fork of the [original repo](https://github.com/davidmoten/rtree2).

Contains the following improvements:
* Compatibility with Android 7.0 (no more java.awt.geom dependencies, no more Java 8 code).
* Polygon geometry class (supports searching for convex polygons in R-tree).
* maven-assembly-plugin for assembling JAR with all its dependencies.

Build
------------
```bash
mvn clean compile assembly:single
```

Deploy
------------
Copy the assembled JAR file into the commonLibraries module of the TML repo.
