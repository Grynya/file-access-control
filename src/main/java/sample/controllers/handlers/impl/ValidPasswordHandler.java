package sample.controllers.handlers.impl;

import javafx.scene.control.TextField;
import sample.controllers.handlers.RegistrationDataHandler;

import java.util.Optional;

public class ValidPasswordHandler extends RegistrationDataHandler {
    public ValidPasswordHandler(RegistrationDataHandler next) {
        super(next);
    }

    @Override
    public Optional<String> getMessage(TextField username, TextField password, TextField submittedPassword) {
        if (isValidPassword(password)){
            if (next!=null) return next.getMessage(username, password, submittedPassword);
            else return Optional.empty();
        }
        return Optional.of("Password's length should be between 5 and 20");
    }

    private boolean isValidPassword(TextField password) {
        return password.getText().length() >= 5 && password.getText().length() <= 20;
    }
}
