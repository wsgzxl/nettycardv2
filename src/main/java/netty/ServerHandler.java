package netty;

import java.sql.Date;

import logic.LogicMain;
import logic.User;
import logic.Manager.UserManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import net.RequestMessage;
import net.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.Message;
import domain.GameRequest;
import domain.MessageQueue;

public class ServerHandler extends ChannelInboundHandlerAdapter{
	
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx){
		
		
		try
		{
		super.handlerAdded(ctx);
        
		//������ȹ���һ��user����
		User user=new User();
		user.setChannelHandler(ctx);
		UserManager.getInstance().addUser(user);
		
		
		logger.info("新进来一个连接:"+ctx.channel().id().toString());
		
		}catch(Exception ex)		
		{
		   logger.error("�쳣��Ϣ:"+ex);
	    }
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx)
	{
		try
		{
	    
		   super.handlerRemoved(ctx);
	       
		   //ɾ������ӵ���Ϣ����,���ǲ�ɾ���û�����Ϊ������û��Զ�����
		   //UserManager.getInstance().removeMessageQueue(ctx);
		   
		   //ɾ��
		   UserManager.getInstance().removeUser(ctx);
		   
		   logger.info("ɾ����һ������:"+ctx.channel().id().toString());
		
		}catch(Exception ex)		
		{
		   logger.error("�쳣��Ϣ:"+ex);
		}
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		try
		{
		
		RequestMessage message=(RequestMessage)msg;
		logger.info("reciver data:"+message.toString());	
		
		UserManager.getInstance().addMessageQueue(new GameRequest(ctx,message));
		
		
	/*	ResponseMessage m=new ResponseMessage(1000,new byte[]{0xc,0xd});
		ctx.writeAndFlush(m);*/
		
		/*ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
        ReferenceCountUtil.release(msg);//����
*/        
       
		/*String body = new String(req,"UTF-8");
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?
				new Date(System.currentTimeMillis()).toString():"BAD ORDER";
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);*/
		
	}catch(Exception ex)
	{
		logger.error("�쳣��Ϣ:"+ex);
	}
}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		try
		{
	
		logger.info(ctx.channel().id()+"�쳣�Ͽ�");
	    cause.printStackTrace();
		
	    ctx.close();
	 
		//ɾ������ӵ���Ϣ����,���ǲ�ɾ���û�����Ϊ������û��Զ�����
        //UserManager.getInstance().removeMessageQueue(ctx);
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}