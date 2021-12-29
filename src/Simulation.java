import java.util.*;
public class Simulation {
	Hashtable<String, Integer> type_cycles = new Hashtable<String, Integer>();
	ArrayList<MemoryEntity> loadStation = new ArrayList<MemoryEntity>();
	ArrayList<MemoryEntity> storeStation = new ArrayList<MemoryEntity>();
	ArrayList<ALUEntity> addSubStation = new ArrayList<ALUEntity>();
	ArrayList<ALUEntity> mulDivStation = new ArrayList<ALUEntity>();
	ArrayList<TableEntity> instructionTable = new ArrayList<TableEntity>();
	ArrayList<RegisterEntity> registerFile = new ArrayList<RegisterEntity>();
	ArrayList<Double> memory = new ArrayList<Double>();
	
	int clock = 1;
	
	public void initialize(ArrayList<TableEntity> test) {
		instructionTable=test;
		for (int i = 0; i < 1024; i++) {
			memory.add(0.0);
		}
		for (int i = 0; i < 11; i++) {
			registerFile.add(new RegisterEntity("F"+i,"0",0));
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
	public static int isFull2(ArrayList<ALUEntity> array,int size) {
		for (int i =0;i<size;i++) {
			if(array.get(i).busy==false) {
				return i;
			}
		}
		return -1;
	}
	public void whoNeedsMe(String opName,String required){
          String op=opName.charAt(0)+"";
          int index=Integer.parseInt(opName.substring(1,opName.length()));
          switch(op){
			  case "M": mulDivStation.get(index).whoNeedsMe.add(required);
			  break;
			  case "A": addSubStation.get(index).whoNeedsMe.add(required);
			  break;
			  case "L": loadStation.get(index).whoNeedsMe.add(required);
			  break;
		  }
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
	public String searchInRegFile(String regName){
		for (int i = 0; i < registerFile.size(); i++) {
			if(regName.equals(registerFile.get(i).regName)){
				if(registerFile.get(i).Qi.equals("0")){
                     return registerFile.get(i).value+"";
				}
				else
					return registerFile.get(i).Qi;
			}

		}
		return "instruction is invalid";
	}
public double performOp(double reg1,double reg2,String op){
		switch(op){
			case "ADD": return reg1+reg2;

			case "SUB": return reg1-reg2;

			case "MUL": return reg1*reg2;

			case "DIV": return reg1/reg2;

			default : return 0.0;
		}
}
//bus method
public void whoNeedsMeAssign(ArrayList<String> whoNM,String required,double value){
	for (int i = 0; i < whoNM.size(); i++) {
		String op=whoNM.get(i).charAt(0)+"";
		int index=Integer.parseInt(whoNM.get(i).substring(1,whoNM.get(i).length()));
		switch(op){
			// assiging values to the whoneeds me
			// simulating writing values on the bus
			case "M":if(mulDivStation.get(index).Qj.equals(required)){
				mulDivStation.get(index).Qj="";
				mulDivStation.get(index).Vj= value;
			}
				if(mulDivStation.get(index).Qk.equals(required)){
					mulDivStation.get(index).Qk="";
					mulDivStation.get(index).Vk= value;
				}
          break;
			case "A":if(addSubStation.get(index).Qj.equals(required)){
				addSubStation.get(index).Qj="";
				addSubStation.get(index).Vj= value;
			}
				if(addSubStation.get(index).Qk.equals(required)){
					addSubStation.get(index).Qk="";
					addSubStation.get(index).Vk= value;
				}
				break;
		}

	}
}
	public void simulate() {
		// this function represents the flow 
		//TODO make sure to print at the end of each cycle 
		int instTableIndex = 0;
		//boolean isIssued = false;

		while(!isExecutionDone()) {
			// Execution Section :(
			for (int i = 0; i < addSubStation.size(); i++) {
				int instIndex=addSubStation.get(i).index;
				String opName= instructionTable.get(instIndex).opCode;
				if(addSubStation.get(i).Qj.equals("")&& addSubStation.get(i).Qk.equals("")&& instructionTable.get(instIndex).issueCycle !=0){
					if(instructionTable.get(instIndex).eEndCycle!=0){
						if(instructionTable.get(instIndex).writeResult==0){
							double value=performOp(addSubStation.get(i).Vj,addSubStation.get(i).Vk,addSubStation.get(i).op);
							whoNeedsMeAssign(addSubStation.get(i).whoNeedsMe,"A"+i,value);
							instructionTable.get(instIndex).writeResult=clock;
						}
					}else {
						if (instructionTable.get(instIndex).eStartCycle == 0) {
							instructionTable.get(instIndex).eStartCycle = clock;
						} else {
							int cycles = type_cycles.get(opName);
							if (clock - instructionTable.get(instIndex).eStartCycle + 1 == cycles) {
								instructionTable.get(instIndex).eEndCycle = clock;
							}
						}
					}
				}

			}
			for (int i = 0; i < mulDivStation.size(); i++) {
				int instIndex = mulDivStation.get(i).index;
				String opName = instructionTable.get(instIndex).opCode;
				if (mulDivStation.get(i).Qj.equals("") && mulDivStation.get(i).Qk.equals("") && instructionTable.get(instIndex).issueCycle != 0) {
					if (instructionTable.get(instIndex).eEndCycle != 0) {
						if (instructionTable.get(instIndex).writeResult == 0) {
							double value = performOp(mulDivStation.get(i).Vj, mulDivStation.get(i).Vk, mulDivStation.get(i).op);
							whoNeedsMeAssign(mulDivStation.get(i).whoNeedsMe, "A" + i, value);
							instructionTable.get(instIndex).writeResult = clock;
						}
					} else {
						if (instructionTable.get(instIndex).eStartCycle == 0) {
							instructionTable.get(instIndex).eStartCycle = clock;
						} else {
							int cycles = type_cycles.get(opName);
							if (clock - instructionTable.get(instIndex).eStartCycle + 1 == cycles) {
								instructionTable.get(instIndex).eEndCycle = clock;
							}
						}
					}
				}
			}
            //issue section :)
			if(instTableIndex<instructionTable.size()) {
				TableEntity currentTableRow = instructionTable.get(instTableIndex);
				int index;
				switch (currentTableRow.opCode) {
					case "L.D":
						index = isFull(loadStation, 2);
						if (index != -1) {
							loadStation.get(index).busy = true;
							loadStation.get(index).address = Integer.parseInt(currentTableRow.j);
							currentTableRow.issueCycle = clock;
							occupyInRegFile(currentTableRow.dist, "L" + index);
							loadStation.get(index).index = instTableIndex;
							instTableIndex++;
						} else {

						}
						break;
					case "S.D":
						index = isFull(storeStation, 2);
						if (index != -1) {
							storeStation.get(index).busy = true;
							storeStation.get(index).address = Integer.parseInt(currentTableRow.j);
							currentTableRow.issueCycle = clock;
							storeStation.get(index).index = instTableIndex;
							instTableIndex++;
						}
						break;
					case "MUL.D":
						index = isFull2(mulDivStation, 3);
						if (index != -1) {
							currentTableRow.issueCycle = clock;
							mulDivStation.get(index).busy = true;
							mulDivStation.get(index).op = "MUL";
							String op1 = searchInRegFile(currentTableRow.j);
							String op2 = searchInRegFile(currentTableRow.k);
							try {
								double value = Double.parseDouble(op1);
								mulDivStation.get(index).Vj = value;
							} catch (Exception e) {
								mulDivStation.get(index).Qj = op1;
								whoNeedsMe(op1, "M" + index);
							}
							try {
								double value = Double.parseDouble(op2);
								mulDivStation.get(index).Vk = value;
							} catch (Exception e) {
								mulDivStation.get(index).Qk = op2;
								whoNeedsMe(op2, "M" + index);

							}
							occupyInRegFile(currentTableRow.dist, "M" + index);
							System.out.println(mulDivStation.get(index).op + " " + mulDivStation.get(index).busy + " " + mulDivStation.get(index).Vj + " " + mulDivStation.get(index).Vk
									+ " " + mulDivStation.get(index).Qj + " " + mulDivStation.get(index).Qk);
							mulDivStation.get(index).index = instTableIndex;
							instTableIndex++;
						}
						break;
					case "DIV.D":
						index = isFull2(mulDivStation, 3);
						if (index != -1) {
							currentTableRow.issueCycle = clock;

							mulDivStation.get(index).busy = true;
							mulDivStation.get(index).op = "DIV";
							String op1 = searchInRegFile(currentTableRow.j);
							String op2 = searchInRegFile(currentTableRow.k);
							try {
								double value = Double.parseDouble(op1);
								mulDivStation.get(index).Vj = value;
							} catch (Exception e) {
								mulDivStation.get(index).Qj = op1;
								whoNeedsMe(op1, "M" + index);

							}
							try {
								double value = Double.parseDouble(op2);
								mulDivStation.get(index).Vk = value;
							} catch (Exception e) {
								mulDivStation.get(index).Qk = op2;
								whoNeedsMe(op2, "M" + index);


							}
							occupyInRegFile(currentTableRow.dist, "M" + index);
							System.out.println(mulDivStation.get(index).op + " " + mulDivStation.get(index).busy + " " + mulDivStation.get(index).Vj + " " + mulDivStation.get(index).Vk
									+ " " + mulDivStation.get(index).Qj + " " + mulDivStation.get(index).Qk);
							mulDivStation.get(index).index = instTableIndex;
							instTableIndex++;
						}
						break;
					case "ADD.D":
						index = isFull2(addSubStation, 3);
						if (index != -1) {
							currentTableRow.issueCycle = clock;
							occupyInRegFile(currentTableRow.dist, "A" + index);
							addSubStation.get(index).busy = true;
							addSubStation.get(index).op = "ADD";
							String op1 = searchInRegFile(currentTableRow.j);
							String op2 = searchInRegFile(currentTableRow.k);
							try {
								double value = Double.parseDouble(op1);
								addSubStation.get(index).Vj = value;
							} catch (Exception e) {
								addSubStation.get(index).Qj = op1;
								whoNeedsMe(op1, "A" + index);
							}
							try {
								double value = Double.parseDouble(op2);
								addSubStation.get(index).Vk = value;
							} catch (Exception e) {
								addSubStation.get(index).Qk = op2;
								whoNeedsMe(op2, "A" + index);
							}
							occupyInRegFile(currentTableRow.dist, "A" + index);
							System.out.println(addSubStation.get(index).op + " " + addSubStation.get(index).busy + " " + addSubStation.get(index).Vj + " " + addSubStation.get(index).Vk
									+ " " + addSubStation.get(index).Qj + " " + addSubStation.get(index).Qk);
							addSubStation.get(index).index = instTableIndex;
							instTableIndex++;
						}

						break;
					case "SUB.D":
						index = isFull2(addSubStation, 3);
						if (index != -1) {
							currentTableRow.issueCycle = clock;
							occupyInRegFile(currentTableRow.dist, "A" + index);
							addSubStation.get(index).busy = true;
							addSubStation.get(index).op = "SUB";
							String op1 = searchInRegFile(currentTableRow.j);
							String op2 = searchInRegFile(currentTableRow.k);
							try {
								double value = Double.parseDouble(op1);
								addSubStation.get(index).Vj = value;
							} catch (Exception e) {
								addSubStation.get(index).Qj = op1;
								whoNeedsMe(op1, "A" + index);
							}
							try {
								double value = Double.parseDouble(op2);
								addSubStation.get(index).Vk = value;
							} catch (Exception e) {
								addSubStation.get(index).Qk = op2;
								whoNeedsMe(op2, "A" + index);
							}
							occupyInRegFile(currentTableRow.dist, "S" + index);
							System.out.println(addSubStation.get(index).op + " " + addSubStation.get(index).busy + " " + addSubStation.get(index).Vj + " " + addSubStation.get(index).Vk
									+ " " + addSubStation.get(index).Qj + " " + addSubStation.get(index).Qk);
							addSubStation.get(index).index = instTableIndex;
							instTableIndex++;
						}
						break;
				}
			}
			//Do the whole logic here
			//go Over the instructions table from start to end 
			// on each instructions see if there anything that can be done t it (i.e. issue, execute, writeback)
			// in case there's an action on it, go do the action and update who needs me mechanism
			// else skip the the instruction and go to the one after it 
			//things to pay attention to: 

			
			
			printOutput();
			clock++;	
		}

		
		
		
		
	}
	public boolean isExecutionDone() {
		for (int i = 0; i < instructionTable.size(); i++) {
			if(instructionTable.get(i).writeResult==0){
				return false;
			}
		}
		
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

		ArrayList<TableEntity> test=new ArrayList<TableEntity>();
		test.add(new TableEntity("MUL.D","F3","F2","F1"));
		test.add(new TableEntity("ADD.D","F6","F4","F1"));
		test.add(new TableEntity("DIV.D","F1","F3","F1"));
		test.add(new TableEntity("SUB.D","F3","F1","F6"));
		test.add(new TableEntity("SUB.D","F5","F6","F1"));
		test.add(new TableEntity("SUB.D","F10","F4","F2"));
		test.add(new TableEntity("ADD.D","F6","F4","F1"));
		s.initialize(test);
		s.simulate();

	}
}
