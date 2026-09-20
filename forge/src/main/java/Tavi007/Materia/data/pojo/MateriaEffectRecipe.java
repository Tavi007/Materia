package Tavi007.Materia.data.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.util.NetworkHelper;
import Tavi007.Materia.util.RegistryHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MateriaEffectRecipe {

    private MateriaEffectRecipeInput input;
    private Set<String> output;

    private MateriaEffectRecipe() {
        input = new MateriaEffectRecipeInput();
        output = new HashSet<>();
    };

    public MateriaEffectRecipe(FriendlyByteBuf buf) {
        input = new MateriaEffectRecipeInput(buf);
        output = NetworkHelper.readStringSet(buf);
    };

    public void encode(FriendlyByteBuf buf) {
        input.encode(buf);
        NetworkHelper.writeStringSet(buf, output);
    }

    public Set<ResourceLocation> getOutput() {
        return toResourceLocationSet(output);
    }

    private Set<ResourceLocation> toResourceLocationSet(Set<String> stringSet) {
        Set<ResourceLocation> Set = new HashSet<>();
        stringSet.forEach(string -> Set.add(new ResourceLocation(string)));
        return Set;
    }

    public boolean isValid() {
        return input != null && input.isValid() && output != null && !output.isEmpty();
    }

    public MateriaEffectRecipe copy() {
        MateriaEffectRecipe copy = new MateriaEffectRecipe();
        copy.input = input.copy();
        output.forEach(entry -> copy.output.add(new String(entry)));
        return copy;
    }

    public boolean doesInputMatch(List<ItemStack> stacks) {
        return input.doesMatch(stacks);
    }

    private class MateriaEffectRecipeInput {

        @SerializedName("items")
        private Set<String> itemStrings;
        @SerializedName("tags")
        private Set<String> tagStrings;

        @SerializedName("_tags")
        Set<TagKey<Item>> tags;
        @SerializedName("_items")
        Set<Item> items;

        private MateriaEffectRecipeInput() {
            this.itemStrings = new HashSet<>();
            this.tagStrings = new HashSet<>();
        }

        public boolean doesMatch(List<ItemStack> stacks) {
            if (stacks == null || stacks.isEmpty()) {
                return false;
            }

            // check if input matches any of the items or tags
            for (ItemStack stack : stacks) {
                if (!getItems().contains(stack.getItem()) && !getTags().stream().anyMatch(tagKey -> stack.is(tagKey))) {
                    return false;
                }
            }

            // check if every item and tag is fulfilled
            for (Item item : getItems()) {
                if (!stacks.stream().anyMatch(stack -> stack.is(item))) {
                    return false;
                }
            }
            for (TagKey<Item> tagKey : getTags()) {
                if (!stacks.stream().anyMatch(stack -> stack.is(tagKey))) {
                    return false;
                }
            }

            return true;
        }

        private Set<TagKey<Item>> getTags() {
            if (tags == null) {
                tags = new HashSet<>();
                for (String tagString : tagStrings) {
                    TagKey<Item> tagKey = RegistryHelper.getTagKey(new ResourceLocation(tagString));
                    if (tagKey != null) {
                        tags.add(tagKey);
                    }
                }
            }
            return tags;
        }

        private Set<Item> getItems() {
            if (items == null) {
                items = new HashSet<>();
                for (String itemString : itemStrings) {
                    Item item = RegistryHelper.getItem(new ResourceLocation(itemString));
                    if (item != null && !Items.AIR.equals(item)) {
                        items.add(item);
                    }
                }
            }
            return items;
        }

        public MateriaEffectRecipeInput copy() {
            MateriaEffectRecipeInput copy = new MateriaEffectRecipeInput();
            itemStrings.forEach(item -> copy.itemStrings.add(new String(item)));
            tagStrings.forEach(tag -> copy.tagStrings.add(new String(tag)));
            return copy;
        }

        public boolean isValid() {
            return items != null && tags != null && (!items.isEmpty() || !tags.isEmpty());
        }

        public MateriaEffectRecipeInput(FriendlyByteBuf buf) {
            itemStrings = NetworkHelper.readStringSet(buf);
            tagStrings = NetworkHelper.readStringSet(buf);
        }

        public void encode(FriendlyByteBuf buf) {
            NetworkHelper.writeStringSet(buf, itemStrings);
            NetworkHelper.writeStringSet(buf, tagStrings);
        }

    }
}
