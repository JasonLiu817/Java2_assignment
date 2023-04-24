package project;


import javafx.application.Application;
import javafx.stage.Stage;
import project.Server.MasterServer;

public class ServerLaucher extends Application {
  public static void main(String[] args) {
    launch(args);
  }


    @Override
    public void start(Stage primaryStage) throws Exception {
        MasterServer masterServer = new MasterServer();
        masterServer.start();
    }
}
