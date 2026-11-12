package com.example.learnclient;

import com.mojang.brigadier.CommandDispatcher;
import com.example.learnclient.modules.Module;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

public class CheatCommands {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("lc")
                .then(CommandManager.literal("toggle")
                    .then(CommandManager.argument("module", com.mojang.brigadier.arguments.StringArgumentType.string())
                        .executes(context -> {
                            String moduleName = com.mojang.brigadier.arguments.StringArgumentType.getString(context, "module");
                            Module mod = LearnClient.MODULES.getModuleByName(moduleName);
                            
                            if (mod != null) {
                                mod.toggle();
                                context.getSource().sendFeedback(() -> Text.literal("Модуль " + mod.getName() + " переключен!"), false);
                                return 1;
                            } else {
                                context.getSource().sendError(Text.literal("Модуль не найден: " + moduleName));
                                return 0;
                            }
                        })
                    )
                )
        );
    }
}
