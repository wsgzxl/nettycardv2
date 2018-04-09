package handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

import logic.Enums.SitDownAndUp;
import logic.Manager.RoomManager;
import logic.Manager.UserManager;
import net.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import Protos.createroomproto;
import constant.ResponseHandlerId;
import core.ObjectToBytes;
import domain.GameRequest;
import domain.GameResponse;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��2��6�� ����3:23:07 

创建房间

 */

public class CreateRoomHandler implements GameHandler{
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public void execute(GameRequest paramGameRequest) {
	    
		logger.info("CreateRoom is startprocess!");
		
	 
		createroomproto.CreateRoomRequest.Builder createroomrequest=createroomproto.CreateRoomRequest.newBuilder();
		try {
			createroomrequest.mergeFrom(paramGameRequest.GetMessage().getData());
			//在这里可以判断token是否满足条件才执行
	        int roomid=RoomManager.getInstance().createRoom();
		    UserManager.getInstance().getUser(paramGameRequest.GetChannelContext()).setSitDownState(SitDownAndUp.down);
		  
		    createroomproto.CreateRoomResponse.Builder createroomresponse=createroomproto.CreateRoomResponse.newBuilder();
		    createroomresponse.setRoomid(roomid);
		    createroomresponse.setErrormessage("");
		    createroomresponse.setSuccess(true);
		    byte[] data=createroomresponse.build().toByteArray();
		    String message="";
		    for(int i=0;i<data.length;i++)
		    {
		    	message+=String.format("%02x", data[i]);
		    	message+="  ";
		    }
		    logger.info(message);
			ResponseMessage responsemessage=new ResponseMessage(ResponseHandlerId._createroom.getState(),data);
			paramGameRequest.GetChannelContext().writeAndFlush(responsemessage);
		} catch (InvalidProtocolBufferException e) {
		
			e.printStackTrace();
		}
		
		
	
	}


}
