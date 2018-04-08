package netty;

import java.nio.ByteBuffer;

import core.Common;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
@author YHL
@qq: 1357098586
@version 创建时间：2018年4月1日 上午2:00:23 

 */

public class ProtoBufServerHandler extends ChannelInboundHandlerAdapter 
{

	  public void channelActive(ChannelHandlerContext ctx)
	  {
		  System.out.println("ctx is :"+ctx.channel().id());
	  }
	
	  @Override
	  public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
		  /*RichManProto.LoginRequest request=(RichManProto.LoginRequest)msg;
		  System.out.println(request.getId()+">"+request.getName()+"->"+request.getEmail());
		  
		  RichManProto.LoginResponse.Builder response=RichManProto.LoginResponse.newBuilder();
		  response.setId(66666);
		  
		  ByteBuf buff=Unpooled.buffer();
		  byte[] buffer=response.build().toByteArray();
		 
		  buff.writeByte((byte)(buffer.length+4));
		  buff.writeByte(0);
		 
		  byte[] d1=Common.int2Byte(buffer.length);
		  buff.writeByte(d1[3]);
		  buff.writeByte(d1[2]);
		  buff.writeByte(d1[1]);
		  buff.writeByte(d1[0]);
		  
		
		  buff.writeBytes(buffer);
		  ctx.writeAndFlush(buff);*/
		  
	  }
	  
	  @Override
	  public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
		  cause.printStackTrace();
		  ctx.close();
	  }
}
