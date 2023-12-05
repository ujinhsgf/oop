package Main.Controller;

import Main.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class LoginController {
    private String name;
    private String pass;

    public LoginController() {
        SettingController settingController = new SettingController();
        this.name = settingController.getName();
        this.pass = settingController.getPass();
    }
    @FXML
    private Button loginbutton;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void userLogin(ActionEvent event) throws Exception{
        checkLogin();
    }
    private void checkLogin() throws IOException {
        MainApplication m = new MainApplication();
    }
   public void saveNotification() {
       if(username.getText().toString().equals(name) && password.getText().toString().equals(pass)){
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
           Image image = new Image(getClass().getResourceAsStream("/Main/PNG/check90px2.png"));
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
           Image image = new Image(getClass().getResourceAsStream("/Main/PNG/check90px2.png"));
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

}