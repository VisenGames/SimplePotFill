package games.visen.simplepotfill;

import games.visen.simplepotfill.commands.PotFillCommand;
import games.visen.simplepotfill.utils.Config;
import games.visen.simplepotfill.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    private static Economy econ = null;

    private static Config config;

    @Override
    public void onEnable() {
        instance = this;
        if(!setupEconomy()){
            System.out.println(Utils.color("[&4&lError&f] &eVault not found."));
            System.out.println(Utils.color("&4SimplePotFill shutting down!"));
            getServer().getPluginManager().disablePlugin(this);
        }
        config = new Config("config.yml");
        new PotFillCommand(this);
     }

    private boolean setupEconomy() {
        if(getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if(rsp == null) {
            return false;
        }

        econ = rsp.getProvider();

        return econ != null;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static Main getInstance() {
        return instance;
    }

    public static Config getPluginConfig() {
        return config;
    }
}
