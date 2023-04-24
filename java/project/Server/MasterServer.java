package project.Server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import project.Dao.UserDaoImpl;
import project.bean.ServerUser;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class MasterServer   {

    /**
     * 用户列表
     */
    private ArrayList<ServerUser> users;

    public ServerSocket masterServer;
    public WorkServer workServer;

    private int port = 8888;

    public void start() {
        users = new ArrayList<ServerUser>();
        try {
            masterServer = new ServerSocket(port);
            try {
                users = (ArrayList<ServerUser>) UserDaoImpl.getInstance().findAll();
                for (ServerUser u:users) {
                    u.setStatus("offline");
                }
                System.out.println("get user"+users.size());
            } catch (SQLException e) {
                System.out.println("userList init failed");
                e.printStackTrace();
            }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("在线聊天系统");
                    URL imageUrl = null;
                    try {
                        imageUrl = new File("src/main/resources/image/logo.jpg").toURI().toURL();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Image image = new Image(String.valueOf(imageUrl));

                    Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(image);
                    alert.setContentText("服务器启动成功！ " );
                    alert.show();
                    System.out.println("server loading");



        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                workServer = new WorkServer(masterServer.accept(), users);
                workServer.start();
                System.out.println("workServer product");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
