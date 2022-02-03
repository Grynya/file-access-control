package sample.controllers.handlers.impl;

import javafx.scene.control.TextField;
import sample.controllers.handlers.RegistrationDataHandler;

import java.util.Optional;

public class EqualsPasswordsHandler extends RegistrationDataHandler {
    public EqualsPasswordsHandler(RegistrationDataHandler next) {
        super(next);
    }

    @Override
    public Optional<String> getMessage(TextField username, TextField password, TextField submittedPassword) {
        if (password.getText().equals(submittedPassword.getText())){
            if (next!=null) return next.getMessage(username, password, submittedPassword);
            else return Optional.empty();
        }
        return Optional.of("Passwords don't match");
    }
}
