package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
	@FXML
    private TextField txtFieldPatch;

    @FXML
    private Button btArchive;
    
    private String caminhoCSV;

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btArchive.setOnMouseClicked((MouseEvent e)->{
			System.out.println("Clique efetuado!!"); 
			caminhoCSV = SelectArchiveCSV();
		});	
	}
	public String SelectArchiveCSV() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Arquivo CSV", "*.csv"));
		File file = fileChooser.showOpenDialog(new Stage());
		if(file != null) {
			caminhoCSV = file.getAbsolutePath();
			txtFieldPatch.setText(caminhoCSV);
			return txtFieldPatch.toString();
		}
		return null;
	}
}