package com.velocitypowered.proxy.connection.client;

import com.velocitypowered.proxy.VelocityServer;
import com.velocitypowered.proxy.connection.MinecraftConnection;
import com.velocitypowered.proxy.protocol.StateRegistry;
import com.velocitypowered.proxy.protocol.packet.HandshakePacket;

public class C4O$HandshakeSessionHandler extends HandshakeSessionHandler {

    private final MinecraftConnection connection;
    private final VelocityServer server;
    
    public C4O$HandshakeSessionHandler(MinecraftConnection connection, VelocityServer server) {
        super(connection, server);
        this.connection = connection;
        this.server = server;
    }

    @Override
    public boolean handle(HandshakePacket handshake) {
        boolean result = super.handle(handshake);

        if (!(connection.getActiveSessionHandler() instanceof InitialLoginSessionHandler))
            return result;

        InitialInboundConnection ic = (InitialInboundConnection) connection.getAssociation();
        LoginInboundConnection lic = new LoginInboundConnection(ic);
        connection.setActiveSessionHandler(StateRegistry.LOGIN, new C4O$InitialLoginSessionHandler(server, connection, lic));

        return result;
    }

}
