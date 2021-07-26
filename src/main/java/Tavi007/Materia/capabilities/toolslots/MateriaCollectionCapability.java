package Tavi007.Materia.capabilities.toolslots;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.SerializableCapabilityProvider;
import Tavi007.Materia.items.IMateriaTool;
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
import net.minecraftforge.items.CapabilityItemHandler;

public class MateriaCollectionCapability {

	@CapabilityInject(MateriaCollection.class)
	public static final Capability<MateriaCollection> MateriaCollection_Capability = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction defaultFacing = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(Materia.MOD_ID, "materia_collection");

	public static void register() {
		CapabilityManager.INSTANCE.register(MateriaCollection.class, new Capability.IStorage<MateriaCollection>() {

			@Override
			public INBT writeNBT(final Capability<MateriaCollection> capability, final MateriaCollection instance, final Direction side) {
				//fill nbt with data
				CompoundNBT nbt = new CompoundNBT();
				nbt.put("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(instance, null));
				return nbt;
			}

			@Override
			public void readNBT(final Capability<MateriaCollection> capability, final MateriaCollection instance, final Direction side, final INBT nbt) {
				CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(instance, null, ((CompoundNBT) nbt).get("inventory"));
			}
		}, () -> new MateriaCollection());
	}

	public static ICapabilityProvider createProvider(final MateriaCollection materiaCollection) {
		return new SerializableCapabilityProvider<>(MateriaCollection_Capability, defaultFacing, materiaCollection);
	}


	/**
	 * Event handler for the {@link IElementalAttack} capability.
	 */
	@Mod.EventBusSubscriber(modid = Materia.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			Item item = event.getObject().getItem();
			if(item instanceof IMateriaTool) {
				final MateriaCollection collection = new MateriaCollection();
				event.addCapability(ID, createProvider(collection));
			}
		}
	}
}