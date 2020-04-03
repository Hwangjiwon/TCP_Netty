import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	byte[] bytes = null;
	String message = null;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		bytes = new byte[100];

		int readByteCount = msg.toString().length();
		String input = ((ByteBuf) msg).toString(Charset.defaultCharset());
		char[] arr = input.toCharArray();
		int tmp = Integer.parseInt(String.valueOf(arr[15])) + 1;

		arr[15] = Character.forDigit(tmp, 10);
		String change = "";
		for (int i = 0; i < 30; i++)
			change += Character.toString(arr[i]);

		try {
			message = new String(bytes, 0, readByteCount, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("[Server : 데이터 받기 성공]: " + input);
		ByteBuf message = Unpooled.buffer(30);
		message.writeBytes(change.getBytes());

		ctx.write(message);
		System.out.println("[Server 전문에 +1 후 보내기]:" + (Object) change);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}
