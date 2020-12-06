
package sportapp.ui;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sportapp.dao.FileSportDao;
import sportapp.dao.FileUserDao;
import sportapp.dao.UserDao;
import sportapp.domain.SportService;
import sportapp.domain.Sport;

/**
 *
 * Graafinen käyttöliittymä
 */
public class SportUi extends Application {

    private SportService sportService;
    
    private Scene sportScene;
    private Scene userScene;
    private Scene loginScene;
    private Scene settingsScene;
    private Scene errorScene;
    private TableView<Sport> table = new TableView<>();
    private ObservableList<Sport> data = FXCollections.observableArrayList();
    private Label meanDistanceLabel = new Label("");
    private Label sumDistanceLabel = new Label("");
    private Label sumTimeLabel = new Label("");
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        
        properties.load(new FileInputStream("config.properties"));
        
        String userFile = properties.getProperty("userFile");
        String sportFile = properties.getProperty("sportFile");
        
        FileUserDao userDao = new FileUserDao(userFile);
        FileSportDao sportDao = new FileSportDao(sportFile, userDao);
        sportService = new SportService(userDao, sportDao);

    }
    public void refreshStatisticsLabels() {
        meanDistanceLabel.setText("Mean distance: " + sportService.countMeanDistance() + " km");
        sumDistanceLabel.setText("Total length: " + sportService.countSumDistance() + " km");
        sumTimeLabel.setText("Total time: " + sportService.countSumTime() + " min");
    }
    @Override
    public void start(Stage window) {
        //first scene/login scene
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(25, 25, 25, 25));
        
        Label loginLabel = new Label("Please login!");
        loginLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        loginGrid.add(loginLabel, 0, 0, 2, 1);
        
        Label usernameLabel = new Label("Username:");
        loginGrid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        loginGrid.add(usernameField, 1, 1);
        
        Label passwordLabel = new Label("Password:");
        loginGrid.add(passwordLabel, 0, 2);
        PasswordField passwordBox = new PasswordField();
        loginGrid.add(passwordBox, 1, 2);
        
        Label loginMessage = new Label("");
        loginGrid.add(loginMessage, 1, 7);
        
        Button loginButton = new Button("Login");
        Button signupButton = new Button("Sign up");
        HBox button = new HBox(10);
        button.setAlignment(Pos.BOTTOM_RIGHT);
        button.getChildren().addAll(loginButton, signupButton);
        loginGrid.add(button, 1, 4);
        
        
        loginButton.setOnAction(e-> {
            String username = usernameField.getText();
            String password = passwordBox.getText();
            if (sportService.login(username, password)) {
                window.setScene(sportScene);
                usernameField.setText("");
                passwordBox.setText("");
                loginMessage.setText("");
                refreshStatisticsLabels();
                for (Sport sport: sportService.getSport()) {
                    data.add(sport);
                }
            } else {
                loginMessage.setText("User " + username + " does not exist!");
                loginMessage.setTextFill(Color.RED);
            }
        });
        
        
        
        
        signupButton.setOnAction(e-> {
            window.setScene(userScene);
        });
    
        loginScene = new Scene(loginGrid, 500, 450);
        
        // userScene to create new user
        
        GridPane signupGrid = new GridPane();
        signupGrid.setAlignment(Pos.CENTER);
        signupGrid.setHgap(10);
        signupGrid.setVgap(10);
        signupGrid.setPadding(new Insets(25, 25, 25, 25));
        
        Label signupTitle = new Label("Sign up");
        signupTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        signupGrid.add(signupTitle, 0, 0, 2, 1);
        
        Label nameLabel = new Label("Username:");
        signupGrid.add(nameLabel, 0, 1);
        TextField nameField = new TextField();
        signupGrid.add(nameField, 1, 1);
        
        Label pwLabel = new Label("Password:");
        signupGrid.add(pwLabel, 0, 2);
        PasswordField pwBox = new PasswordField();
        signupGrid.add(pwBox, 1, 2);
        
        Label firstName = new Label("Firstname:");
        signupGrid.add(firstName, 0, 3);
        TextField firstNameField = new TextField();
        signupGrid.add(firstNameField, 1, 3);
        
        Label ageLabel = new Label("Age:");
        signupGrid.add(ageLabel, 0, 4);
        TextField ageField = new TextField();
        signupGrid.add(ageField, 1, 4);
        
        Label countryLabel = new Label("Country:");
        signupGrid.add(countryLabel, 0, 5);
        TextField countryField = new TextField();
        signupGrid.add(countryField, 1, 5);
       
        Button createUserButton = new Button("Create");
        HBox buttonHb = new HBox(10);
        buttonHb.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHb.getChildren().add(createUserButton);
        signupGrid.add(buttonHb, 1, 6);
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("CAUTION!");
        alert.setHeaderText(null);
        alert.setContentText("");
        
        createUserButton.setOnAction(e-> {
            try {
                String username = nameField.getText();
                String password = pwBox.getText();
                String name = firstNameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String country = countryField.getText();
                if (username.length() < 5 || password.length() < 8) {
                    alert.setContentText("Username has to have at least 5 characters!\n" + "Password has to have at least 8 characters!");
                    alert.showAndWait();
                } else if (sportService.createUser(username, password, name, age, country) == true) {
                    loginMessage.setText("Creating a new user succeeded.");
                    loginMessage.setTextFill(Color.CORAL);
                    nameField.clear();
                    pwBox.clear();
                    firstNameField.clear();
                    ageField.clear();
                    countryField.clear();
                    window.setScene(loginScene);
                } else if (sportService.createUser(username, password, name, age, country) == false) {
                    alert.setContentText("Username already exists!");
                    alert.showAndWait();
                }
            } catch (Exception exception) {
                alert.setContentText("You have to insert your data in specific form!\n"
                        + "Username, password, name & country: characters only\n"
                        + "Age: integer values only (ie. 50)");
                alert.showAndWait();
            }
        });
        
        userScene = new Scene(signupGrid, 500, 450);
        
        // main scene, urheilusuoritusten katsominen ja lisääminen
        
        Button logoutButton = new Button("Log out");
        Button settingsButton = new Button("Settings");

        sportScene = new Scene(new Group());
        
        table.setEditable(true);
        
        Label colLabel = new Label("Your recent sports");
        colLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        
        TableColumn typeCol = new TableColumn("Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<Sport, String>("type"));
        
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(new PropertyValueFactory<Sport, Double>("time"));
        
        TableColumn distanceCol = new TableColumn("Distance");
        distanceCol.setMinWidth(100);
        distanceCol.setCellValueFactory(new PropertyValueFactory<Sport, Double>("distance"));
        
        TableColumn hrCol = new TableColumn("Avg heart rate");
        hrCol.setMinWidth(100);
        hrCol.setCellValueFactory(new PropertyValueFactory<Sport, Integer>("heartrate"));
        
        TableColumn feelingCol = new TableColumn("Feeling");
        feelingCol.setMinWidth(100);
        feelingCol.setCellValueFactory(new PropertyValueFactory<Sport, Integer>("feeling"));
        
        table.setItems(data);
        table.getColumns().addAll(typeCol, timeCol, distanceCol, hrCol, feelingCol);
        
        TextField addType = new TextField();
        addType.setPromptText("Type");
        addType.setMaxWidth(typeCol.getPrefWidth());
        TextField addTime = new TextField();
        addTime.setPromptText("Time");
        addTime.setMaxWidth(timeCol.getPrefWidth());
        TextField addDistance = new TextField();
        addDistance.setPromptText("Distance");
        addDistance.setMaxWidth(distanceCol.getPrefWidth());
        TextField addHeartrate = new TextField();
        addHeartrate.setPromptText("Heart rate");
        addHeartrate.setMaxWidth(hrCol.getPrefWidth());
        TextField addFeeling = new TextField();
        addFeeling.setPromptText("Feeling 1-10");
        addFeeling.setMaxWidth(feelingCol.getPrefWidth());
        
        //Urheilusuorituksen lisääminen taulukkoon nappia painamalla
        Button add = new Button("Add");
        add.setOnAction(e -> {
            try {
                sportService.addSport(addType.getText(), Double.parseDouble(addTime.getText()), Double.parseDouble(addDistance.getText()), Integer.parseInt(addHeartrate.getText()), Integer.parseInt(addFeeling.getText()));
                data.add(new Sport(addType.getText(), Double.parseDouble(addTime.getText()), Double.parseDouble(addDistance.getText()), Integer.parseInt(addHeartrate.getText()), Integer.parseInt(addFeeling.getText()), sportService.getLoggedUser()));
                addType.clear();
                addTime.clear();
                addDistance.clear();
                addHeartrate.clear();
                addFeeling.clear();
                refreshStatisticsLabels();
            } catch (Exception ex) {
                alert.setContentText("You have to insert your data in specific form!\n"
                    + "Type: characters only\n"
                    + "Time: double values only (ie. 30.0)\n"
                    + "Distance: double values only (ie. 5.0)\n"
                    + "Heartrate: integer values only between 40 to 220 (ie. 150)\n"
                    + "Feeling: integer values only between 1 to 10");
                alert.showAndWait();
                addType.clear();
                addTime.clear();
                addDistance.clear();
                addHeartrate.clear();
                addFeeling.clear();
            }
        });
        
        Button deleteAll = new Button("Delete all");
        deleteAll.setOnAction(e -> {
            if (sportService.deleteSports() == true) {
                refreshStatisticsLabels();
                data.clear();
            }
        });
        Button deleteOne = new Button("Delete selected");
        deleteOne.setOnAction(e -> {
            Sport selected = table.getSelectionModel().getSelectedItem();
                if (sportService.deleteSport(selected.getType(), selected.getTime(), selected.getDistance(), selected.getHeartrate(), selected.getFeeling()) == true) {
                    table.getItems().remove(selected);    
            }
        }) ;
        
        HBox colHBox = new HBox();
        colHBox.getChildren().addAll(addType, addTime, addDistance, addHeartrate, addFeeling);
        colHBox.setSpacing(20);
        
        VBox buttons = new VBox();
        buttons.getChildren().addAll(logoutButton, settingsButton, deleteAll, deleteOne);
        buttons.setSpacing(10);
        
        meanDistanceLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        sumDistanceLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        sumTimeLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        pane.add(colLabel, 0, 0);
        pane.add(table, 0, 1);
        pane.add(colHBox, 0, 2);
        pane.add(add, 0, 3);
        pane.add(buttons, 1, 1);
        pane.add(meanDistanceLabel, 0, 4);
        pane.add(sumDistanceLabel, 0, 5);
        pane.add(sumTimeLabel, 0, 6);
                
        ((Group) sportScene.getRoot()).getChildren().add(pane);
        
        //uloskirjautuminen painamalla logout-nappia
        logoutButton.setOnAction(e-> {
            sportService.logout();
            data.clear();
            window.setScene(loginScene);
        });
        settingsButton.setOnAction(e-> {
            window.setScene(settingsScene);
        });
        
        //SettingsScene, käyttäjän poistaminen ja tietojen lisääminen
        
        GridPane settingsGrid = new GridPane();
        settingsGrid.setAlignment(Pos.CENTER);
        settingsGrid.setHgap(10);
        settingsGrid.setVgap(10);
        settingsGrid.setPadding(new Insets(25, 25, 25, 25));
        
        Label settingsLabel = new Label("Settings");
        settingsLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        settingsGrid.add(settingsLabel, 0, 0, 2, 1);
        
        Label backToMainLabel = new Label("Back to sports-page");
        settingsGrid.add(backToMainLabel, 0, 1);
        Button backToMain = new Button("Sports");
        settingsGrid.add(backToMain, 1, 1);
        
        Label instruction = new Label("If you want to delete your user, press the following button.");
        settingsGrid.add(instruction, 0, 2);
        Button delete = new Button("Delete");
        settingsGrid.add(delete, 1, 2);
        
        Label deleteMessage = new Label("");
        settingsGrid.add(deleteMessage, 1, 3);
        
        settingsScene = new Scene(settingsGrid, 500, 450);
        
        delete.setOnAction(e -> {
            if (sportService.deleteUser() == true) {
                window.setScene(loginScene);
                loginMessage.setText("Deleting user succeeded");
            } else {
                deleteMessage.setText("There's an error in deleting your user!");
            }
        });
        backToMain.setOnAction(e -> {
            window.setScene(sportScene);
        });
        
    
       
        window.setTitle("SportApp");
        window.setScene(loginScene);
        window.show();
        window.setOnCloseRequest(e-> {
            System.out.println("This app is about to close");
        });
    
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
