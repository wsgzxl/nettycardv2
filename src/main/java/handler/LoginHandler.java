package handler;

import net.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import constant.ResponseHandlerId;
import Protos.RichManProto;
import domain.GameRequest;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��23�� ����2:19:58 

��¼����� username+password

 */

public class LoginHandler implements GameHandler {

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public void execute(GameRequest paramGameRequest) {
		
		logger.info("LoginHandler is startprocess!");
		byte[] data=paramGameRequest.GetMessage().getData();
		RichManProto.LoginRequest.Builder login=RichManProto.LoginRequest.newBuilder();
		try {
			login.mergeFrom(data);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(login.getToken()+"--->>"+login.getName()+"--->>"+login.getSex());
	
		RichManProto.LoginResponse.Builder loginresult=RichManProto.LoginResponse.newBuilder();
		loginresult.setErrormessage("java return");
		loginresult.setSuccess(false);
		byte[] resultdata=loginresult.build().toByteArray();
	    ResponseMessage message=new ResponseMessage(ResponseHandlerId._addtoroom.getValue(),resultdata);
	    paramGameRequest.GetChannelContext().writeAndFlush(message);
		
	}
}
