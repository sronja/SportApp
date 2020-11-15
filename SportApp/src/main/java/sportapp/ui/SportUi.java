
package sportapp.ui;

import java.io.FileInputStream;
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
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
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
    private UserDao userDao;
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        
        properties.load(new FileInputStream("config.properties"));
        
        String userFile = properties.getProperty("userFile");
        String sportFile = properties.getProperty("sportFile");
        
        FileSportDao sportDao = new FileSportDao(sportFile, userDao);
        sportService = new SportService(userDao, sportDao);
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
        TextField usernameField = new TextField();
        
        loginPane.getChildren().addAll( usernameLabel, usernameField);
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e->{
            String username = usernameField.getText();
            if (sportService.login(username)) {
                window.setScene(sportScene);
                usernameField.setText("");
            }
        });
    
    login.getChildren().addAll(loginLabel, loginPane, loginButton);
    loginBorderPane.setCenter(login);
    loginScene = new Scene(loginBorderPane);
    window.setScene(loginScene);
    window.show();
    
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
