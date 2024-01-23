package com.velocitypowered.proxy.connection.client;

import com.velocitypowered.proxy.VelocityServer;
import com.velocitypowered.proxy.connection.MinecraftConnection;
import com.velocitypowered.proxy.network.Connections;
import com.velocitypowered.proxy.network.ServerChannelInitializer;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import com.velocitypowered.proxy.protocol.StateRegistry;
import com.velocitypowered.proxy.protocol.netty.*;
import io.netty.channel.Channel;
import io.netty.handler.codec.haproxy.HAProxyMessageDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

import static com.velocitypowered.proxy.network.Connections.*;

public class C4O$ServerChannelInitializer extends ServerChannelInitializer {

    private final VelocityServer server;

    public C4O$ServerChannelInitializer(final VelocityServer server) {
        super(server);
        this.server = server;
    }

    @Override
    protected void initChannel(final Channel ch) {
        ch.pipeline()
                .addLast(LEGACY_PING_DECODER, new LegacyPingDecoder())
                .addLast(FRAME_DECODER, new MinecraftVarintFrameDecoder())
                .addLast(READ_TIMEOUT, new ReadTimeoutHandler(this.server.getConfiguration().getReadTimeout(), TimeUnit.MILLISECONDS))
                .addLast(LEGACY_PING_ENCODER, LegacyPingEncoder.INSTANCE)
                .addLast(FRAME_ENCODER, MinecraftVarintLengthEncoder.INSTANCE)
                .addLast(MINECRAFT_DECODER, new MinecraftDecoder(ProtocolUtils.Direction.SERVERBOUND))
                .addLast(MINECRAFT_ENCODER, new MinecraftEncoder(ProtocolUtils.Direction.CLIENTBOUND));

        final MinecraftConnection connection = new MinecraftConnection(ch, this.server);
        connection.setActiveSessionHandler(StateRegistry.HANDSHAKE, new C4O$HandshakeSessionHandler(connection, this.server));
        ch.pipeline().addLast(Connections.HANDLER, connection);

        if (this.server.getConfiguration().isProxyProtocol())
            ch.pipeline().addFirst(new HAProxyMessageDecoder());
    }

}
