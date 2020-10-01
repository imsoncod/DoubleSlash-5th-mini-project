package com.doubleslash.mini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubleslash.mini.domain.IdVO;
import com.doubleslash.mini.domain.IngredientVO;
import com.doubleslash.mini.domain.NutritionVO;
import com.doubleslash.mini.domain.RecipeDetailVO;
import com.doubleslash.mini.domain.RecipeListVO;
import com.doubleslash.mini.domain.StepVO;
import com.doubleslash.mini.mapper.RecipeMapper;

@Service
public class RecipeService {

	@Autowired
	private RecipeMapper mRecipeMapper;

	// 레시피 리스트 조회
	public List<RecipeListVO> getRecipeList(String categorie) throws Exception{
		return mRecipeMapper.getRecipeList(categorie);
	};
	
	//인기 레시피 리스트 조회
	public List<RecipeListVO> getPopularRecipeList(String categorie, String user_id) throws Exception{
		return mRecipeMapper.getPopularRecipeList(categorie, user_id);
	};
		
	//최신 레시피 리스트 조회
	public List<RecipeListVO> getNewRecipeList(String categorie, String user_id) throws Exception{
		return mRecipeMapper.getNewRecipeList(categorie, user_id);
	};

	// 레시피 상세 조회
	public RecipeDetailVO getDetailRecipeList(IdVO vo) throws Exception{
		return mRecipeMapper.getDetailRecipeList(vo);
	};
	
	/* 레시피 상세 조회를 위한 기능들 */
	public List<IngredientVO> getRecipeIngredient(int recipe_id) throws Exception{
		return mRecipeMapper.getRecipeIngredient(recipe_id);
	};

	public List<NutritionVO> getRecipeNutrition(int recipe_id) throws Exception{
		return mRecipeMapper.getRecipeNutrition(recipe_id);
	};

	public List<StepVO> getRecipeStep(int recipe_id) throws Exception{
		return mRecipeMapper.getRecipeStep(recipe_id);
	};
	/* ===================== */

	// 내가 만든 레시피 리스트 조회
	public List<RecipeListVO> getMadeRecipeList(String user_id) throws Exception{
		return mRecipeMapper.getMadeRecipeList(user_id);
	};

	/* 내가 만든 레시피 등록을 위한 기능들 */
	public int postMadeRecipe(IdVO vo) throws Exception{
		return mRecipeMapper.postMadeRecipe(vo);
	};

	public int putRecipeNumberOfUsers(int recipe_id) throws Exception{
		return mRecipeMapper.putRecipeNumberOfUsers(recipe_id);
	};
	/* ======================== */

	// 나의 즐겨찾기 레시피 리스트 조회
	public List<RecipeListVO> getFavoriteRecipeList(String user_id) throws Exception{
		return mRecipeMapper.getFavoriteRecipeList(user_id);
	};

	// 나의 즐겨찾기 레시피 등록
	public int postFavoriteRecipe(IdVO vo) throws Exception{
		return mRecipeMapper.postFavoriteRecipe(vo);
	};

	// 나의 즐겨찾기 레시피 삭제
	public int deleteFavoriteRecipe(IdVO vo) throws Exception{
		return mRecipeMapper.deleteFavoriteRecipe(vo);
	};

	// 메뉴명 검색
	public List<RecipeListVO> searchRecipeList_Menu(String user_id, String keyword) throws Exception{
		return mRecipeMapper.searchRecipeList_Menu(user_id, keyword);
	};

	// 재료명 검색
	public List<RecipeListVO> searchRecipeList_Ingredient(String user_id, String keyword) throws Exception{
		return mRecipeMapper.searchRecipeList_Ingredient(user_id, keyword);
	};

}
