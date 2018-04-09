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
		   byte[] data=message.getData();
		   int length=data.length;
		   int len=length+8;
		   byte[] value=Common.intToByteArray(len);
		   out.writeBytes(value);
		   value=Common.intToByteArray(message.getId());
           out.writeBytes(value);
		   value=Common.intToByteArray(data.length);
		   out.writeBytes(value);
		   out.writeBytes(data);
		}catch(Exception ex)
		{
		   
		}
	}
   
}
