package com.doubleslash.mini.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeListVO {

	private int id;
	
	private String name;
	
	private String short_description;
	
	private int cooking_time;
	
	private String level;
	
	private String thumbnail_url;
	
	private boolean favorites;
	
}
