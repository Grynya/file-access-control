package sample.controllers.handlers.impl;

import javafx.scene.control.TextField;
import sample.controllers.handlers.RegistrationDataHandler;

import java.util.Optional;

public class EmptyFieldHandler extends RegistrationDataHandler {
    public EmptyFieldHandler(RegistrationDataHandler next) {
        super(next);
    }

    @Override
    public Optional<String> getMessage(TextField username, TextField password, TextField submittedPassword) {
        if (!username.getText().trim().isEmpty()
                && !password.getText().trim().isEmpty()
                && !submittedPassword.getText().trim().isEmpty()){
            if (next!=null) return next.getMessage(username, password, submittedPassword);
            else return Optional.empty();
        }
        return Optional.of("Empty field");
    }
}
