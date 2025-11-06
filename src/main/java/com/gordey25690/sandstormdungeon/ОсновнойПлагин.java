package com.gordey25690.sandstormdungeon;

import com.gordey25690.sandstormdungeon.менеджеры.МенеджерКонфигов;
import com.gordey25690.sandstormdungeon.менеджеры.МенеджерПесчанойБури;
import com.gordey25690.sandstormdungeon.менеджеры.МенеджерПодземелья;
import com.gordey25690.sandstormdungeon.слушатели.СлушательПесчанойБури;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Основной класс плагина Песчаный Данж
 */
public class ОсновнойПлагин extends JavaPlugin {
    
    private static ОсновнойПлагин экземпляр;
    private МенеджерКонфигов менеджерКонфигов;
    private МенеджерПесчанойБури менеджерПесчанойБури;
    private МенеджерПодземелья менеджерПодземелья;
    
    @Override
    public void onEnable() {
        экземпляр = this;
        
        // Инициализация менеджеров
        this.менеджерКонфигов = new МенеджерКонфигов(this);
        this.менеджерПесчанойБури = new МенеджерПесчанойБури(this);
        this.менеджерПодземелья = new МенеджерПодземелья(this);
        
        // Загрузка конфигов
        менеджерКонфигов.загрузитьКонфиг();
        
        // Регистрация слушателей
        getServer().getPluginManager().registerEvents(new СлушательПесчанойБури(this), this);
        
        // Регистрация команд
        getCommand("песчанаябуря").setExecutor(new КомандаПесчанойБури(this));
        getCommand("данж").setExecutor(new КомандаДанжа(this));
        
        getLogger().info("Песчаный Данж успешно запущен!");
    }
    
    @Override
    public void onDisable() {
        менеджерПесчанойБури.остановитьВсеБури();
        getLogger().info("Песчаный Данж выключен!");
    }
    
    public static ОсновнойПлагин получитьЭкземпляр() {
        return экземпляр;
    }
    
    public МенеджерКонфигов получитьМенеджерКонфигов() {
        return менеджерКонфигов;
    }
    
    public МенеджерПесчанойБури получитьМенеджерПесчанойБури() {
        return менеджерПесчанойБури;
    }
    
    public МенеджерПодземелья получитьМенеджерПодземелья() {
        return менеджерПодземелья;
    }
}
