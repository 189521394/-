package org.example.webSocket;

import org.example.service.getData;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/uiWebSocket")
public class UIwebSocket {
    private static final CopyOnWriteArrayList<UIwebSocket> sockets = new CopyOnWriteArrayList<UIwebSocket>();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static volatile boolean pushStarted = false;
    private Session session;
    private getData getData = new getData();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sockets.add(this);
        System.out.println("在线数量：" + sockets.size());
        startPush();
    }

    @OnClose
    public void onClose() {
        sockets.remove(this);
        System.out.println("在线数量：" + sockets.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 客户端发来的消息暂不处理
    }

    private synchronized void startPush() {
        if (pushStarted) return;
        pushStarted = true;
        scheduler.scheduleWithFixedDelay(() -> {
            if (sockets.isEmpty()) return;
            String info = getData.getInfo();
            for (UIwebSocket socket : sockets) {
                try {
                    if (socket.session != null && socket.session.isOpen()) {
                        socket.session.getBasicRemote().sendText(info);
                    }
                } catch (IOException e) {
                    System.err.println("推送失败: " + e.getMessage());
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
