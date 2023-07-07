module com.mp4player.mp4player {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.mp4player.mp4player to javafx.fxml;
    exports com.mp4player.mp4player;
}