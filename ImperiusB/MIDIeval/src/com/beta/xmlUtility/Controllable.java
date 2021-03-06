package com.beta.xmlUtility;

import java.util.ArrayList;

public class Controllable {
	private String f_controllerName_s;
	ArrayList<Integer> f_attributes_list = new ArrayList<Integer>();
	
	public int fn_ReturnFunctionValue(String functionName){
		return (int) f_attributes_list.get(0);
	}
	public Controllable(String f_controllerName_s,
			ArrayList<Integer> f_ArrayList) {
		setF_controllerName_s(f_controllerName_s);
		setF_attributes_list(f_ArrayList);
	}

	public String getF_controllerName_s() {
		return f_controllerName_s;
	}

	@Override
	public String toString() {
		return ("MIDI Function:" + this.getF_controllerName_s()
				+ " Attributes: " + this.f_attributes_list.toString());
	}

	public ArrayList<Integer> getF_attributes_list() {
		return f_attributes_list;
	}

	public void setF_attributes_list(ArrayList<Integer> f_attributes_list) {
		this.f_attributes_list = f_attributes_list;
	}
	
	public void setF_controllerName_s(String f_controllerName_s) {
		this.f_controllerName_s = f_controllerName_s;
	}

}
