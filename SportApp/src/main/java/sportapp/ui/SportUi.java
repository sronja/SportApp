
package sportapp.ui;

import java.io.FileInputStream;
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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
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
        BorderPane loginBorderPane = new BorderPane();
        VBox login = new VBox(10);
        HBox loginPane = new HBox(10);
        login.setPadding(new Insets(10));
        Label loginLabel = new Label("Please login to see and add your sports!");
        Label usernameLabel = new Label("Username");
        Label loginMessage = new Label("");
        TextField usernameField = new TextField();
        
        loginPane.getChildren().addAll(usernameLabel, usernameField);
        Button loginButton = new Button("Login");
        Button signupButton = new Button("Sign up");
        loginButton.setOnAction(e-> {
            String username = usernameField.getText();
            if (sportService.login(username)) {
                window.setScene(sportScene);
                usernameField.setText("");
            } else {
                loginMessage.setText("User " + username + " does not exist!");
                loginMessage.setTextFill(Color.RED);
            }
        });
        
        signupButton.setOnAction(e-> {
            usernameField.setText("");
            window.setScene(userScene);
        });
    
        login.getChildren().addAll(loginLabel, loginPane, loginButton, signupButton, loginMessage);
        loginBorderPane.setCenter(login);
        loginScene = new Scene(loginBorderPane, 500, 450);
        
        // userScene to create new user
        
        VBox newUser = new VBox(10);
        HBox newUserPane = new HBox(10);
        newUserPane.setPadding(new Insets(10));
        Label newUsernameLabel = new Label();
        TextField newUsernameField = new TextField();
        newUserPane.getChildren().addAll(newUsernameLabel, newUsernameField);
        
        Label createUserMessage = new Label("Enter a username: ");
        Button createUserButton = new Button("Create");
        createUserButton.setPadding(new Insets(10));
        
        createUserButton.setOnAction(e-> {
            String username = newUsernameField.getText();
            
            if (username.length() < 5) {
                createUserMessage.setText("Username has to have at least 5 characters!");
                createUserMessage.setTextFill(Color.RED);
            } else if (sportService.createUser(username) == true) {
                createUserMessage.setText("");
                loginMessage.setText("Creating a new user succeeded.");
                loginMessage.setTextFill(Color.CORAL);
                window.setScene(loginScene);
            } else {
                createUserMessage.setText("Username already exists!");
                createUserMessage.setTextFill(Color.RED);
            }
        });
        
        newUser.getChildren().addAll(createUserMessage, newUserPane, createUserButton);
        userScene = new Scene(newUser, 500, 450);
        
        // main scene
        
        Button logoutButton = new Button("Log out");

        sportScene = new Scene(new Group());
        
        table.setEditable(true);
        
        Label colLabel = new Label("Sports");
        TableColumn typeCol = new TableColumn("Type");
        typeCol.setMinWidth(100);
        typeCol.setCellFactory(new PropertyValueFactory<>("type"));
        
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellFactory(new PropertyValueFactory<>("time"));
        
        TableColumn distanceCol = new TableColumn("Distance");
        distanceCol.setMinWidth(100);
        distanceCol.setCellFactory(new PropertyValueFactory("distance"));
        
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
