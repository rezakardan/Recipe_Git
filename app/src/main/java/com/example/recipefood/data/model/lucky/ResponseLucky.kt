package com.example.recipefood.data.model.lucky


import com.example.recipefood.data.model.detail.ResponseDetail
import com.google.gson.annotations.SerializedName

data class ResponseLucky(
    @SerializedName("recipes")
    val recipes: List<Recipe>?
) {
    data class Recipe(
        @SerializedName("aggregateLikes")
        val aggregateLikes: Int?, // 9
        @SerializedName("analyzedInstructions")
        val analyzedInstructions: List<ResponseDetail.AnalyzedInstruction>?,
        @SerializedName("cheap")
        val cheap: Boolean?, // false
        @SerializedName("cookingMinutes")
        val cookingMinutes: Int?, // -1
        @SerializedName("creditsText")
        val creditsText: String?, // Foodista.com – The Cooking Encyclopedia Everyone Can Edit
        @SerializedName("cuisines")
        val cuisines: List<Any?>?,
        @SerializedName("dairyFree")
        val dairyFree: Boolean?, // false
        @SerializedName("diets")
        val diets: List<String>?,
        @SerializedName("dishTypes")
        val dishTypes: List<String?>?,
        @SerializedName("extendedIngredients")
        val extendedIngredients: List<ResponseDetail.ExtendedIngredient>?,
        @SerializedName("gaps")
        val gaps: String?, // no
        @SerializedName("glutenFree")
        val glutenFree: Boolean?, // true
        @SerializedName("healthScore")
        val healthScore: Int?, // 0
        @SerializedName("id")
        val id: Int?, // 643129
        @SerializedName("image")
        val image: String?, // https://spoonacular.com/recipeImages/643129-556x370.jpg
        @SerializedName("imageType")
        val imageType: String?, // jpg
        @SerializedName("instructions")
        val instructions: String?, // <ol><li>Combine first five ingredients, stirring over low heat until blended.</li><li>Bring to a boil over moderate heat being careful not to mistake air bubbles for boiling. (VERY IMPORTANT)</li><li>Boil slowly, stirring constantly for a full 5 minutes. (Also very important!).</li><li>Remove from heat and stir in vanilla and chocolate until chocolate is melted. Stir in nuts if needed.</li><li>Turn into a 9 x 9 inch pan that has been sprayed with butter flavored cooking spray and cool completely.</li></ol>
        @SerializedName("license")
        val license: String?, // CC BY 3.0
        @SerializedName("lowFodmap")
        val lowFodmap: Boolean?, // false
        @SerializedName("occasions")
        val occasions: List<Any?>?,
        @SerializedName("originalId")
        val originalId: Any?, // null
        @SerializedName("preparationMinutes")
        val preparationMinutes: Int?, // -1
        @SerializedName("pricePerServing")
        val pricePerServing: Double?, // 33.3
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int?, // 60
        @SerializedName("servings")
        val servings: Int?, // 16
        @SerializedName("sourceName")
        val sourceName: String?, // Foodista
        @SerializedName("sourceUrl")
        val sourceUrl: String?, // http://www.foodista.com/recipe/38ND6CKS/flawless-chocolate-fudge
        @SerializedName("spoonacularScore")
        val spoonacularScore: Double?, // 2.65464448928833
        @SerializedName("spoonacularSourceUrl")
        val spoonacularSourceUrl: String?, // https://spoonacular.com/flawless-chocolate-fudge-643129
        @SerializedName("summary")
        val summary: String?, // Flawless Chocolate Fudge requires approximately <b>1 hour</b> from start to finish. One portion of this dish contains roughly <b>2g of protein</b>, <b>10g of fat</b>, and a total of <b>291 calories</b>. This recipe serves 16 and costs 33 cents per serving. This recipe from Foodista has 9 fans. It is a good option if you're following a <b>gluten free</b> diet. If you have butter, marshmallow fluff, real vanillan extract, and a few other ingredients on hand, you can make it. It works well as a very affordable dessert. Overall, this recipe earns a <b>rather bad spoonacular score of 15%</b>. If you like this recipe, you might also like recipes such as <a href="https://spoonacular.com/recipes/bakers-fabulously-flawless-fudge-142413">Baker's Fabulously Flawless Fudge</a>, <a href="https://spoonacular.com/recipes/chocolate-fudge-cupcakes-filled-with-chocolate-fudge-cookie-bar-and-marshmallow-meringue-frosting-373158">Chocolate Fudge Cupcakes filled with Chocolate Fudge Cookie Bar and Marshmallow Meringue Frosting</a>, and <a href="https://spoonacular.com/recipes/chocolate-fudge-cake-recipe-mega-chocolate-fudge-cake-60909">Chocolate Fudge Cake Recipe (mega Chocolate Fudge Cake)</a>.
        @SerializedName("sustainable")
        val sustainable: Boolean?, // false
        @SerializedName("title")
        val title: String?, // Flawless Chocolate Fudge
        @SerializedName("vegan")
        val vegan: Boolean?, // false
        @SerializedName("vegetarian")
        val vegetarian: Boolean?, // false
        @SerializedName("veryHealthy")
        val veryHealthy: Boolean?, // false
        @SerializedName("veryPopular")
        val veryPopular: Boolean?, // false
        @SerializedName("weightWatcherSmartPoints")
        val weightWatcherSmartPoints: Int? // 15
    )
}