package constant;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��23�� ����3:16:02 

请求登录消息ID，用来区别消息

 */

public enum RequestHandlerId {
   
	login(1),//登录游戏
	exit(2),//退出游戏
	createroom(3),//创建房间
	joinroom(4),//进入房间
	leaveroom(5),//离开房间
	sitdown(6),//坐下
	startgame(7); //开始游戏
	
	private int state=0;
	private RequestHandlerId(int value)
	{
		state=value;
	}
	public int getstate()
	{
		return state;
	}
}
