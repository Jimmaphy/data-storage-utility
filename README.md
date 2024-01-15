# Data Consolidation Utility

This project provides a utility for consolidating multiple data files into a single, easy-to-manage format. It uses regular expressions to determine how the data should be structured.

## Overview

For a project we have access to several data files. These files all need to be read, but this is a lot of work given the number of files. This application combines the files so that it is easy to oversee. Regular expressions are used here to determine how everything should be set up.

## Design Patterns

This project uses the following design patterns:

### Factory Method

The `FileFactory` class is an implementation of the Factory Method design pattern. It provides a static method `OpenFileFactory` that creates and returns an instance of a `File` subclass based on the file extension of the provided file path. The `DataStorageUtility` class uses this Factory Method to create a `File` object.

## Development

This project was developed through pair programming by Wesley and Dimitrie, with Wesley handling the programming work. Pair programming is a technique in which two programmers work together at one workstation. One, the driver, writes code while the other, the observer or navigator, reviews each line of code as it is typed in. The two programmers switch roles frequently.

## Files

### `DataStorageUtility.java`

This file defines the `DataStorageUtility` class, which is the entry point of the application. It uses the `FileFactory` to create a `File` object, and then reads all data from the file into a `RootNode` object.

### `RootNode.java`

This file defines the `RootNode` class, which represents the root of a tree structure. It has methods to check if a node with a specific key exists directly below the root or within a parent node.

### `LeafNode.java`

This file defines the `LeafNode` class, which represents a leaf in a tree structure. A leaf node is a node that does not have any child nodes.

### `CompositeNode.java`

This file defines the `CompositeNode` class, which represents a composite node in a data structure. A composite node is a node that can have child nodes. Each `CompositeNode` has a key that uniquely identifies it within the data structure, and a list of child nodes.

### `IdentifiableNode.java`

This file defines the `IdentifiableNode` interface, which is implemented by classes that represent nodes in a data structure. Each `IdentifiableNode` has a key that uniquely identifies it within the data structure.

### `CSVFile.java`

This file defines the `CSVFile` class, which provides methods for reading data from a CSV file and writing data to a CSV file. The data is represented as a `RootNode` of `IdentifiableNode` objects.

### `FileFactory.java`

This file defines the `FileFactory` class, which provides a static method for creating `File` instances based on a file path.

## Usage

To use this utility, run the `DataStorageUtility` class with a file path as an argument. This will read all data from the file into a `RootNode` object.

## Contributing

To contribute to the project, clone the repository, make your changes, and submit a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for details.
