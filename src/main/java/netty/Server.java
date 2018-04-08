package netty;


import logic.LogicMain;
import net.Heartbeat;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture; 
import io.netty.channel.ChannelInitializer; 
import io.netty.channel.ChannelOption; 
import io.netty.channel.EventLoopGroup; 
import io.netty.channel.nio.NioEventLoopGroup; 
import io.netty.channel.socket.SocketChannel; 
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constant.RequestHandlerId;
import dispatcher.HandlerDispatcher;

public class Server{
   
    final Logger logger=LoggerFactory.getLogger(getClass());
   
    public static void main(String[] args) throws Exception {
    	
    	
    	Server server = new Server();
        server.start(9999);
        
    }
    
    public void start(int port)  throws Exception{
    	
    	logger.info("start server!");
    	LogicMain.getInstance();
    
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        		
        try {
        	
        	/*RichManProto.LoginRequest.Builder request=RichManProto.LoginRequest.newBuilder();
        	request.mergeFrom(new byte[]{0x8,(byte)0xd0,0xf,0x12,0x4,0x67,0x61,0x6d,0x65,0x1a,0xf,0x6c,0x61,0x6f,0x6c,0x6f,0x6e,0x67,0x40,0x31,0x36,0x33,0x2e,0x63,0x6f,0x6d});
        	System.out.println(request.getId());
        	System.out.println(request.getEmail());
        			
        	System.out.println(request.getName());*/
        	
        	/*request.setId(2000);
        	request.setName("game");
        	request.setEmail("laolong@163.com");
        	byte[] data=request.build().toByteArray();
        	String datastr="";
        	for(int i=0;i<data.length;i++)
        	{
        		//datastr+=Integer.toHexString(data[i]);
        		datastr+=Integer.toHexString(data[i]&0xFF);
        		datastr+=" ";
        	}
        	System.out.println(datastr);*/
        	
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                                	ch.pipeline().addLast(new TcpEncoder());
                                	ch.pipeline().addLast(new TcpDecoder());
                                	ch.pipeline().addLast(new ServerHandler());
                                	ch.pipeline().addLast("idleStateHandler",new IdleStateHandler(10,10,0));
                                	ch.pipeline().addLast("hearbeat",new Heartbeat());
                                	//ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                                	//ch.pipeline().addLast(new ProtobufDecoder(RichManProto.LoginRequest.getDefaultInstance()));
                                	//ch.pipeline().addLast(new ProtoBufServerHandler());
                                }
                            }).option(ChannelOption.SO_BACKLOG, 1024) //���ͻ���������Ϊ1000
             .childOption(ChannelOption.SO_KEEPALIVE, true)
             .childOption(ChannelOption.TCP_NODELAY,true);
          
            ChannelFuture f = b.bind(port).sync();
            logger.info("牛牛服务器启动...!");
            f.channel().closeFuture().sync();
         
        }
        finally {
        	
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}