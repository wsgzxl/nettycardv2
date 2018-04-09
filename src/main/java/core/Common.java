package core;

/*
@author YHL
@qq: 1357098586
@version 创建时间：2018年4月7日 下午5:02:13 

int和bytes相互转换，方便处理大小端问题
c#大端在前，java小端在前

 */

public class Common {
	
	/*
	 * byte[]大端转小端
	 */
	public static int byteArrayToInt(byte[] b) {   
		return   b[0] & 0xFF |   
		            (b[1] & 0xFF) << 8 |   
		            (b[2] & 0xFF) << 16 |   
		            (b[3] & 0xFF) << 24;   
		}   
	
	   /*
	    * int转byte[] 转为大端
	    */
		public static byte[] intToByteArray(int a) {   
		return new byte[] {   
				(byte) (a & 0xFF),
			    (byte) ((a >> 8) & 0xFF),   
			    (byte) ((a >> 16) & 0xFF),      
		        (byte) ((a >> 24) & 0xFF),   
		    };   
		} 
}
