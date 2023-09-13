package Tavi007.Materia.init;

import Tavi007.Materia.data.pojo.effects.AttackEffect;
import Tavi007.Materia.data.pojo.effects.MiningEffect;
import Tavi007.Materia.data.pojo.effects.MorphItemEffect;
import Tavi007.Materia.data.pojo.effects.SpellEffect;
import Tavi007.Materia.data.pojo.effects.StatEffect;
import Tavi007.Materia.registries.MateriaEffectRegistry;

public class MateriaEffectList {

    public static void init() {
        MateriaEffectRegistry.register(AttackEffect.class, AttackEffect::new);
        MateriaEffectRegistry.register(MiningEffect.class, MiningEffect::new);
        MateriaEffectRegistry.register(MorphItemEffect.class, MorphItemEffect::new);
        MateriaEffectRegistry.register(SpellEffect.class, SpellEffect::new);
        MateriaEffectRegistry.register(StatEffect.class, StatEffect::new);
    }
}
