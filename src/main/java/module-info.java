module com.example.chatsocket {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.rmi;
    requires java.sql;
    requires java.desktop;
    requires emoji.java;

    opens com.example.chatsocket to javafx.fxml, org.controlsfx.controls, com.dlsc.formsfx, org.kordamp.bootstrapfx.core, com.almasb.fxgl.all;
    exports com.example.chatsocket;
    exports com.example.chatsocket.server; // Export the server package
    exports com.example.chatsocket.client;
}
