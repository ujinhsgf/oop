package Main.Controller;

import Main.Animation.FadeAnimation;
import Main.Animation.RotateAnimation;
import Main.Animation.SlideAnimation;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    private boolean enable = true;
    private static boolean isDayTheme = true;

    public static boolean isIsDayTheme() {
        return isDayTheme;
    }

    public static void setIsDayTheme(boolean isDayTheme) {
        SettingController.isDayTheme = isDayTheme;
    }
    @FXML
    private TextField currentUserName, newUserName, currentPassword, newPassword, confirmNewPassword;
    @FXML
    private AnchorPane dayButton;

    @FXML
    private AnchorPane nightButton;

    @FXML
    private AnchorPane sky;

    @FXML
    private ImageView star;

    @FXML
    private ImageView sun;


    private String name = "12345";
    private String pass = "12345";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @FXML
    void changeTheme(MouseEvent event) {
        if(!enable) return;
        if(isDayTheme) {
            isDayTheme = false;
            changeToNight();
        } else {
            isDayTheme = true;
            changeToDay();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dayButton.setTranslateX(159);
        FadeAnimation.fadeAnimation(nightButton, 1, 0, 0.01);
        star.setTranslateX(150);
        star.setTranslateY(-200);
    }

    public void changeToNight() {
        enable = false;
        SlideAnimation.SlideToAnimation(sky, 0, 330, 1);
        SlideAnimation.SlideToAnimation(sun, -150, 200, 1);
        SlideAnimation.SlideToAnimation(star, 0, 0, 1);
        FadeAnimation.fadeAnimation(nightButton, 0, 1, 1);
        RotateAnimation.rotateAnimation(dayButton, 360, 0, 1);
        SlideAnimation.SlideToAnimation(dayButton, 0, 0, 1);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.play();
        pause.setOnFinished(actionEvent -> {
            enable = true;
        });
    }

    public void changeToDay() {
        enable = false;
        SlideAnimation.SlideToAnimation(sky, 0, 0, 1);
        SlideAnimation.SlideToAnimation(sun, 0, 0, 1);
        SlideAnimation.SlideToAnimation(star, 150, -200, 1);
        FadeAnimation.fadeAnimation(nightButton, 1, 0, 1);
        RotateAnimation.rotateAnimation(dayButton, 0, 360, 1);
        SlideAnimation.SlideToAnimation(dayButton, 159, 0, 1);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.play();
        pause.setOnFinished(actionEvent -> {
            enable = true;
        });
    }

    public void saveNotification() {
        if(currentUserName.getText().toString().equals(name) && currentPassword.getText().toString().equals(pass)){
            if(newPassword.getText().toString().equals(confirmNewPassword.getText().toString())){
                Image image = new Image(getClass().getResourceAsStream("/Main/PNG/check90px.png"));
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("You've log-in successfully");
                notifications.title("Success message");
                notifications.hideAfter(Duration.seconds(2));
                //notifications.position()
                //notifications.darkStyle();
                notifications.show();
                name = newUserName.getText().toString();
                pass = newPassword.getText().toString();
            } else {
                Image image = new Image(getClass().getResourceAsStream("/Main/PNG/check90px2.png"));
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("newPassword and confirmNewPassword are difference");
                notifications.title("Fail");
                notifications.hideAfter(Duration.seconds(2));
                //notifications.position()
                //notifications.darkStyle();
                notifications.show();
            }
        }else if(currentUserName.getText().isEmpty() || newUserName.getText().isEmpty()
                || currentPassword.getText().isEmpty() || newPassword.getText().isEmpty()
                || confirmNewPassword.getText().isEmpty()) {
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
