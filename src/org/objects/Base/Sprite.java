package org.objects.Base;

import org.game.SpriteLoader;
import org.graphics.Camera;
import org.graphics.Renderer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Quinn on 11/26/2017.
 */
public abstract class Sprite {
    protected float posX = 0;
    protected float posY = 0;
    protected BufferedImage image = null;
    protected Rectangle2D boundingBox;	//Bounding Box of the object

    public Sprite(float posX, float posY, String image){
        this.posX = posX;
        this.posY = posY;
        this.image = SpriteLoader.getImage(image);

        //Set a bounding box around the object.
        if(this.image != null){
            this.boundingBox = new Rectangle2D.Double(posX - this.image.getWidth()/2, posY - this.image.getHeight()/2, posX + this.image.getWidth()/2, posY + this.image.getHeight()/2);
        }
    }

    public Sprite(float posX, float posY, float width, float height){
        this.posX = posX;
        this.posY = posY;
        this.boundingBox = new Rectangle2D.Double(posX - (width/2), posY - (height/2), width, height);
    }

    public BufferedImage getImage(){
        return image;
    }

    public float getPosX(){
        return this.posX;
    }

    public float getPosY(){
        return this.posY;
    }

    public Rectangle2D getBoundingBox(){
        return this.boundingBox;
    }

    public abstract void update(float deltaTime);
}