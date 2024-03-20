package me.pesekjak.crypt4offline;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.proxy.VelocityServer;
import com.velocitypowered.proxy.connection.client.C4O$ServerChannelInitializer;
import com.velocitypowered.proxy.network.C4O$ServerChannelInitializerHolder;
import com.velocitypowered.proxy.network.ConnectionManager;
import org.slf4j.Logger;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;

@Plugin(
        id = "crypt4offline",
        name = "Crypt4Offline",
        version = "1.0.1",
        description = "Plugin that enables encryption for servers in offline mode",
        authors = "pesekjak"
)
public class Crypt4Offline {

    private final VelocityServer server;
    private ConnectionManager connectionManager;
    private File file;

    @Inject
    public Crypt4Offline(ProxyServer server, Logger logger) {
        this.server = (VelocityServer) server;

        if (server.getConfiguration().isOnlineMode()) {
            logger.warn("Server is running in online mode, no changes will be applied");
            return;
        }

        try {
            file = Path.of(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();
        } catch (Exception exception) {
            logger.error("Failed to access the plugin jar file");
            return;
        }

        try {
            injectClasses(
                    "com.velocitypowered.proxy.connection.client",
                    com.velocitypowered.proxy.connection.client.ClientConnectionPhases.class
            );
            injectClasses(
                    "com.velocitypowered.proxy.network",
                    com.velocitypowered.proxy.network.Connections.class
            );
        } catch (Exception exception) {
            logger.error("Failed to inject the connection classes");
            return;
        }

        try {
            connectionManager = getConnectionManager(server);
        } catch (Exception exception) {
            logger.error("Failed to access server's connection manager");
            return;
        }

        try {
            injectServerChannelHandler();
            if (!(connectionManager.serverChannelInitializer instanceof C4O$ServerChannelInitializerHolder))
                throw new RuntimeException();
        } catch (Exception exception) {
            logger.error("Failed to inject server channel initializer holder");
            return;
        }

        logger.info("Successfully injected Crypt4Offline handlers");
    }

    public boolean isEnabled() {
        return connectionManager != null && connectionManager.serverChannelInitializer.get() instanceof C4O$ServerChannelInitializer;
    }

    private void injectClasses(String basePackage, Class<?> using) throws Exception {
        List<byte[]> classes = ClassExtractor.getClasses(file, basePackage);
        for (byte[] data : classes)
            ClassDefiner.defineClassPrivatelyIn(using, data);
    }

    private ConnectionManager getConnectionManager(ProxyServer server) throws Exception {
        Field field = VelocityServer.class.getDeclaredField("cm");
        field.setAccessible(true);
        return (ConnectionManager) field.get(server);
    }

    private void injectServerChannelHandler() throws Exception {
        if (connectionManager == null) return;
        Field field = ConnectionManager.class.getField("serverChannelInitializer");
        field.setAccessible(true);
        field.set(connectionManager, new C4O$ServerChannelInitializerHolder(new C4O$ServerChannelInitializer(this.server)));
    }

}
