---
output: html_document
---
# Interpolation-Methods
*A collection of programs for implementing and testing various interpolation methods.*


## Organization
There are three main components to this repository.

> 1. Java project
> 2. Data files
> 3. Presentation material

The Java project is divided into six packages. Each package includes `.java` files that are related to each other. The trajectory folder includes all of the files used in the interpolation analysis. They also include the results for the presentation given regarding this research.

The general procedure for testing an interpolation method involves
> 1. Creating gaps in actual files to which an interpolation porcedure can be applied
> 2. Creating an implementation of the interpolation
> 3. Parsing the files with gaps and applying the interpolation procedure to fill in the gap. Then writing the interpolated data to a new set of files
> 4. Finding the accurracy of the method by comparing the interpolated data to the actual data. 

Typically each step is a separate java program.

##Java packages and files

### `cubic`
This package includes files related to cubic spline interpolation. These files use the [math library](http://commons.apache.org/proper/commons-math/) of [Apache Commons](http://commons.apache.org/) and implement a [natural cubic spline interpolator](http://www.math.drexel.edu/~tolya/cubicspline.pdf).

* `CubicErrorTraj.java`
  * **Summary**: Reads orignal trajectory files and cubic interpolated trajectories (located in the `Cubic_Traj` folder). Each pair of files (the actual trajectory and the corresponding interpolated trajectory) is compared using the `error` class. The kilometers, kilometers squared, and distance for each trajectory is stored in an array. This values are then written to a `.plt` file (specifically the `CubicErrorTraj.plt` file located in the `Error_Analysis` folder. Note that the distance returned is the total distance of the trajectory (rather than the distance of the interpolated gap).
  * **Imports**: `error` and `writeToFile`; both are from the `main` package.
* `CubicErrorTS.java`
  * **Summary**: Performs the same task as `CubicErrorTraj.java` except it uses `tsError.java` to calculate the error between two time series. Reads actual time series files from the folder `Original_TS` and reads the interpolated time series from the folder `Cubic_TS`. The final results for each file are written to the file `CubicTS.plt` in the `Error_Analysis` folder. 
  * **Imports**: `tsError.java` and `writeToFile`; both from the `main` package.
* `CubicSplineMainTraj.java`
  * **Summary**: Gets the files in the folder `Trajectory_with_gaps` and applies the cubic spline interpolation method to filling in the gaps. Creates a new set of files and writes the files to the folder `Cubic_Traj`.
  * **Imports**: `ExtractLatLon.java` and `writeToFile`; both from `main`.
* `CubicTSMain.java`
  * **Summary**: Performs the same function as `CubicSplineMainTraj.java` but with time series data. Reads data from `Time_series_wtih_gaps` and writes to `Cubic_TS`.
  * **Imports**: `writeToFile`
* `SplineInterpolation.java`
  * **Summary**: Provides an implementation of the cubic spline interpolator using the methods provided by the Apache Commons Math library. **Constructed** with the gap length of the particular dataset. It can interpolate both time series and trajectory data.
  * **Methods**:
    * `setTS(double[])` - called with the time series as a parameter. The time series must be an array of doubles
    * `setTraj(String, String)` - used to pass the trajectory data. Two string parameters: one is the latitude values while the other is the longitude values (both should be comma seperated values stored as a single string).
    * `string[] interpolateTraj()` - returns the interpolated trajectory as a list of strings formatted to the structure of GeoLife data (i.e. latitude, longitude, 0, altitude, etc...) where the non-trajectory values are fixed.
    * `double[] interpolateTS()` - returns the interpolated time series as an array of doubles.
    * `int getNumInt` - can only be called after one of the interpolate methods. Returns the number of points that were interpolated.
  * **Imports**: `org.apache.commons.math3.analysis.UnivariateFunction`, `org.apache.commons.math3.analysis.interpolation.SplineInterpolator`,`org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator`, and `OrderPoints` (from the `main` package).

### `linear_NearestNeighbor`
This package includes the files that implement the linear and nearest neighbor interpolation methods. 
* `LinearInterpolationMainTraj.java`
  * **Summary**: Retrieves trajectory files with gaps from the folder `Trajectory_with_gaps`. Applies the linear interpolation algoirithm and writes the new files to the folder `LinInt_Traj`.
  * **Imports**: `writeToFile` from the `main` package.
* `linInterp.java`
  * **Summary**: Given two strings each of which is a line (with the same format as a line from the GeoLife dataset) containing the latitude and longitude values, this program will return either a linearly interpolated dataset or a nearest neighbor interpolated dataset. It's **constructed** with the start string, end string, the length of the gap, and a boolean value denoting whether to include the start and end strings in the list of values returned after interpolation. This program is used specifically for trajectory data.
  * **Methods**:
    * `extractInfo()` - this method extracts latitude and longitude values from the provided strings. It must be called before the other methods.
    * `interpolate()` - performs linear interpolation on the given data
    * `NNinterpolate()` - performs nearest neighbor interpolation on the given data
    * `string[] getPoints()` - accessor method for retrieving the the list of interpolated points
* `linTS`
  * **Summary**: Applies linear interpolation to time series data. Reads from `Time_series_with_gaps` and writes to `Lin_TS`.
* `NearestNeighbor.java`
  * **Summary**: The main program for performing nearest neighbor interpolation on trajectory files. Reads from `Trajectory_with_gaps` folder and writes to `NN_traj` folder.
  * **Imports**: `writeToFile` from `main`.
* `NearestNeighborTS.java`
  * **Summary**: The main program for applying nearest neighbor interpolation to time series data. Reads from `Time_series_with_gaps` and writes to `NN_TS`. 
* `SimpleTS.java`
  * **Summary**: Provides implementations for linear and nearest neighbor interpolation methods applied to time series data. **Constructed** with four integers: the start hilbert value, end hilbert value, start index, and end index. 
  * **Methods**:
    * `linearInterpolation()` - creates interpolation points by fitting a linear model to the data provided.
    * `nearestNeighbor()` - performs nearest neighbor interpolation on the given data.
    * `double[] getInterpolatedValues()` - accessor method that returns the interpolated points. Must be called after one of the interpolation methods.
* `trajectoryErrorMain.java`
  * **Summary**: Calculates the error associated with linear trajectories and nearest neighbor trajectories. Reads actual files from main directory (i.e. `trajectory`) while also importing files from the `LinInt_Traj` and `NN_Traj` folders. Writes the errors to the `Error_Analysis` folder (creates two files: `NNerrorTraj.plt` and `LinerrroTraj.plt`).
  * **Imports**: `error` and `writeToFile` (both from main)
* `tsErrorMain.java`
  * **Summary**: Calculates error from linear and nearest neighbor time series. Reads from the folders `Original_TS`, `Lin_TS`, and `NN_TS`. Writes the error values to the `Error_Analysis` folder as `NNerror.plt` and `Linerror.plt`.
  * **Imports**: `tsError` and `writeToFile` (from main)


### `main`
This package includes general programs that are used by other files to execute specific tasks.
* `error.java`
  * **Summary**: Given a list of actual latitude and longitude trajectory points and a list of interpolated points, this program will calculate a number of error values.
  * **Methods**:
    * **Constructor** `error(string[], string[], int)` - first string is the list of actual points while the second is a list of interpolated points and the integer is the length of the list.
    * `calculate()` - calculates the error for each pair of values in the given lists. Uses the [Haversine formula](https://en.wikipedia.org/wiki/Haversine_formula) to calculate error. This must be called before other accessor methods are called.
    * `double[] returnErr()` - returns a list of error values (error for each pair of points).
    * `double returnErrCum()` - returns the sum of error values.
    * `double realDist()` - returns the total distance covered in the original trajectory.
    * `double dist(double, double, double, double)` - given two coordinates (in the form x1, y1, x2, y2) returns the distance between the two using the Haversine formula.
* `ExtractLatLon.java`
  * **Summary**: Used to get the latitude and longitude values from a file.
  * **Methods**:
    * **Constructor** `ExtractLatLon(string)` - accepts a string representing the filepath of the file with the latitude and longitude values (the file must be in the PLT format exemplified by the GeoLife dataset)
    * `string getLats()` - returns the latitude values as a string of double values separated by commas (note that the final comma is removed so the last character of the string is numeric)
    * `string getLons()` - returns the longitude values in the same format as the latitude values
* `getNum.java`
  * **Summary**: Extracts information from a line of GeoLife data
  * **Methods**:
    * **Constructor** `getNum(string)` - constructed with a string representing one point from the GeoLife data (seven values including latitude and longitude, separated by commas)
    * `extract()` - mutator method that must be called before other methods. It extracts the latitude, longitude, and time values from the given string.
    * `double getLat()` - returns the latitude
    * `double getLon()` - returns the longitude
    * `double getTime()` - returns the time as a double representing the number of days since 12/30/1899
    * `double getTimeD()` - returns the time of day in hours (on a 24-hour clock)
* `OrderPoints.java`
  * **Summary**:
  * **Methods**:
  * **Imports**:
* `time.java`
  * **Summary**:
  * **Methods**:
  * **Imports**:
* `tsError.java`
  * **Summary**:
  * **Methods**:
  * **Imports**:
* `writeToFile.java`
  * **Summary**:
  * **Methods**:
  * **Imports**:

### `other`
* `correction.java`
  * **Summary**:
* `CreateGaps.java`
  * **Summary**:
* `gap.java`
  * **Summary**:
* `gapRemove.java`
  * **Summary**:
* `getFiles.java`
  * **Summary**:
* `Inaccuracy.java`
  * **Summary**:
* `passFiles.java`
  * **Summary**:
* `test.java`
  * **Summary**:
* `testV.java`
  * **Summary**:

### `polynomial`
* `PolynomInterpolation.java`
  * **Summary**:
  * **Imports**:
* `PolynomMainTraj.java`
  * **Summary**:
  * **Imports**:
* `PolynomTS_Main.java`
  * **Summary**:
  * **Imports**:
* `PolyTrajErrorMain.java`
  * **Summary**:
  * **Imports**:

### `velocity_model`
* `optimize.java`
  * **Summary**:
  * **Imports**:
* `test_velocityModel.java`
  * **Summary**:
  * **Imports**:
* `v.java`
  * **Summary**:
  * **Imports**:
* `velocityInterpolation.java`
  * **Summary**:
  * **Imports**:
  
  
  