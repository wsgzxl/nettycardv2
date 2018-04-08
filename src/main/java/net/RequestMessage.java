package net;

import core.Message;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��21�� ����9:00:02 

�ͻ��˵���Ϣ

 */

public class RequestMessage extends Message {

	public RequestMessage(int id, byte[] data) {
		super(id, data);
		
	}

    public int getId()
    {
    	return super.getId();
    }
    
    public byte[] getData()
    {
    	return super.getData();
    }
}
