# MacroProcessor
This is a Java code that implements a simulation for a computer system using the Tomasulo's algorithm. The code simulates the execution of instructions in a computer system with multiple functional units and tracks the dependencies between instructions.



## Simulation Overview

The simulation class `Simulation` contains several data structures and methods to simulate the execution of instructions. Here's an overview of the important components:

### Data Structures

- `Hashtable<String, Integer> type_cycles`: Stores the number of cycles required for each instruction type.
- `ArrayList<MemoryEntity> loadStation`: Represents the load stations.
- `ArrayList<MemoryEntity> storeStation`: Represents the store stations.
- `ArrayList<ALUEntity> addSubStation`: Represents the add/subtract functional units.
- `ArrayList<ALUEntity> mulDivStation`: Represents the multiply/divide functional units.
- `ArrayList<TableEntity> instructionTable`: Stores the information about instructions.
- `ArrayList<RegisterEntity> registerFile`: Represents the register file.
- `ArrayList<Double> memory`: Represents the memory.

### Methods

- `initialize()`: Initializes the data structures with default values.
- `isFull(ArrayList<MemoryEntity> array, int size)`: Checks if a memory station is full.
- `isFull2(ArrayList<ALUEntity> array, int size)`: Checks if an ALU station is full.
- `whoNeedsMe(String opName, String required)`: Updates the list of instructions that depend on a specific result.
- `occupyInRegFile(String regName, String occupiedBy)`: Occupies a register in the register file.
- `searchInRegFile(String regName)`: Searches for a register value in the register file.
- `performOp(double reg1, double reg2, String op)`: Performs the specified arithmetic operation.
- `whoNeedsMeAssign(ArrayList<String> whoNM, String required, double value)`: Assigns values to the instructions that depend on a specific result.
- `removeFromRegFile(String Qi, double value)`: Removes the register dependency and updates the register value.
- `executeStageALU(ArrayList<ALUEntity> station, String st, ArrayList<ArrayList<String>> whoNeedsMeList, ArrayList<String> opNameList, ArrayList<Double> valuesList)`: Executes the ALU stage for a specific station.
- `executeStageMemS(ArrayList<MemoryEntity> station, String st, ArrayList<String> opNameList)`: Executes the store stage for a specific station.
- `executeStageMemL(ArrayList<MemoryEntity> station, String st, ArrayList<ArrayList<String>> whoNeedsMeList, ArrayList<String> opNameList, ArrayList<Double> valuesList)`: Executes the load stage for a specific station.
- `simulate()`: Starts the simulation and executes the instructions in each cycle.

## How to Use

To use this code, you can follow these steps:

1. Create an instance of the `Simulation` class.
2. Call the `simulate()` method to start the simulation.
3. Customize the code as needed by modifying the instructions, functional units, or other parameters.

```java
public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.simulate();
    }
}
```

## Note

This code is provided as a starting point for the simulation. You may need to modify and extend it based on your specific requirements.
