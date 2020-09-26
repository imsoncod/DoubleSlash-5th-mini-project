package com.doubleslash.mini.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDetailVO {
	
	private int id;
	
	private String name;
	
	private String short_description;
	
	private String long_description;
	
	private String tags;
	
	private int cooking_time;
	
	private String level;
	
	private boolean favorites;
	
	private int servings;
	
	private int calorie;
	
	private int number_of_users;
	
	private String video_url;
	
	private boolean made;
	
}
