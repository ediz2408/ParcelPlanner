# Driver Route Optimiser (K-Means Clustering)

This is a Java desktop application that uses a K-Means clustering algorithm to group delivery locations and assign them to drivers. 

The user enters a set of coordinates and a list of available drivers. The program then clusters the coordinates together (where *k* is the number of drivers) and assigns one driver to each cluster so they cover a compact area and drive the shortest overall distance.

## Features

- **Login & Registration:** Users can register a new account and log in. Credentials are saved in `userpass.txt`.
- **Driver & Address Input:** Add and manage driver details and delivery coordinates through the GUI.
- **CSV Storage:** Driver details and address coordinates are saved directly to `driver.csv` and `address.csv`.
- **K-Means Clustering:** Automatically groups coordinates based on distance, setting the number of clusters (*k*) to match the number of drivers.
- **Graph View:** Visualises the coordinate clusters and centres on a graph.

## Project Files

- `src/` - Java source files
- `lib/` - External libraries used by the project
- `address.csv` - Stores the delivery coordinates and address data
- `driver.csv` - Stores the driver records
- `userpass.txt` - Stores login details
- `driver_button.png`, `graph_button.png`, `location_button.png` - Icons used in the GUI

## How to Run

### In Eclipse
1. Download or clone the repository.
2. In Eclipse, go to **File** -> **Open Projects from File System...** and select the project folder.
3. Make sure the JAR files in `lib/` are on the project Build Path (**Right-click project** -> **Build Path** -> **Configure Build Path...**).
4. Run the main class inside the `src/` folder.

### Using the JAR file
If you are running the compiled `.jar` file directly, make sure `address.csv`, `driver.csv`, `userpass.txt`, and the three `.png` button images are in the exact same folder as the `.jar` file so the program can find them.
