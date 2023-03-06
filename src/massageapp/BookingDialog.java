package massageapp;

/** Used for Custom Dialogs */

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;

public class BookingDialog extends Dialog<String> {

    private static ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);

    public BookingDialog(String title, String content) {
        super.setTitle(title);
        super.setContentText(content);
        super.getDialogPane().getButtonTypes().add(type);
    }
}
