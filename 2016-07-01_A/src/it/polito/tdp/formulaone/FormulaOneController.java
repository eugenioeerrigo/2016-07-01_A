package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;

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
    	try {
    		
    		Season s = boxAnno.getValue();
    		if(s==null) {
    			txtResult.appendText("Selezionare una stagione!");
    			return;
    		}
    		model.creaGrafo(s);
    		txtResult.appendText("Grafo creato!\n");
    		
    		Driver d = model.getBestDriver();
    		txtResult.appendText("Miglior pilota per quella stagione: "+ d.getForename()+ " "+d.getSurname());
    		
    		
    	}catch(RuntimeException e) {
    		e.printStackTrace();
    		txtResult.appendText("Errore di connessione al DB");
    	}

    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    	try {
    		try {
    			int k = Integer.parseInt(textInputK.getText());
    			
    			if(k<=0) {
    				txtResult.appendText("Inserire K > 0");
    				return;
    			}
    			
    			List<Driver> team = model.getDreamTeam(k);
    			txtResult.appendText("\n\nBest DREAM TEAM of the season\n");
    			for(Driver d : team)
    				txtResult.appendText(d.toString()+"\n");
    			
    		}catch(NumberFormatException n) {
    			txtResult.appendText("Inserire K > 0");
    			return;
    		}
    		
    	}catch(RuntimeException e) {
    		txtResult.appendText("\nErrore di connessione al DB");
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
    	boxAnno.getItems().addAll(model.getAllSeasons());
    }
}
