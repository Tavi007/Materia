package Tavi007.Materia.recipes.effects;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.util.NetworkHelper;
import Tavi007.Materia.util.RegistryHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MateriaEffectRecipePojo {

    private MateriaEffectRecipeInput input;
    private List<String> output;

    private MateriaEffectRecipePojo() {
        input = new MateriaEffectRecipeInput();
        output = new ArrayList<>();
    };

    public MateriaEffectRecipePojo(FriendlyByteBuf buf) {
        input = new MateriaEffectRecipeInput(buf);
        output = NetworkHelper.readStringList(buf);
    };

    public void encode(FriendlyByteBuf buf) {
        input.encode(buf);
        NetworkHelper.writeStringList(buf, output);
    }

    public List<ResourceLocation> getOutput() {
        return toResourceLocationList(output);
    }

    private List<ResourceLocation> toResourceLocationList(List<String> stringList) {
        List<ResourceLocation> list = new ArrayList<>();
        stringList.forEach(string -> list.add(new ResourceLocation(string)));
        return list;
    }

    public boolean isValid() {
        return input != null && input.isValid() && output != null && !output.isEmpty();
    }

    public MateriaEffectRecipePojo copy() {
        MateriaEffectRecipePojo copy = new MateriaEffectRecipePojo();
        copy.input = input.copy();
        output.forEach(entry -> copy.output.add(new String(entry)));
        return copy;
    }

    public boolean doesInputMatch(List<ItemStack> stacks) {
        return input.doesMatch(stacks);
    }

    private class MateriaEffectRecipeInput {

        @SerializedName("items")
        private List<String> itemStrings;
        @SerializedName("tags")
        private List<String> tagStrings;

        @SerializedName("_tags")
        List<TagKey<Item>> tags;
        @SerializedName("_items")
        List<Item> items;

        private MateriaEffectRecipeInput() {
            this.itemStrings = new ArrayList<>();
            this.tagStrings = new ArrayList<>();
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

        private List<TagKey<Item>> getTags() {
            if (tags == null) {
                tags = new ArrayList<>();
                for (String tagString : tagStrings) {
                    TagKey<Item> tagKey = RegistryHelper.getTagKey(new ResourceLocation(tagString));
                    if (tagKey != null) {
                        tags.add(tagKey);
                    }
                }
            }
            return tags;
        }

        private List<Item> getItems() {
            if (items == null) {
                items = new ArrayList<>();
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
            itemStrings = NetworkHelper.readStringList(buf);
            tagStrings = NetworkHelper.readStringList(buf);
        }

        public void encode(FriendlyByteBuf buf) {
            NetworkHelper.writeStringList(buf, itemStrings);
            NetworkHelper.writeStringList(buf, tagStrings);
        }

    }
}
