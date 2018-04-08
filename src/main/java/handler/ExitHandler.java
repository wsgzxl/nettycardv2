package handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import Protos.RichManProto;
import domain.GameRequest;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��24�� ����6:30:27 

 */

public class ExitHandler implements GameHandler {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public void execute(GameRequest paramGameRequest) {
	    
		logger.info("ExitHandler is startprocess!");
		byte[] data=paramGameRequest.GetMessage().getData();
		RichManProto.LoginResponse.Builder response=RichManProto.LoginResponse.newBuilder();
		try {
			response.mergeFrom(data);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(response.getSuccess()+"--->"+response.getErrormessage());
	}

}
