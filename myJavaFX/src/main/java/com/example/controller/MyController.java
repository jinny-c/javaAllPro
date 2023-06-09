package com.example.controller;

import com.example.utils.FileProcessing;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class MyController {

    @FXML
    private TextArea fileContentTextArea;
    @FXML
    private Button selectButton;
    @FXML
    private Button closeButton;

    public void initialize() {
        GridPane.setHalignment(closeButton, HPos.RIGHT);
    }

    @FXML
    protected void selectButtonClick() {
        fileContentTextArea.clear();

        // 创建一个文件选择器对话框
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        // 显示打开文件对话框
        Stage stage = (Stage) selectButton.getScene().getWindow();
        // 显示文件选择器对话框并等待用户选择文件
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                fileContentTextArea.setText(FileProcessing.getDigestSM3(selectedFile));
            } catch (Exception ex) {
                fileContentTextArea.setText(ex.getMessage());
            }
        }
    }

    @FXML
    protected void closeButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
