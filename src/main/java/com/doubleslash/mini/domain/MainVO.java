package com.doubleslash.mini.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainVO {

	private String title;
	
	private String subtitle;
	
	private List<RecipeListVO> menu_list;
	
	private boolean horizon;
	
}
