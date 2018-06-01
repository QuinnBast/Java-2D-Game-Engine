package org.userInterface.window.centerScreen.resourceTabs.spriteTabs;

import org.developmentEngine.resourceManager.resourceProperties.ResourceProperties;
import org.developmentEngine.resourceManager.resourceProperties.SpriteProperties;
import org.userInterface.window.centerScreen.resourceTabs.Tab;
import org.developmentEngine.resourceManager.Resources.Resource;
import org.developmentEngine.resourceManager.Resources.SpriteResource;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.userInterface.UserInterface.window;

/**
 * Created by Quinn on 5/11/2018.
 */
public class SpritePropertiesTab extends Tab  {

    JLabel filePathText;
    private SpriteProperties spriteProperties;
    JLabel imageLabel = new JLabel();
    JSpinner originX = new JSpinner();
    JSpinner originY = new JSpinner();
    JLabel imageWidthText = new JLabel("Width: 0px");
    JLabel imageHeightText = new JLabel("Height: 0px");
    JSpinner height = new JSpinner();
    JSpinner width = new JSpinner();

    public SpritePropertiesTab(Resource r){
        super(r);
        r.getProperties().addPropertyObserver(this);
        this.spriteProperties = (SpriteProperties)((SpriteResource) r).getProperties();

        //Create the file name print-out and the file selector
        JPanel filePanel = new JPanel();
        if(spriteProperties.getFilepaths().get(0) != null) {
            filePathText = new JLabel(spriteProperties.getFilepaths().get(0));
        } else {
            filePathText = new JLabel("");
        }
        JButton fileChooser = new JButton("Select Image");
        fileChooser.setVisible(true);
        fileChooser.addActionListener(al);
        filePanel.add(filePathText);
        filePanel.add(fileChooser);

        //Display the image
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        SpritePropertyCanvas spc = new SpritePropertyCanvas(spriteProperties);
        imagePanel.add(spc, BorderLayout.CENTER);
        imagePanel.setPreferredSize(new Dimension(350, 350));

        SpinnerNumberModel originXModel;
        SpinnerNumberModel originYModel;
        SpinnerNumberModel heightModel;
        SpinnerNumberModel widthModel;

        if(spriteProperties.getFilepaths().get(0) != "") {
            originXModel = new SpinnerNumberModel(spriteProperties.getOrigin().getX(), 0,spriteProperties.getSize().getWidth(), 1);
            originYModel = new SpinnerNumberModel(spriteProperties.getOrigin().getY(), 0, spriteProperties.getSize().getHeight(), 1);
            widthModel = new SpinnerNumberModel(spriteProperties.getSize().getWidth(), 0, 9999, 1);
            heightModel = new SpinnerNumberModel(spriteProperties.getSize().getHeight(), 0, 9999, 1);
        } else {
            originXModel = new SpinnerNumberModel(0, 0, 0, 1);
            originYModel = new SpinnerNumberModel(0, 0, 0, 1);
            heightModel = new SpinnerNumberModel(0, 0, 9999, 1);
            widthModel = new SpinnerNumberModel(0, 0, 9999, 1);
        }

        //Panel to set the x origin
        JPanel originXPanel = new JPanel();
        JLabel xoriginText = new JLabel("X Origin");
        originX.setModel(originXModel);
        originXPanel.add(xoriginText);
        originXPanel.add(originX);
        originXPanel.add(imageWidthText);
        originX.addChangeListener(originXChangeListener);

        //Panel to set the Y origin
        JPanel originYPanel = new JPanel();
        JLabel yoriginText = new JLabel("Y Origin");
        originY.setModel(originYModel);
        originYPanel.add(yoriginText);
        originYPanel.add(originY);
        originYPanel.add(imageHeightText);
        originY.addChangeListener(originYChangeListener);

        //Panel to set the sprite height
        JPanel heightPanel = new JPanel();
        JLabel heightText = new JLabel("Height");
        height.setModel(heightModel);
        heightPanel.add(heightText);
        heightPanel.add(height);
        height.addChangeListener(heightChangeListener);

        //Panel to set the sprite width
        JPanel widthPanel = new JPanel();
        JLabel widthText = new JLabel("Width");
        width.setModel(widthModel);
        widthPanel.add(widthText);
        widthPanel.add(width);
        width.addChangeListener(widthChangeListener);

        JPanel centerOriginPanel = new JPanel();
        JButton centerOriginButton = new JButton("Center Origin");
        centerOriginPanel.add(centerOriginButton);
        centerOriginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spriteProperties.setOrigin(new Point2D.Double(spriteProperties.getSize().getWidth()/2, spriteProperties.getSize().getHeight() / 2));
            }
        });

        this.add(filePanel);
        this.add(imagePanel);
        this.add(originXPanel);
        this.add(originYPanel);
        this.add(centerOriginPanel);
        this.add(widthPanel);
        this.add(heightPanel);
        this.setVisible(true);
    }

    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg, .png, .gif, .tiff, .jpeg", "jpg", "gif", "png", "tiff", "jpeg");
            fileChooser.setFileFilter(filter);
            int returnOption = fileChooser.showOpenDialog(null);
            if(returnOption == JFileChooser.APPROVE_OPTION) {
                SpriteProperties spriteProperties = (SpriteProperties) window.getOpenFileResource().getProperties();
                spriteProperties.setFilepath(fileChooser.getSelectedFile().getAbsolutePath());
                spriteProperties.setSize(new Rectangle2D.Double(0, 0, spriteProperties.getImageIcon().getIconWidth(), spriteProperties.getImageIcon().getIconHeight()));
            }
        }
    };

    ChangeListener originXChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            spriteProperties.setOrigin(new Point2D.Double((double)originX.getValue(), spriteProperties.getOrigin().getY()));
        }
    };

    ChangeListener originYChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            spriteProperties.setOrigin(new Point2D.Double(spriteProperties.getOrigin().getX(), (double)originY.getValue()));
        }
    };

    ChangeListener widthChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            spriteProperties.setSize(new Rectangle2D.Double(0, 0, (Double)width.getValue(), spriteProperties.getSize().getHeight()));


        }
    };

    ChangeListener heightChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            spriteProperties.setSize(new Rectangle2D.Double(0, 0, spriteProperties.getSize().getWidth(), (Double)height.getValue()));
        }
    };

    @Override
    public void onResourceUpdate(ResourceProperties properties) {
        this.filePathText.setText(spriteProperties.getFilepaths().get(0));

        if(spriteProperties.getFilepaths().get(0) != "") {
            ImageIcon originalImage = spriteProperties.getImageIcon();

            ImageIcon imgIcon = new ImageIcon(originalImage.getImage().getScaledInstance(280, 280, Image.SCALE_SMOOTH));
            imageLabel.setIcon(imgIcon);

            originX.setModel(new SpinnerNumberModel(spriteProperties.getOrigin().getX(), 0, spriteProperties.getSize().getWidth(), 1));
            originY.setModel(new SpinnerNumberModel(spriteProperties.getOrigin().getY(), 0, spriteProperties.getSize().getHeight(), 1));
            width.setModel(new SpinnerNumberModel(spriteProperties.getSize().getWidth(), 0, 9999, 1));
            height.setModel(new SpinnerNumberModel(spriteProperties.getSize().getHeight(), 0, 9999, 1));
            imageWidthText.setText("Width: " + spriteProperties.getSize().getWidth() + "px");
            imageHeightText.setText("Height: " + spriteProperties.getSize().getHeight() + "px");
        } else {
            originX.setModel(new SpinnerNumberModel(0, 0, 0, 1));
            originY.setModel(new SpinnerNumberModel(0, 0, 0, 1));
            height.setModel(new SpinnerNumberModel(0, 0, 9999, 1));
            width.setModel(new SpinnerNumberModel(0, 0, 9999, 1));
            imageWidthText.setText("Width: 0px");
            imageHeightText.setText("Height: 0px");
        }

    }

}