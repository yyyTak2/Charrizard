package com.programmingwizzard.charrizard.bot.response.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/*
 * @author ProgrammingWizzard
 * @date 10.02.2017
 */
public class HTTPServerInit extends ChannelInitializer<SocketChannel> {

    private final ServerBootstrap bootstrap;

    public HTTPServerInit() {
        this.bootstrap = new ServerBootstrap();
    }

    public void start() {
        this.bootstrap.group(new NioEventLoopGroup()).channel(NioServerSocketChannel.class).childHandler(this);
        ChannelFuture future = bootstrap.bind(81).syncUninterruptibly();
        if (future.isSuccess()) {
            future.channel().closeFuture().syncUninterruptibly();
        } else {
            future.cause().printStackTrace();
        }
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HTTPServerHandler());
    }
}
