---
output: pdf_document
---
# Interpolation-Methods
*A collection of programs for implementing and testing various interpolation methods.*


## Organization
There are three main components to this repository.

> 1. Java project
> 2. Data files
> 3. Presentation material

The Java project is divided into six packages. Each package includes `.java` files that are related to each other. The trajectory folder includes all of the files used in the interpolation analysis. They also include the results for the presentation summarizing this research (located in the `First presentation` folder).

The general procedure for testing an interpolation method involves

> 1. Creating gaps in actual files to which an interpolation porcedure can be applied
> 2. Creating an implementation of the interpolation
> 3. Parsing the files with gaps and applying the interpolation procedure to fill in the gap. Then writing the interpolated data to a new set of files
> 4. Finding the accurracy of the method by comparing the interpolated data to the actual data. 

Typically each step is a separate java program.

##Java packages and files

Note that some programs will have two forms in which one form of the program will have the word "general" in its name. This occured because some programs were modified to read a more general set of files in which information is contained in the filenames themselves. Refer the the class summaries for more specific information.

### `cubic`
This package includes files related to cubic spline interpolation. It uses the [math library](http://commons.apache.org/proper/commons-math/) of [Apache Commons](http://commons.apache.org/) and implement a [natural cubic spline interpolator](http://www.math.drexel.edu/~tolya/cubicspline.pdf).

* `CubicErrorTraj.java`
  * **Summary**: Reads orignal trajectory files and cubic interpolated trajectories (located in the `Cubic_Traj` folder). Each pair of files (the actual trajectory and the corresponding interpolated trajectory) is compared using the `error` class. The error (in kilometers), error squared (kilometers squared), and distance (km) for each trajectory is stored in an array. These values are then written to a `.plt` file (specifically the `CubicErrorTraj.plt` file located in the `Error_Analysis` folder. Note that the distance returned is the total distance of the trajectory (rather than the distance of the interpolated gap).
  * **Imports**: `error.java` and `writeToFile.java`; both are from the `main` package.
* `CubicErrorTrajGeneral.java`
  * **Summary**: Reads original trajectory files from the folder `OriginalTrajectories` and the interpolated trajectories from the folder `Cubic_Traj`. It calculates the cumulative error (km), error squared (km squared), and distance (of the entire original trajectory, km) for each original trajectory and the corresponding interpolated trajectory. Note that the file assumes the length of the interpolated gap is included in the filename after the first 15 characters. It's also important that the length is followed by an underscore character and at least 15 more characters (including the filename extension but excluding the aforementioned underscore). An example of a filename that would work is `20070921120306_50_13450_Cubic_Traj.plt` where 50 is the length of the gap.
  * **Imports**: `error.java` and `writeToFile.java` from the `main` package.
* `CubicErrorTS.java`
  * **Summary**: Performs the same task as `CubicErrorTraj.java` except it uses `tsError.java` to calculate the error between two time series. Reads actual time series files from the folder `Original_TS` and reads the interpolated time series from the folder `Cubic_TS`. The final results for each file are written to the file `CubicTS.plt` in the `Error_Analysis` folder.
  * **Imports**: `tsError.java` and `writeToFile.java`; both from the `main` package.
* `CubicErrorTS_General.java`
  * **Summary**: Performs the same error analysis as `CubicErrorTS` but reads the gap length from the file names. The gap length should be embedded in the filename as an integer and hsould be preceded by exactly 18 characters, followed by an underscore and then at least 11 more characters (including the file extension). An example of a valid filename is `20070921120306_TS_100_Cubic_222281.plt` where 100 is the size of the gap.
* `CubicSplineMainTraj.java`
  * **Summary**: Gets the files in the folder `Trajectory_with_gaps` and applies the cubic spline interpolation method to filling in the gaps. Creates a new set of files and writes the files to the folder `Cubic_Traj`.
  * **Imports**: `ExtractLatLon.java` and `writeToFile.java`; both from `main`.
* `CubicTSMain.java`
  * **Summary**: Performs the same function as `CubicSplineMainTraj.java` but with time series data. Reads data from `Time_series_wtih_gaps` and writes to `Cubic_TS`.
  * **Imports**: `writeToFile.java`
* `SplineInterpolation.java`
  * **Summary**: Provides an implementation of the cubic spline interpolator using the methods provided by the Apache Commons Math library. **Constructed** with the gap length of the particular dataset. It can interpolate both time series and trajectory data.
  * **Methods**:
    * `setTS(double[])` - called with the time series as a parameter. The time series must be an array of doubles
    * `setTraj(String, String)` - used to pass the trajectory data. Two string parameters: one is the latitude values while the other is the longitude values (both should be comma seperated values stored as a single string).
    * `string[] interpolateTraj()` - returns the interpolated trajectory as a list of strings formatted to the structure of GeoLife data (i.e. latitude, longitude, 0, altitude, etc...) where the non-trajectory values are fixed.
    * `double[] interpolateTS()` - returns the interpolated time series as an array of doubles.
    * `int getNumInt` - can only be called after one of the interpolate methods. Returns the number of points that were interpolated.
  * **Imports**: `org.apache.commons.math3.analysis.UnivariateFunction`, `org.apache.commons.math3.analysis.interpolation.SplineInterpolator`,`org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator`, and `OrderPoints` (from the `main` package).
* `cubicTS_General.java`
  * **Summary**: Applies the cubic spline interpolator to a general set of files read from the folder `TS_Gaps`. Assumes that the gap size is embedded in the filename as an integer with 18 characters preceding it and 4 characters following it (the last four characters should be the filename extension, i.e. `.plt`).
  * **Imports**: `writeToFile.java` from `main`.
* `cubicTrajGeneral.java`
  * **Summary**: Applies cubic spline interpolation to a set of trajectories with gaps. It is assumed that the gap length is embedded in the filename with exactly 15 characters preceding it. It should be followed only by the four character file extension.
  * **Imports**: `ExtractLatLon.java` and `writeToFile.java` from the `main` folder.

### `linear_NearestNeighbor`
This package includes the files that implement the linear and nearest neighbor interpolation methods. 
* `LinearInterpolationMainTraj.java`
  * **Summary**: Retrieves trajectory files with gaps from the folder `Trajectory_with_gaps`. Applies the linear interpolation algoirithm and writes the new files to the folder `LinInt_Traj`.
  * **Imports**: `writeToFile.java` from the `main` package.
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
  * **Imports**: `writeToFile.java` from `main`.
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
  * **Imports**: `tsError.java` and `writeToFile.java` (from main)

### LOESS
* `LoessErrorTrajGeneral.java`
  * **Summary**: Reads original trajectory files and LOESS interpolated trajectories (located in the `Loess_Traj` folder). Each pair of files (the actual trajectory and the corresponding interpolated trajectory) is compared using the `error` class. The error (in kilometers), error squared (kilometers squared), and distance (km) for each trajectory is stored in an array. These values are then written to a `.plt` file (namely, the `LoessErrorTraj.plt` file located in the `Error_Analysis` folder. Note that the distance returned is the total distance of the trajectory rather than the distance of the interpolated gap. Assumes the gap length is embedded in the filename after the first 15 characters and followed by an underscore and at least 15 characters.
  * **Imports**: `error.java` and `writeToFile.java`
* `LoessErrorTS_General.java`
  * **Summary**: Read original time series files and LOESS interpolated time series (located in the `Loess_TS` folder). Each pair of files (the original time series and the corresponding interpolated time series) is compared using the `tsError` class. The error (Hilberts), the error squared (Hilberts squared), and the legnth (number of points) are recorded in an array. The values are written to the `LoessTSError.plt` file in the `Error_Analysis` folder. Assumes the gap length is embedded in the filename after the first 18 characters and followed by an underscore and at least 11 characters.
  * **Imports**: `tsError.java` and `writeToFile.java`
* `LoessInterpolation.java`
  * **Summary**: Provides an implementation of the LOESS interpolator using the Apache Commons Math library.
  * **Methods**:
    * **Constructor** `LoessInterpolation(int)` - the constructor consists of a single integer representing the length of the gap to be interpolated.
    * `setTraj(string, string)` - this function takes two string parameters which represent the latitude and longitude values (both strings should be a series of values separated by commas). Call this function if interpolating trajectories.
    * `setTS(double[])` - this function sets the time series to be interpolated. The parameter must be an array of doubles representing the Hilbert values.
    * `string[] interpolateTraj()` - applies the LOESS interpolator to the trajectory data. It returns the interpolated values in the GeoLife format as an array of strings with each element of the array denoting a single data value. The function `setTraj` must be called before this function.
    * `double[] interpolateTS()` - applies the LOESS interpolator to the time series data. It returns the interpolated Hilbert values as an array of doubles. The function `setTS` must be called before this function.
  * **Imports**: the following from `org.apache.commons.math3.analysis`: `UnivariateFunction`, `interpolation.LoessInterpolator`, `interpolation.SplineInterpolator`, and `interpolation.UnivariateInterpolator`, along with `OrderPoints.java` from `main`.
* `LoessTrajGeneral.java`
  * **Summary**: This is the main file for interpolating trajectories with LOESS. It reads the trajectories with gaps from the `Trajectory_Gaps` folder and writes the interpolated files to the `Loess_Traj` file. Assumes the gap length is embedded in the file name directly before the four character file extension and directly after 15 characters.
  * **Imports**: `ExtractLatLon.java` and `writeToFile` from `main`.
* `LoessTS_General.java`
  * **Summary**: This program reads time series from the folder `TS_Gaps` and applies the LOESS interpolator to them. It writes the interpolated files to the folder `Loess_TS`. Assumes the gap length is embedded in the file name directly before the four character file extension and directly after 18 characters.
  * **Imports**: `writeToFile` from `main`.

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
  * **Summary**: Sorts two arrays of corresponding x and y points in ascending order based on the x values. For example, if x = {1, 3, 2} and y = {9, 8, 7} then the resulting arrays would be x = {1, 2, 3} and y = {9, 7, 8}.
  * **Methods**:
    * **Constructor** `OrderPoints(Double[], Double[])` - accepts two arrays of Double values; the first is the x values and the second is the y values.
    * `Double[] getX()` - returns the list of sorted x values
    * `Double[] getY()` - returns the list of corresponding y values.
* `time.java`
  * **Summary**: Performs analysis of a double value that represents the number of days since 12/30/1899
  * **Methods**:
    * **Constructor** `time(double)` - constructed with a single value representing the number of days since 1899
    * `print()` - prints the hour, minutes, seconds, and the fractional part of the seconds value (represents time of day in the 24-hour clock format)
    * `int getMin()` - returns the number of minutes (a value from 0 to 59)
    * `int getSec()` - returns the seconds (an integer 0 to 59)
    * `int getHour()` - returns the hour of the day (an integer 0 to 23)
* `tsError.java`
  * **Summary**: Reads two files of time series values and calculates the difference between the two (error)
  * **Methods**:
    * **Constructor** `tsError(string, string, boolean, boolean, int)` - the first sting provides the filepath for the actual time series values while the second string is the filepath for the interpolated time series. The first boolean denotes whether the file's values are separated by commas (when set to true), and the second denotes the same style but for the interpolated time series. The final value is an integer representing the gap length of the trajectory or time series (i.e. the number of interpolated points).
    * `double getErrorSq()` - returns the error squared
    * `double getError()` - returns the total error
    * `int getLen()` - returns the length of the time series or the number of points
* `writeToFile.java`
  * **Summary**: Creates a specified file (or overwrites file if the name already exists) and writes a given list of strings to the file
  * **Methods**:
    * **Constructor** `writeToFile(string[], string)` - the array of strings includes the contents to be written to the file while the string is the filepath (with the filename and extension)
    * `write()` - performs the main operation of writing contents to file
### `other`
This package contains a number of miscellaneous programs - some of which are incomplete - that were used mainly for testing or for one time tasks. Since these programs aren't really useful, the descriptions are not very detailed.
* `correction.java`
  * **Summary**: This program was originally created to fix a problem with linearly interpolating the time series data. However, it ended up not being needed.
* `CreateGaps.java`
  * **Summary**: This program is used to create the original list of trajectory files with gaps. It parses the files in the `trajectory` folder removes a predetermined number of lines. It then writes the files to the same folder. This program ran once and afterwards the trajectories with gaps were moved to the folder `Trajectory_with_gaps`.
* `gap.java`
  * **Summary**: This is an incomplete program.
* `gapRemove.java`
  * **Summary**: Originally intended to parse the trajectory files and fill in gaps where necessary (anytime the time gap between adjacent points is greater than 5 seconds). However the program needs to be debugged.
* `getFiles.java`
  * **Summary**: Essentially the same as `gapRemove.java` but includes the main method and extracts files from the folder `test`.
* `Inaccuracy.java`
  * **Summary**: This program ran once. It calculates the length of the gaps of both the trajectories and time series. For example, if a gap in a trajectory were to exist between 10am and 11am, this program would calculate (using data from the actual trajectory) the total distance in kilometers traveled by that individual during that time interval. It outputs the distance values to the file `totalDistance.plt` in the `Error_Analysis` folder. Each line of output consists of (in this order) the trajectory distance, time series distance, and the file size (to match the trajectory or time series with their respective distance values).
* `passFiles.java`
  * **Summary**: Incomplete program that was intended to pass files to `gapRemove.java`.
* `test.java`
  * **Summary**: Program used to test various classes and methods.
* `testV.java`
  * **Summary**: Another program to test various methods and classes.

### `polynomial`
This package includes files related to polynomial interpolation. The pacakge includes programs to support interpolating time series; however, only trajectory data have been used with the polynomial interpolating algorithm. Since the algorithm is so time consuming (also the reason it wasn't applied to time series data), there isn't a program to calculate error from the interpolated time series data.

* `PolynomInterpolation.java`
  * **Summary**: Implements a polynomial interpolation algorithm for use with trajectory and time series data. Uses the Apache Commons Math library to incorporate [Neville's algorithm](https://en.wikipedia.org/wiki/Neville%27s_algorithm) for evaluating the polynomial. NOTE: this algorithm is time consuming.
  * **Methods**:
    * **Constructor** `PolynomInterpolation(int)` - takes an integer representing the length of the gap
    * `setTraj(string lats, string lons)` - sets the latitude and longitude values for the trajectory. The values must be contained in a single string and separated by commas.
    * `setTS(double[])` - mutator method that stores the time series. The values must be passed as an array of doubles.
    * `string[] interpolateTraj()` - must be caled after `setTraj()`. Performs that desired interpolation on the trajectory data and returns a list of interpolated values. Note that only the interpolated values are returned and that the returned values are formatted to the plt format of the GeoLife data.
    * `double[] interpolateTS()` - Applies polynomial interpolator to time sries data. Returns an error if `setTS()` hasn't been called. The interpolated values are stored as an array of doubles.
    * `int getNumInt()` - can only be called after one of the interpolate methods. Returns the number of points that were interpolated.
  * **Imports**: `OrderPoints.java` (main), `org.apache.commons.math3.analysis.UnivariateFunction`, and `org.apache.commons.math3.analysis.interpolation.*`. 
* `PolynomMainTraj.java`
  * **Summary**: Reads files with gaps from the `Trajectory_with_gaps` folder and applies the polynomial interpolation program to each trajecotry. Writes the new trajectories to the folder `Polynom_Traj`.
  * **Imports**: `ExtractLatLon.java` and `writeToFile.java`
* `PolynomTS_Main.java`
  * **Summary**: Parses time series files and applies polynomial interpolation thereby creating a new set of files with interpolated values. Reads files from the folder `Time_series_with_gaps` and writes the interpolated time series to the folder `Polynom_TS`. 
  * **Imports**: `writeToFile.java`
* `PolyTrajErrorMain.java`
  * **Summary**: Calculates the error generated by the polynomial interpolation algorithm for trajectory data. Reads the original trajectories from the folder `trajectory` and the interpolated trajecotires from `Polynom_Traj`. The error values are written to the file `PolynomErrorTraj.plt` in the `Error_Analysis` folder.
  * **Imports**: `error.java` and `writeToFile.java`

### `velocity_model`
The velocity model is an alternative method of interpolating trajectory points. A summary of the model can be found in the presentation folder (on slide 40 of the presentation). The `velocity_model` package includes programs associated with the model.

* `optimize.java`
  * **Summary**: This program is incomplete. It is meant to optimize the user-defined velocity threshold value in the model based on the actual trajectory.
  * **Imports**: `error.java`
* `test_velocityModel.java`
  * **Summary**: A program to pass values to the velocity model and write the interpolated points to a new file.
  * **Imports**: `writeToFile.java`
* `v.java`
  * **Summary**: Calculates the velocity (km/hr) and compass direction of a trajectory between two given spatial points.
  * **Methods**:
    * **Constructor** `v(string, string)` - accepts two strings or two lines from the GeoLife dataset (representing two separate points). It is assumed that the first string precedes the second in time. 
    * `double getVel()` - returns the velocity
    * `double getDir()` - returns the direction
    * `double dir(double, double, double, double)` - returns the direction between the given latitude and longitude values. This is a private static method.
    * `double dist(double, double, double, double)` - returns the distance in kilometers between the given points. This is also a private static method.
  * **Imports**: `getNum.java`
* `velocityInterpolation.java`
  * **Summary**: This is the main program for the model. It reads a file of trajectory points and is capable of interpolated a specificed number of points at a specificed number of locations within the trajectory.
  * **Methods**:
    * **Constructor** `velocityInterpolation(string, int)` - the string denotes the filepath for the trajectory to be read into the model and the integer represents the length (number of lines) of the file. Note that the file must match the format of the GeoLife dataset.
    * `string[] beginInterpolation(int[], double)` - the integer array represents the starting index of each gap (e.g. the array {2, 12, 144} means that the model will interpolate between the 2nd and 3rd points, the 12th and 13th, and the 144th and 145th points). The double represents the value of the user-defined threshold. This method returns an array of strings which includes the original points and the interpolated points.
    * `int getMatch(int)` - given the index of a current trajectory point, this method will return the index of a match value (the selection procedure for finding a match is outlined in the presentation)
    * `double acceleration(int)` - returns the acceleration (km/hr/hr) associated with a trajectory point at the given integer index
    * `double slope(double)` - given a bearing direction in degrees, this method returns the slope
    * `double[] interpolate(int, int)` - computes the characteristic values (latitude, longitude, time, direction, and velocity) of the new point (interpolated point) given the index of the neighboring point and the match.
    * `double refAngle(double)` - returns the reference angle for a given bearing in degrees
    * `double directionChange(int)` - gives the change in direction association with point at the given integer index
    * `update(double[], int)` - updates the member variables with the given array of doubles (in the form {latitude, longitude, time, direction, velocity}). Inserts the new values after the point at the given integer index.
    * `ArrayList<String> getInterpolatedLines()` - returns a list of the interpolated points (in the GeoLife data format)
  * **Imports**: `getNum.java`

### `roughness`
This package includes two programs that were used to calculate the roughness of trajectories. Essentially roughness is calculated as the aggregate directional change between pairs of points divided by the number of points (giving the average directional change per point).

* `calculateRoughness.java`
  * **Summary**: This program reads a trajectory file and calculates the total roughness.
  * **Methods**:
    * **Constructor** `calculateRoughness(string)` - the string represents the path of the trajectory file.
    * `double getRoughness()` - returns the roughness as a double.
  * **Imports**: `v.java` from the `velocity_model` package.
* `roughnessGeneral.java`
  * **Summary**: This program reads all of the trajectory files from the folder `Trajectory_Gaps` and calculates the roughness for each one. It writes the output to the file `roughness.plt` in the folder `Error_Analysis`.
  * **Imports**: `writeToFile.java` from the `main` package.


###`com.space.filling.curve`
This package was modified so that it could convert a large set of desired trajectories to time series. Some of the previously described programs such as `getNum.java` and `writeToFile.java` were imported to this project so that the conversion could be completed.

`Construct2DimCurve_v2.java` is the only original file that is modified. The only methods that have been added/altered are the `main()` and `mainS()` methods.

