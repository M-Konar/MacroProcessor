

import java.io.File;
import java.util.*;
public class MainCode {
//	Hashtable<String, int> type_cycles = new Hashtable<String, int>();
	ArrayList<MemoryEntity> loadStation = new ArrayList<MemoryEntity>(2);
	ArrayList<MemoryEntity> storeStation = new ArrayList<MemoryEntity>(2);
	ArrayList<ALUEntity> addSubStation = new ArrayList<ALUEntity>(3);
	ArrayList<ALUEntity> mulDivStation = new ArrayList<ALUEntity>(3);
	ArrayList<TableEntity> instructionTable = new ArrayList<TableEntity>();
	ArrayList<RegisterEntity> registerFile = new ArrayList<RegisterEntity>(11);
	ArrayList<Integer> memory = new ArrayList<Integer>(1024);
	
	public MainCode(ArrayList<String> instuctions) {
//		Instructions  = instuctions;
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
	public static void simulate() {
		
	}
	
	public void loadInput() {
		//TODO: fill the instruction table with the inputs from the input.txt
		//
	}

	public static void main (String [] args) {
		//String instructionsFile = File.read
	}
}