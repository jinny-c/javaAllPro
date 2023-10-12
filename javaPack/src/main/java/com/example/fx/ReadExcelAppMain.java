package com.example.fx;

import com.example.utils.ExcelConvertToJavaBean;
import com.example.utils.ExcelUtils;
import com.example.utils.FxModuleAssemblyUtils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/7
 */
public class ReadExcelAppMain extends Application {
    private Stage primaryStage;
    private TextArea fileContentArea;
    private HBox hBox1;
    private HBox hBox2;
    private HBox hBox3;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        hBox1 = FxModuleAssemblyUtils.initMyHBoxToToggleButton("是否自动转换成驼峰式（若选择是，则自动加注解SerializedName）：");
        hBox2 = FxModuleAssemblyUtils.initMyHBoxToInPut(new String[]{"加载的sheet-", "开始的行数-", "列数-"}, new Integer[]{1, 3, 7}, 50);
        hBox3 = FxModuleAssemblyUtils.initMyHBoxToInPut(new String[]{"所在列数，字段=", "描述=", "类型=", "备注="}, new Integer[]{2, 3, 4, 7}, 50);

        // 创建一个按钮
        Button openFileButton = new Button("选择.xlsx文件");
        openFileButton.setOnAction(e -> showFileSelectionDialog());


        Button btnSave = new Button("保存文件");
        btnSave.setOnAction(e -> saveFile());


        // 创建一个文本域用于显示文件内容
        fileContentArea = new TextArea();
        fileContentArea.setEditable(false);
        fileContentArea.setPrefHeight(300);

        // 创建一个布局容器，将按钮和文本域嵌入其中
        VBox root = new VBox(hBox1, hBox2, hBox3, openFileButton, fileContentArea, btnSave, FxModuleAssemblyUtils.initMyHBoxToCloseButton(primaryStage));
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        // 创建一个场景并将容器添加到场景中
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("File Viewer");
        primaryStage.show();
    }

    /**
     * 显示文件选择对话框并读取所选文件内容
     */
    private void showFileSelectionDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            if (StringUtils.endsWith(selectedFile.getPath(), ".xlsx")) {
                readFileContent(selectedFile);
            }else {
                fileContentArea.setText(selectedFile.getPath() + "，不支持的文件格式，请选择.xlsx格式的文件");
            }
        }
    }

    /**
     * 读取文件内容并在文本域中显示
     */
    private void readFileContent(File file) {
        List<Integer> readFile = convertList(hBox2);
        List<Integer> convertContent = convertList(hBox3);
        boolean isSelect  = convertSelect(hBox1);
        List<List<String>> resutlContent;
        try (InputStream in = new FileInputStream(file)) {
            try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in)) {
//                List<List<String>> fileRestList = ExcelUtils.oneSheetRead2007Excel(xssfWorkbook, 1, 3, 7);
//                resutlContent = ExcelConvertToJavaBean.convertContent(fileRestList, 2, 3, 4, 7);
                ListIterator<Integer> it1 = readFile.listIterator();
                ListIterator<Integer> it2 = convertContent.listIterator();
                List<List<String>> fileRestList = ExcelUtils.oneSheetRead2007Excel(xssfWorkbook, it1.next(), it1.next(), it1.next());
                resutlContent = ExcelConvertToJavaBean.convertContent(fileRestList, it2.next(), it2.next(), it2.next(), it2.next(),isSelect);
            }
            StringBuilder sbd = new StringBuilder();
            for (List<String> line : resutlContent) {
                sbd.append(StringUtils.join(line, ExcelConvertToJavaBean.newline_default));
                sbd.append(ExcelConvertToJavaBean.newline_default);
            }
            fileContentArea.setText(sbd.toString());
        } catch (Exception e) {
            //log.error("Exception", e);
        }
    }

    private List<Integer> convertList(HBox hBox) {
        List<Integer> result = new ArrayList<>();
        for (Node child : hBox.getChildren()) {
            if (child instanceof TextField) {
                TextField textField = (TextField) child;
                result.add(Integer.parseInt(textField.getText()));
            }
        }
        return result;
    }

    private boolean convertSelect(HBox hBox) {
        for (Node child : hBox.getChildren()) {
            if (child instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) child;
                return toggleButton.isSelected();
            }
        }
        return false;
    }


    private void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存文件");
        FileChooser.ExtensionFilter ft = new FileChooser.ExtensionFilter("文本文件", "*.txt");
        fileChooser.getExtensionFilters().add(ft);
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            //log.warn("取消，放弃写文件");
            return;
        }
        Path path = file.toPath();
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(fileContentArea.getText());
        } catch (IOException ex) {
            //log.error("文件写入出错: " + ex.getMessage());
        }
    }
}
