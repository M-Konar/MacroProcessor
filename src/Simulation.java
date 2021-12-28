import java.io.File;
import java.util.*;
public class Simulation {
	Hashtable<String, Integer> type_cycles = new Hashtable<String, Integer>();
	ArrayList<MemoryEntity> loadStation = new ArrayList<MemoryEntity>();
	ArrayList<MemoryEntity> storeStation = new ArrayList<MemoryEntity>();
	ArrayList<ALUEntity> addSubStation = new ArrayList<ALUEntity>();
	ArrayList<ALUEntity> mulDivStation = new ArrayList<ALUEntity>();
	ArrayList<TableEntity> instructionTable = new ArrayList<TableEntity>();
	ArrayList<RegisterEntity> registerFile = new ArrayList<RegisterEntity>();
	ArrayList<Integer> memory = new ArrayList<Integer>(); 
	
	int clock = 0;
	
	public void initialize() {
		for (int i = 0; i < 11; i++) {
			registerFile.add(new RegisterEntity("F"+i,"0"));
		}
		System.out.println("added");
		for (int i = 0;i<2;i++) {
			System.out.println("added");
			loadStation.add( new MemoryEntity());
			storeStation.add( new MemoryEntity());
		}
		for (int i = 0; i < 3; i++) {
			addSubStation.add( new ALUEntity());
			mulDivStation.add( new ALUEntity());
		}
		type_cycles.put("L.D", 2);
		type_cycles.put("S.D", 1);
		type_cycles.put("MUL.D", 6);
		type_cycles.put("DIV.D", 10);
		type_cycles.put("ADD.D", 3);
		type_cycles.put("SUB.D", 3);
	}
	
	public static int isFull(ArrayList<MemoryEntity> array,int size) {
		for (int i =0;i<size;i++) {
			if(array.get(i).busy==false) {
				return i;
			}
		}
		return -1;
	}
	public static boolean isFull2(ArrayList<ALUEntity> array,int size) {
		return false;
	}
	
	public void occupyInRegFile(String regName,String occupiedBy) {
		for (int i = 0; i < registerFile.size(); i++) {
			if(registerFile.get(i).regName.equals(regName)) {
				if(registerFile.get(i).Qi.equals("0")) {
					registerFile.get(i).Qi=occupiedBy;
				}
			}
		}
	}
	
	public void simulate() {
		// this function represents the flow 
		//TODO make sure to print at the end of each cycle 
		int instTableIndex = 0;
		boolean isIssued = false;
		while(!isExecutionDone()) {
			TableEntity currentTableRow = instructionTable.get(instTableIndex);
			int index;
			switch(currentTableRow.opCode) {
				case "L.D" :
					index =isFull(loadStation,2);
					if(index !=-1){
						loadStation.get(index).busy=true;
						loadStation.get(index).address = Integer.parseInt(currentTableRow.j);
						currentTableRow.issueCycle = clock;
						occupyInRegFile(currentTableRow.dist,"L"+index);
					}
				break;
				case "S.D":
					index =isFull(storeStation,2);
					if(index !=-1){
						storeStation.get(index).busy=true;
						storeStation.get(index).address = Integer.parseInt(currentTableRow.j);
						currentTableRow.issueCycle = clock;
					}
				break;
				case "MUL.D":if(!isFull2(mulDivStation,3)) {
					
				}
				break;
				case "DIV.D":if(!isFull2(mulDivStation,3)) {
					
				}
				break;
				case "ADD.D":if(!isFull2(addSubStation,3)) {
					
				}
				break;
				case "SUB.D":if(!isFull2(addSubStation,3)) {
	
				}
				break;
			}
			//Do the whole logic here
			//go Over the instructions table from start to end 
			// on each instructions see if there anything that can be done t it (i.e. issue, execute, writeback)
			// in case there's an action on it, go do the action and update who needs me mechanism
			// else skip the the instruction and go to the one after it 
			//things to pay attention to: 
				// 
			
			
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
		Simulation s =new Simulation();
		s.initialize();
		s.simulate();
	}
}
