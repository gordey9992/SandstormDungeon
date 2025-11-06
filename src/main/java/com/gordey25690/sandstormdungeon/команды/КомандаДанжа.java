package com.gordey25690.sandstormdungeon.команды;

import com.gordey25690.sandstormdungeon.ОсновнойПлагин;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Обработчик команды для телепорта в подземелье
 */
public class КомандаДанжа implements CommandExecutor {
    
    private final ОсновнойПлагин плагин;
    
    public КомандаДанжа(ОсновнойПлагин плагин) {
        this.плагин = плагин;
    }
    
    @Override
    public boolean onCommand(CommandSender отправитель, Command команда, String метка, String[] аргументы) {
        
        if (!(отправитель instanceof Player игрок)) {
            отправитель.sendMessage("§cЭту команду можно использовать только в игре!");
            return true;
        }
        
        if (!игрок.hasPermission("песчаныйданж.игрок")) {
            игрок.sendMessage("§cУ вас нет прав для использования этой команды!");
            return true;
        }
        
        // Телепортируем игрока в подземелье
        Location локацияПодземелья = плагин.получитьМенеджерПодземелья().получитьЛокациюПодземелья(игрок.getWorld());
        игрок.teleport(локацияПодземелья);
        
        // Создаем храм если его нет
        плагин.получитьМенеджерПодземелья().создатьХрам(локацияПодземелья);
        
        // Запускаем бурю
        плагин.получитьМенеджерПесчанойБури().запуститьБурю(локацияПодземелья);
        
        игрок.sendMessage("§6Вы телепортированы в Песчаный Данж! Древний храм где-то рядом...");
        
        return true;
    }
}
