package com.s2so2.xaeromark;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OnlyIn(Dist.CLIENT)
public class XaeroIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger("XaeroIntegration");

    public static boolean addWaypoint(String name, int x, int y, int z, String hexColor) {
        try {
            // Проверяем наличие Xaero's Minimap через reflection
            Class<?> waypointClass = Class.forName("xaero.common.util.Waypoint");
            Class<?> waypointManagerClass = Class.forName("xaero.client.file.FileManager");

            // Создаём новый waypoint
            Object waypoint = waypointClass.getDeclaredConstructor(
                    String.class, String.class, int.class, int.class, int.class, byte.class, boolean.class
            ).newInstance(
                    name,                    // название
                    name.substring(0, Math.min(3, name.length())).toUpperCase(), // инициалы
                    x, y, z,                 // координаты
                    (byte) 0,                // dimension (0 = Overworld)
                    false                    // disabled
            );

            // Устанавливаем цвет
            int colorInt = Integer.parseInt(hexColor, 16);
            waypointClass.getMethod("setColor", int.class).invoke(waypoint, colorInt);

            // Добавляем в список waypoints
            Object fileManager = waypointManagerClass.getMethod("getInstance").invoke(null);
            fileManager.getClass().getMethod("addWaypoint", waypointClass).invoke(fileManager, waypoint);

            LOGGER.info("Waypoint добавлена успешно: {} at {} {} {}", name, x, y, z);
            return true;

        } catch (ClassNotFoundException e) {
            LOGGER.warn("Xaero's Minimap не найден в classpath");
            return false;
        } catch (Exception e) {
            LOGGER.error("Ошибка при добавлении waypoint: ", e);
            return false;
        }
    }
}
