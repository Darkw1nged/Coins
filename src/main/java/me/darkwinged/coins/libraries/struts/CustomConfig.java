package me.darkwinged.coins.libraries.struts;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class CustomConfig {

    private YamlConfiguration customConfig;
    private File customConfigFile;
    private final Plugin plugin;
    private final String configName;
    private final String path;
    private final boolean isResource;

    public CustomConfig(Plugin plugin, String configName, boolean isResource) {
        this.plugin = plugin;
        this.configName = configName;
        this.isResource = isResource;
        path = "";
        customConfigFile = new File(plugin.getDataFolder() + "/" + path, configName + ".yml");
    }

    public CustomConfig(Plugin plugin, String configName, String path) {
        this.plugin = plugin;
        this.configName = configName;
        this.path = path;
        isResource = false;
        customConfigFile = new File(plugin.getDataFolder() + "/" + path, configName + ".yml");
    }

    public void reloadConfig() {
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        if (isResource) {
            Reader defConfigStream = null;
            try {
                defConfigStream = new InputStreamReader(plugin.getResource(configName + ".yml"), "UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                customConfig.setDefaults(defConfig);
            }
        }
    }

    public YamlConfiguration getConfig() {
        if (customConfig == null)
            reloadConfig();
        return customConfig;
    }

    public void saveConfig() {
        try {
            customConfig.save(customConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder() + "/" + path, configName + ".yml");
        }
        if (!customConfigFile.exists()) {
            plugin.saveResource(configName + ".yml", false);
        }
    }

    public File getCustomConfigFile() {
        return customConfigFile;
    }

}