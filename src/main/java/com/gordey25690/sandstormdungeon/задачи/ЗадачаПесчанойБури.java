package com.gordey25690.sandstormdungeon.задачи;

import com.gordey25690.sandstormdungeon.ОсновнойПлагин;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

/**
 * Задача для создания эффекта песчаной бури с движением блоков
 */
public class ЗадачаПесчанойБури extends BukkitRunnable {
    
    private final ОсновнойПлагин плагин;
    private final Location центрБури;
    private final int радиус;
    private final List<Material> блокиДляДвижения;
    
    public ЗадачаПесчанойБури(ОсновнойПлагин плагин, Location центрБури) {
        this.плагин = плагин;
        this.центрБури = центрБури;
        this.радиус = плагин.получитьМенеджерКонфигов().получитьРадиусБури();
        this.блокиДляДвижения = Arrays.asList(
            Material.SAND,
            Material.RED_SAND,
            Material.GRAVEL,
            Material.SUSPICIOUS_SAND
        );
    }
    
    @Override
    public void run() {
        // Создаем частицы песка вокруг всех игроков в радиусе бури
        for (Player игрок : центрБури.getWorld().getPlayers()) {
            if (игрок.getLocation().distance(центрБури) <= радиус) {
                создатьЧастицыПеска(игрок.getLocation());
                двигатьБлокиПеска(игрок.getLocation());
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
                Material.SAND.createBlockData()
            );
        }
    }
    
    /**
     * Двигает блоки песка в направлении ветра
     */
    private void двигатьБлокиПеска(Location локацияИгрока) {
        if (!плагин.получитьМенеджерКонфигов().получитьКонфиг().getBoolean("песчаная-буря.двигать-блоки", true)) {
            return;
        }
        
        int силаВетра = плагин.получитьМенеджерКонфигов().получитьСилуВетра();
        
        // Проверяем случайные блоки вокруг игрока
        for (int i = 0; i < силаВетра; i++) {
            int смещениеX = (int) (Math.random() * 8 - 4);
            int смещениеZ = (int) (Math.random() * 8 - 4);
            
            Block блок = локацияИгрока.clone().add(смещениеX, -1, смещениеZ).getBlock();
            
            if (блокиДляДвижения.contains(блок.getType())) {
                // Пытаемся сдвинуть блок в направлении ветра (на восток)
                сдвинутьБлок(блок, 1, 0);
            }
        }
    }
    
    /**
     * Сдвигает блок в указанном направлении
     */
    private void сдвинутьБлок(Block исходныйБлок, int направлениеX, int направлениеZ) {
        Block целевойБлок = исходныйБлок.getRelative(направлениеX, 0, направлениеZ);
        
        // Если целевая позиция пуста - перемещаем блок
        if (целевойБлок.getType().isAir()) {
            целевойБлок.setType(исходныйБлок.getType());
            исходныйБлок.setType(Material.AIR);
            
            // Эффект частиц при движении
            исходныйБлок.getWorld().spawnParticle(
                Particle.BLOCK_DUST,
                исходныйБлок.getLocation().add(0.5, 0.5, 0.5),
                3,
                0.2, 0.2, 0.2,
                0.1,
                исходныйБлок.getType().createBlockData()
            );
        }
    }
}
