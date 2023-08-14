 package com.example.app.demo.jfoenix.controller.ui;

 import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;




 @ViewController(value = "/fxml/ui/TextField.fxml", title = "Material Design Example")
 public class TextFieldController
 {
   @FXML
   private JFXTextField validatedText;
   @FXML
   private JFXPasswordField validatedPassword;
   @FXML
   private JFXTextArea jfxTextArea;

   @PostConstruct
   public void init() {
     this.validatedText.focusedProperty().addListener((o, oldVal, newVal) -> {
           if (!newVal.booleanValue()) {
             this.validatedText.validate();
           }
         });
     this.validatedPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
           if (!newVal.booleanValue()) {
             this.validatedPassword.validate();
           }
         });
    this.jfxTextArea.focusedProperty().addListener((o, oldVal, newVal) -> {
           if (!newVal.booleanValue())
             this.jfxTextArea.validate();
         });
   }
 }