package just.plots.database;

import just.plots.JustPlots;
import just.plots.Plot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class PlotLoader implements Runnable {

    private final JustPlots plots;

    public PlotLoader(JustPlots plots) {
        this.plots = plots;

        plots.getServer().getScheduler().runTaskAsynchronously(plots, this);
    }

    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        plots.getLogger().info("Loading plots...");

        int counter = 0;

        try (PreparedStatement statement = JustPlots.getDatabase().prepareStatement("SELECT * FROM justplots_plots")) {
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                String world = results.getString("world");
                int x = results.getInt("x");
                int z = results.getInt("z");
                String owner = results.getString("owner");
                Timestamp creation = results.getTimestamp("creation");
                try {
                    new Plot(world, x, z, UUID.fromString(owner), creation.getTime());
                } catch (Exception e) {
                    plots.getLogger().warning("Could not load plot " + world + ";" + x + ";" + z);
                    e.printStackTrace();
                }

                if (++counter % 10000 == 0) {
                    plots.getLogger().info("Loading plots... (" + counter + ")");
                }
            }
        } catch (SQLException e) {
            plots.getLogger().severe("FAILED TO LOAD PLOTS");
            e.printStackTrace();
            return;
        }

        plots.getLogger().info("Loaded plots (took " + (System.currentTimeMillis() - timer) + "ms)");
    }
}
