package implement;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import controller.MainController;
import javafx.scene.control.Alert;
import model.People;

public class ConvertArchive implements Runnable{
	
	private String pathCSV;
	
	public ConvertArchive(String PathCSV){
		this.pathCSV = PathCSV;
	}
	public void run() {	
		
		
		
		createJsonFile("C:\\Users\\paulo.vitor.de.lima\\OneDrive - "
				+ "Complexo de Ensino Superior do Brasil LTDA\\Prática de Sistemas de Informação\\"
				+ "Projeto Conversao de Arquivo\\ProjectConvertArchive\\Convert.json", createPeopleList(pathCSV));
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
