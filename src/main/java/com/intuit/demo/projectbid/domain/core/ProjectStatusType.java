package com.intuit.demo.projectbid.domain.core;

public enum ProjectStatusType {
	O, C;
	
	public static boolean contains(String value) {
	    for (ProjectStatusType c : ProjectStatusType.values()) {
	        if (c.name().equals(value)) {
	            return true;
	        }
	    }
	    return false;
	}
}
