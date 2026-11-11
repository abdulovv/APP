package com.app.account_service.utils;

import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TemplateRenderer {
    private static final String TEMPLATES_PATH = "/templates/email/";
    private static final Map<String, String> CACHE = new ConcurrentHashMap<>();

    private TemplateRenderer() {
    }

    public static String render(String templateName, Map<String, String> values) {
        String html = CACHE.computeIfAbsent(templateName, TemplateRenderer::load);
        for (Map.Entry<String, String> entry : values.entrySet()) {
            html = html.replace("{{" + entry.getKey() + "}}", HtmlUtils.htmlEscape(entry.getValue(), "UTF-8"));
        }
        return html;
    }

    private static String load(String templateName) {
        try (InputStream in = TemplateRenderer.class.getResourceAsStream(TEMPLATES_PATH + templateName)) {
            if (in == null) {
                throw new IllegalStateException("Email template not found: " + templateName);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
