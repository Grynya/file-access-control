package sample.controllers.impl.mainpagescontrollers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sample.services.file.FileService;
import sample.utils.UserSession;
import sample.controllers.PageLoader;
import sample.model.CustomFile;
import sample.services.file.impl.FileServiceImpl;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class PageController implements PageLoader {
    public final FileService fileService;

    @FXML
    private ListView<ButtonBar> files;

    protected PageController() {
        this.fileService = new FileServiceImpl();
    }

    public abstract String getResourceName();

    public abstract List<CustomFile> getFiles();

    public abstract Button getDeleteButton(int fileId);

    @FXML
    public abstract void processAddFileBtn(ActionEvent actionEvent) throws IOException ;

    @FXML
    public void initialize() {
        loadFiles();
        name.setText(UserSession.getInstance().getUsername());
    }

    @FXML
    private Label name;


    public void loadFiles() {
        if (files == null) {
            files = new ListView<>();
        }
        for (CustomFile file : getFiles()) {
            ButtonBar bar = new ButtonBar();
            Button bo = getOpenButton(file.getName(), file.getPath());
            bo.setTranslateX(-800);
            bar.getButtons().addAll(bo, getDeleteButton(file.getId()),
                    getEditButton(file));
            files.getItems().add(bar);
        }
    }
    private Button getOpenButton(String fileName, String filePath){
        Button bo = new Button(fileName);
        bo.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                File f = new File(filePath);
                if(f.exists()) {
                    try {
                        desktop.open(f);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }else loadAlert("Error opening file", "File doesn't exist");
            }
        });
        return bo;
    }


    private Button getEditButton(CustomFile file) {
        Button be = new Button();
        ImageView im = new ImageView("/img/edit.png");
        be.setStyle("-fx-background-color: white");
        im.setFitWidth(20);
        im.setFitHeight(20);
        be.setMaxWidth(5);
        be.setGraphic(im);
        be.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        fileService.edit(file.getId(), getFile().getAbsolutePath());
                        try {
                            refreshStage(event);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                });
        return be;
    }


    protected void refreshStage(ActionEvent actionEvent) throws IOException {
        loadResource(getResourceName(), actionEvent);
    }

    @FXML
    void processLogOutBtn(ActionEvent actionEvent) throws IOException {
        UserSession.getInstance().cleanUserSession();
        loadResource("/fxml/index.fxml", actionEvent);
    }

    protected void loadAlert(String title, String contextText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
}
