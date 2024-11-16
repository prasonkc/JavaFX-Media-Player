package org.example.multimedia;

import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class MediaManager {
    private MediaPlayer mPlayer;
    private final Stage stage;
    private final Controller controller;

    public MediaManager(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void loadMedia(File file, Scene mediaScene, Slider seekBar, Slider volumeSlider) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.dispose();
        }

        Media media = new Media(file.toURI().toString());
        mPlayer = new MediaPlayer(media);
        controller.getMediaView().setMediaPlayer(mPlayer);

        stage.setTitle("Now Playing: " + file.getName());
        stage.setScene(mediaScene);

        mPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            seekBar.setValue(newValue.toSeconds());
        });

        seekBar.setOnMousePressed(event -> mPlayer.seek(Duration.seconds(seekBar.getValue())));
        seekBar.setOnMouseDragged(event -> mPlayer.seek(Duration.seconds(seekBar.getValue())));

        mPlayer.setOnReady(() -> {
            seekBar.setMax(mPlayer.getMedia().getDuration().toSeconds());
            mPlayer.play();
        });

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mPlayer.setVolume(newValue.doubleValue());
        });

        mPlayer.setOnEndOfMedia(() -> mPlayer.stop());
    }

    public void play() {
        if (mPlayer != null) mPlayer.play();
    }

    public void pause() {
        if (mPlayer != null) mPlayer.pause();
    }

    public void stop() {
        if (mPlayer != null) mPlayer.stop();
    }
}
