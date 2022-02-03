package sample.controllers.impl.mainpagescontrollers.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import sample.controllers.impl.mainpagescontrollers.PageController;
import sample.model.CustomFile;
import sample.model.User;
import sample.services.user.UserService;
import sample.services.user.impl.UserServiceImpl;
import sample.utils.UserSession;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminPageController extends PageController {
    public Button addFileBtn;
    private final UserService userService;
    @FXML
    private ComboBox<User> userToWhichAdd;

    public AdminPageController() {
        this.userService = new UserServiceImpl();
    }
    @FXML
    public void initialize() {
        loadFiles();
        loadUsers();
    }

    private void loadUsers() {
        List<User> users = userService.users();
        userToWhichAdd.getItems()
                .addAll(new ArrayList<>(users));
        userToWhichAdd.setValue(users.get(0));
    }

    @Override
    public String getResourceName() {
        return "/fxml/adminPage.fxml";
    }

    @Override
    public List<CustomFile> getFiles() {
        return fileService.files();
    }

    @Override
    public Button getDeleteButton(int fileId) {
        Button bd = new Button("");
        bd.setStyle("-fx-background-color: white");
        ImageView im = new ImageView("/img/del.png");
        im.setFitWidth(20);
        im.setFitHeight(20);
        bd.setMaxWidth(20);
        bd.setGraphic(im);
        bd.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                fileService.fullDelete(new User(UserSession.getInstance().getId(),
                        UserSession.getInstance().getUsername()), fileId);
                try {
                    refreshStage(e);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        });
        return bd;
    }

    @Override
    public void processAddFileBtn(ActionEvent actionEvent) throws IOException {
        File file = getFile();
            if (file != null) {
                if (fileService.add(userToWhichAdd.getValue().getId(), file.getAbsolutePath()))
                    refreshStage(actionEvent);
                else loadAlert("Adding file error", "File already exists");
            }
    }

}
