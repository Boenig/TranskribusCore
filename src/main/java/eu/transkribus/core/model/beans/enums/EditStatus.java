package eu.transkribus.core.model.beans.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.transkribus.core.util.EnumUtils;

public enum EditStatus {
	NEW(0, "New"),
	IN_PROGRESS(1, "In Progress"),
//	REOPENED,
//	RETAINED,
	DONE(2, "Done"),
//	APPROVED,
	FINAL(3, "Final"),
	GT(Integer.MAX_VALUE, "Ground Truth");
	
	int value=Integer.MIN_VALUE;
	String str=null;
	
	EditStatus(int value, String str) {
		this.value = value;
		this.str = str;
	}
	
	public int getValue() { return value; }
	public String getStr() { return str; }
	
    public static EditStatus fromString(String v) {
    	if (v != null) {
	        for (EditStatus c: EditStatus.values()) {
	            if (c.str.equals(v)) {
	                return c;
	            }
	        }
    	}
        throw new IllegalArgumentException("for setting new version status " + v);
    }

	public static String[] getStatusListWithoutNew() {
		String [] stati = EnumUtils.stringsArray(EditStatus.class);
		
	    final List<String> list = new ArrayList<String>();
	    Collections.addAll(list, stati); 
	    list.remove("New");
	    stati = list.toArray(new String[list.size()]);
		return stati;
	}	
}
