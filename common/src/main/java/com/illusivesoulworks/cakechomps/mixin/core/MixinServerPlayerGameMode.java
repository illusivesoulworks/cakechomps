package com.illusivesoulworks.cakechomps.mixin.core;

import com.illusivesoulworks.cakechomps.mixin.CakePipeline;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public abstract class MixinServerPlayerGameMode {

  @Unique
  @Final
  CakePipeline cakechomps$cakePipeline = new CakePipeline();

  @Inject(
      at = @At("HEAD"),
      method = "useItemOn(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"
  )
  private void cakechomps$useItemOn(ServerPlayer serverPlayer, Level level, ItemStack stack,
                                    InteractionHand hand, BlockHitResult blockHitResult,
                                    CallbackInfoReturnable<InteractionResult> cir) {
    this.cakechomps$cakePipeline.init(level, blockHitResult);
  }

  @Inject(
      at = @At("RETURN"),
      method = "useItemOn(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"
  )
  private void cakechomps$useItemOnReturn(ServerPlayer serverPlayer, Level level, ItemStack stack,
                                          InteractionHand hand, BlockHitResult blockHitResult,
                                          CallbackInfoReturnable<InteractionResult> cir) {
    this.cakechomps$cakePipeline.runCheck(serverPlayer, level, blockHitResult);
  }
}
