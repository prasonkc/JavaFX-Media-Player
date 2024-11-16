package org.example.multimedia;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    private final Stage stage;
    private MediaView mView;
    private Scene startScene;
    private Scene mScene;
    private Slider seekBar;
    private Slider volumeSlider;

    public Controller(Stage stage) {
        this.stage = stage;
    }

    public void initializeUI(MediaManager mediaManager) {
        mView = new MediaView();

        // Configure MediaView
        mView.fitWidthProperty().bind(Bindings.selectDouble(mView.sceneProperty(), "width"));
        mView.fitHeightProperty().bind(Bindings.selectDouble(mView.sceneProperty(), "height"));
        mView.setPreserveRatio(true);

        // Media Controls
        Button playButton = new Button("Play");
        Button pauseButton = new Button("Pause");
        Button stopButton = new Button("Stop");

        volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setPrefWidth(150);

        HBox volumeControl = new HBox(new Label("Volume"), volumeSlider);
        volumeControl.setSpacing(5);

        seekBar = new Slider();
        seekBar.setPrefWidth(400);

        HBox controls = new HBox(playButton, pauseButton, stopButton, volumeControl);
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(10);

        VBox mainControls = new VBox(seekBar, controls);
        mainControls.setAlignment(Pos.BOTTOM_CENTER);

        StackPane mediaPane = new StackPane(mView, mainControls);

        // File chooser scene
        Button fileChooseBtn = new Button("Select a file");
        StackPane startPane = new StackPane(fileChooseBtn);
        startPane.setAlignment(Pos.CENTER);

        startScene = new Scene(startPane, 650, 500);
        mScene = new Scene(mediaPane, 650, 500);

        // Button Actions
        fileChooseBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a Media File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv"),
                    new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );

            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                mediaManager.loadMedia(selectedFile, mScene, seekBar, volumeSlider);
            }
        });

        playButton.setOnAction(event -> mediaManager.play());
        pauseButton.setOnAction(event -> mediaManager.pause());
        stopButton.setOnAction(event -> mediaManager.stop());
    }

    public Scene getStartScene() {
        return startScene;
    }

    public MediaView getMediaView() {
        return mView;
    }
}
