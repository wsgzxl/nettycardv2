package logic;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import logic.Enums.SitDownAndUp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ResponseMessage;


/*
 * ������
 */

public class Room {
   
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private final int maxusers=8;//8����λ
	
	private ConcurrentHashMap<Integer,User> users=new ConcurrentHashMap<Integer,User>(); //�����ϵ�����
	
	private Integer roomno=-1; //����
	
	private Vector roomindexno=new Vector();//��λ����
	
	private ReentrantLock lockobj=new ReentrantLock();//��
	
	private boolean isstartgame=false;//�Ƿ�ʼ��Ϸ
	
	public Room(int roomindex)
	{
		for(int i=0;i<maxusers;i++)
		{
	    	roomindexno.add(i);
		}
		setRoomNo(roomindex);
		isstartgame=false;
	}
	
	/**
	 * ���÷����
	 * @param roomno
	 */
	public void setRoomNo(int roomno)
	{
		this.roomno=roomno;
	}
	
	/*
	 * ���ط����
	 */
	public Integer getRoomNo()
	{
		return roomno;
	}
	
	/*
	 * ����ĳ����
	 */
	public void sendToUser(User user,ResponseMessage message)
	{
	    user.Send(message);
	}
	
	/*
	 * ����һЩ��
	 */
	public void sendToUsers(User[] users,ResponseMessage message)
	{
		for(int i=0;i<users.length;i++)
		{
			users[i].Send(message);
		}
	}
	
	/*
	 * ���͸���������
	 */
	public void sendAll(ResponseMessage message)
	{
		for(User user:users.values())
		{
			user.Send(message);
		}
	}
	
	/**
	 * �����ҵ�����
	 * @param user
	 */
	public void addUser(User user)
	{
		if(users.size()>maxusers)
		{
			logger.info("������������");
			
			return;
		}
		
		lockobj.lock();
		{
		  int lastindex=roomindexno.size()-1;
		 
		  int roomindex=(int)roomindexno.get(lastindex);
		  user.setRoomIndex(roomindex);
		  roomindexno.remove(lastindex);
		}
		lockobj.unlock();

		users.put(user.hashCode(), user);
	    
		for(int i=0;i<users.size();i++)
		{
			for(int j=0;j<users.size();j++)
			{
		    	//users.get(j).Send(users.get(i).getBeforeGameMessage());
			}
		}
		
	}
	
	/**
	 * �뿪����
	 * @param user
	 */
	public void remoUser(User user)
	{
		
		users.remove(users.hashCode());
		
		lockobj.lock();
		{
	    	//������λ��
		    roomindexno.add(user.getRoomIndex());
		}
		
		lockobj.unlock();
		
		user.setSitDownState(SitDownAndUp.up);//����״̬Ϊվ��
		
		
	}

    /**
     * ��ʼ��Ϸ
     */
	public void startGame()
	{
		isstartgame=true;
	}
	
}
