package sample.controllers.impl.mainpagescontrollers.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import sample.model.User;
import sample.utils.UserSession;
import sample.controllers.impl.mainpagescontrollers.PageController;
import sample.model.CustomFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserPageController extends PageController {

    @Override
    public String getResourceName() {
        return "/fxml/userPage.fxml";
    }

    @Override
    public List<CustomFile> getFiles() {
        return super.fileService.files(UserSession.getInstance().getId());
    }

    @FXML
    @Override
    public void processAddFileBtn(ActionEvent actionEvent) throws IOException {
        File file = getFile();
        if ( file!= null) {
            if (fileService.add(UserSession.getInstance().getId(), file.getAbsolutePath())) refreshStage(actionEvent);
            else loadAlert("Adding file error", "File already exists");
        }
    }

    @Override
    public Button getDeleteButton(int fileId) {
        Button bd = new Button();
        bd.setStyle("-fx-background-color: white");
        ImageView im = new ImageView("/img/del.png");
        im.setFitWidth(20);
        im.setFitHeight(20);
        bd.setMaxWidth(5);
        bd.setGraphic(im);
        bd.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                fileService.delete(new User(UserSession.getInstance().getId(),
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
}
