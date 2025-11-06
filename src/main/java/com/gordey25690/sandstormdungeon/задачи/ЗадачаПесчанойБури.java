package com.gordey25690.sandstormdungeon.задачи;

import com.gordey25690.sandstormdungeon.ОсновнойПлагин;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Задача для создания эффекта песчаной бури
 */
public class ЗадачаПесчанойБури extends BukkitRunnable {
    
    private final ОсновнойПлагин плагин;
    private final Location центрБури;
    private final int радиус;
    
    public ЗадачаПесчанойБури(ОсновнойПлагин плагин, Location центрБури) {
        this.плагин = плагин;
        this.центрБури = центрБури;
        this.радиус = плагин.получитьМенеджерКонфигов().получитьРадиусБури();
    }
    
    @Override
    public void run() {
        // Создаем частицы песка вокруг всех игроков в радиусе бури
        for (Player игрок : центрБури.getWorld().getPlayers()) {
            if (игрок.getLocation().distance(центрБури) <= радиус) {
                создатьЧастицыПеска(игрок.getLocation());
            }
        }
    }
    
    private void создатьЧастицыПеска(Location локацияИгрока) {
        int плотность = плагин.получитьМенеджерКонфигов().получитьПлотностьЧастиц();
        
        for (int i = 0; i < плотность; i++) {
            double смещениеX = (Math.random() - 0.5) * 10;
            double смещениеY = (Math.random() - 0.5) * 4;
            double смещениеZ = (Math.random() - 0.5) * 10;
            
            Location локацияЧастицы = локацияИгрока.clone().add(смещениеX, смещениеY, смещениеZ);
            
            // Горизонтальные частицы песка
            локацияИгрока.getWorld().spawnParticle(
                Particle.BLOCK_DUST,
                локацияЧастицы,
                1,
                0.1, 0.1, 0.1,
                0.1,
                org.bukkit.Material.SAND.createBlockData()
            );
        }
    }
}
