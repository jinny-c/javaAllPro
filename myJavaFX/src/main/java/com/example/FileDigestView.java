package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.Security;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/31
 */

public class FileDigestView {

    private Stage stage;
    private FileChooser fileChooser = new FileChooser();
    private TextArea textArea = new TextArea();

    public FileDigestView() {
        stage = new Stage();
        stage.setTitle("File Select View");
        stage.initModality(Modality.APPLICATION_MODAL);

        textArea.setWrapText(true);

        Button openButton = new Button("Select File");
        openButton.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    textArea.setText(getDigestSM3(file));
                } catch (Exception e) {
                    textArea.setText(e.getMessage());
                }
            }
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> stage.close());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(openButton, textArea, closeButton);

        Scene scene = new Scene(vbox, 300, 200);

        stage.setScene(scene);
    }

    public void show() {
        stage.showAndWait();
    }

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDigestSM3(File file) throws Exception {
        try (InputStream fis = new FileInputStream(file)) {
            byte buffer[] = new byte[1024];
            MessageDigest md = MessageDigest.getInstance("SM3");
            for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
                md.update(buffer, 0, numRead);
            }
            return Hex.toHexString(md.digest());
//            int num = Integer.parseInt(new String(md.digest()), 2);
//            return Integer.toHexString(num);
        }
    }
}