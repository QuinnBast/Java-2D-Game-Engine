package org.userInterface.window.navBar.fileMenu.buttons;

import org.developmentEngine.DevelopmentEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Quinn on 5/4/2018.
 */
public class NewProjectButton extends JMenuItem {

    public NewProjectButton(){
        this.setText("New Project");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DevelopmentEngine.projectManager.newProject();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
