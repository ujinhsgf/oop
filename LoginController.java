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
public class LoginController extends SettingController{

    public LoginController(){

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
        if(username.getText().toString().equals("12345") && password.getText().toString().equals("12345")) {
            //wrongLogIn.setText("Success!");
            //m.changeScene("afterLogin.fxml");
            //saveNotification();

        }

        else if(username.getText().isEmpty() && password.getText().isEmpty()) {
            //wrongLogIn.setText("Please enter your data.");
            //saveNotification1();
        }
        else {
            //wrongLogIn.setText("Wrong username or password!");
            //saveNotification2();
        }
    }

   public void saveNotification() {
       if(username.getText().toString().equals(getName().toString()) && password.getText().toString().equals(getPass().toString())){
           Image image = new Image(getClass().getResourceAsStream("/Main/PNG/check90px.png"));
           Notifications notifications = Notifications.create();
           notifications.graphic(new ImageView(image));
           notifications.text("You've log-in successfully");
           notifications.title("Success message");
           notifications.hideAfter(Duration.seconds(2));
           //notifications.position()
           //notifications.darkStyle();
           notifications.show();
       }else if(username.getText().isEmpty() && password.getText().isEmpty()) {
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