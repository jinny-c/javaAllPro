package demos.datafx;

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
  implements FlowContainer<StackPane>
{
  private final StackPane view;
  private final Duration duration;
  private Function<AnimatedFlowContainer, List<KeyFrame>> animationProducer;
  private Timeline animation;
  private final ImageView placeholder;

  public ExtendedAnimatedFlowContainer() {
     this(Duration.millis(320.0D));
  }






  public ExtendedAnimatedFlowContainer(Duration duration) {
/*  45 */     this(duration, ContainerAnimations.FADE);
  }







  public ExtendedAnimatedFlowContainer(Duration duration, ContainerAnimations animation) {
/*  55 */     this(duration, animation.getAnimationProducer());
  }








  public ExtendedAnimatedFlowContainer(Duration duration, Function<AnimatedFlowContainer, List<KeyFrame>> animationProducer) {
/*  66 */     this.view = new StackPane();
/*  67 */     this.duration = duration;
/*  68 */     this.animationProducer = animationProducer;
/*  69 */     this.placeholder = new ImageView();
/*  70 */     this.placeholder.setPreserveRatio(true);
/*  71 */     this.placeholder.setSmooth(true);
  }

  public void changeAnimation(ContainerAnimations animation) {
/*  75 */     this.animationProducer = animation.getAnimationProducer();
  }


  public <U> void setViewContext(ViewContext<U> context) {
/*  80 */     updatePlaceholder(context.getRootNode());
/*  81 */     if (this.animation != null) {
/*  82 */       this.animation.stop();
    }
/*  84 */     this.animation = new Timeline();
/*  85 */     this.animation.getKeyFrames().addAll(this.animationProducer.apply(this));
/*  86 */     this.animation.getKeyFrames().add(new KeyFrame(this.duration, e -> clearPlaceholder(), new javafx.animation.KeyValue[0]));
/*  87 */     this.animation.play();
  }







  public ImageView getPlaceholder() {
/*  97 */     return this.placeholder;
  }






  public Duration getDuration() {
     return this.duration;
  }

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
             (int)this.view.getWidth(), (int)this.view.getHeight()));
       this.placeholder.setImage(placeholderImage);
       this.placeholder.setFitWidth(placeholderImage.getWidth());
       this.placeholder.setFitHeight(placeholderImage.getHeight());
    } else {
       this.placeholder.setImage((Image)null);
    }
     this.placeholder.setVisible(true);
     this.placeholder.setOpacity(1.0D);
     this.view.getChildren().setAll(new Node[] { this.placeholder, newView });
     this.placeholder.toFront();
  }
}


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\datafx\ExtendedAnimatedFlowContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */