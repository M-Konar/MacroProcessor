import java.io.File;
import java.util.*;
public class Simulation {
	Hashtable<String, Integer> type_cycles = new Hashtable<String, Integer>();
	ArrayList<MemoryEntity> loadStation = new ArrayList<MemoryEntity>(2);
	ArrayList<MemoryEntity> storeStation = new ArrayList<MemoryEntity>(2);
	ArrayList<ALUEntity> addSubStation = new ArrayList<ALUEntity>(3);
	ArrayList<ALUEntity> mulDivStation = new ArrayList<ALUEntity>(3);
	ArrayList<TableEntity> instructionTable = new ArrayList<TableEntity>();
	ArrayList<RegisterEntity> registerFile = new ArrayList<RegisterEntity>(11);
	ArrayList<Integer> memory = new ArrayList<Integer>(1024);
	int clock = 0;
	
	public void initialize() {
		for (int i = 0; i < 11; i++) {
			registerFile.set(i,new RegisterEntity("F"+i,"0"));
		}
		for (int i = 0;i<loadStation.size();i++) {
			loadStation.set(i, new MemoryEntity());
			storeStation.set(i, new MemoryEntity());
		}
		for (int i = 0; i < addSubStation.size(); i++) {
			addSubStation.set(i, new ALUEntity());
			mulDivStation.set(i, new ALUEntity());
		}
	}
	public void simulate() {
		// this function represents the flow 
		//TODO make sure to print at the end of each cycle 
				
		while(!isExecutionDone()) {
			//Do the whole logic here
			//go Over the instructions table from start to end 
			// on each instructions see if there anything that can be done t it (i.e. issue, execute, writeback)
			// in case there's an actio on it, go do the action and updte the subscription mechanism 
			// else skip the the instruction and go to the one after it 
			
			
			printOutput();
			clock++;	
		}
		
		
		
		
	}
	public boolean isExecutionDone() {
		// this function figures if all instructions in the queue got doneand returns true, otherwise false
		
		return true;
	}
	
	public void loadInput() {
		//TODO: fill the instruction table with the inputs from the input.txt 
		//TODO: fill the instruction types from the input.txt file
		
	}
	public  void printOutput() {
		
	}

	public static void main (String [] args) {
		//String instructionsFile = File.read
	}
}
