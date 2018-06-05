package it.polito.tdp.formulaone.model;

public class Vittorie {
	
	private int driver1Id;
	private int driver2Id;
	private int vittorie;
	
	public Vittorie(int driver1Id, int driver2Id, int vittorie) {
		this.driver1Id = driver1Id;
		this.driver2Id = driver2Id;
		this.vittorie = vittorie;
	}

	public int getDriver1Id() {
		return driver1Id;
	}

	public void setDriver1Id(int driver1Id) {
		this.driver1Id = driver1Id;
	}

	public int getDriver2Id() {
		return driver2Id;
	}

	public void setDriver2Id(int driver2Id) {
		this.driver2Id = driver2Id;
	}

	public int getVittorie() {
		return vittorie;
	}

	public void setVittorie(int vittorie) {
		this.vittorie = vittorie;
	}
	
	
	

}
