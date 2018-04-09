package handler;

import logic.Manager.RoomManager;
import logic.Manager.UserManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import Protos.addroomproto;
import core.BytesToObject;
import domain.GameRequest;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��2��6�� ����7:22:49 

���뷿��

 */

public class JoinRoomHandler implements GameHandler {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public void execute(GameRequest paramGameRequest) {
	    
		logger.info("JoinRoomHandler is startprocess!");
	    byte[] data=paramGameRequest.GetMessage().getData();
		addroomproto.LoginRoomRequest.Builder addroom=addroomproto.LoginRoomRequest.newBuilder();
		try {
			addroom.mergeFrom(data);
			int roomno=addroom.getRoomnumber();
			logger.info(addroom.getToken()+ "请求加入房间:"+roomno);
		    RoomManager.getInstance().JoinRoom(roomno, UserManager.getInstance().getUser(paramGameRequest.GetChannelContext()));
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		
	}
}
