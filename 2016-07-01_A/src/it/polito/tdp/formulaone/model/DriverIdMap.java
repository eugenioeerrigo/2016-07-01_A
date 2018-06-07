package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.Map;

public class DriverIdMap extends HashMap<Integer, Driver>{

	public Driver get(Driver driver) {
		Driver old = super.get(driver.getDriverId());
		if(old==null) {
			super.put(driver.getDriverId(), driver);
			return driver;
		}
		return old;
		
	}
}
