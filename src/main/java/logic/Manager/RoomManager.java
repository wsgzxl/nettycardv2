package logic.Manager;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constant.ResponseHandlerId;
import core.ObjectToBytes;
import logic.Room;
import logic.User;
import logic.Enums.SitDownAndUp;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��25�� ����3:58:28 

���ӹ���

 */

public class RoomManager {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private  final int roomnostep=10;//ÿ�μ�10
	
	private AtomicInteger roomnoincreasing=new AtomicInteger(100000);//ԭ�Ӳ�������
	
	private static RoomManager _instance=new RoomManager();
	
	private  final Object lockobj=new Object();//��
	
	private RoomManager()
	{
		
	}
	
	public static RoomManager getInstance()
	{
		return _instance;
	}
	
	private ConcurrentHashMap<Integer,Room> rooms=new ConcurrentHashMap<Integer,Room>();
	
    /**
     * ��������
     * @return �����
     */
	public int createRoom()
	{
		int roomno=roomnoincreasing.addAndGet(roomnostep);
		Room room=new Room(roomno);
		rooms.put(roomno, room);
		return roomno;
	}
	
	/**
	 * 加入房间
	 * 
	 * @param roomid
	 *       
	 */
	public void JoinRoom(int roomid, User user) {
		
		if(roomid==-1 || user==null)
		{
			logger.info("请求登录消息: roomid:"+roomid+ "user:"+ user);
			return;
		}
		synchronized (lockobj) {
			if (rooms.containsKey(roomid)) {
				rooms.get(roomid).addUser(user);
				ResponseMessage message=new ResponseMessage(
					ResponseHandlerId._addtoroom.ordinal(),null);
				
			} else {
				ResponseMessage message = new ResponseMessage(
						ResponseHandlerId._nofindroom.ordinal(), null);
				logger.info("未找到房间:"+roomid);
				user.Send(message);
			}
		}
	}

	/**
	 * �뿪����
	 * @param roomid
	 * @param user
	 */
	public void leaveToRoom(int roomid,User user)
	{
		synchronized (lockobj) {
			if (rooms.containsKey(roomid)) {
				rooms.get(roomid).remoUser(user);
				
			} else {
				ResponseMessage message = new ResponseMessage(
						ResponseHandlerId._nofindroom.ordinal(), null);
				logger.info("δ�ҵ������:"+roomid);
				user.Send(message);
			}
		}
	}
	
	/*
	 * ��ɢ����
	 */
	public void dissolutionRoom(int roomid,User user) {
		synchronized (lockobj) {
			if (rooms.containsKey(roomid)) {
				rooms.remove(roomid);
			} else {
				logger.error("�쳣:δ�ҵ������" + roomid);
				ResponseMessage message = new ResponseMessage(
			    ResponseHandlerId._nofindroom.ordinal(), null);
				user.Send(message);
			}
		}
	}
	
}
