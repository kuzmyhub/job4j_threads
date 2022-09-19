package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private static final String POST = "POST";

    private static final String GET = "GET";

    private final ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> map
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String httpRequestType = req.getHttpRequestType();
        String sourceName = req.getSourceName();
        String param = req.getParam();
        String text = null;
        if (POST.equals(httpRequestType)) {
            ConcurrentHashMap<String,
                    ConcurrentLinkedQueue<String>> tempMap
                    = map.get(sourceName);
            if (tempMap != null) {
                for (ConcurrentLinkedQueue<String> t : tempMap.values()) {
                    t.add(param);
                }
            }
        } else if (GET.equals(httpRequestType)) {
            map.putIfAbsent(sourceName, new ConcurrentHashMap<>());
            ConcurrentHashMap<String,
                    ConcurrentLinkedQueue<String>> tempMap
                    = map.get(sourceName);
            if (tempMap != null) {
                tempMap.putIfAbsent(param, new ConcurrentLinkedQueue<>());
            }
            tempMap = map.get(sourceName);
            text = tempMap.get(param).poll();

        }
        return new Resp(text == null ? "" : text,
                "200");
    }
}