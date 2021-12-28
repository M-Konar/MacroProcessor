

import java.util.ArrayList;

public class MemoryEntity {

	boolean busy;
	int address;
	ArrayList<String> whoNeedsMe;
	public MemoryEntity() {
		busy = false;
		address = 0;
		whoNeedsMe = new ArrayList<String>();
	}
	
}
