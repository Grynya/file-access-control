package sample.controllers.handlers;

import javafx.scene.control.TextField;

import java.util.Optional;

public abstract class RegistrationDataHandler {
    public final RegistrationDataHandler next;

    public RegistrationDataHandler(RegistrationDataHandler next) {
        this.next=next;
    }

    public abstract Optional<String> getMessage(TextField username, TextField password, TextField submittedPassword);

}
