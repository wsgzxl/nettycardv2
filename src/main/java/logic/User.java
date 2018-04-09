package logic;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constant.ResponseHandlerId;
import core.ObjectToBytes;
import domain.GameRequest;
import domain.MessageQueue;
import net.ResponseMessage;
import logic.Enums.SitDownAndUp;
import io.netty.channel.ChannelHandlerContext;


/*
 * 用户类
 * 
 */

public class User {
   
	 private Logger logger=LoggerFactory.getLogger(getClass());
	
	 private int token=-1;//token
	
	 public int getToken()
	 {
		 return token;
	 }
	 
	 private String name="";//玩家名
	 
	 public String getName()
	 {
		 return name;
	 }
	 
	 public void setName(String name)
	 {
		 this.name=name;
	 }
	 
	 private SitDownAndUp sitdown;
	 
	 /*
	  * 设置目前状态
	  */
	 public SitDownAndUp getSitDownState()
	 {
		 return sitdown;
	 }
	 
	 /*
	  * 设置状态
	  */
	 public void setSitDownState(SitDownAndUp state)
	 {
		 this.sitdown=state;
	 }
	 
	 private ChannelHandlerContext ctx;//ͨ通信管道 
	 
	 public ChannelHandlerContext getHandlerContext()
	 {
		 return ctx;
	 }
	 
	 /**
	  * 设置通信管道
	  * @param ctx
	  */
	 public void setChannelHandler(ChannelHandlerContext ctx){
		 this.ctx=ctx;
	 }
	
	 private int roomindex=0;//在房间的序号，意思是桌上的序号
	 
	 public void setRoomIndex(int roomindex)
	 {
	     this.roomindex=roomindex;
	 }
	 
	 public int getRoomIndex()
	 {
		 return this.roomindex;
	 }
	 
	 private Room room=null;//房间
	 
	 public Room getRoom()
	 {
		 return room;
	 }
	 
	 public void setRoom(Room room)
	 {
		 this.room=room;
	 }
	 
	 /*
	  * 将自己添加到房间
	  */
	 public void addToRoom(Room room)
	 {
		 room.addUser(this);
	 }
	 
	 /**
	  * 发送消息
	  * @param message
	  */
	 public void Send(ResponseMessage message)
	 {
		 ctx.writeAndFlush(message);
	 }
	 
}
