package netty;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.Common;
import net.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TcpDecoder extends ByteToMessageDecoder  {

	
	private  Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) {

       
		try
		{
		    int readableBytes=in.readableBytes();
		    System.out.println("readableBytes="+readableBytes);
			if(in.readableBytes()<4)
			{
				return;
			}
			in.markReaderIndex();
			byte[] data=new byte[4];
			in.readBytes(data);
			int totalLength=Common.byteArrayToInt(data);
			System.out.println("totalLength:"+totalLength);
			if(in.readableBytes()<totalLength)
			{
				in.resetReaderIndex();
				return;
			}
			
			byte[] dataid=new byte[4];
			in.readBytes(dataid);
			int requestId=Common.byteArrayToInt(dataid);
			System.out.println("requestId:"+requestId);
			int bodylength=totalLength-4;
			byte[] bodydata=new byte[bodylength];
			in.readBytes(bodydata);
			out.add(new RequestMessage(requestId,bodydata));
			
		}
		catch(Exception ex)
		{
			logger.error("�쳣��Ϣ:"+ex);
		}
		
	}
   
}
