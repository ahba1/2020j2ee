package com.ahba1.homework.websocket;

import com.ahba1.homework.HomeworkApplication;
import com.ahba1.homework.pojo.ChatMsg;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/msg/{username}")
public class WebSocketEndPoint {

    //当前连接的端点的会话
    private String username;
    private Session session;

    public final static ConcurrentHashMap<String, WebSocketEndPoint> webSocketSet = new ConcurrentHashMap<String, WebSocketEndPoint>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username){
        //保存会话对象
        this.username = username;
        this.session = session;
        //使用键值对缓存会话对象
        webSocketSet.put(username, this);
        //test
    }

    @OnClose
    public void onClose(){
        if (!"".equals(username)){
            webSocketSet.remove(username);
        }
    }

    @OnMessage
    public void onMessage(Session session, String msg){
        ChatMsg chatMsg = JSON.parseObject(msg, ChatMsg.class);
        HomeworkApplication.logger.info(msg);
        try {
            sendMsg(chatMsg);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMsg(ChatMsg msg) throws IOException {
        WebSocketEndPoint receiver = webSocketSet.get(msg.getReceiver());
        WebSocketEndPoint sender = webSocketSet.get(msg.getSender());
        if (receiver!=null){
            receiver.session.getBasicRemote().sendText(JSON.toJSONString(msg));
        }else{
            sender.session.getBasicRemote().sendText("用户不在线！");
        }
    }
}
