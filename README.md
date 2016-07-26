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
This package includes files related to cubic spline interpolation. These files use the [math library](http://commons.apache.org/proper/commons-math/) of [Apache Commons](http://commons.apache.org/) and implement a [natural cubic spline interpolator](http://www.math.drexel.edu/~tolya/cubicspline.pdf){:target="_blank"}.
