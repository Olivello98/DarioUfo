package it.polito.tdp.newufosightings.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Model model=new Model();
			model.creaGrafo(1990, 2);
			System.out.println(model.getGraph());
			model.simula(1990, 2, 2, 4);
	}

}
