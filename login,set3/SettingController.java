package Main.Controller;

import Main.Animation.FadeAnimation;
import Main.Animation.RotateAnimation;
import Main.Animation.SlideAnimation;
import Main.DictionaryCMD.HTMLDictionary;
import Main.DictionaryCMD.textToHTML;
import com.jfoenix.controls.JFXButton;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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

    @FXML
    private JFXButton resetSetting;
    @FXML
    private AnchorPane blockPane;
    @FXML
    private AnchorPane notificationPane;
    @FXML
    private JFXButton confirmButton;
    @FXML
    private JFXButton cancelButton;

    private boolean checkCredentials(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("login.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(" ");
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
        blockPane.setVisible(false);
        notificationPane.setVisible(true);
        notificationPane.setTranslateY(700);
        dayButton.setTranslateX(159);
        FadeAnimation.fadeAnimation(nightButton, 1, 0, 0.01);
        star.setTranslateX(150);
        star.setTranslateY(-200);

        resetSetting.setOnMouseClicked(event -> {
            blockPane.setVisible(true);
            SlideAnimation.SlideToAnimation(notificationPane, 0, 0, 0.5);
        });
        confirmButton.setOnMouseClicked(event -> {
            try {
                textToHTML.resetHtmlDictionary();
                HTMLDictionary.insertFromHTMLFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            blockPane.setVisible(false);
            SlideAnimation.SlideToAnimation(notificationPane, 0, 700, 0.5);
        });
        cancelButton.setOnMouseClicked(event -> {
            blockPane.setVisible(false);
            SlideAnimation.SlideToAnimation(notificationPane, 0, 700, 0.5);
        });
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
        if(checkCredentials(currentUserName.getText().toString(), currentPassword.getText().toString())){
            if(newPassword.getText().toString().equals(confirmNewPassword.getText().toString())){
                Image image = new Image(getClass().getResourceAsStream("/Main/PNG/check90px.png"));
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("You've edit information surccesffuly");
                notifications.title("Success");
                notifications.hideAfter(Duration.seconds(2));
                //notifications.position()
                //notifications.darkStyle();
                notifications.show();
                try {
                    // Read all lines from the file
                    List<String> lines = Files.readAllLines(Paths.get("login.txt"));

                    // Find the line with the user's information and update it
                    for (int i = 0; i < lines.size(); i++) {
                        String[] credentials = lines.get(i).split(",");
                        if (credentials[0].equals(currentUserName.getText().toString()) && credentials[1].equals(currentPassword)) {
                            lines.set(i, currentUserName.getText().toString() + "," + newPassword);
                            break;
                        }
                    }
                    // Write the updated data back to the file
                    Files.write(Paths.get("login.txt"), lines);

                    // Show success notification
                    Image image1 = new Image(getClass().getResourceAsStream("/Main/PNG/check90px.png"));
                    // ...
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Image image = new Image(getClass().getResourceAsStream("/Main/PNG/warning90px.png"));
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

}