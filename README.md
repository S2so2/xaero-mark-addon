# Xaero Mark Addon

Простой Forge мод для Minecraft 1.20.1, который добавляет команду для быстрого добавления waypoint'ов в Xaero's Minimap.

## Требования

- **Minecraft**: 1.20.1
- **Forge**: 47.2.20+
- **Xaero's Minimap**: установлен на клиент

## Установка

1. Скачай JAR файл из [Releases](https://github.com/S2so2/xaero-mark-addon/releases)
2. Положи в папку `mods`
3. Запусти Minecraft

## Синтаксис команды

### Добавить метку с цветом по умолчанию (оранжевый)
```
/xaero_mark TestPoint
```

### Добавить метку с кастомным цветом (HEX)
```
/xaero_mark TestPoint FF0000
```

## Примеры

```
/xaero_mark MyBase           → оранжевая метка "MyBase" на позиции игрока
/xaero_mark Dungeon FF0000   → красная метка "Dungeon" на позиции игрока
/xaero_mark Village 00FF00   → зелёная метка "Village" на позиции игрока
/xaero_mark Fortress 0000FF  → синяя метка "Fortress" на позиции игрока
```

## Цвета (HEX)

- `FF0000` - Красный
- `00FF00` - Зелёный
- `0000FF` - Синий
- `FFFF00` - Жёлтый
- `FF00FF` - Фиолетовый
- `00FFFF` - Голубой
- `FFA500` - Оранжевый (по умолчанию)
- `FFFFFF` - Белый
- `000000` - Чёрный

## Разработка

### Требования

- JDK 17+
- Gradle 8.0+

### Компиляция

```bash
./gradlew build
```

GAR файл будет в `build/libs/`

## Лицензия

MIT
