package com.mygdx.game;

import java.util.concurrent.Callable;

import com.badlogic.gdx.graphics.Texture;

public class button {
	
	public static float standardSizeX = 0.15f;
	public static float standardSizeY = 0.15f * gui.ar;
	/*
	  				img=leftArrow,
		            x=  buttonSizeX/2,
		            y=1-buttonSizeY/2,
		            scale=1,
		            xp=80,yp=80,
		            onPress=leftDirButton
	 */
	public Texture img;
	public float x;
	public float y;
	public float scale;
	public float defaultScale;
	public Callable<Void> onPress;
	public Callable<Void> onRelease;
	
	public button(Texture img_, float x_, float y_, float defaultScale_, Callable<Void> onPress_, Callable<Void> onRelease_){
		img = img_;
		x = x_;
		y = y_;
		defaultScale = defaultScale_;
		scale = defaultScale_;
		onPress = onPress_;
		onRelease = onRelease_;
	}
}
