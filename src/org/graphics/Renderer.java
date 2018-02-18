package org.graphics;

import org.fpsChecker.FPS;
import org.game.Game;
import org.input.userInput;
import org.objects.Sprite;
import org.world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Quinn on 11/26/2017.
 */
public class Renderer {

    private static Frame frame;     //the window
    private static Canvas canvas;   //the drawable area

    private static int canvasWidth = 0;
    private static int canvasHeight = 0;

    public static int scaleFactor = 1;

    private static final int GAME_WIDTH = 400;  //'Pixels'  -   Target default size
    private static final int GAME_HEIGHT = 250; //'Pixels'  -   Target default size

    private static int gameWidth = 0;     // Size that the game ends up
    private static int gameHeight = 0;    // Size that the game ends up

    public static Camera camera;
    private static Image bg;

    public Renderer(){
        this.init();
    }

    private static void getBestSize(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        while(canvasWidth < screenSize.width && canvasHeight < screenSize.height){
            canvasWidth += GAME_WIDTH;
            canvasHeight += GAME_HEIGHT;
        }
        canvasHeight -= GAME_HEIGHT;
        canvasWidth -= GAME_WIDTH;

        scaleFactor = canvasWidth/GAME_WIDTH;

        gameWidth = (canvasWidth / scaleFactor) + ((screenSize.width - canvasWidth)/scaleFactor);
        gameHeight = (canvasHeight / scaleFactor) + ((screenSize.height - canvasHeight)/scaleFactor);

        canvasWidth = gameWidth * scaleFactor;
        canvasHeight = gameHeight * scaleFactor;
    }

    private static void makeFullscreen(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();        //Video card that is being used

        if(gd.isFullScreenSupported()){
            frame.setUndecorated(true);
            gd.setFullScreenWindow(frame);
        }
    }

    private static void renderingThread(){

        //Multi-Threading!
        Thread thread = new Thread(){
            public void run(){

                GraphicsConfiguration gc = canvas.getGraphicsConfiguration();
                VolatileImage vi = gc.createCompatibleVolatileImage(canvasWidth, canvasHeight);

                //Always render
                while(true){

                    if(vi.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE){
                        //If the graphics validation is not compatible, recreate the image
                        vi = gc.createCompatibleVolatileImage(canvasWidth, -canvasHeight);
                    }

                    //Draw everything to the volatile image, draw the volatile image ot the screen.
                    Graphics g = vi.getGraphics();

                    //Render graphics
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, canvasWidth, canvasHeight);    //Set the width of the background to fit the canvas (screen size)

                    //Render the world and world objects.
                    if(camera != null) {
                        World.render(g, camera);
                        camera.fixOnCamera(g);
                    }
                    World.update();
//                    if(bg == null){
//                        bg = new Image("/resources/images/Background.jpg");
//                    }
//                    g.drawImage(bg.getImage(), 0, 0, Renderer.canvasWidth, Renderer.canvasHeight, null);

                    //Draw FPS counter
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawString(String.valueOf(FPS.getFPS()), (int)(canvasWidth - 20), (int)(canvasHeight - 20));

                    g.dispose();

                    g = canvas.getGraphics();   //Draw image to canvas
                    g.drawImage(vi, 0,0,canvasWidth, canvasHeight, null);
                    g.dispose();
                }
            }
        };
        thread.setName("Rendering Thread");
        thread.start();

    }
    public static void init(){
        getBestSize();      //Calculates the factor at which the game has been scaled for the user's screen size.

        frame = new Frame();
        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        canvas.addKeyListener(new userInput());                             //Add a key listener to the canvas


        frame.add(canvas);                  //Add the canvas to the frame
        makeFullscreen();                   //Makes the window(frame) fullscreen
        frame.pack();                       //Make the frame's size fit its compenents.
        frame.setResizable(false);          //Dont let user's resize
        frame.setLocationRelativeTo(null);  //put window in center of screen

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
            }

            public void windowClosing(WindowEvent e){
                Game.quit();
            }
        });
        frame.setVisible(true);             //Make the frame visible

        renderingThread();
    }

    public static BufferedImage loadImage(String pathName) throws IOException {
        BufferedImage rawImage = ImageIO.read(Renderer.class.getResource(pathName));
        //Want to optimize the image for the highest performace.
        //Create a new image that is compatible with the graphics configuration of our canvas.
        BufferedImage finalImage = canvas.getGraphicsConfiguration().createCompatibleImage(rawImage.getWidth(), rawImage.getHeight(), rawImage.getTransparency());
        finalImage.getGraphics().drawImage(rawImage, 0, 0, rawImage.getWidth(), rawImage.getHeight(), null);
        return finalImage;  //Return the compatible image.
    }

    public static void setCamera(int width, int height, Sprite sprite){
        camera = new Camera(width, height, sprite);
    }

    public static int getCanvasWidth(){
        return canvasWidth;
    }

    public static int getCanvasHeight(){
        return canvasHeight;
    }

    public static Point2D getScreenPos(Point2D.Float point){
        if(camera != null) {
            return new Point2D.Double(camera.getMinX() - point.getX(), camera.getMinY() - point.getY());
        } else{
            return null;
        }
    }

}