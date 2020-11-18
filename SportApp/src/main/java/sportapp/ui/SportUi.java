
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
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    private VBox sports;
    
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
    public Node createSportNode(Sport sport) {
        HBox box = new HBox(10);
        Label typeLabel = new Label("Type: " + sport.getType());
        Label timeLabel = new Label("Time: " + sport.getTime());
        Label distanceLabel = new Label("Distance: " + sport.getDistance());
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().addAll(typeLabel, timeLabel, distanceLabel, spacer);
        return box;
    }
    public void redrawSportList() {
        sports.getChildren().clear();
        List<Sport> allSports = sportService.getSport();
        allSports.forEach(sport->{
            sports.getChildren().add(createSportNode(sport));
        });
    }
    @Override
    public void start (Stage window) {
        //first scene/login scene
        BorderPane loginBorderPane = new BorderPane();
        VBox login = new VBox(10);
        HBox loginPane = new HBox(10);
        login.setPadding(new Insets(10));
        Label loginLabel = new Label ("Please login to see and add your sports!");
        Label usernameLabel = new Label("Username");
        Label loginMessage = new Label("");
        TextField usernameField = new TextField();
        
        loginPane.getChildren().addAll( usernameLabel, usernameField);
        Button loginButton = new Button("Login");
        Button signupButton = new Button("Sign up");
        loginButton.setOnAction(e->{
            String username = usernameField.getText();
            if (sportService.login(username)) {
                redrawSportList();
                window.setScene(sportScene);
                usernameField.setText("");
            } else {
                loginMessage.setText("User " + username + " does not exist!");
                loginMessage.setTextFill(Color.RED);
            }
        });
        
        signupButton.setOnAction(e->{
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
        
        createUserButton.setOnAction(e->{
            String username = newUsernameField.getText();
            
            if (username.length() < 5) {
                createUserMessage.setText("Username has to have at least 5 characters!");
                createUserMessage.setTextFill(Color.RED);
            } else if (sportService.createUser(username)==true) {
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
        
        ScrollPane scrollPane = new ScrollPane();
        Button logoutButton = new Button("Log out");
        
        BorderPane mainPane = new BorderPane(scrollPane);
        sportScene = new Scene(mainPane, 500, 450);
        
        HBox menu = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        menu.getChildren().addAll(menuSpacer, logoutButton);
        
        logoutButton.setOnAction(e->{
            sportService.logout();
            window.setScene(loginScene);
        });
        
        HBox addForm = new HBox(10);
        Button addSport = new Button("Add");
        Region addSpacer = new Region();
        HBox.setHgrow(addSpacer, Priority.ALWAYS);
        addForm.getChildren().addAll( addSpacer, addSport);
        
        sports = new VBox(10);
        sports.setMaxWidth(300);
        sports.setMinWidth(300);
        redrawSportList();
        
        scrollPane.setContent(sports);
        mainPane.setBottom(addForm);
        mainPane.setTop(menu);
        
        addSport.setOnAction(e-> {
            window.setScene(addSportScene);
        });
        
        VBox newSportPane = new VBox(10);
        HBox sportTypePane = new HBox(10);
        Label addSportLabel = new Label ("Here you can add your sport");
        TextField sportType = new TextField();
        Label newSportType = new Label("Type: ");
        sportTypePane.getChildren().addAll(newSportType, sportType);
        HBox sportTimePane = new HBox(10);
        TextField sportTime = new TextField();
        Label newSportTime = new Label("Time: ");
        sportTimePane.getChildren().addAll(newSportTime, sportTime);
        HBox sportDistancePane = new HBox(10);
        TextField sportDistance = new TextField();
        Label newSportDistance = new Label("Distance: ");
        sportDistancePane.getChildren().addAll(newSportDistance, sportDistance);
        Button add = new Button("Add");
        
        newSportPane.getChildren().addAll(addSportLabel, sportTypePane, sportTimePane, sportDistancePane, add);
        
        add.setOnAction(e->{
            sportService.addSport(sportType.getText(), Double.parseDouble(sportTime.getText()), Double.parseDouble(sportDistance.getText()));
            sportType.setText("");
            sportTime.setText("");
            sportDistance.setText("");
            redrawSportList();
            window.setScene(sportScene);
        });
        addSportScene = new Scene(newSportPane, 500, 450);
        
        window.setTitle("Sports");
        window.setScene(loginScene);
        window.show();
        window.setOnCloseRequest(e->{
            System.out.println("This app is about to close");
        });
    
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
