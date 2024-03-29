package com.example.app;

import com.example.my.FxModuleAssemblyUtils;
import com.example.utils.ExcelConvertToJavaBean;
import com.example.utils.ExcelUtils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.ListIterator;

import static com.example.my.CommonConvertUtils.convertList;
import static com.example.my.CommonConvertUtils.convertSelect;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/7
 */
@Slf4j
public class OpenPlantReadExcelAppMain extends Application {
    private Stage primaryStage;
    private TextArea fileContentArea;
    private HBox hBox1;
    private HBox hBox2;
    private HBox hBox3;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        hBox1 = FxModuleAssemblyUtils.initMyHBoxToToggleButton("字段格式：","转蛇形","转驼峰");
        hBox2 = FxModuleAssemblyUtils.initMyHBoxToInPut(new String[]{"加载的sheet-", "开始的行数-", "列数-"}, new String[]{"1", "1", "7"}, 50);
        hBox3 = FxModuleAssemblyUtils.initMyHBoxToInPut(new String[]{"所在列数，字段名=", "中文名称=", "是否必填=", "类型=", "说明="}, new String[]{"1", "2", "3", "4", "5"}, 50);

        // 创建一个按钮
        Button openFileButton = new Button("选择.xlsx文件");
        openFileButton.setOnAction(e -> showFileSelectionDialog());


        Button btnClear = new Button("清除text");
        btnClear.setPrefHeight(50);
        btnClear.setOnAction(e -> fileContentArea.clear());


        // 创建一个文本域用于显示文件内容
        fileContentArea = new TextArea();
        fileContentArea.setEditable(false);
        fileContentArea.setPrefHeight(300);

        // 创建一个布局容器，将按钮和文本域嵌入其中
        VBox root = new VBox(hBox1, hBox2, hBox3, openFileButton, fileContentArea, btnClear, FxModuleAssemblyUtils.initMyHBoxToCloseButton(primaryStage));
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
            } else {
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
        boolean isSelect = convertSelect(hBox1);
        List<String> resutlContent;
        try (InputStream in = new FileInputStream(file)) {
            try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in)) {
                ListIterator<Integer> it1 = readFile.listIterator();
                ListIterator<Integer> it2 = convertContent.listIterator();
                List<List<String>> fileRestList = ExcelUtils.oneSheetRead2007Excel(xssfWorkbook, it1.next(), it1.next(), it1.next());
                resutlContent = ExcelConvertToJavaBean.convertContent(fileRestList, it2, isSelect);
            }
            StringBuilder sbd = new StringBuilder();
            fileContentArea.setText(StringUtils.join(resutlContent, ExcelConvertToJavaBean.newline_default));
        } catch (Exception e) {
            //log.error("Exception", e);
        }
    }
}
