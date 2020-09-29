package com.doubleslash.mini.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchVO {
	
	private List<RecipeListVO> search_menu;
	
	private List<RecipeListVO> search_ingredient;
	
}
