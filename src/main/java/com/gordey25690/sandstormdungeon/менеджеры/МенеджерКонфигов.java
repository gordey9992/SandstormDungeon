package com.gordey25690.sandstormdungeon.менеджеры;

import com.gordey25690.sandstormdungeon.ОсновнойПлагин;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Менеджер для работы с конфигурацией
 */
public class МенеджерКонфигов {
    
    private final ОсновнойПлагин плагин;
    private FileConfiguration конфиг;
    
    public МенеджерКонфигов(ОсновнойПлагин плагин) {
        this.плагин = плагин;
    }
    
    public void загрузитьКонфиг() {
        плагин.saveDefaultConfig();
        this.конфиг = плагин.getConfig();
    }
    
    public void перезагрузитьКонфиг() {
        плагин.reloadConfig();
        this.конфиг = плагин.getConfig();
    }
    
    public FileConfiguration получитьКонфиг() {
        return конфиг;
    }
    
    public boolean получитьБуряВключена() {
        return конфиг.getBoolean("песчаная-буря.включена", true);
    }
    
    public int получитьПлотностьЧастиц() {
        return конфиг.getInt("песчаная-буря.плотность-частиц", 50);
    }
    
    public int получитьРадиусБури() {
        return конфиг.getInt("песчаная-буря.радиус-бури", 100);
    }
    
    public int получитьСилуВетра() {
        return конфиг.getInt("песчаная-буря.сила-ветра", 3);
    }
}
