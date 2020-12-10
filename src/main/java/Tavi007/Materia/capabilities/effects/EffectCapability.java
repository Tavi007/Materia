package Tavi007.Materia.capabilities.effects;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.SerializableCapabilityProvider;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EffectCapability {
	@CapabilityInject(EffectList.class)
	public static final Capability<EffectList> EFFECT_CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction DEFAULT_FACING = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(Materia.MOD_ID, "materia_effect");

	public static void register() {
		CapabilityManager.INSTANCE.register(EffectList.class, new Capability.IStorage<EffectList>() {

			@Override
			public INBT writeNBT(final Capability<EffectList> capability, final EffectList instance, final Direction side) {
				return instance.writeNBT();
			}

			@Override
			public void readNBT(final Capability<EffectList> capability, final EffectList instance, final Direction side, final INBT nbt) {
				instance.readNBT((CompoundNBT) nbt);
			}
		}, () -> new EffectList());
	}

	public static ICapabilityProvider createProvider(final EffectList list) {
		return new SerializableCapabilityProvider<>(EFFECT_CAPABILITY, DEFAULT_FACING, list);
	}


	/**
	 * Event handler for the {@link IElementalAttack} capability.
	 */
	@Mod.EventBusSubscriber(modid = Materia.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			ItemStack stack = event.getObject();
			if(stack.getItem() instanceof IMateriaTool) {
				final EffectList list = new EffectList();
				
				MateriaEffectFire fire = new MateriaEffectFire();
				list.add(fire);
				
				event.addCapability(ID, createProvider(list));
			}
		}
	}
}
