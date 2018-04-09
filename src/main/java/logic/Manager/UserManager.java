package logic.Manager;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dispatcher.HandlerDispatcher;
import domain.GameRequest;
import domain.MessageQueue;
import net.ResponseMessage;
import logic.User;

/*
 *  用户管理
 */

public class UserManager {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private static UserManager _instance=new UserManager();//new
	
	private ConcurrentHashMap<Integer,User> players=new ConcurrentHashMap<Integer,User>();//�������
	
	private ConcurrentHashMap<ChannelHandlerContext,User> cus=new ConcurrentHashMap<ChannelHandlerContext,User>();//��ҵ�channelcontext,user
	
	private HandlerDispatcher handlerDispatcher;
	
	private UserManager()
	{
	
	}
	
	public static UserManager getInstance()
	{
		return _instance;
	}
	
	/**
	 * 返回玩家
	 * @return
	 */
	public Map<Integer,User> getUser(){
		return players;
	}
	
	/*
	 * 给单个用户发送消息
	 */
	public void sendMessage(User user,ResponseMessage message)
	{
		user.Send(message);
	}
	
	//给几个用户发消息
	public void sendUsers(User[] users,ResponseMessage message)
	{
		for(int i=0;i<users.length;i++)
		{
			users[i].Send(message);
		}
	}
	
	//广播消息
	public void sendAll(ResponseMessage message)
	{
		for(User user : players.values())
		{
	    	user.Send(message);
	    }
	}
	
	//添加用户
	public  void addUser(User user)
	{
		 players.put(user.hashCode(),user);
		 cus.put(user.getHandlerContext(),user);
	}
	
	//删除用户
	public  void removeUser(User user)
	{
		players.remove(user.hashCode());
		cus.remove(user.getHandlerContext());
	}
	
	/**
	 * 根据ctx查找用户
	 * @param ctx
	 * @return
	 */
	public User getUser(ChannelHandlerContext ctx)
	{
		return cus.get(ctx);
	}
	
	/*
	 * 返回用户数量
	 */
	public int getUserCount()
	{
		return players.size();
	}
	
	/*
	 * 设置消息分发器
	 */
	public void setHandlerDispatcher(HandlerDispatcher handlerDispatcher){
		this.handlerDispatcher=handlerDispatcher;
	}
	
    /*
     * 添加消息
     */
    
    public void addMessageQueue(GameRequest request){
       if(null!=this.handlerDispatcher){
    	   this.handlerDispatcher.addMessage(request);
       }
    }
    
    /*
     * 删除某个链接的所有消息
     */
    private void removeMessageQueue(ChannelHandlerContext channel)
    {
    	//TODO:删除消息
    }
    
    /*
     * 根据ctx删除用户
     */
    public void removeUser(ChannelHandlerContext ctx){
    	User user=cus.remove(ctx);
    	if(user!=null){
    		players.remove(user);
    	}
    }
    
}
