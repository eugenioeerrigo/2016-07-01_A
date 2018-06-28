package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private FormulaOneDAO dao;
	private SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> graph;
	private DriversIdMap dmap;
	
	private List<Driver> dreamTeam;
	private int bestTassoSconfitta;
	
	public Model() {
		dao = new FormulaOneDAO();
		dmap = new DriversIdMap();
	}

	public List<Season> getAllSeasons() {
		return dao.getAllSeasons();
	}

	public void creaGrafo(Season season) {
		graph = new SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(graph, dao.getDrivers(season, dmap));
		
		for(DriversPair dp : dao.getDriversPair(season, dmap))
			Graphs.addEdge(graph, dp.getDriver1(), dp.getDriver2(), dp.getNum());
		
		System.out.println(String.format("Grafo: %d - %d", graph.vertexSet().size(), graph.edgeSet().size()));
		
	}

	public DriversPair bestDriver() {
		int best = Integer.MIN_VALUE;
		int punteggio = 0;
		Driver bestDriver = null;
		
		for(Driver d : graph.vertexSet()) {
			
			for(DefaultWeightedEdge e : graph.outgoingEdgesOf(d))
				punteggio += graph.getEdgeWeight(e);
			
			for(DefaultWeightedEdge e : graph.incomingEdgesOf(d))
				punteggio -= graph.getEdgeWeight(e);
			
			if(punteggio>best) {
				best = punteggio;
				bestDriver = d;
			}
		}
		
		return new DriversPair(bestDriver, null, best);
	}

	public List<Driver> dreamTeam(int dim) {
		dreamTeam = new ArrayList<>();
		bestTassoSconfitta = Integer.MAX_VALUE;
		
		recursive(0, new ArrayList<Driver>(), dim);
		
		return dreamTeam;
	}

	private void recursive(int step, List<Driver> parziale, int dim) {

		if(step>=dim) {
			if(tassoSconfitta(parziale)<bestTassoSconfitta) {
				dreamTeam = new ArrayList<>(parziale);
				bestTassoSconfitta = tassoSconfitta(parziale);
				return;
			}
		}
		
		for(Driver d : graph.vertexSet()) {
			if(!parziale.contains(d)) {
				parziale.add(d);
				recursive(step+1, parziale, dim);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	private int tassoSconfitta(List<Driver> parziale) {
		int sum = 0;
		
		Set<Driver> team = new HashSet<>(parziale);             //Piloti appartenenti al team
		Set<Driver> all = new HashSet<>(graph.vertexSet());
		all.removeAll(team);                                   //Adesso 'all' contiene i piloti non appartenenti al team
		
		for(DefaultWeightedEdge e : graph.edgeSet()) {
			if(all.contains(graph.getEdgeSource(e)) && team.contains(graph.getEdgeTarget(e)))
				sum += graph.getEdgeWeight(e);
		}
		
		return sum;
	}
	

}
