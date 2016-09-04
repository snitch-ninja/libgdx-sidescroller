package com.mygdx.game;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

//TODO DRAW CHARACTER ANIMATIONS, ADD RESOURCE COLLECTION/BUTTON FUNCTIONALITY, ADD CHARACTER MOVEMENT LAG

public class renderer {
	
	public static Texture dirtImg;
	public static Texture tgrassImg;
	public static Texture grassImg;
	public static Texture background;
	
	public static Texture stoneSheet;
	
	static float cloud = 0.0f;
	
	public static void init(){
		dirtImg = new Texture(Gdx.files.absolute("C:/assets/dirt.png"));
		tgrassImg = new Texture(Gdx.files.absolute("C:/assets/tallgrass.png"));
		grassImg = new Texture(Gdx.files.absolute("C:/assets/grass.png"));
		background = new Texture(Gdx.files.absolute("C:/assets/sky.png"));
		stoneSheet = new Texture(Gdx.files.absolute("C:/assets/stones.png"));
	}
	
	public static void draw(){
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		float charx = MyGdxGame.character.x;
		
		float movPos = (charx*width) % (width);
		
		//INITIALIZE
		SpriteBatch batch = MyGdxGame.batch;
		Texture img = MyGdxGame.img;
		List<Object> stonesData = MyGdxGame.stonesData;
		List<Object>  treesData = MyGdxGame.treesData;
		Character character = MyGdxGame.character;
		Texture treeSheet = MyGdxGame.treeSheet;
		
		//END
		
		batch.draw(img, 0, 0); //TESTIMG
		
		//[[ DRAW BACKGROUND ]]
	    
		cloud+=Gdx.graphics.getDeltaTime()*8;
		
		movPos/=2;
		movPos+=cloud;
		batch.draw(background, movPos        ,height/2,width/2,height/2);
		batch.draw(background, movPos-width/2,height/2,width/2,height/2);
		batch.draw(background, movPos-width,height/2,width/2,height/2);
		batch.draw(background, movPos+width/2,height/2,width/2,height/2);
		batch.draw(background, movPos+width,height/2,width/2,height/2);
		movPos-=cloud;
	    movPos*=2;
	    
	    //[[ END BACKGROUND ]]
		
		//[[ DRAW FOREGROUND ]]
		
		for(int i = 0; i < 9; i++){
			batch.draw(dirtImg  , movPos+      (width/8)*i, height/2 - (height / 8) * (750.f/262.f) - (height / 4) * (262.f/750.f), width/8, (height / 8) * (750.f/262.f));
			batch.draw(dirtImg  , movPos+width+(width/8)*i, height/2 - (height / 8) * (750.f/262.f) - (height / 4) * (262.f/750.f), width/8, (height / 8) * (750.f/262.f));
			batch.draw(dirtImg  , movPos-width+(width/8)*i, height/2 - (height / 8) * (750.f/262.f) - (height / 4) * (262.f/750.f), width/8, (height / 8) * (750.f/262.f));

			batch.draw(tgrassImg, movPos+      (width/8)*i, height/2, width/8, height / 8);
			batch.draw(tgrassImg, movPos+width+(width/8)*i, height/2, width/8, height / 8);
			batch.draw(tgrassImg, movPos-width+(width/8)*i, height/2, width/8, height / 8);

			batch.draw(grassImg , movPos+      (width/8)*i, height/2 - (height / 4) * (262.f/750.f), width/8, (height / 4) * (262.f/750.f));
			batch.draw(grassImg , movPos+width+(width/8)*i, height/2 - (height / 4) * (262.f/750.f), width/8, (height / 4) * (262.f/750.f));
			batch.draw(grassImg , movPos-width+(width/8)*i, height/2 - (height / 4) * (262.f/750.f), width/8, (height / 4) * (262.f/750.f));
		}
				
		//[[ END FOREGROUND ]]
		
		//[[ BEGIN DRAW TREES ]]
		
		for(int i = 0; i < treesData.size(); i++){
		batch.draw(treeSheet,
				(((Map<String, Float>) treesData.get(i)).get("x-location") + charx) * Gdx.graphics.getWidth() - ((Gdx.graphics.getHeight()/4)*(51.f/55.f))/2,
				Gdx.graphics.getHeight()/2,
				((Gdx.graphics.getHeight()/4)*(51.f/55.f)), //* ((treeSheet.getWidth ()/6) / treeSheet.getHeight()),
				((Gdx.graphics.getHeight()/4)*(55.f/51.f)), //* (treeSheet.getHeight() / (treeSheet.getWidth ()/6)),
				(int)(((float)treeSheet.getWidth()/6.f) * (((Map<String, Float>) treesData.get(i)).get("image-variation")-1.f)),
				0,
				treeSheet.getWidth()/6,
				treeSheet.getHeight(),
				false,
				false);
		}
		
		//[[ END DRAW TREES ]]
		
		//[[ BEGIN DRAW STONES ]]
		
		for(int i = 0; i < stonesData.size(); i++){
			batch.draw(stoneSheet,
				(((Map<String, Float>) stonesData.get(i)).get("x-location") + charx) * Gdx.graphics.getWidth() - ((Gdx.graphics.getHeight()/8)*(44.f/53.f))/2,
				Gdx.graphics.getHeight()/2,
				((Gdx.graphics.getHeight()/8)*(53.f/44.f)), //* ((treeSheet.getWidth ()/6) / treeSheet.getHeight()),
				((Gdx.graphics.getHeight()/8)*(44.f/53.f)), //* (treeSheet.getHeight() / (treeSheet.getWidth ()/6)),
				(int)(((float)stoneSheet.getWidth()/4.f) * (((Map<String, Float>) stonesData.get(i)).get("image-variation")-1.f)),
				0,
				stoneSheet.getWidth()/4,
				stoneSheet.getHeight(),
				false,
				false);
		}
				
		//[[ END DRAW STONES ]]
		
		//[[ DRAW CHARACTER ]]
		character.draw(batch);
		
		if (gui.buttons.get(gui.activeGUI)[3].scale < 1) {
			//aaa = aaa + 1/60;
			/*draw(character.item.img,
					(0.5*width-character.size*0.25) + character.size*0.25 - 64*character.facing,
					0.5*height-character.size*1.5,
					(360-45*character.facing) * (Math.PI/180.f),
					(width/16/character.item.y)*-character.facing,
					width/16/character.item.y);*/
		}
		//[[ END CHARACTER ]]
		
		//CLOSE
		
	}
}
