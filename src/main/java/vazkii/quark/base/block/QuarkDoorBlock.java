package vazkii.quark.base.block;

import java.util.function.BooleanSupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import vazkii.arl.interf.IBlockItemProvider;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.handler.RenderLayerHandler;
import vazkii.quark.base.handler.RenderLayerHandler.RenderTypeSkeleton;
import vazkii.quark.base.item.QuarkDoubleHighBlockItem;
import vazkii.quark.base.module.QuarkModule;

public class QuarkDoorBlock extends DoorBlock implements IQuarkBlock, IBlockItemProvider {

	private final QuarkModule module;
	private BooleanSupplier enabledSupplier = () -> true;

	public QuarkDoorBlock(String regname, QuarkModule module, CreativeModeTab creativeTab, Properties properties) {
		super(properties);
		this.module = module;

		RenderLayerHandler.setRenderType(this, RenderTypeSkeleton.CUTOUT);
		RegistryHelper.registerBlock(this, regname);
		if(creativeTab != null)
			RegistryHelper.setCreativeTab(this, creativeTab);
	}

	@Override
	public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
		if(isEnabled() || group == CreativeModeTab.TAB_SEARCH)
			super.fillItemCategory(group, items);
	}

	@Override
	public QuarkDoorBlock setCondition(BooleanSupplier enabledSupplier) {
		this.enabledSupplier = enabledSupplier;
		return this;
	}

	@Override
	public boolean doesConditionApply() {
		return enabledSupplier.getAsBoolean();
	}

	@Nullable
	@Override
	public QuarkModule getModule() {
		return module;
	}

	@Override
	public BlockItem provideItemBlock(Block block, Item.Properties props) {
		return new QuarkDoubleHighBlockItem(this, props);
	}

}
