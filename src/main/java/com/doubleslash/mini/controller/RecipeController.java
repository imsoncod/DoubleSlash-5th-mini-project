package com.doubleslash.mini.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubleslash.mini.domain.IdVO;
import com.doubleslash.mini.domain.RecipeDetailVO;
import com.doubleslash.mini.domain.RecipeListVO;
import com.doubleslash.mini.service.RecipeService;

@RequestMapping(value = "/recipe")
@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService mRecipeService;
	
	//카테고리별 전체 레시피 조회(미사용)
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<RecipeListVO> getRecipeList(HttpServletRequest request) throws Exception{
		String kategorie = request.getParameter("kategorie");
		List<RecipeListVO> vo = mRecipeService.getRecipeList(kategorie);
	
		return vo;
	}
	
	//카테고리별 인기 레시피 조회
	@RequestMapping(value = "/popular", method = RequestMethod.GET)
	@ResponseBody
	public List<RecipeListVO> getPopularRecipeList(HttpServletRequest request, @RequestHeader(value="user_id") String user_id) throws Exception{
		String kategorie = request.getParameter("kategorie");
		List<RecipeListVO> vo = mRecipeService.getPopularRecipeList(kategorie, user_id);
			
		return vo;
	}
	
	//카테고리별 최신 레시피 조회
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	@ResponseBody
	public List<RecipeListVO> getNewRecipeList(HttpServletRequest request, @RequestHeader(value="user_id") String user_id) throws Exception{
		String kategorie = request.getParameter("kategorie");
		List<RecipeListVO> vo = mRecipeService.getNewRecipeList(kategorie, user_id);
		
		return vo;
	}
	
	//레시피 세부 정보 조회
	@RequestMapping(value = "/{recipe_id}", method = RequestMethod.GET)
	@ResponseBody
	public RecipeDetailVO getRecipeDetail(@PathVariable int recipe_id, @RequestHeader(value="user_id") String user_id) throws Exception{
		IdVO id_vo = new IdVO();
		id_vo.setUser_id(user_id);
		id_vo.setRecipe_id(recipe_id);
		
		RecipeDetailVO vo = mRecipeService.getDetailRecipeList(id_vo);
		
		return vo;
	}
	
	//내가 만든 레시피 조회
	@RequestMapping(value = "/made", method = RequestMethod.GET)
	@ResponseBody
	public List<RecipeListVO> getMadeRecipeList(@RequestHeader(value="user_id") String user_id) throws Exception{
		List<RecipeListVO> vo = mRecipeService.getMadeRecipeList(user_id);
		
		return vo;
	}
	
	//내가 만든 레피시 등록, 해당 레시피 사용자수 +1
	@RequestMapping(value = "/made/{recipe_id}", method = RequestMethod.POST)
	@ResponseBody
	public int postMadeRecipe(@PathVariable int recipe_id, @RequestHeader(value="user_id") String user_id) throws Exception{
		IdVO id_vo = new IdVO();
		id_vo.setUser_id(user_id);
		id_vo.setRecipe_id(recipe_id);
		
		try {
			mRecipeService.postMadeRecipe(id_vo);
			mRecipeService.putRecipeNumberOfUsers(recipe_id);
		}catch(Exception e) {
			return 0;
		}
		
		return 1;
	}
	
	//나의 즐겨찾기 레시피 조회
	@RequestMapping(value = "/favorites", method = RequestMethod.GET)
	@ResponseBody
	public List<RecipeListVO> getFavoriteRecipeList(@RequestHeader(value="user_id") String user_id) throws Exception{
		List<RecipeListVO> vo = mRecipeService.getFavoriteRecipeList(user_id);
		
		return vo;
	}
	
	//나의 즐겨찾기 레시피 등록
	@RequestMapping(value = "/favorites/{recipe_id}", method = RequestMethod.POST)
	@ResponseBody
	public int postFavoriteRecipe(@PathVariable int recipe_id, @RequestHeader(value="user_id") String user_id) throws Exception{
		IdVO id_vo = new IdVO();
		id_vo.setUser_id(user_id);
		id_vo.setRecipe_id(recipe_id);
		
		mRecipeService.postFavoriteRecipe(id_vo);
		
		return 1;
	}
	
	//나의 즐겨찾기 레시피 삭제
	@RequestMapping(value = "/favorites/{recipe_id}", method = RequestMethod.DELETE)
	@ResponseBody
	public int deleteFavoriteRecipe(@PathVariable int recipe_id, @RequestHeader(value="user_id") String user_id) throws Exception{
		IdVO id_vo = new IdVO();
		id_vo.setUser_id(user_id);
		id_vo.setRecipe_id(recipe_id);
		
		mRecipeService.deleteFavoriteRecipe(id_vo);
		
		return 1;
	}
	
	//레시피 검색
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchRecipeList(HttpServletRequest request, @RequestHeader(value="user_id") String user_id) throws Exception{
		String keyword = request.getParameter("keyword");
		
		Map<String, Object> search_result = new HashMap<String, Object>();
		
		//메뉴로 레시피 검색
		List<RecipeListVO> menu_vo = mRecipeService.searchRecipeList_Menu(user_id, keyword);
		
		//재료로 레시피 검색
		List<RecipeListVO> ingredient_vo = mRecipeService.searchRecipeList_Ingredient(user_id, keyword);
		
		search_result.put("search_menu", menu_vo);
		search_result.put("search_ingredient", ingredient_vo);
		
		return search_result;
	}
	
}
