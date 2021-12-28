

import java.util.ArrayList;

class ALUEntity{
	boolean busy;
	String op;
	String Vj;
	String Vk;
	String Qj;
	String Qk;
	ArrayList<String> whoNeedsMe;
	public ALUEntity(){
		busy = false;
		op = "";Vj="";Vk="";Qj="";Qk="";
		whoNeedsMe = new ArrayList<String>();
	}
}
