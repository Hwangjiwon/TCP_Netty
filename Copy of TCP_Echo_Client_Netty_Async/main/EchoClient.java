import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
 
public final class EchoClient {
 
  public static void main(String[] args) throws Exception{
    EventLoopGroup  group = new NioEventLoopGroup();
 
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)//1. 이벤트 루프그룹 설정(서버랑 다르게 1개만 - 연결된 채널이 하나만 존재)
        .channel(NioSocketChannel.class)//2. 채널의 종류설정(NIO 소켓채널로 설정)
        .handler(new ChannelInitializer<SocketChannel>() {//3.채널파이프 라인 설정에 일반 소캣채널 클래스 SocketChannel 설정
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new EchoClientHandler());
             
          }
        });
       // ChannelFuture f = b.connect("127.0.0.1",20000
      ChannelFuture f = b.connect("127.0.0.1",8889
    	       		).sync(); //4. 비동기 입출력 메소드 connect 호출
        //4.1 connect() : 메서드의 호출결과로 비동기 메서드의 처리 결과 확인,
        //4.2.ChannelFuture 객체의 sync 메서드는 ChannelFuture 객체의 요청이 완료될때가지 대기, 단, 요청이 실패하면 예외를 던진다.
         
        f.channel().closeFuture().sync();
    }
    finally {
      group.shutdownGracefully();
    }
     
  }
 
}
