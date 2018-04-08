package domain;

import java.util.Map;

import handler.GameHandler;
import logic.LogicMain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 @author YHL
 @qq: 1357098586
 @version ����ʱ�䣺2018��1��23�� ����12:59:20 

 */

public class MessageWorker implements Runnable {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private MessageQueue messageQueue;
	private GameRequest request;

	public MessageWorker(MessageQueue messageQueue) {
		messageQueue.setRunning(true);
		this.messageQueue = messageQueue;
		this.request = (GameRequest) messageQueue.getRequestQueue().poll();
	}

	@Override
	public void run() {
		try {
			handleMessageQueue();
		} catch (Exception ex) {
			logger.error("�쳣��Ϣ:" + ex);
		} finally {
			this.messageQueue.setRunning(false);
		}
	}

	/*
	 * 
	 */
	private void handleMessageQueue() {
		int messageId = this.request.getRequestId();
		Map<Integer, GameHandler> maphandlers = LogicMain.getInstance()
				.getDispatcher().getHandlerMap();
		GameHandler handler = maphandlers.get(messageId);
		if (handler != null) {
			handler.execute(this.request);
		} else {
			logger.info("未找到处理handler!");
		}
	}

}
