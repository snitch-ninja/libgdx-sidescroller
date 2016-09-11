package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

public enum Item {
	AIR,
	WOOD,
	STONE;
	
	static Map<Item, String> string = new HashMap<Item, String>();
	static{
		string.put(Item.AIR, "AIR");
		string.put(Item.WOOD, "WOOD");
		string.put(Item.STONE, "STONE");
	}
}
