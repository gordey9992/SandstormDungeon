package com.gordey25690.sandstormdungeon.менеджеры;

import com.gordey25690.sandstormdungeon.ОсновнойПлагин;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * Менеджер для генерации и управления подземельем
 */
public class МенеджерПодземелья {
    
    private final ОсновнойПлагин плагин;
    
    public МенеджерПодземелья(ОсновнойПлагин плагин) {
        this.плагин = плагин;
    }
    
    /**
     * Создать древний храм в указанной локации
     */
    public void создатьХрам(Location локация) {
        World мир = локация.getWorld();
        int центрX = локация.getBlockX();
        int центрZ = локация.getBlockZ();
        int базовыйУровень = локация.getBlockY();
        
        // Генерация основания пирамиды
        сгенерироватьОснованиеПирамиды(мир, центрX, базовыйУровень, центрZ);
        
        // Генерация обломков храма
        сгенерироватьОбломкиХрама(мир, центрX, базовыйУровень, центрZ);
        
        // Размещение лута
        разместитьЛут(мир, центрX, базовыйУровень, центрZ);
        
        плагин.getLogger().info("Древний храм сгенерирован в локации: " + локация);
    }
    
    private void сгенерироватьОснованиеПирамиды(World мир, int центрX, int базовыйУровень, int центрZ) {
        // Основание из песчаника
        for (int x = -10; x <= 10; x++) {
            for (int z = -10; z <= 10; z++) {
                Block блок = мир.getBlockAt(центрX + x, базовыйУровень, центрZ + z);
                if (Math.abs(x) >= 8 || Math.abs(z) >= 8) {
                    блок.setType(Material.SANDSTONE);
                }
            }
        }
    }
    
    private void сгенерироватьОбломкиХрама(World мир, int центрX, int базовыйУровень, int центрZ) {
        // Колонны и обломки
        Material[] материалыХрама = {Material.SANDSTONE, Material.CHISELED_SANDSTONE, Material.SMOOTH_SANDSTONE};
        
        // Случайные колонны
        for (int i = 0; i < 5; i++) {
            int смещениеX = (int) (Math.random() * 15 - 7);
            int смещениеZ = (int) (Math.random() * 15 - 7);
            int высота = 3 + (int) (Math.random() * 4);
            
            for (int y = 0; y < высота; y++) {
                Block блок = мир.getBlockAt(центрX + смещениеX, базовыйУровень + y + 1, центрZ + смещениеZ);
                блок.setType(материалыХрама[(int) (Math.random() * материалыХрама.length)]);
            }
        }
    }
    
    private void разместитьЛут(World мир, int центрX, int базовыйУровень, int центрZ) {
        // Сундук с лутом
        Location локацияСундука = new Location(мир, центрX, базовыйУровень + 1, центрZ);
        Block сундук = мир.getBlockAt(локацияСундука);
        сундук.setType(Material.CHEST);
        
        // TODO: Добавить логику заполнения сундука лутом
    }
    
    /**
     * Получить локацию центра подземелья из конфига
     */
    public Location получитьЛокациюПодземелья(World мир) {
        int x = плагин.получитьМенеджерКонфигов().получитьКонфиг().getInt("подземелье.центр-х", 100);
        int z = плагин.получитьМенеджерКонфигов().получитьКонфиг().getInt("подземелье.центр-z", 100);
        int y = мир.getHighestBlockYAt(x, z);
        
        return new Location(мир, x, y, z);
    }
}
