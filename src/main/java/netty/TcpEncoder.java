package netty;

import core.Common;
import net.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;



public class TcpEncoder extends MessageToByteEncoder<ResponseMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponseMessage message, ByteBuf out)
			throws Exception {
		try
		{
		   byte[] data=message.toByteArray();
		   int length=data.length;
		   int len=length+4;
		   byte[] value=Common.intToByteArray(len);
		   out.writeByte(value[3]);
		   out.writeByte(value[2]);
		   out.writeByte(value[1]);
		   out.writeByte(value[0]);
		   value=Common.intToByteArray(message.getId());
		   out.writeByte(value[3]);
		   out.writeByte(value[2]);
		   out.writeByte(value[1]);
		   out.writeByte(value[0]);
		   out.writeBytes(data);
		}catch(Exception ex)
		{
		   
		}
	}
   
}
