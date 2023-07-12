package Tavi007.Materia.recipes.effects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.resources.ResourceLocation;

public class MateriaEffectRecipePojo {

    List<String> input;
    List<String> output;

    private MateriaEffectRecipePojo() {
        input = new ArrayList<>();
        output = new ArrayList<>();
    };

    public List<ResourceLocation> getInput() {
        return toResourceLocationList(input);
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
        return input != null && !input.isEmpty() && output != null && !output.isEmpty();
    }

    public MateriaEffectRecipePojo copy() {
        MateriaEffectRecipePojo copy = new MateriaEffectRecipePojo();
        input.forEach(entry -> copy.input.add(entry));
        output.forEach(entry -> copy.output.add(entry));
        return copy;
    }

    public boolean doesInputMatch(List<ResourceLocation> itemRL) {
        List<ResourceLocation> inputRL = getInput();
        return inputRL.size() == itemRL.size()
            && inputRL.containsAll(itemRL);
    }
}
