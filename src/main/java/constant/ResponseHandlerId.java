package constant;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��2��6�� ����3:51:13 

�ط���ͻ��˵���Ϣ

 */

public enum ResponseHandlerId {

     _createroom(128),//创建房间
	 _nofindroom(129),//未找到房间
     _addtoroom(130),//进入房间
     _leavetoroom(131), //离开房间
     _roomfull(132),// 房间人数已满
     _loginresult(133);//登录结果返回
     
     private final int value;
     
     private ResponseHandlerId(int value)
     {
    	 this.value=value;
     }
     
     public int getValue()
     {
    	 return value;
     }
}
