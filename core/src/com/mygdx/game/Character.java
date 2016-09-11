package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Character {
	public float x;
	public float y;
	public Dir dir;
	public float size;
	public int facing;
	public boolean swinging;
	
	public Inventory inventory;
	
	public int woodCount;
	public int stoneCount;
	
	public Item collecting;
	
	public float time;
	public float window;
	public float cursorPosition;
	
	public static Texture charImg;
	
	public float tilePosition;
	int anim = 0;
	int[] animPause = {32, 0, 0};
	int[] animTiles = {7, 4, 4};
	
	//currently held item
	public Item item;
	ShapeRenderer shapeRenderer;
	
	public Character(float x_, float y_, float size_, int facing_){
		x = x_;
		y = y_;
		size = size_;
		facing = facing_;
		dir = new Dir(0.f, 0.f, 0.f);
		item = null;
		
		charImg = new Texture(Gdx.files.classpath("assets/char.png"));
		
		shapeRenderer = new ShapeRenderer();

		woodCount = 0;
		stoneCount = 0;
		collecting = null;
		tilePosition = 0;
		time = MyGdxGame.random.nextFloat()*0.5f+0.25f;
		window = 0.25f;
		cursorPosition = 0.f;
		
		inventory = new Inventory();
	}
	
	public void draw(SpriteBatch batch){
		tilePosition += Gdx.graphics.getDeltaTime()*8;
		if (tilePosition > animTiles[anim]){
			tilePosition = -animPause[anim];
		}
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		batch.draw(charImg,
				(0.5f-((size*facing)/2))*width,
				0.5f*height,
				size*width*facing,
				size*height*5,
				32*(int)Math.max(tilePosition, 0),
				64*anim,
				(int)(32),
				(int)(64),
				false, 
				false);
	}
	
	
	
	public boolean collect(){
		if(collecting == null)
			return false;
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		
		//shapeRenderer.setAutoShapeType(true);
		shapeRenderer.begin(ShapeType.Filled);
		if (cursorPosition >= 0.f){
			shapeRenderer.setColor(0, 1, 0, 0.667f);
			shapeRenderer.rect(width/2f - width/64.f, height/1.5f, width/32.f, height/128.f);
			shapeRenderer.setColor(1, 0, 0, 0.667f);
			shapeRenderer.rect(width/2f - width/64.f + width/32.f*time, height/1.5f, width/32.f*window, height/128.f);
			shapeRenderer.setColor(1, 1, 1, 0.667f);
			shapeRenderer.rect(width/2f - width/64.f, height/1.5f, width/32.f*cursorPosition, height/128.f);
		}
		
		
		if (cursorPosition >= -64.f && cursorPosition <= -60.f){
			shapeRenderer.rect(width/2f - width/64.f, height/1.5f, width/32.f, height/128.f);
			cursorPosition++;//Gdx.graphics.getDeltaTime();
			if(cursorPosition > -63.f){
				cursorPosition = 0.f;
				time = MyGdxGame.random.nextFloat()*0.5f+0.25f;
				
				if(collecting != Item.AIR){
					MyGdxGame.character.inventory.put(collecting, 1);
					System.out.println(Item.string.get(collecting));
				}
				collecting = null;
			}
		}else if (cursorPosition > 1.f){
			//not collected
			shapeRenderer.setColor(1, 0, 0, 0.667f);
			shapeRenderer.rect(width/2f - width/64.f, height/1.5f, width/32.f, height/128.f);
			System.out.println("**CORLECTED: " + collecting);
			cursorPosition = -64.f;
			collecting = Item.AIR;
		} else if (cursorPosition < 0.f){
			//collected
			shapeRenderer.setColor(0, 1, 0, 0.667f);
			shapeRenderer.rect(width/2f - width/64.f, height/1.5f, width/32.f, height/128.f);
			System.out.println("**CORLECTED: " + collecting);
			cursorPosition = -64.f;
			//collecting = Item.AIR;
			
		} else cursorPosition+=Gdx.graphics.getDeltaTime();
		
		shapeRenderer.end();
		return false;
	}
}