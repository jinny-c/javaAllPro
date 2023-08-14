package com.example.app.demo.jfoenix.controller.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ViewController(value = "/fxml/ui/SVGLoader.fxml", title = "Material Design Example")
public class SVGLoaderController {
    private static final String FX_BACKGROUND_INSETS_0 = "-fx-background-insets: 0;";

    private static final String DEFAULT_OPACITY = "33";

    private static final String THUMB = ".thumb";

    private static final String ANIMATED_THUMB = ".animated-thumb";

    private static final String COLORED_TRACK = ".colored-track";

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private StackPane detailsContainer;

    @FXML
    private JFXButton browseFont;

    @FXML
    private StackPane iconsContainer;

    private JFXButton lastClicked = null;

    private final String fileName = "icomoon.svg";

    private GlyphDetailViewer glyphDetailViewer;

    @PostConstruct
    public void init() throws Exception {
        Stage stage = (Stage) this.context.getRegisteredObject("Stage");
        this.glyphDetailViewer = new GlyphDetailViewer();
        this.detailsContainer.getChildren().add(this.glyphDetailViewer);
        ScrollPane scrollableGlyphs = allGlyphs();
        scrollableGlyphs.setStyle("-fx-background-insets: 0;");
        this.iconsContainer.getChildren().add(scrollableGlyphs);
        this.browseFont.setOnAction(action -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SVG files (*.svg)", new String[]{"*.svg"});
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                SVGGlyphLoader.clear();
                try {
                    SVGGlyphLoader.loadGlyphsFont(new FileInputStream(file), file.getName());
                    ScrollPane newglyphs = allGlyphs();
                    newglyphs.setStyle("-fx-background-insets: 0;");
                    this.iconsContainer.getChildren().clear();
                    this.iconsContainer.getChildren().add(newglyphs);
                } catch (IOException ioExc) {
                    ioExc.printStackTrace();
                }
            }
        });
    }

    private ScrollPane allGlyphs() {
        List<SVGGlyph> glyphs = (List<SVGGlyph>) SVGGlyphLoader.getAllGlyphsIDs().stream().map(glyphName -> {
            try {
                return SVGGlyphLoader.getIcoMoonGlyph(glyphName);
            } catch (Exception e) {
                return null;
            }
        }).collect(Collectors.toList());
        glyphs.sort(Comparator.comparing(SVGGlyph::getName));
        glyphs.forEach(glyph -> glyph.setSize(16.0D));
        List<Button> iconButtons = (List<Button>) glyphs.stream().map(this::createIconButton).collect(Collectors.toList());
        iconButtons.forEach(button -> button.setCache(true));
        Platform.runLater(() -> ((Button) iconButtons.get(0)).fire());
        FlowPane glyphLayout = new FlowPane();
        glyphLayout.setHgap(10.0D);
        glyphLayout.setVgap(10.0D);
        glyphLayout.setPadding(new Insets(10.0D));
        glyphLayout.getChildren().setAll((Collection) iconButtons);
        glyphLayout.setPrefSize(600.0D, 300.0D);
        ScrollPane scrollableGlyphs = new ScrollPane(glyphLayout);
        scrollableGlyphs.setFitToWidth(true);
        return scrollableGlyphs;
    }

    private Button createIconButton(SVGGlyph glyph) {
        JFXButton button = new JFXButton(null, (Node) glyph);
        button.setPrefSize(30.0D, 30.0D);
        button.setMinSize(30.0D, 30.0D);
        button.setMaxSize(30.0D, 30.0D);
        button.ripplerFillProperty().bind(this.glyphDetailViewer.colorPicker.valueProperty());
        this.glyphDetailViewer.colorPicker.valueProperty().addListener((o, oldVal, newVal) -> {
            String webColor = "#" + Integer.toHexString(newVal.hashCode()).substring(0, 6).toUpperCase();
            Consumer<String> lookupConsumer = (lookup) -> {
                BackgroundFill fill = ((Region) (this.glyphDetailViewer).lookup(lookup)).getBackground().getFills().get(0);
                this.glyphDetailViewer.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.valueOf(webColor), fill.getRadii(), fill.getInsets())}));
            };
            lookupConsumer.accept(".thumb");
            lookupConsumer.accept(".colored-track");
            lookupConsumer.accept(".animated-thumb");
            if (this.lastClicked != null) {
                String currentColor = this.glyphDetailViewer.colorPicker.getValue().toString().substring(0, 8);
                BackgroundFill backgroundFill = new BackgroundFill(Color.valueOf(currentColor + "33"), ((BackgroundFill) this.lastClicked.getBackground().getFills().get(0)).getRadii(), null);
                this.lastClicked.setBackground(new Background(new BackgroundFill[]{backgroundFill}));
            }
        });
        button.setOnAction(event -> {
            if (this.lastClicked != null) {
                this.lastClicked.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.TRANSPARENT, ((BackgroundFill) this.lastClicked.getBackground().getFills().get(0)).getRadii(), null)}));
            }
            String currentColor = this.glyphDetailViewer.colorPicker.getValue().toString().substring(0, 8);
            button.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.valueOf(currentColor + "33"), (button.getBackground() == null) ? null : ((BackgroundFill) button.getBackground().getFills().get(0)).getRadii(), null)}));
            this.lastClicked = button;
            viewGlyphDetail(glyph);
        });
        Tooltip.install((Node) button, new Tooltip(glyph.getName()));
        return (Button) button;
    }

    private void viewGlyphDetail(SVGGlyph glyph) {
        try {
            this.glyphDetailViewer.setGlyph(SVGGlyphLoader.getIcoMoonGlyph("icomoon.svg." + glyph.getName()));
        } catch (Exception exception) {
        }
    }

    private static final class GlyphDetailViewer extends VBox {
        private static final int MIN_ICON_SIZE = 8;

        private static final int DEFAULT_ICON_SIZE = 128;

        private static final int MAX_ICON_SIZE = 256;

        private final ObjectProperty<SVGGlyph> glyph = new SimpleObjectProperty<>();

        private final Label idLabel = new Label();

        private final Label nameLabel = new Label();

        private final ColorPicker colorPicker = new ColorPicker(Color.BLACK);

        private final JFXSlider sizeSlider = new JFXSlider(8.0D, 256.0D, 128.0D);

        private final Label sizeLabel = new Label();

        private final StackPane centeredGlyph = new StackPane();

        GlyphDetailViewer() {
            GridPane details = new GridPane();
            details.setHgap(10.0D);
            details.setVgap(10.0D);
            details.setPadding(new Insets(24.0D));
            details.setMinSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
            Label sizeCalculator = new Label("999");
            Group sizingRoot = new Group(new Node[]{sizeCalculator});
            new Scene(sizingRoot);
            sizingRoot.applyCss();
            sizingRoot.layout();
            this.sizeLabel.setMinWidth(25.0D);
            this.sizeLabel.setPrefWidth(sizeCalculator.getWidth());
            this.sizeLabel.setAlignment(Pos.BASELINE_RIGHT);
            this.sizeSlider.setIndicatorPosition(JFXSlider.IndicatorPosition.RIGHT);
            this.sizeSlider.getStyleClass().add("svg-slider");
            HBox sizeControl = new HBox(5.0D, new Node[]{this.sizeLabel, (Node) this.sizeSlider});
            sizeControl.prefWidthProperty().bind(this.colorPicker.widthProperty());
            details.addRow(0, new Node[]{new Label("Id"), this.idLabel});
            details.addRow(1, new Node[]{new Label("Name"), this.nameLabel});
            details.addRow(2, new Node[]{new Label("Color"), this.colorPicker});
            details.addRow(3, new Node[]{new Label("Size"), sizeControl});
            this.sizeLabel.textProperty().bind(this.sizeSlider.valueProperty().asString("%.0f"));
            VBox.setVgrow(this.centeredGlyph, Priority.ALWAYS);
            StackPane.setMargin(this.centeredGlyph, new Insets(10.0D));
            this.centeredGlyph.setPrefSize(276.0D, 276.0D);
            glyphProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue != null) {
                    oldValue.fillProperty().unbind();
                    oldValue.prefWidthProperty().unbind();
                    oldValue.prefHeightProperty().unbind();
                }
                refreshView();
            });
            getChildren().setAll(new Node[]{details, this.centeredGlyph});
            setMinWidth(300.0D);
        }

        private void refreshView() {
            if (this.glyph.getValue() == null) {
                this.idLabel.setText("");
                this.nameLabel.setText("");
                return;
            }
            this.sizeSlider.valueProperty().addListener(observable -> ((SVGGlyph) this.glyph.get()).setSize(this.sizeSlider.getValue()));
            this.idLabel.setText(String.format("%04d", new Object[]{Integer.valueOf(((SVGGlyph) this.glyph.get()).getGlyphId())}));
            this.nameLabel.setText(((SVGGlyph) this.glyph.get()).getName());
            ((SVGGlyph) this.glyph.get()).setFill(this.colorPicker.getValue());
            ((SVGGlyph) this.glyph.get()).fillProperty().bind(this.colorPicker.valueProperty());
            this.centeredGlyph.getChildren().setAll(new Node[]{(Node) this.glyph.get()});
        }

        public final SVGGlyph getGlyph() {
            return this.glyph.get();
        }

        final ObjectProperty<SVGGlyph> glyphProperty() {
            return this.glyph;
        }

        final void setGlyph(SVGGlyph glyph) {
            glyph.setSize(this.sizeSlider.getValue());
            this.glyph.set(glyph);
        }
    }
}
