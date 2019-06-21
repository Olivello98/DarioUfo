/**
 * Sample Skeleton for 'NewUfoSightings.fxml' Controller Class
 */

package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;
import it.polito.tdp.newufosightings.model.State;
import it.polito.tdp.newufosightings.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewUfoSightingsController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtxG"
	private TextField txtxG; // Value injected by FXMLLoader

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="txtT1"
	private TextField txtT1; // Value injected by FXMLLoader

	@FXML // fx:id="txtT2"
	private TextField txtT2; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimula"
	private Button btnSimula; // Value injected by FXMLLoader

	@FXML
	void doCreaGrafo(ActionEvent event) {
		if(this.txtAnno.getText()!=null && this.txtxG.getText()!=null) {
			try {
			int anno=Integer.parseInt(txtAnno.getText());
			int giorni=Integer.parseInt(txtxG.getText());
			if(anno<1906 || anno >2014)
				txtResult.appendText("inserisci un anno compreso tra 1906 e 2014 estremi inclusi\n");
			else if(giorni<1 || giorni>180)
				txtResult.appendText("inserisci un numero di giorni compreso tra 1 e 180 estremi inclusi \n");
			else {
				model.creaGrafo(anno,giorni);
				for(State s: model.getGraph().vertexSet()) {
					txtResult.appendText(s.getName()+" "+model.getSommaPesoArchiAdiacenti(s)+"\n");
				}
			}
			}catch (NumberFormatException e) {
				txtResult.appendText("Inserisci numeri in campi anno e xG \n");
				return;
			}
		}
		else
			txtResult.appendText("Inserisci un anno e un numero di giorni in anno e xG \n");
	}

	@FXML
	void doSimula(ActionEvent event) {
		if(this.txtT1.getText()!=null && this.txtT2.getText()!=null) {
			try {
				int t1=Integer.parseInt(txtT1.getText());
				int t2=Integer.parseInt(txtT2.getText());
				int anno=Integer.parseInt(txtAnno.getText());
				int giorni=Integer.parseInt(txtxG.getText());
				if(t1>365 || t2>365 || t1<0 || t2<0)
					txtResult.appendText("Inserisci numeri compresi tra 0 e 365 nei campi T1 e T2 \n ");
				else if(anno<1906 || anno >2014)
					txtResult.appendText("inserisci un anno compreso tra 1906 e 2014 estremi inclusi\n");
				else if(giorni<1 || giorni>180)
					txtResult.appendText("inserisci un numero di giorni compreso tra 1 e 180 estremi inclusi \n");
				else {
					txtResult.appendText("Livello allerta massimo totale "+model.simula(anno,giorni,t1,t2));
				}
				}catch (NumberFormatException e) {
					txtResult.appendText("Inserisci numeri in campi T1 e T2 \n");
					return;
				}
		}
		else
			txtResult.appendText("Inserisci numero di giorni nei campi T1 e T2 \n");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtxG != null : "fx:id=\"txtxG\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT2 != null : "fx:id=\"txtT2\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;

	}
}
