package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class gui {
	
	//TODO CHANGE INVENTORY SCREEN IMAGE AND DRAW SOME REMNANT OF THE ITEM SUCH AS A STRING
	
	public static String activeGUI = "playscreen";
	public static Map<String, button[]> buttons;
	public static Map<String, GUItex[]> images;
	static BitmapFont font = new BitmapFont();
	
	public static float ar = (float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
	
	public static void draw(SpriteBatch batch){
		if(buttons.containsKey(activeGUI)) for(int i = 0; i < buttons.get(activeGUI).length; i++){
			button b = buttons.get(activeGUI)[i];
			batch.draw(b.img,
					(b.x + (1-b.scale)*button.standardSizeX/2) * Gdx.graphics.getWidth(),
					(b.y + (1-b.scale)*button.standardSizeY/2) * Gdx.graphics.getHeight(),
					Gdx.graphics.getWidth() * button.standardSizeX * b.scale,
					Gdx.graphics.getHeight() * button.standardSizeY * b.scale);
		}
		if(images.containsKey(activeGUI)) for(int i = 0; i < images.get(activeGUI).length; i++){
			GUItex tex = images.get(activeGUI)[i];
			batch.draw(tex.img,
					tex.x * Gdx.graphics.getWidth(),
					tex.y * Gdx.graphics.getHeight(),
					Gdx.graphics.getWidth()  * tex.width,
					Gdx.graphics.getHeight() * tex.height);
		}
		font.setColor(Color.BLACK);
		font.draw(batch, Item.string.get(Item.STONE) + ": " + MyGdxGame.character.inventory.get(Item.STONE), 100, Gdx.graphics.getHeight() - 150);
		font.draw(batch, Item.string.get(Item.WOOD)  + ": " + MyGdxGame.character.inventory.get(Item.WOOD) , 100, Gdx.graphics.getHeight() - 100);
	}
	
	public static void init(){
		buttons = new HashMap<String, button[]>();
		
		button[] playscreenButtons = new button[4];
		
		playscreenButtons[0] = new button(new Texture(Gdx.files.classpath("assets/Sprites/shadedLight/shadedLight24.png")),
				0,
				0,
				1.f,
				new Callable<Void>(){

					@Override
					public Void call() throws Exception {
				        MyGdxGame.character.dir.maxx = 0.5f;
				        MyGdxGame.character.facing = -1;
				        MyGdxGame.character.anim = 1;
				        MyGdxGame.character.tilePosition = 0.f;
						return null;
					}},
				new Callable<Void>(){

						@Override
						public Void call() throws Exception {
							MyGdxGame.character.dir.maxx = 0.f;
							MyGdxGame.character.anim = 0;
						    MyGdxGame.character.tilePosition = 0.f;
							return null;
						}});
		
		playscreenButtons[1] = new button(new Texture(Gdx.files.classpath("assets/Sprites/shadedLight/shadedLight25.png")),
				1-button.standardSizeX,
				0,
				1.f,
				new Callable<Void>(){

					@Override
					public Void call() throws Exception {
						MyGdxGame.character.dir.maxx = -0.5f;
						MyGdxGame.character.facing = 1;
						MyGdxGame.character.anim = 1;
						MyGdxGame.character.tilePosition = 0.f;
						return null;
					}},
				new Callable<Void>(){

						@Override
						public Void call() throws Exception {
							MyGdxGame.character.dir.maxx = 0.f;
							MyGdxGame.character.anim = 0;
						    MyGdxGame.character.tilePosition = 0.f;
							return null;
						}});
		
		playscreenButtons[2] = new button(new Texture(Gdx.files.classpath("assets/Sprites/shadedLight/shadedLight22.png")),
				0+button.standardSizeX,
				0,
				0.5f,
				new Callable<Void>(){

					@Override
					public Void call() throws Exception {
				        activeGUI = "inventory";
						return null;
					}},
				new Callable<Void>(){

						@Override
						public Void call() throws Exception {
							
							return null;
						}});
		
		playscreenButtons[3] = new button(new Texture(Gdx.files.classpath("assets/Sprites/shadedLight/shadedLight48.png")),
				1-button.standardSizeX,
				0+button.standardSizeY,
				0.5f,
				new Callable<Void>(){

					@Override
					public Void call() throws Exception {
						MyGdxGame.character.swinging = true;
						
						for(int i = 0; i < MyGdxGame.stonesData.size(); i++)
							if(Math.abs((((Map<String, Float>) MyGdxGame.stonesData.get(i)).get("x-location") + MyGdxGame.character.x) - 0.5f) < 0.03125f)
								MyGdxGame.character.collecting = Item.STONE;
						
						for(int i = 0; i < MyGdxGame.treesData.size(); i++)
							if(Math.abs((((Map<String, Float>) MyGdxGame.treesData.get(i)).get("x-location") + MyGdxGame.character.x) - 0.5f) < 0.03125f)
								MyGdxGame.character.collecting = Item.WOOD;
						
						return null;
					}},
				new Callable<Void>(){

						@Override
						public Void call() throws Exception {
							MyGdxGame.character.swinging = false;
							
							if(MyGdxGame.character.collecting == Item.AIR || MyGdxGame.character.collecting == null)
								return null;
							
							else if (MyGdxGame.character.cursorPosition > MyGdxGame.character.time && MyGdxGame.character.cursorPosition < MyGdxGame.character.time + MyGdxGame.character.window)
								MyGdxGame.character.cursorPosition = -1.1f;
								
							else
								MyGdxGame.character.cursorPosition = 1.1f;
							
							
							return null;
						}});
		
		buttons.put("playscreen", playscreenButtons);
		
		images = new HashMap<String, GUItex[]>();
		
		GUItex[] inventoryScreenImgs = new GUItex[1];
		
		inventoryScreenImgs[0] = new GUItex(new Texture(Gdx.files.classpath("assets/Sprites/shadedLight/shadedLight48.png")), 0.1f, 0.1f, 0.8f, 0.8f);
		
		images.put("inventory", inventoryScreenImgs);
	}
	
	static boolean inBox (float x, float y, float bX, float bY, float bW, float bH){
		return  ((x>bX)     &&
				(x<(bX+bW)) &&
				(y>bY)      &&
				(y<(bY+bH)));
	}
	
	public static void pressed(float x, float y) throws Exception{
		button[] butts = buttons.get(activeGUI);
		for(int i = 0; i < butts.length; i++){
			if (inBox(x,y,
				butts[i].x,
				butts[i].y,
				button.standardSizeX, button.standardSizeY)){
				
				butts[i].scale = (float) Math.pow(0.9 * butts[i].defaultScale, 1.1);
				butts[i].onPress.call();
			}
		}
	}
	
	public static void released(float x, float y) throws Exception{
		for (String gooey : buttons.keySet()){
			if(!buttons.containsKey(gooey))
				continue;
			button[] butts = buttons.get(gooey);
			for(int i = 0; i < butts.length; i++){
				if(butts[i].scale < butts[i].defaultScale){
					butts[i].onRelease.call();
					butts[i].scale = butts[i].defaultScale;
				}
			}
		}
	}
}
