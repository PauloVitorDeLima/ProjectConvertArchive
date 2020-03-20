package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import implement.ConvertArchive;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
	@FXML
    private TextField txtFieldPatch;

    @FXML
    private Button btArchive;
    
    @FXML
    private Button btConvert;
    
    @FXML
	private ProgressBar ProgressBar;
    
    @FXML
    private ProgressIndicator ProgressInedicatorConvert;
    
    private String pathCSV;
    

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		btArchive.setOnMouseClicked((MouseEvent e)->{	
			ProgressBar.setProgress(0.2);
			ProgressInedicatorConvert.setProgress(0.2);
			pathCSV = SelectArchiveCSV();
			ProgressBar.setProgress(0.4);
			ProgressInedicatorConvert.setProgress(0.4);
		});
		
		btConvert.setOnMouseClicked((MouseEvent e)->{
		
			ProgressBar.setProgress(0.6);
			ProgressInedicatorConvert.setProgress(0.6);
			long timeStarted = System.currentTimeMillis();	
			ConvertArchive TI = new ConvertArchive(pathCSV);
			Thread convert = new Thread(TI);
			ProgressBar.setProgress(0.8);
			ProgressInedicatorConvert.setProgress(0.8);
			convert.start();
			try {
				convert.join();
				
			long timeFinish = System.currentTimeMillis();
			ProgressBar.setProgress(1);
			ProgressInedicatorConvert.setProgress(1);
			
			Alert sucess = new Alert(Alert.AlertType.CONFIRMATION);
			sucess.setHeaderText("SUCESSO");
			sucess.setContentText("Arquivo convertido com sucesso, tempo de execução em "+ ((timeFinish - timeStarted) / 1000) +" segundos");
			sucess.showAndWait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		});	
	}
	
	
	public String SelectArchiveCSV() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Arquivo CSV", "*.csv"));
		File file = fileChooser.showOpenDialog(new Stage());	
		if(file != null) {
			pathCSV = file.getAbsolutePath();
			txtFieldPatch.setText(pathCSV);
			return pathCSV;	
		}
		return null;
	}
}