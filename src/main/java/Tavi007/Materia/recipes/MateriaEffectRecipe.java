package Tavi007.Materia.recipes;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class MateriaEffectRecipe implements Recipe<Container> {

    @Override
    public boolean matches(Container p_44002_, Level p_44003_) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ItemStack assemble(Container p_44001_) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResourceLocation getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        // TODO Auto-generated method stub
        return null;
    }

    public static class Serializer implements RecipeSerializer<MateriaEffectRecipe> {

        @Override
        public MateriaEffectRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            return null;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MateriaEffectRecipe recipe) {
        }

        @Nullable
        @Override
        public MateriaEffectRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            return null;
        }
    }
}
