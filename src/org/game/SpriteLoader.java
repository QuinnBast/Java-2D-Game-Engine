package org.game;

import org.graphics.Renderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Quinn on 11/29/2017.
 */
public class SpriteLoader {

    public static SpriteLoader spriteLoader;
    private static HashMap<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();

    protected SpriteLoader(){
    }

    public static SpriteLoader init(){
        if(spriteLoader == null){
            spriteLoader = new SpriteLoader();
        }
        spriteLoader.loadResources();
        return spriteLoader;
    }

    public static BufferedImage getImage(String path){
        if(imageMap.containsKey(path)){
            return imageMap.get(path);
        } else {
            return null;
        }
    }

    private void loadResources() {

        String[] paths = {
                "Test.png",
                "Background.jpg"
        };

        for(String path : paths) {
            BufferedImage image = null;
            try {
                image = Renderer.loadImage("/resources/images/" + path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(image != null){
                imageMap.put(path, image);
            }
        }
    }
}