// Source code is decompiled from a .class file using FernFlower decompiler.
package demos;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import demos.gui.main.MainController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainDemo extends Application {
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    public MainDemo() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        (new Thread(() -> {
            try {
                //SVGGlyphLoader.loadGlyphsFont(MainDemo.class.getResourceAsStream("/fonts/icomoon.svg"), "icomoon.svg");
                SVGGlyphLoader.loadGlyphsFont(getClass().getResourceAsStream("/fonts/icomoon.svg"), "icomoon.svg");
            } catch (IOException var1) {
                var1.printStackTrace();
            }

        })).start();
        Flow flow = new Flow(MainController.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        this.flowContext = new ViewFlowContext();
        this.flowContext.register("Stage", stage);
        flow.createHandler(this.flowContext).start(container);
        JFXDecorator decorator = new JFXDecorator(stage, container.getView());
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(""));
        stage.setTitle("JFoenix Demo");
        double width = 800.0;
        double height = 600.0;

        try {
            Rectangle2D bounds = ((Screen) Screen.getScreens().get(0)).getBounds();
            width = bounds.getWidth() / 2.5;
            height = bounds.getHeight() / 1.35;
        } catch (Exception var11) {
        }

        Scene scene = new Scene(decorator, width, height);
        ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(new String[]{JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(), JFoenixResources.load("css/jfoenix-design.css").toExternalForm(), MainDemo.class.getResource("/css/jfoenix-main-demo.css").toExternalForm()});
        stage.setScene(scene);
        stage.show();
    }
}
