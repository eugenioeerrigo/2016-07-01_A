package it.polito.tdp.formulaone.model;

public class DriversPair {
	
	private Driver driver1;
	private Driver driver2;
	private int num;
	
	public DriversPair(Driver driver1, Driver driver2, int num) {
		this.driver1 = driver1;
		this.driver2 = driver2;
		this.num = num;
	}

	public Driver getDriver1() {
		return driver1;
	}

	public void setDriver1(Driver driver1) {
		this.driver1 = driver1;
	}

	public Driver getDriver2() {
		return driver2;
	}

	public void setDriver2(Driver driver2) {
		this.driver2 = driver2;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	
	

}
