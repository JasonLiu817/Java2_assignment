package project.Client.Login;

import javafx.scene.image.Image;
import project.Client.model.ClientModel;
import project.Client.stage.ControlledStage;
import project.Client.stage.StageController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import project.ClientLaucher;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements ControlledStage, Initializable {

    @FXML
    TextField textPassword;
    @FXML
    TextField txtUsername;
    @FXML
    TextField txtHostName;
    @FXML
    Button btn_login;
    @FXML
    ImageView imageView;
    @FXML
    Button btn_signIn;

    StageController myController;
    ClientModel model;

    public LoginViewController() {
        super();
    }


    public void setStageController(StageController stageController) {
        this.myController = stageController;
        model = ClientModel.getInstance();
    }

    public void initialize(URL location, ResourceBundle resources) {


    }

    public void goToMain() {
        myController.loadStage(ClientLaucher.mainViewID,ClientLaucher.mainViewRes);
        myController.setStage(ClientLaucher.mainViewID,ClientLaucher.loginViewID);
        myController.getStage(ClientLaucher.mainViewID).setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                model.disConnect();
                myController.unloadStage(ClientLaucher.EmojiSelectorID);
            }
        });
    }



    /**
     * 最小化窗口
     * @param event
     */
    @FXML public void minBtnAction(ActionEvent event){
        Stage stage = myController.getStage(ClientLaucher.loginViewID);
        stage.setIconified(true);
    }
    /**
     * 关闭窗口，关闭程序
     * @param event
     */
    @FXML public void closeBtnAction(ActionEvent event){
        Platform.exit();
        System.exit(0);
    }

    public void showError(String error) {

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
        alert.setContentText("登录失败 " + error);
        alert.show();
    }

    @FXML
    void logIn(ActionEvent event) {
        StringBuffer result = new StringBuffer();
        if (model.CheckLogin(txtUsername.getText(), txtHostName.getText(),textPassword.getText(), result, 0)) {
            goToMain();
        } else {
            showError(result.toString());
        }
    }

    @FXML
    void signUp(ActionEvent event) {
        StringBuffer result = new StringBuffer();
        if (model.CheckLogin(txtUsername.getText(), txtHostName.getText(),textPassword.getText(), result, 1)) {
            goToMain();
        } else {
            showError(result.toString());
        }
    }

}
