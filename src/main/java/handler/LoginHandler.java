package handler;

import logic.Manager.UserManager;
import net.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import constant.ResponseHandlerId;
import core.GlobalData;
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
			e.printStackTrace();
		}
		System.out.println(login.getToken()+"--->>"+login.getName()+"--->>"+login.getSex());
		RichManProto.LoginResponse.Builder loginresult = RichManProto.LoginResponse
				.newBuilder();
		UserManager.getInstance().getUser(paramGameRequest.GetChannelContext()).setName(login.getName());
	    logger.info("玩家名为:"+login.getName()+"登录!");
		if (UserManager.getInstance().getUserCount() > GlobalData.maxcount) {
			loginresult.setErrormessage("人数已满");
			loginresult.setSuccess(false);
			logger.info("人数已满,不允许登录!");
		}
		else
		{
			loginresult.setErrormessage("");
			loginresult.setSuccess(false);
			logger.info("登录成功");
		}
		byte[] resultdata = loginresult.build().toByteArray();
		/*ResponseMessage message = new ResponseMessage(
				ResponseHandlerId._addtoroom.getValue(), resultdata);
		paramGameRequest.GetChannelContext().writeAndFlush(message);*/
		
	}
}
