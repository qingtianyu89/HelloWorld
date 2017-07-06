package com.test.action.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by pangming on 2017/6/29.
 */
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        System.out.println(new String(bytes));
        String rep = "get your request";
        ByteBuf buffer = Unpooled.copiedBuffer(rep.getBytes());
        ctx.write(buffer);
    }


}
