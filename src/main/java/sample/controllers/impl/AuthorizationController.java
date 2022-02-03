package sample.controllers.impl;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.services.user.UserService;
import sample.utils.UserSession;
import sample.controllers.PageLoader;
import sample.model.Roles;
import sample.model.User;
import sample.services.user.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Optional;

public class AuthorizationController implements PageLoader {
    private final UserService userService;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label errorMessage;

    public AuthorizationController() {
        this.userService = new UserServiceImpl();
    }

    @FXML
    void processLoginBtn(ActionEvent actionEvent) throws IOException {
        errorMessage.setText("");
        Optional<User> user = userService.getUser(username.getText(), password.getText());
        if (isEmpty(username) && isEmpty(password)) {
               if(user.isPresent()){
                   //create session
                   UserSession.getInstance(user.get().getId(), user.get().getUsername());
                   System.out.println(UserSession.getInstance());
                   if ((user.get().getRole().getName().equals(Roles.USER.name()))) {
                       loadResource("/fxml/userPage.fxml", actionEvent);
                   } else {
                       loadResource("/fxml/adminPage.fxml", actionEvent);
                   }
               }else errorMessage.setText("Invalid credentials");
        }else errorMessage.setText("Empty field");
    }

    @FXML
    public void registration(ActionEvent actionEvent) throws IOException {
        loadResource("/fxml/registration.fxml", actionEvent);
    }

    private boolean isEmpty(TextField value){
        return !value.getText().trim().isEmpty();
    }

}
