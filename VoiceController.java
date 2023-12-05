package Main.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class VoiceController  {
    @FXML
    private ComboBox<String> fromLanguage, toLanguage;
    @FXML
    private TextField inputtxt, outputtxt;
    @FXML
    private ImageView voiceFromLanguage, voiceToLanguage;

    @FXML
    public void initialize() {
        Map<String, String> languageCodes = new HashMap<>();
        languageCodes.put("Arabic", "ar-eg");
        languageCodes.put("Bulgarian", "bg-bg");
        languageCodes.put("Catalan", "ca-es");
        languageCodes.put("Chinese (Simplified)", "zh-cn");
        languageCodes.put("Chinese (Traditional)", "zh-tw");
        languageCodes.put("Corsican", "hr-hr");
        languageCodes.put("Czech", "cs-cz");
        languageCodes.put("Danish", "da-dk");
        languageCodes.put("Dutch", "nl-be");
        languageCodes.put("English", "en-us");
        languageCodes.put("Finnish", "fi-fi");
        languageCodes.put("French", "fr-fr");
        languageCodes.put("German", "de-de");
        languageCodes.put("Greek", "el-gr");
        languageCodes.put("Hebrew", "he-l");
        languageCodes.put("Hindi", "hi-in");
        languageCodes.put("Hungarian", "hu-hu");
        languageCodes.put("Indonesian", "id-id");
        languageCodes.put("Italian", "it-it");
        languageCodes.put("Japanese", "ja-jp");
        languageCodes.put("Korean", "ko-kr");
        languageCodes.put("Malay", "ms-my");
        languageCodes.put("Norwegian", "nb-no");
        languageCodes.put("Polish", "pl-pl");
        languageCodes.put("Portuguese (Portugal, Brazil)", "pt-br");
        languageCodes.put("Romanian", "ro-ro");
        languageCodes.put("Russian", "ru-ru");
        languageCodes.put("Slovak", "sk-sk");
        languageCodes.put("Slovenian", "sl-si");
        languageCodes.put("Spanish", "es-mx");
        languageCodes.put("Swedish", "sv-se");
        languageCodes.put("Tamil", "ta-in");
        languageCodes.put("Thai", "th-th");
        languageCodes.put("Turkish", "tr-tr");
        languageCodes.put("Vietnamese", "vi-vn");

        voiceFromLanguage.setOnMouseClicked(event -> {
            try {
                String languageCode = languageCodes.get(fromLanguage.getValue());
                textToSpeech(languageCode, inputtxt.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        voiceToLanguage.setOnMouseClicked(event -> {
            try {
                String languageCode = languageCodes.get(toLanguage.getValue());
                textToSpeech(languageCode, outputtxt.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


}

    public static void textToSpeech(String languageCode, String text) throws IOException {
        try {
            URL url = new URL("http://api.voicerss.org/?key=f60a6e464a374d819459b59abf9f1a17"
                    + "&hl=" + languageCode
                    + "&src=" + URLEncoder.encode(text, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream sound = conn.getInputStream();
            File audioFile = new File("audio.wav");
            copyInputStreamToFile(sound, audioFile);
            playAudio(audioFile);
            conn.disconnect();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }

    private static void playAudio(File audioFile) throws Exception {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        Thread.sleep(clip.getMicrosecondLength() / 1000);
    }
}

