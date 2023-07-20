package org.ether.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author IntoEther-7
 * @date 2023/7/4 15:15
 * @project ZhihuProject
 */
public class ImageTextHandler {
    /**
     * 处理图像和文本混合的数据
     */
    public static void htmlHandle(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            // 看是否是文字
            if (str.contains("<figure")) {
                // 处理图像
                Pattern pattern = Pattern.compile("data-original=\\\"(.+)\\\" data-actualsrc");
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    String url = matcher.group(1);
                    list.set(i, "<img>%s".formatted(url));
                }
            } else {
                list.set(i, str.replaceAll("<[^>]+>", "").replaceAll("<\\[^>]+>", ""));
            }
        }
    }
}
