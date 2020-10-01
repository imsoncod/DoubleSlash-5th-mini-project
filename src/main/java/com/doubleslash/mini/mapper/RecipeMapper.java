package com.doubleslash.mini.mapper;

import java.util.List;

import com.doubleslash.mini.domain.IdVO;
import com.doubleslash.mini.domain.IngredientVO;
import com.doubleslash.mini.domain.NutritionVO;
import com.doubleslash.mini.domain.RecipeDetailVO;
import com.doubleslash.mini.domain.RecipeListVO;
import com.doubleslash.mini.domain.StepVO;

public interface RecipeMapper {

	//레시피 리스트 조회(미사용)
	public List<RecipeListVO> getRecipeList(String categorie) throws Exception;
	
	//인기 레시피 리스트 조회
	public List<RecipeListVO> getPopularRecipeList(String categorie, String user_id) throws Exception;
		
	//최신 레시피 리스트 조회
	public List<RecipeListVO> getNewRecipeList(String categorie, String user_id) throws Exception;
	
	//레시피 상세 조회
	public RecipeDetailVO getDetailRecipeList(IdVO vo) throws Exception;
	
	
	/* 레시피 상세 조회를 위한 기능들 */
	public List<IngredientVO> getRecipeIngredient(int recipe_id) throws Exception;
	
	public List<NutritionVO> getRecipeNutrition(int recipe_id) throws Exception;
	
	public List<StepVO> getRecipeStep(int recipe_id) throws Exception;
	/* ===================== */
	
	
	//내가 만든 레시피 리스트 조회
	public List<RecipeListVO> getMadeRecipeList(String user_id) throws Exception;
	
	
	/* 내가 만든 레시피 등록을 위한 기능들 */
	public int postMadeRecipe(IdVO vo) throws Exception;
	
	public int putRecipeNumberOfUsers(int recipe_id) throws Exception;
	/* ======================== */
	
	
	//나의 즐겨찾기 레시피 리스트 조회
	public List<RecipeListVO> getFavoriteRecipeList(String user_id) throws Exception;
	
	//나의 즐겨찾기 레시피 등록
	public int postFavoriteRecipe(IdVO vo) throws Exception;
	
	//나의 즐겨찾기 레시피 삭제
	public int deleteFavoriteRecipe(IdVO vo) throws Exception;
	
	//메뉴명 검색
	public List<RecipeListVO> searchRecipeList_Menu(String user_id, String keyword) throws Exception;
	
	//재료명 검색
	public List<RecipeListVO> searchRecipeList_Ingredient(String user_id, String keyword) throws Exception;
	
}
