# Interpolation-Methods
*A collection of programs for implementing and testing various interpolation methods.*


## Organization
There are three main components to this repository.
1. Java project
2. Data files
3. Presentation material

The Java project is divided into six packages. Each package includes `.java` files that are related to each other. The trajectory folder includes all of the files used in the interpolation analysis. They also include the results for the presentation given regarding this research.

##Java files

### `cubic`
This package includes files related to cubic spline interpolation. These files use the [math library](http://commons.apache.org/proper/commons-math/) of [Apache Commons](http://commons.apache.org/) and implement a [natural cubic spline interpolator](http://www.math.drexel.edu/~tolya/cubicspline.pdf).

* `CubicErrorTraj.java`

Summary: Reads orignal trajectory files and cubic interpolated trajectories (located in the `Cubic_Traj` folder). Each pair of files (the actual trajectory and the corresponding interpolated trajectory) is compared using the `error` class. The kilometers, kilometers squared, and distance for each trajectory is stored in an array. This values are then written to a `.plt` file (specifically the `CubicErrorTraj.plt` file located in the `Error_Analysis` folder. Note that the distance returned is the total distance of the trajectory (rather than the distance of the interpolated gap)

Imports: `error.java` and `writeToFile`; both are from the `main` package.
