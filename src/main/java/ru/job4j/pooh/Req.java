package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode,
               String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] lines = content.split(System.lineSeparator());
        String[] firstLine = lines[0].split(" ");
        String type = firstLine[0];
        String[] reqParams = firstLine[1].split("/");
        String pooh = reqParams[1];
        String source = reqParams[2];
        String par;
        if (reqParams.length > 3) {
            par = reqParams[3];
        } else {
            par = lines[lines.length - 1];
            if (par.equals("Accept: */*")) {
                par = "";
            }
        }

        return new Req(type, pooh, source, par);
    }

    public String getHttpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
