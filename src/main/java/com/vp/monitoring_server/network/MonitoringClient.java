package com.vp.monitoring_server.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
public class MonitoringClient {

    private final String host;
    private final int port;
    private final EventLoopGroup group;
    private Channel channel;

    public MonitoringClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.group = new NioEventLoopGroup();
    }

    // Метод для инициализации и подключения к серверу
    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new MonitoringClientHandler());
                    }
                });

        // Подключаемся к серверу
        channel = bootstrap.connect(host, port).sync().channel();
    }

    // Метод для отправки запроса
    public void sendRequest(String metric) {
        if (channel != null && channel.isActive()) {
            String request = metric;  // Формируем запрос
            channel.writeAndFlush(Unpooled.copiedBuffer(request, CharsetUtil.UTF_8));
            System.out.println("Request sent: " + metric);
        } else {
            System.err.println("Channel is not active, request not sent.");
        }
    }

    // Метод для начала периодического опроса
    public void startPolling(String metric) {
        // Отправка запроса через 0 секунд после старта и с интервалом в 1 секунду
        group.scheduleAtFixedRate(() -> {
            sendRequest(metric);
        }, 0, 1, TimeUnit.SECONDS);  // Каждую секунду
    }

    // Метод для завершения работы клиента
    public void stop() throws InterruptedException {
        if (channel != null && channel.isActive()) {
            channel.close().sync();
        }
        group.shutdownGracefully().sync();
    }
}
