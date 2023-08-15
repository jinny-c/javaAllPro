package com.xwintop.xJavaFxTool;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

import java.util.Map;

public abstract class HttpToolView implements Initializable {
    @FXML
    protected TextField urlTextField;

    @FXML
    protected ChoiceBox<String> methodChoiceBox;

    @FXML
    protected Button sendButton;

    @FXML
    protected Button toBrowerButton;

    @FXML
    protected CheckBox paramsDataCheckBox;

    @FXML
    private Button addParamsDataButton;

    @FXML
    protected CheckBox paramsDataIsStringCheckBox;

    @FXML
    protected TextArea paramsDataTextArea;

    @FXML
    protected TableView<Map<String, String>> paramsDataTableView;

    @FXML
    protected TableColumn<Map<String, String>, String> paramsDataNameTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> paramsDataValueTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> paramsDataRemarkTableColumn;

    @FXML
    protected CheckBox paramsHeaderCheckBox;

    @FXML
    private Button addParamsHeaderButton;

    @FXML
    protected TableView<Map<String, String>> paramsHeaderTableView;

    @FXML
    protected TableColumn<Map<String, String>, String> paramsHeaderNameTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> paramsHeaderValueTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> paramsHeaderRemarkTableColumn;

    @FXML
    protected CheckBox paramsCookieCheckBox;

    @FXML
    private Button addParamsCookieButton;

    @FXML
    protected TableView<Map<String, String>> paramsCookieTableView;

    @FXML
    protected TableColumn<Map<String, String>, String> paramsCookieNameTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> paramsCookieValueTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> paramsCookieRemarkTableColumn;

    @FXML
    protected TextArea ResponseBodyTextArea;

    @FXML
    protected TextArea ResponseHeaderTextArea;

    @FXML
    protected WebView ResponseHtmlWebView;

    @FXML
    protected ImageView ResponseImgImageView;

    public TextField getUrlTextField() {
        return this.urlTextField;
    }

    public ChoiceBox<String> getMethodChoiceBox() {
        return this.methodChoiceBox;
    }

    public Button getSendButton() {
        return this.sendButton;
    }

    public Button getToBrowerButton() {
        return this.toBrowerButton;
    }

    public CheckBox getParamsDataCheckBox() {
        return this.paramsDataCheckBox;
    }

    public Button getAddParamsDataButton() {
        return this.addParamsDataButton;
    }

    public CheckBox getParamsDataIsStringCheckBox() {
        return this.paramsDataIsStringCheckBox;
    }

    public TextArea getParamsDataTextArea() {
        return this.paramsDataTextArea;
    }

    public TableView<Map<String, String>> getParamsDataTableView() {
        return this.paramsDataTableView;
    }

    public TableColumn<Map<String, String>, String> getParamsDataNameTableColumn() {
        return this.paramsDataNameTableColumn;
    }

    public TableColumn<Map<String, String>, String> getParamsDataValueTableColumn() {
        return this.paramsDataValueTableColumn;
    }

    public TableColumn<Map<String, String>, String> getParamsDataRemarkTableColumn() {
        return this.paramsDataRemarkTableColumn;
    }

    public CheckBox getParamsHeaderCheckBox() {
        return this.paramsHeaderCheckBox;
    }

    public Button getAddParamsHeaderButton() {
        return this.addParamsHeaderButton;
    }

    public TableView<Map<String, String>> getParamsHeaderTableView() {
        return this.paramsHeaderTableView;
    }

    public TableColumn<Map<String, String>, String> getParamsHeaderNameTableColumn() {
        return this.paramsHeaderNameTableColumn;
    }

    public TableColumn<Map<String, String>, String> getParamsHeaderValueTableColumn() {
        return this.paramsHeaderValueTableColumn;
    }

    public TableColumn<Map<String, String>, String> getParamsHeaderRemarkTableColumn() {
        return this.paramsHeaderRemarkTableColumn;
    }

    public CheckBox getParamsCookieCheckBox() {
        return this.paramsCookieCheckBox;
    }

    public Button getAddParamsCookieButton() {
        return this.addParamsCookieButton;
    }

    public TableView<Map<String, String>> getParamsCookieTableView() {
        return this.paramsCookieTableView;
    }

    public TableColumn<Map<String, String>, String> getParamsCookieNameTableColumn() {
        return this.paramsCookieNameTableColumn;
    }

    public TableColumn<Map<String, String>, String> getParamsCookieValueTableColumn() {
        return this.paramsCookieValueTableColumn;
    }

    public TableColumn<Map<String, String>, String> getParamsCookieRemarkTableColumn() {
        return this.paramsCookieRemarkTableColumn;
    }

    public TextArea getResponseBodyTextArea() {
        return this.ResponseBodyTextArea;
    }

    public TextArea getResponseHeaderTextArea() {
        return this.ResponseHeaderTextArea;
    }

    public WebView getResponseHtmlWebView() {
        return this.ResponseHtmlWebView;
    }

    public ImageView getResponseImgImageView() {
        return this.ResponseImgImageView;
    }
}