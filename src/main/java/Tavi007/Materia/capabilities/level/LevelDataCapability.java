package Tavi007.Materia.capabilities.level;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.SerializableCapabilityProvider;
import Tavi007.Materia.items.BaseMateria;
import net.minecraft.item.Item;
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

public class LevelDataCapability {

	@CapabilityInject(LevelData.class)
	public static final Capability<LevelData> LevelData_Capability = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction defaultFacing = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(Materia.MOD_ID, "level_data");

	public static void register() {
		CapabilityManager.INSTANCE.register(LevelData.class, new Capability.IStorage<LevelData>() {

			@Override
			public INBT writeNBT(final Capability<LevelData> capability, final LevelData instance, final Direction side) {

				//fill nbt with data
				CompoundNBT nbt = new CompoundNBT();
				nbt.putInt("level", instance.level);
				nbt.putInt("ap", instance.ap);
				return nbt;
			}

			@Override
			public void readNBT(final Capability<LevelData> capability, final LevelData instance, final Direction side, final INBT nbt) {
				if (nbt instanceof CompoundNBT) {
					instance.level = ((CompoundNBT) nbt).getInt("level");
					instance.ap = ((CompoundNBT) nbt).getInt("ap");
				}
			}
		}, () -> new LevelData());
	}

	public static ICapabilityProvider createProvider(final LevelData levelData) {
		return new SerializableCapabilityProvider<>(LevelData_Capability, defaultFacing, levelData);
	}


	/**
	 * Event handler for the {@link IElementalAttack} capability.
	 */
	@Mod.EventBusSubscriber(modid = Materia.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			Item item = event.getObject().getItem();
			if(item instanceof BaseMateria) {
				final LevelData atck = new LevelData();
				event.addCapability(ID, createProvider(atck));
			}
		}
	}
}