package com.gordey25690.sandstormdungeon.менеджеры;

import com.gordey25690.sandstormdungeon.ОсновнойПлагин;
import com.gordey25690.sandstormdungeon.задачи.ЗадачаПесчанойБури;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.scheduler.BukkitTask;

/**
 * Менеджер для управления песчаными бурями
 */
public class МенеджерПесчанойБури {
    
    private final ОсновнойПлагин плагин;
    private BukkitTask задачаБури;
    
    public МенеджерПесчанойБури(ОсновнойПлагин плагин) {
        this.плагин = плагин;
    }
    
    /**
     * Запустить песчаную бурю в указанной локации (только в пустыне)
     */
    public boolean запуститьБурю(Location локация) {
        // Проверяем, что локация в пустыне
        if (!этоПустыня(локация)) {
            return false;
        }
        
        остановитьБурю(); // Останавливаем предыдущую бурю
        
        задачаБури = new ЗадачаПесчанойБури(плагин, локация).runTaskTimer(плагин, 0L, 5L);
        
        плагин.getLogger().info("Песчаная буря запущена в пустыне: " + локация);
        return true;
    }
    
    /**
     * Проверить, является ли биом пустыней
     */
    public boolean этоПустыня(Location локация) {
        Biome биом = локация.getBlock().getBiome();
        return биом == Biome.DESERT || 
               биом == Biome.DESERT_HILLS || 
               биом.toString().contains("DESERT");
    }
    
    /**
     * Остановить текущую бурю
     */
    public void остановитьБурю() {
        if (задачаБури != null && !задачаБури.isCancelled()) {
            задачаБури.cancel();
            задачаБури = null;
            плагин.getLogger().info("Песчаная буря остановлена");
        }
    }
    
    /**
     * Остановить все бури (при выключении плагина)
     */
    public void остановитьВсеБури() {
        остановитьБурю();
    }
    
    public boolean буряАктивна() {
        return задачаБури != null && !задачаБури.isCancelled();
    }
}
