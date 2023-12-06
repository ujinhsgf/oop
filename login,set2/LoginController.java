package Main.Controller;

import Main.Animation.SlideAnimation;
import Main.MainApplication;
import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField textField;
    @FXML
    private JFXButton viewButton;
    @FXML
    private JFXButton hideButton;
    @FXML
    private Button loginbutton;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private AnchorPane blockPane;
    @FXML
    private TextField confirmCreatePass;
    @FXML
    private AnchorPane createAccountPane;
    @FXML
    private TextField createPass;
    @FXML
    private TextField createUsername;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        password.textProperty().bindBidirectional(textField.textProperty());

        // Initially, only the password field and the view button should be visible
        password.setVisible(true);
        textField.setVisible(false);
        viewButton.setVisible(true);
        hideButton.setVisible(false);

        // When the view button is clicked, show the text field and the hide button
        viewButton.setOnAction(e -> {
            password.setVisible(false);
            textField.setVisible(true);
            viewButton.setVisible(false);
            hideButton.setVisible(true);
        });

        // When the hide button is clicked, show the password field and the view button
        hideButton.setOnAction(e -> {
            password.setVisible(true);
            textField.setVisible(false);
            viewButton.setVisible(true);
            hideButton.setVisible(false);
        });

        blockPane.setVisible(false);
        createAccountPane.setTranslateY(700);
    }
    private boolean checkCredentials(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("login"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void userLogin(ActionEvent event) throws Exception{
        checkLogin();
    }

    private void checkLogin() throws IOException {
        MainApplication m = new MainApplication();
    }

    public void saveNotification() {
        if(checkCredentials(username.getText().toString(), password.getText().toString())==true){
            Image image = new Image(getClass().getResourceAsStream("/Main/PNG/check90px.png"));
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("You've log-in successfully");
            notifications.title("Success message");
            notifications.hideAfter(Duration.seconds(2));
            //notifications.position()
            //notifications.darkStyle();
            notifications.show();
        }else if(username.getText().isEmpty() || password.getText().isEmpty()) {
            Image image = new Image(getClass().getResourceAsStream("/Main/PNG/warning90px.png"));
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Please enter your data");
            notifications.title("Fail");
            notifications.hideAfter(Duration.seconds(2));
            //notifications.position()
            //notifications.darkStyle();
            notifications.show();
        }
        else {
            Image image = new Image(getClass().getResourceAsStream("/Main/PNG/warning90px.png"));
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Wrong username or password");
            notifications.title("Fail");
            notifications.hideAfter(Duration.seconds(2));
            //notifications.position()
            //notifications.darkStyle();
            notifications.show();
        }

    }

    @FXML
    void showCreateAccountPane(ActionEvent event) {
        SlideAnimation.SlideToAnimation(createAccountPane, 0, 0, 0.5);
        blockPane.setVisible(true);
        createUsername.setText("");
        createPass.setText("");
        confirmCreatePass.setText("");
    }

    @FXML
    void createAccount(ActionEvent event) {
        SlideAnimation.SlideToAnimation(createAccountPane, 0, 700, 0.5);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.play();
        pause.setOnFinished(event1 -> blockPane.setVisible(false));
        //create account here <-------------------------

    }

    @FXML
    void cancelCreateAccount(ActionEvent event) {
        SlideAnimation.SlideToAnimation(createAccountPane, 0, 700, 0.5);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.play();
        pause.setOnFinished(event1 -> blockPane.setVisible(false));
    }
}