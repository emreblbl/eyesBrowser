package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import controllers.MainController;
import controllers.TabController;
import database.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.swing.*;

import static controllers.MainController.tabPane;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.W;

/**
 *
 * @author Segp-Group 3
 */
public class Main extends Application {
	
	/**
	 * Holds a reference to the resource folder of all fxml files
	 */
	public static String FXMLS = "/fxml/";
	public static String CSS = "/css/";
	public static String IMAGES = "/img/";

	MainController object = new MainController();
	static Stage stage;
	public static Scene scene;
	public static Scene sceneCopy;

	private static StackPane pane = new StackPane();

	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(FXMLS+"MainFXML.fxml"));
		Parent root = loader.load();

		// the RootBorder is get to show pin dialoge box that will appear on a
		// screen

		pane.getChildren().add(root);
		stage.getIcons().add(new Image(IMAGES+"eye.png"));
		scene = new Scene(pane);

		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.isControlDown() && event.getCode() == KeyCode.L){

					TabController tabController = new TabController();
					System.out.println(tabController.getSearchField().getText());
				}
			}
		});
		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if( keyEvent.isControlDown() && keyEvent.getCode()==KeyCode.W){

					tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
				}

			}
		});
		
		scene.setOnKeyPressed(event -> {
			
			if (event.getCode() == KeyCode.P && event.isControlDown()) {
				
				JFXButton button = new JFXButton("Ok");
				JFXTextField textfield = new JFXTextField();
				
				button.addEventHandler(MouseEvent.MOUSE_CLICKED, (e6) -> {
					System.out.println("Pin is :" + textfield.getText());
					
					Pattern ipAddress = Pattern.compile("[0-9]{4}");
					Matcher m1 = ipAddress.matcher(textfield.getText());
					boolean b1 = m1.matches();
					
					if (b1) {
						//TabController ob  = new TabController();
						/*
						 * FXMLLoader loader = new
						 * FXMLLoader(getClass().getResource(Main.FXMLS+"Tab.fxml"));
						 * try { loader.load(); } catch (Exception e) { // TODO
						 * Auto-generated catch block e.printStackTrace(); } ob
						 * = loader.getController(); //
						 * ob.getHtmlAsPdf().setVisible(true);
						 * ob.getHamburger().setDisable(true);
						 * //ob.getHamburger().setDisable(false);
						 * //ob.getBookmark().setDisable(false);
						 */
						
					} else {
						
						Notifications.create().title("Wronge Pin").text("Your pin is exceeding limit or your pin is consists\n" + "of invalid characters")
								.hideAfter(Duration.seconds(5)).showError();
						
					}
					
				});
				
				setDialouge(button, "Pin", "Please type your pin", textfield);
				
				Notifications.create().title("Pin Activation").text("You are going to access Pro-Verion.").hideAfter(Duration.seconds(3)).showInformation();
			}
			
			if (event.getCode() == KeyCode.T && event.isControlDown()) {
				
				MainController mainCont = new MainController();
				mainCont.creatNewTab(mainCont.getTabPane(), mainCont.getAddNewTab());
				
			}
		});
		
		scene.getStylesheets().add(getClass().getResource(CSS+"stylesheet.css").toExternalForm());
		stage.setTitle("EYES Browser");
		stage.setScene(scene);
		stage.show();
		
		setStage(stage);
		setScene(scene);
		
	}
	
	public static StackPane getPane() {
		return pane;
	}
	
	public void setPane(StackPane pane) {
		this.pane = pane;
	}
	
	private void setScene(Scene scene) {
		sceneCopy = scene;
	}
	
	public static Scene getScene() {
		return sceneCopy;
	}
	
	@SuppressWarnings("static-access")
	private void setStage(Stage stage) {
		// TODO Auto-generated method stub
		this.stage = stage;
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	private static JFXDialogLayout content = new JFXDialogLayout();
	
	public static void setDialouge(JFXButton applyButton , String heading , String text , Node ob) {
		
		JFXButton button = applyButton;
		
		content.setHeading(new Text(heading));
		content.setBody(new Text(text));
		
		JFXDialog dialoge = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, (e6) -> {
			dialoge.close();
		});
		
		content.setActions(ob, button);
		
		// To show overlay dialougge box
		dialoge.show();
	}
	
	/**
	 * @param args
	 *            the command line arguments
	 */
	
	public static void main(String[] args) {
		
		HistoryManagment.CreateDataBase();
		UserAccounts.createUserAccountsDataBase();
		BookMarksDataBase.createBookMarksDataBase();
		DownloadDatabase.createDownloadDataBase();
		CRUD.createUserDataBase();
		
		launch(args);
		System.exit(1);
	}
	
}
