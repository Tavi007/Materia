package Tavi007.Materia.capabilities.magic;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.SerializableCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
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

public class MagicDataCapability {

	@CapabilityInject(MagicData.class)
	public static final Capability<MagicData> CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction defaultFacing = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(Materia.MOD_ID, "magic");

	public static void register() {
		CapabilityManager.INSTANCE.register(MagicData.class, new Capability.IStorage<MagicData>() {

			@Override
			public INBT writeNBT(final Capability<MagicData> capability, final MagicData instance, final Direction side) {
				//fill nbt with data
				CompoundNBT nbt = new CompoundNBT();
				nbt.putInt("index", instance.getSpellIndex());
				nbt.putInt("mana", instance.getMana());
				return nbt;
			}

			@Override
			public void readNBT(final Capability<MagicData> capability, final MagicData instance, final Direction side, final INBT nbt) {
				instance.setSpellIndex(((CompoundNBT) nbt).getInt("index"));
				instance.setMana(((CompoundNBT) nbt).getInt("mana"));
			}
		}, () -> new MagicData());
	}

	public static ICapabilityProvider createProvider(final MagicData MagicData) {
		return new SerializableCapabilityProvider<>(CAPABILITY, defaultFacing, MagicData);
	}

	@Mod.EventBusSubscriber(modid = Materia.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<Entity> event) {
			if(event.getObject() instanceof PlayerEntity) {
				final MagicData collection = new MagicData();
				event.addCapability(ID, createProvider(collection));
			}
		}
	}
}