package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
	
	public Map<Item, Integer> items;
	public int itemCount = 0;
	
	public Inventory(){
		items = new HashMap<Item, Integer>();
	}
	
	public void put(Item item, int count){
		if(items.containsKey(item)){
			items.put(item, items.get(item)+count);
		} else items.put(item, count);
		
		itemCount++;
	}
	
	public int get(Item item){
		return items.containsKey(item)?items.get(item):0;
	}
}
