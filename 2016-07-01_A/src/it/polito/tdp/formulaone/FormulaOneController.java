package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.DriversPair;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Season> boxAnno;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	model.creaGrafo(boxAnno.getValue());
    	
    	DriversPair bestDriver = model.bestDriver();
    	txtResult.appendText("Pilota col miglior punteggio: "+bestDriver.getDriver1()+" - "+bestDriver.getNum());

    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
			int dim = Integer.parseInt(this.textInputK.getText());
			
			txtResult.appendText("DREAM TEAM\n");
			List<Driver> results = model.dreamTeam(dim);
			
			for(Driver d : results)
				txtResult.appendText(d.toString()+"\n");
			
		} catch (NumberFormatException e) {
			txtResult.appendText("Inserire un valore numerico valido");
		}
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	
    	this.boxAnno.getItems().addAll(model.getAllSeasons());
    }
    
}
