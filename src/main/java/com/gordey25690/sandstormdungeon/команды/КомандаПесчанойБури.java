package com.gordey25690.sandstormdungeon.команды;

import com.gordey25690.sandstormdungeon.ОсновнойПлагин;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработчик команд для управления песчаной бурей
 */
public class КомандаПесчанойБури implements CommandExecutor, TabCompleter {
    
    private final ОсновнойПлагин плагин;
    
    public КомандаПесчанойБури(ОсновнойПлагин плагин) {
        this.плагин = плагин;
    }
    
    @Override
    public boolean onCommand(CommandSender отправитель, Command команда, String метка, String[] аргументы) {
        
        if (!отправитель.hasPermission("песчаныйданж.админ")) {
            отправитель.sendMessage("§cУ вас нет прав для использования этой команды!");
            return true;
        }
        
        if (аргументы.length == 0) {
            отправитель.sendMessage("§6Использование: /песчанаябуря <старт|стоп|перезагрузить>");
            отправитель.sendMessage("§6Использование: /sandstorm <start|stop|reload>");
            return true;
        }
        
        switch (аргументы[0].toLowerCase()) {
            case "start":
            case "старт":
    if (!(отправитель instanceof Player игрок)) {
        отправитель.sendMessage("§cЭту команду можно использовать только в игре!");
        return true;
    }
    
    // Проверяем, что игрок в пустыне
    if (!плагин.получитьМенеджерПесчанойБури().этоПустыня(игрок.getLocation())) {
        игрок.sendMessage("§cПесчаная буря может быть только в пустыне!");
        return true;
    }
    
    boolean успех = плагин.получитьМенеджерПесчанойБури().запуститьБурю(игрок.getLocation());
    if (успех) {
        игрок.sendMessage("§aПесчаная буря запущена!");
    }
                break;
                
            case "stop":
            case "стоп":
                плагин.получитьМенеджерПесчанойБури().остановитьБурю();
                отправитель.sendMessage("§aПесчаная буря остановлена!");
                break;
                
            case "reload":
            case "перезагрузить":
                плагин.получитьМенеджерКонфигов().перезагрузитьКонфиг();
                отправитель.sendMessage("§aКонфигурация перезагружена!");
                break;
                
            default:
                отправитель.sendMessage("§cНеизвестная подкоманда! Используйте: старт, стоп или перезагрузить");
                break;
        }
        
        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender отправитель, Command команда, String метка, String[] аргументы) {
        List<String> варианты = new ArrayList<>();
        
        if (аргументы.length == 1) {
            варианты.add("старт");
            варианты.add("стоп");
            варианты.add("перезагрузить");
            варианты.add("start");
            варианты.add("stop");
            варианты.add("reload");
        }
        
        return варианты;
    }
}
