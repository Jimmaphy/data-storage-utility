# Data Storage Utility

This project provides a console utility for combining multiple data files into a single file.
When started, the application walks you through five faces.
During each face, a part of the merging is configured.
The faces are:

1. **Starting**: shows a welcome message.
2. **Collecting**: which helps you to select your data files.
3. **Defining**: defining the different steps to take for correct merging.
4. **Merging**: the actual magic.
5. **Finished**: the application is done, where do you want to save the result.

## Case

For a project with the municipality of Goes, several data files from different sources are used.
These files all need to be read, but this is a lot of work given inconsistency of data.
This application combines the files easily in order to continue working on the actual product.

## Design Patterns

In order to create the product, design patterns were used to adhere to industry standards.
Using design patterns allow the application to be designed in a constructive and common way.
The application uses (1) `Static Factory Method` to determine the type of file object to make;
(2) `Composite` in order to make a digital representation of the data;
(3) `Builder` to help create the composite structure;
(4) `State` to keep track of the current phase in the merging-process;
(5) `Iterator` to loop over all the nodes of the composite structure &
(6) `Decorator` to dynamically adjust the processing of data before merging.

### Static Factory Method

![Composite Diagram](./diagrams/Static%20Factory%20Diagram.png)

The goal of the factory is to create instances of the `File` interface.
The `File` interface is implemented by classes that represent specific file types.
In the current state of the application, this includes the JSON and the CSV format.
These formats are therefore represented in the classes `CSVFile` and `JSONFile`.

The `FileFactory` class using a single static method to construct the right type.
This is achieved through a functionality introduced in Java 21, called Pattern Matching Switching.
This feature makes it easy to create various types of instances.
The code that performs the magic follows.
Based on the extension of the file, the right file instance is created and returned to the client.
Whenever an unsupported file would have been loaded, an error is thrown.

```java
return switch (extension) {
    case "csv" -> new CSVFile(filePath, true, ';');
    case "json" -> new JSONFile(filePath);
    default -> throw new IllegalArgumentException("Invalid file extension");
};
```

### Composite

![Composite Diagram](./diagrams/Composite%20Diagram.png)

The composite pattern allows the creation of a tree-like structure that represents a data file.
An `IdentifiableNode` represents a node in the tree.
This is then specified using a `CompositeNode`, representing a node that contains more nodes;
or a `LeafNode` which represents a data point.

### Builder

![Builder Diagram](./diagrams/Builder%20Diagram.png)

The builder pattern is used to create the structure whenever a file is read.
This is done using the `RootNode` class, which houses the functionality to build a tree.
For the client, the `addChild()` method is the only method of concern during this process.
Two versions of this method exist: one to add a child to the root of the tree, and one to a child to another node.
This is done through method overloading as seen in the diagram above.
The `RootNode` class, next to being a builder, manages the tree.

### State

![State Diagram](./diagrams/State%20Diagram.png)

The state or phase of the program is tracked using the `State` enum.
Inside the application, this enum is used exclusively by the `Commander`.
The `Commander` class is the code that manages the state and execution of the entire program.
The `execute()` method is used by the client to perform the next operation.
Whenever an operation returns true, the current phase is done.
The `Commander` makes sure to move the program to the next state whenever this occurs.
This can be seen in the code below.

```java
public boolean execute() {
    boolean executionResult = switch(this.state) {
        case STARTING -> this.executeStarting();
        case COLLECTING -> this.executeCollecting();
        case DEFINING -> this.executeDefining();
        case MERGING -> this.executeMerging();
        default -> throw new IllegalStateException();
    };

    if (executionResult) {
        this.nextState();
    }

    return this.state.equals(State.FINISHED);
}
```

### Iterator

![Iterator Diagram](./diagrams/Itterator%20Diagram.png)

The `RootNode` handles the iteration of the data structure.
As a manager, this class has the governance over the entire structure.
This way, navigating the tree becomes more simplistic.

### Decorator

![Decorator Diagram](./diagrams/Decorator%20Diagram.png)

A decorator pattern is used to process data within the files before combining them.
This way, it becomes possible to create multiple sets of action to be performed on data at once,
without immediately changing the saved data until you like the rules.

An abstract `BaseParser` is introduced to handle the actual processing of the data.
This approach was picked to allow different parser implementation to focus on how to handle different nodes,
without having to copy the base code for every single class.
The `NodeParser` class is used as the root of the decorator and implements an identity function,
this ends the loop of decorators.

## Development

This project was developed through pair programming by Wesley and Dimitrie, with Wesley handling the programming work.
Pair programming is a technique in which two programmers work together at one workstation. One, the driver, writes code
while the other, the observer or navigator, reviews each line of code as it is typed in. The two programmers switch
roles frequently.
