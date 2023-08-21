package xyz.janek.betterthanauth.mixin;

import net.minecraft.core.net.command.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.janek.betterthanauth.command.LoginCommand;
import xyz.janek.betterthanauth.command.RegisterCommand;

@Mixin(Commands.class)
public abstract class MixinCommands {
    @Inject(method="initCommands()V", at = @At("TAIL"), remap = false)
    private static void initCommands(CallbackInfo ci) {
        Commands.commands.add(new RegisterCommand());
        Commands.commands.add(new LoginCommand());
    }
}
