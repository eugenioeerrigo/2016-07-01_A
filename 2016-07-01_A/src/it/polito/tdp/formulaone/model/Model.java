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
	
	private FormulaOneDAO fdao; 
	private SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> graph;
	private DriverIdMap dmap;
	
	private List<Driver> bestDreamTeam;
	private int bestDreamTeamValue;

	public Model() {
		fdao = new FormulaOneDAO();
		dmap = new DriverIdMap();
	}
	
	public List<Season> getAllSeasons() {
		return fdao.getAllSeasons();
	}

	public void creaGrafo(Season s) {
		graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<Driver> drivers = fdao.getAllDriversBySeason(s, dmap);
		Graphs.addAllVertices(graph, drivers);
		
		//ARCHI : for annidati per considerare coppia di drivers oppure con DB
		for(Vittorie v: fdao.getDriversBySeason(s, dmap))
			Graphs.addEdgeWithVertices(graph, v.getD1(), v.getD2(), v.getVittorie());
		
		System.out.println(String.format("Grafo creato: %d archi, %d nodi\n", graph.edgeSet().size(), graph.vertexSet().size()));
	}

	public Driver getBestDriver() {
		if(graph==null)
			throw new RuntimeException("Creare il grafo prima!");
		
		Driver bestDriver = null;
		int best = Integer.MIN_VALUE;
		
		for(Driver d : graph.vertexSet()) {
			int sum = 0;
		
			//Itero sugli archi uscenti
			for(DefaultWeightedEdge e : graph.outgoingEdgesOf(d)) {
				sum += graph.getEdgeWeight(e);
			}
			//Itero sugli archi entranti
			for(DefaultWeightedEdge w : graph.incomingEdgesOf(d)) {
				sum -= graph.getEdgeWeight(w);
			}
			
			if(sum>best || bestDriver == null) {
				best = sum;
				bestDriver = d;
			}
		}
		
		
		if(bestDriver==null)
			throw new RuntimeException("BestDriver not found!");
		
		return bestDriver;
	}
	
	public List<Driver> getDreamTeam(int k){
		bestDreamTeam = new ArrayList<>();
		bestDreamTeamValue = Integer.MAX_VALUE;
		
		recursive(0, new ArrayList<Driver>(), k);
		
		return bestDreamTeam;
	}

	private void recursive(int step, List<Driver> temp, int k) {
		
		if(step>=k) {
			if(evaluate(temp) < bestDreamTeamValue) {
				bestDreamTeamValue = evaluate(temp);
				bestDreamTeam = new ArrayList<Driver>(temp);
				return;
			}
		}
		
		for(Driver d : graph.vertexSet()) {
			if(!temp.contains(d)) {
				temp.add(d);
				recursive(step+1, temp, k);
				temp.remove(d);
			}
		}
		
	}

	private int evaluate(List<Driver> temp) {
		int sum = 0;
		Set<Driver> in = new HashSet<>(temp);  //Velocizza il contains se ho lista molto grande
		Set<Driver> out = new HashSet<>(graph.vertexSet());
		
		for(DefaultWeightedEdge d : graph.edgeSet()) {
			if(out.contains(graph.getEdgeSource(d)) && in.contains(graph.getEdgeTarget(d))) {
				sum += graph.getEdgeWeight(d);
			}
		}
		return sum;
	}

}
