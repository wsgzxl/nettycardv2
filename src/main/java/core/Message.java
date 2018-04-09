package core;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.netty.buffer.ByteBuf;

/**
 * @author: LYW
 * @qq: 1357098586
 * @email: 1357098586@qq.com
 * 
 * 协议内容  包长度(4 bytes)+id(4 bytes)+data(byte[])
 * 
 * 收发消息都是Message
 */
public class Message {
	
	private int id; //消息ID
	private byte[] data;//数据内容
	
	public Message(int id,byte[] data){
		this.id = id;
		this.data = data;
	}
	
	public int getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public String toString()
	{
		byte[] data=getData();
		String message=String.valueOf(getId());
		message+=" ";
		for(int i=0;i<data.length;i++)
		{
			message+=Integer.toHexString(data[i]);
			message+=" ";
		}
		return message;
	}
}
