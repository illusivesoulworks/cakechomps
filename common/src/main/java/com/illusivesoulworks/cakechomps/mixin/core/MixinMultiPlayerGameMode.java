package com.illusivesoulworks.cakechomps.mixin.core;

import com.illusivesoulworks.cakechomps.mixin.CakePipeline;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode {

  @Unique
  @Final
  CakePipeline cakechomps$cakePipeline = new CakePipeline();

  @Inject(
      at = @At("HEAD"),
      method = "performUseItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"
  )
  private void cakechomps$useItemOn(LocalPlayer player, InteractionHand hand, BlockHitResult result,
                                    CallbackInfoReturnable<InteractionResult> cir) {
    this.cakechomps$cakePipeline.init(player.level(), result);
  }

  @Inject(
      at = @At("RETURN"),
      method = "performUseItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"
  )
  private void cakechomps$useItemOnReturn(LocalPlayer player, InteractionHand hand,
                                          BlockHitResult result,
                                          CallbackInfoReturnable<InteractionResult> cir) {
    this.cakechomps$cakePipeline.runCheck(player, player.level(), result);
  }
}
