package com.example.app.demo.jfoenix.controller;

import io.datafx.controller.context.ViewContext;
import io.datafx.controller.flow.FlowContainer;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;
import java.util.function.Function;


public class ExtendedAnimatedFlowContainer
        extends AnimatedFlowContainer
        implements FlowContainer<StackPane> {
    private final StackPane view;
    private final Duration duration;
    private Function<AnimatedFlowContainer, List<KeyFrame>> animationProducer;
    private Timeline animation;
    private final ImageView placeholder;

    public ExtendedAnimatedFlowContainer() {
        this(Duration.millis(320.0D));
    }


    public ExtendedAnimatedFlowContainer(Duration duration) {
        /*  45 */
        this(duration, ContainerAnimations.FADE);
    }


    public ExtendedAnimatedFlowContainer(Duration duration, ContainerAnimations animation) {
        this(duration, animation.getAnimationProducer());
    }


    public ExtendedAnimatedFlowContainer(Duration duration, Function<AnimatedFlowContainer, List<KeyFrame>> animationProducer) {
        this.view = new StackPane();
        this.duration = duration;
        this.animationProducer = animationProducer;
        this.placeholder = new ImageView();
        this.placeholder.setPreserveRatio(true);
        this.placeholder.setSmooth(true);
    }

    public void changeAnimation(ContainerAnimations animation) {
        this.animationProducer = animation.getAnimationProducer();
    }


    @Override
    public <U> void setViewContext(ViewContext<U> context) {
        updatePlaceholder(context.getRootNode());
        if (this.animation != null) {
            this.animation.stop();
        }
        this.animation = new Timeline();
        this.animation.getKeyFrames().addAll(this.animationProducer.apply(this));
        this.animation.getKeyFrames().add(new KeyFrame(this.duration, e -> clearPlaceholder(), new javafx.animation.KeyValue[0]));
        this.animation.play();
    }


    @Override
    public ImageView getPlaceholder() {
        /*  97 */
        return this.placeholder;
    }


    @Override
    public Duration getDuration() {
        return this.duration;
    }

    @Override
    public StackPane getView() {
        return this.view;
    }

    private void clearPlaceholder() {
        this.view.getChildren().remove(this.placeholder);
    }

    private void updatePlaceholder(Node newView) {
        if (this.view.getWidth() > 0.0D && this.view.getHeight() > 0.0D) {
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            Image placeholderImage = this.view.snapshot(parameters, new WritableImage(
                    (int) this.view.getWidth(), (int) this.view.getHeight()));
            this.placeholder.setImage(placeholderImage);
            this.placeholder.setFitWidth(placeholderImage.getWidth());
            this.placeholder.setFitHeight(placeholderImage.getHeight());
        } else {
            this.placeholder.setImage((Image) null);
        }
        this.placeholder.setVisible(true);
        this.placeholder.setOpacity(1.0D);
        this.view.getChildren().setAll(new Node[]{this.placeholder, newView});
        this.placeholder.toFront();
    }
}