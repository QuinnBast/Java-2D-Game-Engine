package org.developmentEngine.resourceManager.resourceProperties;

import org.userInterface.window.fileBrowser.Resources.ObjectResource;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by Quinn on 5/19/2018.
 */
public class RoomProperties extends ResourceProperties {

    private Rectangle2D size = new Rectangle2D.Double(0, 0, 0, 0);
    private String name = "";
    private int desiredFramerate = 60;
    private Color backgroundColor = Color.BLACK;
    private String backgroundImageLink = "";
    private ArrayList<ObjectResource> objectsInRoom = new ArrayList<>();

    public Rectangle2D getSize() {
        return size;
    }

    public void setSize(Rectangle2D size) {
        this.size = size;
        this.notifyUpdate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.notifyUpdate();
    }

    public int getDesiredFramerate() {
        return desiredFramerate;
    }

    public void setDesiredFramerate(int desiredFramerate) {
        this.desiredFramerate = desiredFramerate;
        this.notifyUpdate();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.notifyUpdate();
    }

    public String getBackgroundImageLink() {
        return backgroundImageLink;
    }

    public void setBackgroundImageLink(String backgroundImageLink) {
        this.backgroundImageLink = backgroundImageLink;
        this.notifyUpdate();
    }

    public ArrayList<ObjectResource> getObjectsInRoom() {
        return objectsInRoom;
    }

    public void setObjectsInRoom(ArrayList<ObjectResource> objectsInRoom) {
        this.objectsInRoom = objectsInRoom;
        this.notifyUpdate();
    }

    public RoomProperties(){

    }
}