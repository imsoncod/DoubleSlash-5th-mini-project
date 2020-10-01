package com.doubleslash.mini.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.doubleslash.mini.domain.IngredientVO;
import com.doubleslash.mini.domain.NutritionVO;
import com.doubleslash.mini.domain.RecipeDetailVO;
import com.doubleslash.mini.domain.RecipeDetailVO_Return;
import com.doubleslash.mini.domain.RecipeListVO;
import com.doubleslash.mini.domain.RecipeListVO_Return;
import com.doubleslash.mini.domain.SearchVO;
import com.doubleslash.mini.domain.StepVO;
import com.doubleslash.mini.service.RecipeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "Cookice", description = "API")
@RequestMapping(value = "/recipe")
@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService mRecipeService;
	
	/*
	//카테고리별 전체 레시피 조회(미사용)
	@ApiOperation(value = "카테고리별 전체 레시피 리스트 조회")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<RecipeListVO> getRecipeList(HttpServletRequest request) throws Exception{
		String categorie = request.getParameter("categorie");
		List<RecipeListVO> vo = mRecipeService.getRecipeList(categorie);
	
		return vo;
	}
	*/
	
	
	//카테고리별 인기 레시피 조회
	@ApiOperation(value = "카테고리별 인기 메뉴 조회")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "categorie", value = "카테고리", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "user_id", value = "유저 ID", required = true, dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/popular", method = RequestMethod.GET)
	@ResponseBody
	public RecipeListVO_Return getPopularRecipeList(HttpServletRequest request, @RequestHeader(value="user_id") String user_id) throws Exception{
		String categorie = request.getParameter("categorie");
		List<RecipeListVO> list_vo = mRecipeService.getPopularRecipeList(categorie, user_id);
		
		RecipeListVO_Return vo = new RecipeListVO_Return();
		vo.setMenu_list(list_vo);
			
		return vo;
	}
	
	//카테고리별 최신 레시피 조회
	@ApiOperation(value = "카테고리별 최신 메뉴 조회")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "categorie", value = "카테고리", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "user_id", value = "유저 ID", required = true, dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	@ResponseBody
	public RecipeListVO_Return getNewRecipeList(HttpServletRequest request, @RequestHeader(value="user_id") String user_id) throws Exception{
		String categorie = request.getParameter("categorie");
		List<RecipeListVO> list_vo = mRecipeService.getNewRecipeList(categorie, user_id);
		
		RecipeListVO_Return vo = new RecipeListVO_Return();
		vo.setMenu_list(list_vo);
			
		return vo;
	}
	
	//레시피 세부 정보 조회
	@ApiOperation(value = "선택 메뉴 세부 레시피 정보 조회")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "recipe_id", value = "레시피 ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "user_id", value = "유저 ID", required = true, dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/{recipe_id}", method = RequestMethod.GET)
	@ResponseBody
	public RecipeDetailVO_Return getRecipeDetail(@PathVariable int recipe_id, @RequestHeader(value="user_id") String user_id) throws Exception{
		IdVO id_vo = new IdVO();
		id_vo.setUser_id(user_id);
		id_vo.setRecipe_id(recipe_id);
		
		RecipeDetailVO detail_vo = mRecipeService.getDetailRecipeList(id_vo);
		List<IngredientVO> ingredient_vo = mRecipeService.getRecipeIngredient(recipe_id);
		List<NutritionVO> nutrition_vo = mRecipeService.getRecipeNutrition(recipe_id);
		List<StepVO> step_vo = mRecipeService.getRecipeStep(recipe_id);
		
		//재료 데이터 가공
		Map<String, Object> ingredient_json = new LinkedHashMap<String, Object>();
		Map<String, Object> ingredient_json_main = new LinkedHashMap<String, Object>();
		Map<String, Object> ingredient_json_sub = new LinkedHashMap<String, Object>();
		
		for(int i = 0; i < ingredient_vo.size(); i++) {
			IngredientVO temp = ingredient_vo.get(i);
			if(temp.getMain() == 1) {
				ingredient_json_main.put(temp.getName(), temp.getAmount());
			}else {
				ingredient_json_sub.put(temp.getName(), temp.getAmount());
			}
		}
		ingredient_json.put("main", ingredient_json_main);
		ingredient_json.put("sub", ingredient_json_sub);
		
		//영양정보 데이터 가공
		Map<String, Object> nutrition_json = new LinkedHashMap<String, Object>();
		Map<String, Object> nutrition_json_main = new LinkedHashMap<String, Object>();
		
		for(int i = 0; i < nutrition_vo.size(); i++) {
			NutritionVO temp = nutrition_vo.get(i);
			nutrition_json_main.put(temp.getName(), temp.getAmount());
		}
		nutrition_json.put("nutrition_info", nutrition_json_main);
		nutrition_json.put("calorie", detail_vo.getCalorie());
		
		//레시피 단계 데이터 가공
		Map<String, Object> step_json = new LinkedHashMap<String, Object>();
		Map<String, Object> step_json_main = new LinkedHashMap<String, Object>();
		int prev_step = 1;

		for(int i = 0; i < step_vo.size(); i++) {
			StepVO temp = step_vo.get(i);
			if(prev_step != temp.getParents_num()) {
				step_json.put("Step" + prev_step , step_json_main);
				step_json_main = new HashMap<String, Object>();
			}
			step_json_main.put(String.valueOf(temp.getChildren_num()), temp.getDescription());
			prev_step = temp.getParents_num();
		}
		step_json.put("Step" + prev_step, step_json_main);
		
		//태그 데이터 가공
		Map<String, Object> tags_json = new LinkedHashMap<String, Object>();
		String tags[] = detail_vo.getTags().split("#");
		for(int i = 1; i < tags.length; i++) {
			tags_json.put("tags" + i, tags[i]);
		}
		
		RecipeDetailVO_Return vo = new RecipeDetailVO_Return();
		vo.setId(detail_vo.getId());
		vo.setName(detail_vo.getName());
		vo.setShort_description(detail_vo.getShort_description());
		vo.setLong_description(detail_vo.getLong_description());
		vo.setCooking_time(detail_vo.getCooking_time());
		vo.setLevel(detail_vo.getLevel());
		vo.setServings(detail_vo.getServings());
		vo.setVideo_url(detail_vo.getVideo_url());
		vo.setFavorites(detail_vo.isFavorites());
		vo.setMade(detail_vo.isMade());
		vo.setTags(tags_json);
		vo.setIngredient(ingredient_json);
		vo.setNutrition(nutrition_json);
		vo.setStep(step_json);		
		
		return vo;
	}
	
	//내가 만든 레시피 조회
	@ApiOperation(value = "내가 만든 메뉴 조회")
    @ApiImplicitParam(name = "user_id", value = "유저 ID", required = true, dataType = "string", paramType = "header")
	@RequestMapping(value = "/made", method = RequestMethod.GET)
	@ResponseBody
	public RecipeListVO_Return getMadeRecipeList(@RequestHeader(value="user_id") String user_id) throws Exception{
		List<RecipeListVO> list_vo = mRecipeService.getMadeRecipeList(user_id);
		
		RecipeListVO_Return vo = new RecipeListVO_Return();
		vo.setMenu_list(list_vo);
			
		return vo;
	}
	
	//내가 만든 레피시 등록, 해당 레시피 사용자수 +1
	@ApiOperation(value = "내가 만든 메뉴 등록")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "recipe_id", value = "레시피 ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "user_id", value = "유저 ID", required = true, dataType = "string", paramType = "header")
	})
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
	@ApiOperation(value = "나의 즐겨찾기(스크랩) 메뉴 조회")
	@ApiImplicitParam(name = "user_id", value = "유저 ID", required = true, dataType = "string", paramType = "header")
	@RequestMapping(value = "/favorites", method = RequestMethod.GET)
	@ResponseBody
	public RecipeListVO_Return getFavoriteRecipeList(@RequestHeader(value="user_id") String user_id) throws Exception{
		List<RecipeListVO> list_vo = mRecipeService.getFavoriteRecipeList(user_id);
		
		RecipeListVO_Return vo = new RecipeListVO_Return();
		vo.setMenu_list(list_vo);
			
		return vo;
	}
	
	//나의 즐겨찾기 레시피 등록
	@ApiOperation(value = "나의 즐겨찾기(스크랩) 메뉴 등록")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "recipe_id", value = "레시피 ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "user_id", value = "유저 ID", required = true, dataType = "string", paramType = "header")
	})
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
	@ApiOperation(value = "나의 즐겨찾기(스크랩) 메뉴 삭제")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "recipe_id", value = "레시피 ID", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "user_id", value = "유저 ID", required = true, dataType = "string", paramType = "header")
	})
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
	@ApiOperation(value = "메뉴 / 재료 검색")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "keyword", value = "검색 키워드", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "user_id", value = "유저 ID", required = true, dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public SearchVO searchRecipeList(HttpServletRequest request, @RequestHeader(value="user_id") String user_id) throws Exception{
		String keyword = request.getParameter("keyword");
		
		SearchVO vo = new SearchVO();
		
		//메뉴로 레시피 검색
		List<RecipeListVO> menu_vo = mRecipeService.searchRecipeList_Menu(user_id, keyword);
	
		//재료로 레시피 검색
		List<RecipeListVO> ingredient_vo = mRecipeService.searchRecipeList_Ingredient(user_id, keyword);
		
		vo.setSearch_menu(menu_vo);
		vo.setSearch_ingredient(ingredient_vo);
		
		return vo;
	}
	
}
