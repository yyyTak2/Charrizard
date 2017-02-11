package com.programmingwizzard.charrizard.bot.response.netty;

import com.programmingwizzard.charrizard.bot.managers.HTTPManager;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/*
 * @author ProgrammingWizzard
 * @date 10.02.2017
 */
public class HTTPServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext context, Object message) throws Exception {
        if (!(message instanceof HttpRequest)) {
            return;
        }
        HttpRequest request = (HttpRequest) message;
        if (is100ContinueExpected(request)) {
            context.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
        }
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(getResponse(request)));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        context.write(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private byte[] getResponse(HttpRequest request) {
        String uri = request.getUri();
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        if (uri.length() < 1 || "/favicon.ico".equals(uri)) {
            return new byte[0];
        }
        String args[] = uri.split("/");
        if (args.length < 1) {
            return new byte[0];
        }
        String api = args[0];
        if ("api".equals(api)) {
            return new byte[0];
        }
        args = Arrays.copyOfRange(args, 1, args.length);
        System.out.println("Http request: " + Arrays.toString(args).replace("\n", "\\n"));
        return HTTPManager.handleMessage(args);
    }

}
