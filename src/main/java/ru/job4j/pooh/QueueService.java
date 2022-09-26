package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private static final String POST = "POST";

    private static final String GET = "GET";

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String httpRequestType = req.getHttpRequestType();
        String sourceName = req.getSourceName();
        String param = req.getParam();
        String text = null;
        if (POST.equals(httpRequestType)) {
            map.putIfAbsent(sourceName, new ConcurrentLinkedQueue<>());
            map.get(sourceName).add(param);
        } else if (GET.equals(httpRequestType)) {
            ConcurrentLinkedQueue<String> tempQueue = map.getOrDefault(
                    sourceName, new ConcurrentLinkedQueue<>()
            );
            text = tempQueue.poll();
        }
        return new Resp(text == null ? "" : text,
                text == null ? "204" : "200");
    }
}