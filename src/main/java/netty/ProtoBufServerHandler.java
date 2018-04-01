package netty;

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
		  RichManProto.LoginRequest request=(RichManProto.LoginRequest)msg;
		  System.out.println(request.getId()+">"+request.getName()+"->"+request.getEmail());
	  }
	  
	  @Override
	  public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
		  cause.printStackTrace();
		  ctx.close();
	  }
}
