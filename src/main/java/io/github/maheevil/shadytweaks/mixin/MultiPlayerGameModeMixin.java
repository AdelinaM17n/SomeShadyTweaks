package io.github.maheevil.shadytweaks.mixin;

import me.shedaniel.autoconfig.ConfigData;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin{
    @Inject(
            method = "method_41933(Lorg/apache/commons/lang3/mutable/MutableObject;Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;I)Lnet/minecraft/network/protocol/Packet;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void injectOnAnonFunc(MutableObject<InteractionResult> mutableObject, LocalPlayer localPlayer, InteractionHand interactionHand, BlockHitResult blockHitResult, int i, CallbackInfoReturnable<Packet<?>> cir){
        if(!localPlayer.getLevel().dimensionType().bedWorks()){
            Block block = localPlayer.level.getBlockState(blockHitResult.getBlockPos()).getBlock();
            System.out.println(block.getName().getString());
            if(block instanceof BedBlock){
                mutableObject.setValue(InteractionResult.CONSUME);
                cir.setReturnValue(new ServerboundUseItemPacket(interactionHand,i));
                cir.cancel();
            }
        }
    }

    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/multiplayer/MultiPlayerGameMode.ensureHasSentCarriedItem ()V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void injectAfterEnsure(Player player, Entity targetEntity, CallbackInfo ci){
        if(targetEntity instanceof Villager){
            ci.cancel();
        }else if(targetEntity instanceof TamableAnimal tamableAnimal && tamableAnimal.isTame()){
            ci.cancel();
        }
    }
}
