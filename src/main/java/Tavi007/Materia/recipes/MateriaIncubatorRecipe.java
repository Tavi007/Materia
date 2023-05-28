package Tavi007.Materia.recipes;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class MateriaIncubatorRecipe implements Recipe<Container> {

    ResourceLocation recipeId;
    protected final Ingredient ingredient;
    protected final ItemStack result;

    protected MateriaIncubatorRecipe(ResourceLocation recipeId, Ingredient ingredient, ItemStack result) {
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public boolean matches(Container p_44002_, Level p_44003_) {
        return false;
    }

    @Override
    public ItemStack assemble(Container p_44001_) {
        return null; // not needed
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<MateriaIncubatorRecipe> {

        @Override
        public MateriaIncubatorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            return null;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MateriaIncubatorRecipe recipe) {
        }

        @Nullable
        @Override
        public MateriaIncubatorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
            return null;
        }
    }
}
