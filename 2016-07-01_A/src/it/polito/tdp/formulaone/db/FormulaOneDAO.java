package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.DriversIdMap;
import it.polito.tdp.formulaone.model.DriversPair;
import it.polito.tdp.formulaone.model.Season;


public class FormulaOneDAO {

	public List<Integer> getAllYearsOfRace() {
		
		String sql = "SELECT year FROM races ORDER BY year" ;
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(rs.getInt("year"));
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
public List<Driver> getDrivers(Season season, DriversIdMap dmap) {
		
		String sql = "SELECT DISTINCT d.driverId, driverRef, d.number, code, forename, surname, dob, nationality, d.url "
				+ "FROM results as r, races as s, drivers as d "
				+ "WHERE r.raceId=s.raceId AND d.driverId=r.driverId AND s.year = ? AND r.position IS NOT NULL "
				+ "ORDER BY d.driverid" ;
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, season.getYear().getValue());
			
			ResultSet rs = st.executeQuery() ;
			
			List<Driver> list = new ArrayList<>() ;
			
			while(rs.next()) {
				Driver driver = new Driver(rs.getInt("d.driverid"), rs.getString("driverRef"), rs.getInt("d.number"),
											rs.getString("code"), rs.getString("forename"), rs.getString("surname"),
											rs.getDate("dob").toLocalDate(), rs.getString("nationality"), rs.getString("d.url"));
				list.add(dmap.get(driver));
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Season> getAllSeasons() {
		
		String sql = "SELECT year, url FROM seasons ORDER BY year" ;
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(Year.of(rs.getInt("year")), rs.getString("url"))) ;
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Circuit> getAllCircuits() {

		String sql = "SELECT circuitId, name FROM circuits ORDER BY name";

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Circuit> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Circuit(rs.getInt("circuitId"), rs.getString("name")));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Constructor> getAllConstructors() {

		String sql = "SELECT constructorId, name FROM constructors ORDER BY name";

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Constructor> constructors = new ArrayList<>();
			while (rs.next()) {
				constructors.add(new Constructor(rs.getInt("constructorId"), rs.getString("name")));
			}

			conn.close();
			return constructors;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<DriversPair> getDriversPair(Season season, DriversIdMap dmap) {

		String sql = "SELECT r1.driverId as d1, r2.driverId as d2, COUNT(*) as peso "
				+ "FROM results as r1, results as r2, races as d WHERE r1.raceId=r2.raceId "
				+ "AND r1.driverId<>r2.driverId AND r1.raceId=d.raceId AND r1.position>r2.position "
				+ "AND d.year = ? GROUP BY r1.driverId, r2.driverId";

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, season.getYear().getValue());
			
			ResultSet rs = st.executeQuery();

			List<DriversPair> list = new ArrayList<>();
			
			while (rs.next()) {
				Driver d1 = dmap.get(rs.getInt("d1"));
				Driver d2 = dmap.get(rs.getInt("d2"));
				if(d1!=null && d2!=null) {
					DriversPair dp = new DriversPair(d1, d2, rs.getInt("peso"));
					list.add(dp);
				}
			}

			conn.close();
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
}
