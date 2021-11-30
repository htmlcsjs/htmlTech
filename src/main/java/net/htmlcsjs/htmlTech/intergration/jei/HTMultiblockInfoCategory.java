package net.htmlcsjs.htmlTech.intergration.jei;

import com.google.common.collect.ImmutableMap;
import gregtech.integration.jei.multiblock.MultiblockInfoRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.gui.recipes.RecipeLayout;
import net.htmlcsjs.htmlTech.HTValues;
import net.htmlcsjs.htmlTech.intergration.jei.multiblock.LaserCollectorInfo;
import net.htmlcsjs.htmlTech.intergration.jei.multiblock.LaserProjectorInfo;
import net.minecraft.client.resources.I18n;

public class HTMultiblockInfoCategory implements IRecipeCategory<MultiblockInfoRecipeWrapper> {

    private final IDrawable background;
    private final IGuiHelper guiHelper;
    private static ImmutableMap<String, MultiblockInfoRecipeWrapper> multiblockRecipes;

    public HTMultiblockInfoCategory(IJeiHelpers helpers) {
        this.guiHelper = helpers.getGuiHelper();
        this.background = guiHelper.createBlankDrawable(176, 166);
    }

    public static ImmutableMap<String, MultiblockInfoRecipeWrapper> getRecipes() {
        if (multiblockRecipes == null) {
            multiblockRecipes = new ImmutableMap.Builder<String, MultiblockInfoRecipeWrapper>()
                    .put("laser_projector", new MultiblockInfoRecipeWrapper(new LaserProjectorInfo()))
                    .put("laser_colelctor", new MultiblockInfoRecipeWrapper(new LaserCollectorInfo()))
                    .build();
        }

        return multiblockRecipes;
    }

    public static void registerRecipes(IModRegistry registry) {
        registry.addRecipes(getRecipes().values(), "gregtech:multiblock_info");
    }

    @Override
    public String getUid() {
        return HTValues.MODID + ":multiblock_info";
    }

    @Override
    public String getTitle() {
        return I18n.format("gregtech.multiblock.title");
    }

    @Override
    public String getModName() {
        return HTValues.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MultiblockInfoRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeWrapper.setRecipeLayout((RecipeLayout) recipeLayout, guiHelper);

        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        itemStackGroup.addTooltipCallback(recipeWrapper::addBlockTooltips);
    }
}
