package com.s2so2.xaeromark;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class XaeroMarkCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = net.minecraft.commands.Commands.literal("xaero_mark")
                .then(net.minecraft.commands.Commands.argument("name", StringArgumentType.word())
                        .executes(ctx -> addWaypoint(ctx.getSource(), StringArgumentType.getString(ctx, "name"), "FFA500"))
                        .then(net.minecraft.commands.Commands.argument("color", StringArgumentType.word())
                                .executes(ctx -> addWaypoint(ctx.getSource(), StringArgumentType.getString(ctx, "name"), StringArgumentType.getString(ctx, "color")))
                        )
                );

        dispatcher.register(builder);
    }

    private static int addWaypoint(CommandSourceStack source, String name, String color) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            source.sendFailure(Component.literal("§cОшибка: игрок не найден"));
            return 0;
        }

        try {
            double x = mc.player.getX();
            double y = mc.player.getY();
            double z = mc.player.getZ();

            // Проверка формата HEX цвета
            if (!isValidHexColor(color)) {
                source.sendFailure(Component.literal("§cОшибка: неверный формат цвета. Используйте HEX (например: FF0000)"));
                return 0;
            }

            // Попытка добавить waypoint в Xaero's Minimap
            boolean success = XaeroIntegration.addWaypoint(name, (int) x, (int) y, (int) z, color);

            if (success) {
                source.sendSuccess(() -> Component.literal(
                    "§a✓ Метка добавлена на карту!\n" +
                    "§eИмя: " + name + "\n" +
                    "§eКоординаты: " + (int)x + " " + (int)y + " " + (int)z
                ), false);
                return 1;
            } else {
                source.sendFailure(Component.literal("§cОшибка: не удалось добавить waypoint. Возможно, Xaero's Minimap не установлен."));
                return 0;
            }

        } catch (Exception e) {
            source.sendFailure(Component.literal("§cОшибка: " + e.getMessage()));
            return 0;
        }
    }

    private static boolean isValidHexColor(String color) {
        return color.matches("^[0-9A-Fa-f]{6}$");
    }
}
