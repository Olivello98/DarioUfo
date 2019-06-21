package it.polito.tdp.newufosightings.model;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {

	private SimpleWeightedGraph<State,DefaultWeightedEdge> graph;
	private NewUfoSightingsDAO dao;

	public Model() {
		dao=new NewUfoSightingsDAO();
	}




	public SimpleWeightedGraph<State, DefaultWeightedEdge> getGraph() {
		return graph;
	}




	public void creaGrafo(int anno, int giorni) {
		
		//CREO GRAFO 
		graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class); //(potrebbe essere diverso se pesato o orientato)
		//AGGIUNGO I VERTICI
		Graphs.addAllVertices(graph,dao.loadAllStates());
		//AGGIUNGO ARCHI
		for(State s:graph.vertexSet()) {
			for(State ss:graph.vertexSet()) {
				if(!ss.equals(s)) {
					if(dao.isNeighbor(s,ss))
						Graphs.addEdge(graph, s, ss, dao.getPeso(s,ss,anno,giorni));
				}
			}
		}
	}




	public int getSommaPesoArchiAdiacenti(State s) {
		int rst=0;
		for(DefaultWeightedEdge e:graph.outgoingEdgesOf(s))
			rst+=graph.getEdgeWeight(e);
			
		return rst;
	}




	public int simula(int anno, int giorni, int t1, int t2) {
		// TODO Auto-generated method stub
		Simulazione sim=new Simulazione(anno,giorni,t1,t2);
		sim.init(graph.vertexSet());
		sim.run();
		return sim.getNumStatiAllertaMax(); 
	}

}
