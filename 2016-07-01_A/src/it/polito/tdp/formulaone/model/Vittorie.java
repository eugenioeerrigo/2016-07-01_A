package it.polito.tdp.formulaone.model;

public class Vittorie {
	
	private Driver d1;
	private Driver d2;
	private int vittorie;
	
	public Vittorie(Driver d1, Driver d2, int vittorie) {
		this.d1 = d1;
		this.d2 = d2;
		this.vittorie = vittorie;
	}

	public Driver getD1() {
		return d1;
	}

	public void setD1(Driver d1) {
		this.d1 = d1;
	}

	public Driver getD2() {
		return d2;
	}

	public void setD2(Driver d2) {
		this.d2 = d2;
	}

	public int getVittorie() {
		return vittorie;
	}

	public void setVittorie(int vittorie) {
		this.vittorie = vittorie;
	}
	
	
	
	
	

}
