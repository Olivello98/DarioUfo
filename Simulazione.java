package it.polito.tdp.newufosightings.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graphs;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Evento.tipo;

public class Simulazione {

	

		private PriorityQueue<Evento> queue=new PriorityQueue<>();
		private Map<String,State> mapStati;
		//PARAMETRI
		private int anno;
		private int giorni;
		private int t1;
		private int t2;
		
		
		//STATISTICHE RACCOLTE
		private int numStatiAllertaMax;
	
	
		
		public int getNumStatiAllertaMax() {
			return numStatiAllertaMax;
		}


		public Simulazione(int anno, int giorni, int t1, int t2) {
			super();
			this.anno = anno;
			this.giorni = giorni;
			this.t1 = t1;
			this.t2 = t2;
		}
		
		
		public void init(Set<State> stati) {
			numStatiAllertaMax=0;
			NewUfoSightingsDAO dao=new NewUfoSightingsDAO();
			for(State s:stati) {
				for(State ss:stati) {
					if(!ss.equals(s)) {
						if(dao.isNeighbor(s,ss)){
							for(Sighting sss:dao.getSighting(anno, giorni, s, ss)) {
								if(sss!=null) {
									Evento ev=new Evento(sss,tipo.AVVISTAMENTO,sss.getDatetime(),sss.getState());
									if(!queue.contains(ev))
										queue.add(ev);
								}		
							}
						}
					}
				}
			}
			mapStati=new HashMap<>();
			for(State s:dao.loadAllStates()) {
			if(s!=null)
				mapStati.put(s.getId(), s);
			}
			numStatiAllertaMax=0;
		}
		
		public void run() {
			while(!queue.isEmpty()) {
				Evento e=queue.poll();
				State stato=mapStati.get(e.getStato());
				switch(e.getTip()) {
				case AVVISTAMENTO:
				
					if(stato!=null && !stato.isEmergenza()) {
					
						if(stato.getDEFCON()==1) {
							stato.setEmergenza(true);
							queue.add(new Evento(tipo.FINE_T2,e.getAvvistamento().getDatetime().plusDays((long)t2),stato.getId()));
							numStatiAllertaMax++;
						}else if(stato.getDEFCON()!=1) {
							stato.decrementeDEFCON();
							queue.add(new Evento(tipo.FINE_T1,e.getAvvistamento().getDatetime().plusDays((long)t1),stato.getId()));
						}
					}
				break;
				case FINE_T1:
					stato.incrementaDEFCON();
				break;
				case FINE_T2:
					stato.setEmergenza(false);
					stato.setDEFCON(5);
				break;
				}
				
		
			}
		}
		
}
