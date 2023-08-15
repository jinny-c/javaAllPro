package com.xwintop.xJavaFxTool;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class HttpToolService {
    public void setHttpToolController(HttpToolController httpToolController) {
        this.httpToolController = httpToolController;
    }

    private static final Logger log = LoggerFactory.getLogger(HttpToolService.class);

    private HttpToolController httpToolController;

    public HttpToolController getHttpToolController() {
        return this.httpToolController;
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    public void sendAction() throws Exception {
        String url = this.httpToolController.getUrlTextField().getText().trim();
        if (StringUtils.isEmpty(url)) {
            //TooltipUtil.showToast(");
            return;
        }
        Map<String, String> paramsMap = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        Map<String, String> cookieMap = new HashMap<>();
        if (this.httpToolController.getParamsDataCheckBox().isSelected()) {
            for (Map<String, String> map : (Iterable<Map<String, String>>) this.httpToolController.getParamsDatatableData()) {
                if (StringUtils.isNotEmpty(map.get("name")) && StringUtils.isNotEmpty(map.get("value"))) {
                    paramsMap.put(map.get("name"), map.get("value"));
                }
            }
        }
        if (this.httpToolController.getParamsHeaderCheckBox().isSelected()) {
            for (Map<String, String> map : (Iterable<Map<String, String>>) this.httpToolController.getParamsHeadertableData()) {
                if (StringUtils.isNotEmpty(map.get("name")) && StringUtils.isNotEmpty(map.get("value"))) {
                    headerMap.put(map.get("name"), map.get("value"));
                }
            }
        }
        if (this.httpToolController.getParamsCookieCheckBox().isSelected() && isNotEmpty(cookieMap)) {
            StringBuffer paramsCookieBuffer = new StringBuffer();
            for (Map<String, String> map : (Iterable<Map<String, String>>) this.httpToolController.getParamsCookietableData()) {
                if (StringUtils.isNotEmpty(map.get("name")) && StringUtils.isNotEmpty(map.get("value"))) {
                    cookieMap.put(map.get("name"), map.get("value"));
                    paramsCookieBuffer.append(map.get("name")).append("=").append(map.get("value")).append(";");
                }
            }
            paramsCookieBuffer.deleteCharAt(paramsCookieBuffer.length() - 1);
            headerMap.put("Cookie", paramsCookieBuffer.toString());
        }
        String methodString = this.httpToolController.getMethodChoiceBox().getValue();
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        if ("GET".equals(methodString)) {
            final StringBuffer paramsDataBuffer = new StringBuffer();
            //if (MapUtils.isNotEmpty(paramsMap)) {
            if (isNotEmpty(paramsMap)) {
                if (url.contains("?")) {
                    paramsDataBuffer.append("&");
                } else {
                    paramsDataBuffer.append("?");
                }
                paramsMap.forEach(new BiConsumer<String, String>() {
                    @Override
                    public void accept(String key, String value) {
                        paramsDataBuffer.append(key).append("=").append(value).append("&");
                    }
                });
                paramsDataBuffer.deleteCharAt(paramsDataBuffer.length() - 1);
            }
            url = url + paramsDataBuffer.toString();
            request = (new Request.Builder()).url(url).headers(Headers.of(headerMap)).build();
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map<String, String> map : (Iterable<Map<String, String>>) this.httpToolController.getParamsDatatableData()) {
                builder.add(map.get("name"), map.get("value"));
            }
            FormBody formBody = builder.build();
            if ("POST".equals(methodString)) {
                if (this.httpToolController.getParamsDataIsStringCheckBox().isSelected()) {
                    if (this.httpToolController.getParamsDataCheckBox().isSelected()) {
                        RequestBody rbody = RequestBody.create(MEDIA_TYPE_MARKDOWN, this.httpToolController.getParamsDataTextArea().getText());
                        request = (new Request.Builder()).url(url).post(rbody).build();
                    } else {
                        request = (new Request.Builder()).url(url).post((RequestBody) formBody).build();
                    }
                } else {
                    request = (new Request.Builder()).url(url).post((RequestBody) formBody).headers(Headers.of(headerMap)).build();
                }
            } else if ("HEAD".equals(methodString)) {
                request = (new Request.Builder()).url(url).head().headers(Headers.of(headerMap)).build();
            } else if ("PUT".equals(methodString)) {
                request = (new Request.Builder()).url(url).put((RequestBody) formBody).headers(Headers.of(headerMap)).build();
            } else if ("PATCH".equals(methodString)) {
                request = (new Request.Builder()).url(url).patch((RequestBody) formBody).headers(Headers.of(headerMap)).build();
            } else if ("DELETE".equals(methodString)) {
                request = (new Request.Builder()).url(url).delete((RequestBody) formBody).headers(Headers.of(headerMap)).build();
            }
        }
        Response response = client.newCall(request).execute();
        Headers headers = response.headers();
        StringBuffer headerStringBuffer = new StringBuffer();
        for (int i = 0; i < headers.size(); i++) {
            headerStringBuffer.append(headers.name(i)).append(":").append(headers.value(i)).append("\n");
        }
        this.httpToolController.getResponseHeaderTextArea().setText(headerStringBuffer.toString());
        this.httpToolController.getResponseBodyTextArea().setText(response.body().string());
        this.httpToolController.getResponseHtmlWebView().getEngine().load(url);
    }

    public void toBrowerAction() {
        if (StringUtils.isEmpty(this.httpToolController.getUrlTextField().getText())) {
            //TooltipUtil.showToast(");
            return;
        }
        try {
            HttpClientUtil.openBrowseURLThrowsException(this.httpToolController.getUrlTextField().getText());
        } catch (Exception e1) {
            //TooltipUtil.showToast("+ e1.getMessage());
            log.error(e1.getMessage());
        }
    }

    public HttpToolService(HttpToolController httpToolController) {
        this.httpToolController = httpToolController;
    }

    private boolean isNotEmpty(Map map) {
        boolean isEmpty = (map == null || map.isEmpty());
        return !isEmpty;
    }
}
