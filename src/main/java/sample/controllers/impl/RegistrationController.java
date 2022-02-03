package sample.controllers.impl;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.services.user.UserService;
import sample.utils.UserSession;
import sample.controllers.PageLoader;
import sample.controllers.handlers.impl.EmptyFieldHandler;
import sample.controllers.handlers.impl.EqualsPasswordsHandler;
import sample.controllers.handlers.RegistrationDataHandler;
import sample.controllers.handlers.impl.ValidPasswordHandler;
import sample.model.User;
import sample.services.user.impl.UserServiceImpl;

public class RegistrationController implements PageLoader {
    private final UserService userService = new UserServiceImpl();
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField submittedPassword;
    @FXML
    private Label errorMessage;

    public RegistrationController() {
    }

    @FXML
    void processCreateBtn(ActionEvent actionEvent) throws IOException {
        this.errorMessage.setText("");
        Optional<String> errorMsg = this.getFilterChain().getMessage(this.username, this.password, this.submittedPassword);
        if (errorMsg.isPresent()) {
            this.errorMessage.setText(errorMsg.get());
        } else {
            Optional<User> user = this.userService.add(this.username.getText(), this.password.getText());
            if (user.isPresent()) {
                UserSession.getInstance((user.get()).getId(), (user.get()).getUsername());
                loadResource("/fxml/userPage.fxml", actionEvent);
            } else this.errorMessage.setText("User with name "+this.username.getText()+" already exists");
        }

    }

    private RegistrationDataHandler getFilterChain() {
        return new EmptyFieldHandler(new EqualsPasswordsHandler(new ValidPasswordHandler(null)));
    }
}
