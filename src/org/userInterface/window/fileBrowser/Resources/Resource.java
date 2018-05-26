package org.userInterface.window.fileBrowser.Resources;

import org.applicationEngine.objects.ObjectType;
import org.developmentEngine.resourceManager.ResourceObserver;
import org.developmentEngine.resourceManager.resourceProperties.PropertyObserver;
import org.developmentEngine.resourceManager.resourceProperties.ResourceProperties;

import javax.swing.*;

/**
 * Created by Quinn on 5/4/2018.
 */
public abstract class Resource extends JPanel {

    private String filePath;
    protected ResourceProperties resourceProperties;

    public Resource(String path){
        this.filePath = path;
    }

    public String getFilePath(){
        return this.filePath;
    }

    public ObjectType getObjectType(){
        if(this instanceof SpriteResource){
            return ObjectType.SPRITE;
        } else if (this instanceof RoomResource){
            return ObjectType.ROOM;
        } else if (this instanceof ObjectResource){
            return ObjectType.OBJECT;
        } else if (this instanceof ScriptResource){
            return ObjectType.SCRIPT;
        }
        return null;
    }

    public ResourceProperties getProperties(){
        return this.resourceProperties;
    }

    @Override
    public String toString() {
        return this.filePath;
    }
}
