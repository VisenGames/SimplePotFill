package games.visen.simplepotfill.commands;

import games.visen.simplepotfill.Main;
import games.visen.simplepotfill.utils.Config;
import games.visen.simplepotfill.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class PotFillCommand implements CommandExecutor {

    private final Main plugin;

    public PotFillCommand(Main instance) {
        plugin = instance;
        plugin.getCommand("potfill").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        Config config = Main.getPluginConfig();
        int cost = config.getInt("cost");
        Economy economy = Main.getEcon();
        if(cost > economy.getBalance(player)) {
            Utils.message(player, "&cYou do not have enough money to use this!");
            return true;
        }
        EconomyResponse response = economy.withdrawPlayer(player, cost);
        if(!response.transactionSuccess()) {
            System.out.println(Utils.color("&cError while trying to charge a player!"));
            return true;
        }
        PlayerInventory inventory = player.getInventory();
        Potion potion = new Potion(PotionType.INSTANT_HEAL, 1).splash();
        ItemStack item = potion.toItemStack(1);
        for(int i = 0; i < 36; i++) {
            inventory.addItem(item);
        }
        Utils.message(player, "&eYour inventory was filled, you have been charged: &c" + cost);
        return true;
    }
}
