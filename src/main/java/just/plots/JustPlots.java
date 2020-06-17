package just.plots;

import just.plots.converters.PlotSquaredConverter;
import just.plots.database.Database;
import just.plots.database.SQLiteDatabase;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class JustPlots extends JavaPlugin {

    private static Database database;

    private static HashMap<String, PlotWorld> plotWorlds = new HashMap<>();

    @Override
    public void onEnable() {

        database = new SQLiteDatabase(new File(this.getDataFolder(), "plots.db"));
        database.createTables();

        new PlotSquaredConverter(this);

    }

    @Override
    public void onDisable() {
        getLogger().info("Closing database connection");
        database.closeConnection();
    }

    public static PlotWorld getPlotWorld(World world) {
        return getPlotWorld(world.getName());
    }

    public static PlotWorld getPlotWorld(String world) {
        PlotWorld plotWorld = plotWorlds.get(world);

        if (plotWorld == null) {
            plotWorld = new PlotWorld(world);
            plotWorlds.put(world, plotWorld);
        }

        return plotWorld;
    }

    public static Plot getPlot(String world, int x, int z) {
        return getPlotWorld(world).getPlot(x, z);
    }

    public static Database getDatabase() {
        return database;
    }

    public static Plot createPlot(String world, int x, int z, UUID owner) {
        return createPlot(world, x, z, owner, System.currentTimeMillis());
    }

    public static Plot createPlot(String world, int x, int z, UUID owner, long creation) {
        Plot plot = new Plot(world, x, z, owner, creation);
        plot.createInDatabase();
        return plot;
    }

}
