package net.adeptstack.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.palettes.GlassPaneBlock;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.adeptstack.behaviour.doorBlock.DoorBlockMovementBehaviour;
import net.adeptstack.behaviour.doorBlock.DoorBlockMovingInteraction;
import net.adeptstack.behaviour.slidingDoor.TrainSlidingDoorMovementBehaviour;
import net.adeptstack.behaviour.slidingDoor.TrainSlidingDoorMovingInteraction;
import net.adeptstack.blocks.panelBlocks.IsoWallBlock;
import net.adeptstack.blocks.panelBlocks.platformBlocks.PlatformBlockCH;
import net.adeptstack.utils.TrainSlidingDoorProperties;
import net.adeptstack.blocks.doors.slidingDoor.TrainSlidingDoorBlock;
import net.adeptstack.blocks.LineBlock;
import net.adeptstack.blocks.panelBlocks.platformBlocks.PlatformBlockDE;
import net.adeptstack.blocks.panelBlocks.platformBlocks.PlatformBlockNL;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlock.GLASS_SET_TYPE;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.adeptstack.Main.REGISTRATE;
import static net.adeptstack.registry.ModTabs.TRAINUTILS_TAB;

@SuppressWarnings({"unused","removal"})
public class TrainUtilitiesBuilderTransformers {



}
