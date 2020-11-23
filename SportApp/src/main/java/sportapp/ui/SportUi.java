
package sportapp.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
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
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
 * @author Ronja
 */
public class SportUi extends Application {

    private SportService sportService;
    
    private Scene sportScene;
    private Scene userScene;
    private Scene loginScene;
    private Scene addSportScene;
    private TableView<Sport> table = new TableView<>();
    private ObservableList<Sport> data = FXCollections.observableArrayList();
    
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
        
        Label usernameLabel = new Label("Username");
        loginGrid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        loginGrid.add(usernameField, 1, 1);
        
        Label passwordLabel = new Label("Password: ");
        loginGrid.add(passwordLabel, 0, 2);
        PasswordField passwordBox = new PasswordField();
        loginGrid.add(passwordBox, 1, 2);
        
        Label loginMessage = new Label("");
        loginGrid.add(loginMessage, 1, 6);
        
        Button loginButton = new Button("Login");
        Button signupButton = new Button("Sign up");
        HBox button = new HBox(10);
        button.setAlignment(Pos.BOTTOM_RIGHT);
        button.getChildren().addAll(loginButton, signupButton);
        loginGrid.add(button, 1, 4);
                
        loginButton.setOnAction(e-> {
            String username = usernameField.getText();
            if (sportService.login(username)) {
                window.setScene(sportScene);
                usernameField.setText("");
                passwordBox.setText("");
            } else {
                loginMessage.setText("User " + username + " does not exist!");
                loginMessage.setTextFill(Color.RED);
            }
        });
        
        signupButton.setOnAction(e-> {
            usernameField.setText("");
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
       
        Button createUserButton = new Button("Create");
        HBox buttonHb = new HBox(10);
        buttonHb.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHb.getChildren().add(createUserButton);
        signupGrid.add(buttonHb, 1, 4);
        
        Label createUserMessage = new Label("");
        signupGrid.add(createUserMessage, 1, 6);
        
        createUserButton.setOnAction(e-> {
            String username = nameField.getText();
            String password = pwBox.getText();
            if (username.length() < 5 || password.length() < 8) {
                createUserMessage.setText("Username has to have at least 5 characters! \n Password has to have at least 8 characters!");
                createUserMessage.setTextFill(Color.RED);
            } else if (sportService.createUser(username, password) == true) {
                createUserMessage.setText("");
                loginMessage.setText("Creating a new user succeeded.");
                loginMessage.setTextFill(Color.CORAL);
                window.setScene(loginScene);
            } else {
                createUserMessage.setText("Username already exists!");
                createUserMessage.setTextFill(Color.RED);
            }
        });
        
        userScene = new Scene(signupGrid, 500, 450);
        
        // main scene
        
        Button logoutButton = new Button("Log out");

        sportScene = new Scene(new Group());
        
        table.setEditable(true);
        
        Label colLabel = new Label("Sports");
        TableColumn typeCol = new TableColumn("Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<Sport, String>("type"));
        
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(new PropertyValueFactory<Sport, Double>("time"));
        
        TableColumn distanceCol = new TableColumn("Distance");
        distanceCol.setMinWidth(100);
        distanceCol.setCellValueFactory(new PropertyValueFactory<Sport, Double>("distance"));
        
        table.setItems(data);
        table.getColumns().addAll(typeCol, timeCol, distanceCol);
        
        TextField addType = new TextField();
        addType.setPromptText("Type");
        addType.setMaxWidth(typeCol.getPrefWidth());
        TextField addTime = new TextField();
        addTime.setPromptText("Time");
        addTime.setMaxWidth(timeCol.getPrefWidth());
        TextField addDistance = new TextField();
        addDistance.setPromptText("Distance");
        addDistance.setMaxWidth(distanceCol.getPrefWidth());
        
        
        Button add = new Button("Add");
        add.setOnAction(e -> {
            sportService.addSport(addType.getText(), Double.parseDouble(addTime.getText()), Double.parseDouble(addDistance.getText()));
            data.add(new Sport(addType.getText(), Double.parseDouble(addTime.getText()), Double.parseDouble(addDistance.getText()), sportService.getLoggedUser()));
            addType.clear();
            addTime.clear();
            addDistance.clear();
        });
        
        HBox colHBox = new HBox();
        colHBox.getChildren().addAll(addType, addTime, addDistance, add);
        colHBox.setSpacing(3);
        
        VBox colVBox = new VBox();
        colVBox.setSpacing(5);
        colVBox.setPadding(new Insets(10, 0, 0, 10));
        colVBox.getChildren().addAll(logoutButton, colLabel, table, colHBox);
        
        ((Group) sportScene.getRoot()).getChildren().addAll(colVBox);
        
        logoutButton.setOnAction(e-> {
            sportService.logout();
            window.setScene(loginScene);
        });
       
        window.setTitle("Sports");
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
