package net.htmlcsjs.htmlTech.intergration.jei;

import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class HTJEIPlugin implements IModPlugin {

    private IIngredientBlacklist itemBlacklist;
    private IIngredientRegistry iItemRegistry;
    public static IJeiRuntime jeiRuntime;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new HTMultiblockInfoCategory(registry.getJeiHelpers()));

    }

    @Override
    public void register(IModRegistry registry) {
        itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
        iItemRegistry = registry.getIngredientRegistry();
        HTMultiblockInfoCategory.registerRecipes(registry);

        //Multiblock info page registration
        HTMultiblockInfoCategory.getRecipes().values().forEach(v -> {
            MultiblockInfoPage infoPage = v.getInfoPage();
            registry.addIngredientInfo(infoPage.getController().getStackForm(),
                    VanillaTypes.ITEM,
                    infoPage.getDescription());
        });
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        this.jeiRuntime = jeiRuntime;
        IRecipeRegistry recipeRegistry = jeiRuntime.getRecipeRegistry();
        IRecipeCategory recipeCategory = recipeRegistry.getRecipeCategory("gregtech:multiblock_info");

/*        for (RecipeMap<?> recipeMap : RecipeMap.getRecipeMaps()) {
            recipeRegistry.hideRecipeCategory(GTValues.MODID + ":" + recipeMap.unlocalizedName);
        }*/

    }

}
