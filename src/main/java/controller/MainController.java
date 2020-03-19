package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.People;

public class MainController implements Initializable {
    
	@FXML
    private TextField txtFieldPatch;

    @FXML
    private Button btArchive;
    
    @FXML
    private Button btConvert;
    
    @FXML
    private ProgressBar ProgressBar;
    
    private String pathCSV;
    

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		btArchive.setOnMouseClicked((MouseEvent e)->{	
			pathCSV = SelectArchiveCSV();
		});
		
		btConvert.setOnMouseClicked((MouseEvent e)->{	
			CriarArquivoJSON(pathCSV);
			  Alert sucess = new Alert(Alert.AlertType.CONFIRMATION);
			  sucess.setHeaderText("SUCESSO");
			  sucess.setContentText("Arquivo convertido com sucesso");
			  sucess.showAndWait();
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
	
	
	
	public void CriarArquivoJSON(String caminhoCSV) {
		
		createJsonFile("C:\\Users\\paulo.vitor.de.lima\\OneDrive - "
				+ "Complexo de Ensino Superior do Brasil LTDA\\Prática de Sistemas de Informação\\"
				+ "Projeto Conversao de Arquivo\\ProjectConvertArchive\\Convert.json", createPeopleList(caminhoCSV));
	}
	
	
	
	
	private static <T> void createJsonFile(String filePathJSON, List<T> fileContent) {
		try {
			Gson gson = new Gson();
			String Json = gson.toJson(fileContent);
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePathJSON),StandardOpenOption.WRITE);
			writer.write(Json);
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private static List<People> createPeopleList(String filePathCSV) {
		try {
		List<String> listFileContent = readFile(filePathCSV);
		//	List<String> listFileContent = lerArquivo(filePathCSV);
		if(listFileContent != null) {	
		
			String[] arrayFileContent = listFileContent.toArray(new String[listFileContent.size()]);
			List<People> listOfPeoples =  new ArrayList<People>();;
			for (int i = 1; i < arrayFileContent.length; i++) {
			
				String[] tempString = removeIndefChars(arrayFileContent[i]);
				People people = new People(tempString);
				listOfPeoples.add(people);
			}		
			return listOfPeoples;
		}
		return null;
		
		}catch (Exception e) {
			// TODO: handle exception
			  Alert error = new Alert(Alert.AlertType.ERROR);
			   error.setHeaderText("ERROR");
			   error.setContentText("ERROR DE CRIAÇÃO DO ARQUIVO JSON");
			   error.showAndWait();
			   return null;
		}
	}

	private static List<String> readFile(String filePathCSV) {
		try {
			Path path = Paths.get(filePathCSV);
			return Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			   Alert error = new Alert(Alert.AlertType.ERROR);
			   error.setHeaderText("ERROR");
			   error.setContentText("ERROR DE LEITURA DO ARQUIVO CSV");
			   error.showAndWait();
		}
		return null;
	}
	
	private static String[] removeIndefChars(String notFormated) {

		String[] formated = notFormated.split(",");
		
		formated[6] = formated[6].replace("\"", "");
		return formated;
	}	
}