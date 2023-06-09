package com.example.view;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/9
 */
public class ConfirmDialog extends Dialog<Boolean> {
    private final Stage owner;

    public ConfirmDialog(Stage owner) {
        this.owner = owner;

        setTitle("Confirm Close");
        setContentText("Are you sure you want to close the window?");

        ButtonType yesButtonType = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButtonType = new ButtonType("No", ButtonBar.ButtonData.NO);
        getDialogPane().getButtonTypes().addAll(yesButtonType, noButtonType);

        setResultConverter(dialogButton -> dialogButton == yesButtonType);
        initOwner(owner);
    }
}
