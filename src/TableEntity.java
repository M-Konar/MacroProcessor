

import java.util.ArrayList;

class TableEntity{
	String opCode;
	String dist;
	String j;
	String k;
	int issueCycle;
	int eStartCycle;
	int eEndCycle;
	int writeResult;
	public TableEntity(String opCode,String dist,String j, String k, int issueCycle, int eStartCycle, int eEndCycle, int writeResult ) {
		this.opCode = opCode;
		this.dist = dist;
		this.j = j;
		this.k = k;
		this.issueCycle = issueCycle;
		this.eStartCycle = eStartCycle;
		this.eEndCycle = eEndCycle;
		this.writeResult = writeResult;
	}
}
