package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GUItex {
	public float x;
	public float y;
	public float width;
	public float height;
	public Texture img;
	
	public GUItex(Texture img_, float x_, float y_, float width_, float height_){
		x = x_;
		y = y_;
		width = width_;
		height = height_;
		img = img_;
	}
}
