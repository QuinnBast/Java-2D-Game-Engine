package org.developmentEngine.resourceManager.resourceProperties;

import org.userInterface.window.fileBrowser.Resources.Resource;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Quinn on 5/19/2018.
 */
public abstract class ResourceProperties {

    private ArrayList<PropertyObserver> propertyObservers = new ArrayList<>();

    public void notifyUpdate(){
        for(PropertyObserver po : propertyObservers){
            try {
                po.onResourceUpdate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPropertyObserver(PropertyObserver po){
        this.propertyObservers.add(po);
    }

    public void removePropertyObserver(PropertyObserver po){
        this.propertyObservers.remove(po);
    }


}
