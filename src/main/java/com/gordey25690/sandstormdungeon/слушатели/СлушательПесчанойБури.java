package com.gordey25690.sandstormdungeon.слушатели;

import com.gordey25690.sandstormdungeon.ОсновнойПлагин;
import org.bukkit.event.Listener;

/**
 * Слушатель событий для песчаной бури
 */
public class СлушательПесчанойБури implements Listener {
    
    private final ОсновнойПлагин плагин;
    
    public СлушательПесчанойБури(ОсновнойПлагин плагин) {
        this.плагин = плагин;
    }
    
    // TODO: Добавить обработку движения блоков от ветра
    // TODO: Добавить эффекты при входе в зону бури
}
