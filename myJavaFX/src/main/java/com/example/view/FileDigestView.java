package com.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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

    public FileDigestView() {
        stage = new Stage();
        stage.setTitle("File Select View");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false); // 将窗口大小设置为固定不变

        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(5); // 设置首选行数
        textArea.setWrapText(true);// 自动换行

        Button openButton = new Button("Select File To Get Cryptographic Digest");
        openButton.setPrefHeight(50);
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

        Button closeButton = new Button("Close Window");
        closeButton.setPrefHeight(50);
        closeButton.setOnAction(event -> stage.close());


        //VBox.setMargin(closeButton, new Insets(10, 0, 10, 0));


        //水平布局的容器 HBox
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setMinHeight(Region.USE_PREF_SIZE);
        hBox.getChildren().addAll(closeButton);

        //垂直布局的容器 VBox
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(20);//上下控件之间的 间隔
        //vbox.setAlignment(Pos.CENTER);//居中
        vbox.setMinHeight(Region.USE_PREF_SIZE);

        vbox.getChildren().addAll(openButton, textArea, hBox);

        Scene scene = new Scene(vbox, 500, 300);

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