package com.doubleslash.mini.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepVO {
	
	private int ms;
	
	private int parents_num;
	
	private int children_num;
	
	private String description;
	
}
