package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	public static SpriteBatch batch;
	public static Texture img;
	
	public static List<Object> stonesData;
	public static List<Object>  treesData;
	public static Character character;
	public static Random random;
	
    public static Texture treeSheet;
	
	@Override
	public void create () {
		
		random = new Random(); // uses System.nanoTime() as seed
		
		Gdx.input.setInputProcessor(this);
		
		treesData  = new ArrayList<Object>();
		stonesData = new ArrayList<Object>();
		
		treeSheet = new Texture(Gdx.files.absolute("C:/assets/trees.png"));
		
		character = new Character(0, 0, 0.03125f, 1);
		
		for(int i = 0; i < 32; i++){
		    while(true){
		    	//generate a random number from -10 to 10, rounding by 0.25  
		        float spce = (float)Math.floor((random.nextFloat()-0.5f)*20.f*4.f)/4.f;
		        if (checkAvail(spce)){
		        	System.out.println(spce);
		        	//make a new hashmap full of data for the new tree and add it to the tree info container
		        	Map<String, Float> tree = new HashMap<String, Float>();
		        	tree.put("health", 100.f);
		        	tree.put("x-location", spce);
		        	tree.put("image-variation", (float) Math.floor(random.nextFloat()*6.f));
		        	tree.put("title", spce);
		        	
		        	treesData.add(new HashMap<String, Float>(tree));
		        	break;
		    	}
		        //else try again
		    }
		}
		
		for(int i = 0; i < 32; i++){
		    while(true){
		    	//generate a random number from -10 to 10, rounding by 0.25  
		        float spce = (float)Math.floor((random.nextFloat()-0.5f)*20.f*4.f)/4.f;
		        if (checkAvail(spce)){
		        	System.out.println(spce);
		        	//make a new hashmap full of data for the new stone and add it to the tree info container
		        	Map<String, Float> stone = new HashMap<String, Float>();
		        	stone.put("health", 100.f);
		        	stone.put("x-location", spce);
		        	stone.put("image-variation", (float) Math.floor(random.nextFloat()*4.f));
		        	stone.put("title", spce);
		        	
		        	stonesData.add(new HashMap<String, Float>(stone));
		        	break;
		    	}
		        //else try again
		    }
		}

		batch = new SpriteBatch(1000, createDefaultShader());
		img = new Texture("badlogic.jpg");
		
		
		gui.init();
		renderer.init();
	}

	@Override
	public void render () {
		
		//UPDATE SECTION
		
		doMovement(Gdx.graphics.getDeltaTime());
		
		
		//END
		
		//DRAW SECTION
		
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		renderer.draw();
		gui.draw(batch);
		
		batch.end();
		
		character.collect();
		//END
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
	static public ShaderProgram createDefaultShader () {
	    String vertexShader = "#version 330 core\n"
	       + "in vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
	       + "in vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
	       + "in vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
	       + "uniform mat4 u_projTrans;\n" //
	       + "out vec4 v_color;\n" //
	       + "out vec2 v_texCoords;\n" //
	       + "\n" //
	       + "void main()\n" //
	       + "{\n" //
	       + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
	       + "   v_color.a = v_color.a * (255.0/254.0);\n" //
	       + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
	       + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
	       + "}\n";
	    String fragmentShader = "#version 330 core\n"
	       + "#ifdef GL_ES\n" //
	       + "#define LOWP lowp\n" //
	       + "precision mediump float;\n" //
	       + "#else\n" //
	       + "#define LOWP \n" //
	       + "#endif\n" //
	       + "in LOWP vec4 v_color;\n" //
	       + "in vec2 v_texCoords;\n" //
	       + "out vec4 fragColor;\n" //
	       + "uniform sampler2D u_texture;\n" //
	       + "void main()\n"//
	       + "{\n" //
	       + "  fragColor = v_color * texture(u_texture, v_texCoords);\n" //
	       + "}";

	    ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
	    if (shader.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
	    return shader;
	 }
	
	public static boolean at(float x){
		for(int i = 0; i < treesData.size(); i++){
	        if (treesData.get(i) == null)
	            return false;
	        
	        if (Math.abs(((Map<String, Float>)treesData.get(i)).get("x-location") - x) < 0.25) {
	            return false;
	        }
		}
		return true;
	}
	
	public static boolean as(float x){
		for(int i = 0; i < stonesData.size(); i++){
			if (stonesData.get(i) == null)
	            return false;
			
	        if (Math.abs(((Map<String, Float>)stonesData.get(i)).get("x-location") - x) < 0.25) {
	            return false;
	        }
		}
	    return true;
	}
	
	public static boolean checkAvail(float x){
		return at(x) && as(x);
	}
	
	public static void doMovement(float dt){
		if((Math.abs(character.dir.maxx) + Math.abs(character.dir.x)) < 0.0009765625f)
			return;
		character.x = character.x + character.dir.x*dt;

		if ((! (character.dir.x == character.dir.maxx))){
			//ween to max moving speed in 1/8 seconds
			character.dir.x = character.dir.x + (((character.dir.maxx-character.dir.x)*8)*dt);

			if (Math.abs(character.dir.maxx-character.dir.x) <=
					((((character.dir.maxx-character.dir.x)*16)*dt))) {
				character.dir.x = character.dir.maxx;
			}
		}
	}
	
	@Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		float x = (float)screenX / (float)Gdx.graphics.getWidth();
    	float y = 1.f - (float)screenY / (float)Gdx.graphics.getHeight();
    	
        try {
			gui.pressed(x, y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
    	float x = screenX / Gdx.graphics.getWidth();
    	float y = screenY / Gdx.graphics.getHeight();
    	
    	try {
			gui.released(x, y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }
	
	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
    	// TODO Auto-generated method stub
    	return false;
    }

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}