package com.doubleslash.mini.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDetailVO_Return {
	
	private int id;
	
	private String name;
	
	private String short_description;
	
	private String long_description;
	
	private int cooking_time;
	
	private String level;

	private int servings;
	
	private String video_url;

	private boolean favorites;
	
	private boolean made;
	
	private Object tags;
	
	private Object ingredient;
	
	private Object nutrition;
	
	private Object step;
	
	private Object ms;
	
}
