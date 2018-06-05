package it.polito.tdp.formulaone.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private FormulaOneDAO fdao; 
	private SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> graph; 
	

	public Model() {
		fdao = new FormulaOneDAO();
		
	}
	
	public List<Season> getAllSeasons() {
		return fdao.getAllSeasons();
	}

	public void creaGrafo(Season s) {
		graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<Driver> drivers = fdao.getAllDriversBySeason(s);
		Graphs.addAllVertices(graph, drivers);
		
		//ARCHI : for annidati per considerare coppia di drivers oppure con DB
		
	}

	

}
