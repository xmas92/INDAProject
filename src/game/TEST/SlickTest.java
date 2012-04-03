package game.TEST;

import game.client.Map.Map;
import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.tiled.TiledMap;

public class SlickTest extends BasicGame {

	 private static final int SCREEN_WIDTH = 800;
	 private static final int SCREEN_HEIGHT = 600;
	 
	 private Map map;
	 private Animation playerAnim;
	 Rectangle viewPort; 
	 SpriteSheet sheet;
	 Color transColor = new Color(255, 255, 255);; 
	

	 float x = 0;
	 float y = 0;
	 float speed = 0.0f;
	    
    public SlickTest() {
        super("...");
       
    }
    
    @Override
    public void init(GameContainer container) throws SlickException {  
    	viewPort = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    	sheet = new SpriteSheet("data/GameAssets/captainsheet.png", 19, 43, transColor);
    	map = new Map("data/GameAssets/Map/untitled.tmx");
    	
    	playerAnim = new Animation();
		playerAnim.setAutoUpdate(true);
		playerAnim.setPingPong(false);
		
		for (int i = 0; i < 5; i++) {
			playerAnim.addFrame(sheet.getSprite(i,0), 150);
		}		
    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {  
    	System.out.println(speed);
    	if(speed <= 0.1f){
    		playerAnim.stop();
    	} else {
    		playerAnim.start();
    	}
    		movement(container ,delta);

    }
  

    public void movement(GameContainer container ,int delta){
    	 
    	Input input = container.getInput();
    	if (!input.isKeyDown(Input.KEY_W) ||
    		!input.isKeyDown(Input.KEY_A) ||
    		!input.isKeyDown(Input.KEY_S) ||
    		!input.isKeyDown(Input.KEY_D)) {
    		speed = 0.0f; 
    	}
    	
        if(input.isKeyDown(Input.KEY_D)) { 
        	speed = 0.05f * delta; 
        	if(input.isKeyDown(Input.KEY_LSHIFT)) {
        		speed = 0.18f * delta;
        	}
        	x += speed; 
        } 
 
        if(input.isKeyDown(Input.KEY_W)) {
        	speed = 0.05f * delta; 

        	if(input.isKeyDown(Input.KEY_LSHIFT)) {
        		speed = 0.18f * delta;
        	}
            y -= speed;
        } 
        
        if(input.isKeyDown(Input.KEY_S)) {
        	speed = 0.05f * delta; 

        	if(input.isKeyDown(Input.KEY_LSHIFT)) {
        		speed = 0.18f * delta;
        	}
            y += speed;
        }
        
        if(input.isKeyDown(Input.KEY_A)) {
        	speed = 0.05f * delta; 

        	if(input.isKeyDown(Input.KEY_LSHIFT)) {
        		speed = 0.18f * delta;
        	}
        	x -= speed;    	
        } 
    }
   
    
    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
    	//g.translate(-viewPort.getX()- x + SCREEN_WIDTH/2, -viewPort.getY() - y + SCREEN_HEIGHT/2);
    	map.draw(x-SCREEN_WIDTH/2, y-SCREEN_HEIGHT/2, SCREEN_WIDTH , SCREEN_HEIGHT);
        //player.draw(x, y, scale);
        g.drawAnimation(playerAnim, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        
    }

    public static void main(String[] args) 
    		throws SlickException {
    	AppGameContainer app =
    			new AppGameContainer( new SlickTest());
     
             app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
             app.start();
    }
}