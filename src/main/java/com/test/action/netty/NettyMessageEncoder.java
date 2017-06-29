package com.test.action.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by pangming on 2017/5/12.
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException{
        this.marshallingEncoder = new MarshallingEncoder(null);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          NettyMessage msg, List<Object> list) throws Exception {
        if(msg == null || msg.getHeader() == null){
            throw new Exception("The encode message is null");
        }
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(msg.getHeader().getCrcCode());
        byteBuf.writeInt(msg.getHeader().getLength());
        byteBuf.writeLong(msg.getHeader().getSessionID());
        byteBuf.writeByte(msg.getHeader().getType());
        byteBuf.writeByte(msg.getHeader().getPriority());
        byteBuf.writeInt(msg.getHeader().getAttachment().size());

        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("utf-8");
            byteBuf.writeInt(keyArray.length);
            byteBuf.writeBytes(keyArray);
            value = param.getValue();
        }
    }
}
