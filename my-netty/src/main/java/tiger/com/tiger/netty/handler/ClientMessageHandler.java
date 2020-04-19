package tiger.com.tiger.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import tiger.com.tiger.netty.common.MessageType;
import tiger.com.tiger.netty.common.UserState;
import tiger.com.tiger.netty.entity.CustomMessage;
import tiger.com.tiger.netty.entity.MessageFrame;
import tiger.com.tiger.netty.entity.User;
import tiger.com.tiger.netty.util.UserManager;

import java.util.List;

@Slf4j
public class ClientMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CustomMessage customMessage = (CustomMessage) msg;
        log.info("receive:" + customMessage.getBody());
        switch (customMessage.getType()) {
            case LOGIN:
                handleLoginMessage(ctx, customMessage);
                break;
            case SESSION:
                relayMessage(ctx, customMessage);
                break;
            case LOGOUT:
                handLogoutMessage(ctx, customMessage);
                break;
        }


    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("channel occur exception, close channel");
        ctx.close();
    }


    private void handleLoginMessage(ChannelHandlerContext ctx, CustomMessage customMessage) {
        log.info("user:{} login", customMessage.getFrom());
        String from = customMessage.getFrom();
        String password = customMessage.getBody();
        User user = new User(from, ctx.channel());
        UserManager.add(user);
        MessageFrame frame = new MessageFrame();
        frame.setTo(from.getBytes());
        frame.setFrom("server".getBytes());
        frame.setType((byte) (MessageType.STATUS.flag & 0xff));
        frame.setBody("200".getBytes());
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(frame.toBytes())); // 发送登录成功消息
        sendUserList();
    }

    private void sendUserList() {
        List<String> list = UserManager.getUserList();
        if (list.size() == 0) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String user : list) {
            builder.append(user + ",");
        }
        builder.replace(builder.length() - 1, builder.length(), "");
        MessageFrame frame = new MessageFrame();
        frame.setType((byte) MessageType.USER_LIST.flag);
        frame.setFrom("server".getBytes());
        frame.setBody(builder.toString().getBytes());
        for (String user : list) {
            User client = UserManager.get(user);
            frame.setTo(user.getBytes());
            if (client != null) {
                log.info("send user list to {}", user);
                client.send(frame.toBytes());
            }
        }
    }


    private void relayMessage(ChannelHandlerContext ctx, CustomMessage customMessage) {
        String to = customMessage.getTo().trim();
        User user = UserManager.get(to);
        if (user != null && user.getState() != UserState.LOGOUT) {
            MessageFrame frame = new MessageFrame();
            frame.setType((byte) customMessage.getType().flag);
            frame.setFrom(customMessage.getFrom().getBytes());
            frame.setTo(customMessage.getTo().getBytes());
            frame.setBody(customMessage.getBody().getBytes());
            user.send(frame.toBytes());
        } else {
            log.info("user:{} has logout", user.getId());
        }
    }

    private void handLogoutMessage(ChannelHandlerContext ctx, CustomMessage customMessage) {
        String user = customMessage.getFrom();
        UserManager.remove(user);
        log.info("user:{} logout", user);
        ctx.close();
    }
}
