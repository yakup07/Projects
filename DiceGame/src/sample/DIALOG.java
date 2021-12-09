package sample;

import javax.swing.*;

public interface DIALOG {
    static void showDialog(String message, String title){
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.OK_OPTION);
    }
}
