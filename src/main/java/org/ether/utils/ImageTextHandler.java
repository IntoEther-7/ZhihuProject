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
     * ����ͼ����ı���ϵ�����
     */
    public static void htmlHandle(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            // ���Ƿ�������
            if (str.contains("<figure")) {
                // ����ͼ��
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
