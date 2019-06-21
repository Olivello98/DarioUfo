package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	private Sighting avvistamento;
	private tipo tip;
	private LocalDateTime data;
	private String stato;
	
	public enum tipo{
		AVVISTAMENTO,
		FINE_T1,
		FINE_T2
	}
	
	
	

	public Evento(tipo tip, LocalDateTime data, String stato) {
		super();
		this.tip = tip;
		this.data = data;
		this.stato = stato;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Evento(Sighting avvistamento, tipo tip, LocalDateTime data,String stato) {
		super();
		this.avvistamento = avvistamento;
		this.tip = tip;
		this.data = data;
		this.stato=stato;
	}

	public Sighting getAvvistamento() {
		return avvistamento;
	}

	public void setAvvistamento(Sighting avvistamento) {
		this.avvistamento = avvistamento;
	}

	public tipo getTip() {
		return tip;
	}

	public void setTip(tipo tip) {
		this.tip = tip;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.data.compareTo(o.data);
	}
	
	
	
}
