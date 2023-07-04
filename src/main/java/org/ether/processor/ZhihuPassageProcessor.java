package org.ether.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author IntoEther-7
 * @date 2023/7/4 13:44
 * @project ZhihuProject
 */
public class ZhihuZhuanlanProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private static final String AUTHOR_NAME = ".Post-Author > div:nth-child(1) > div:nth-child(1) > meta:nth-child(1)"; // content
    private static final String AUTHOR_IMG =
            ".Post-Author > div:nth-child(1) > div:nth-child(1) > meta:nth-child(2)"; // content
    private static final String AUTHOR_URL =
            ".Post-Author > div:nth-child(1) > div:nth-child(1) > meta:nth-child(3)"; // content
    public static final String ANS_CONTENT = ".Post-RichText";
    private static final String ANS_INFO = ".ContentItem-time";

    @Override
    public void process(Page page) {
        page.putField("author_name", page.getHtml().css(AUTHOR_NAME, "content").get());
        page.putField("author_img", page.getHtml().css(AUTHOR_IMG, "content").get());
        page.putField("author_url", page.getHtml().css(AUTHOR_URL, "content").get());

        // 文章内容处理
        Selectable content = page.getHtml().css(ANS_CONTENT);
        List<String> all = content.all();
        for (int i = 0; i < all.size(); i++) {
            // 如果涉及图像?
            String s = all.get(i);
            if (s.contains("<img")) {
                Pattern pattern = Pattern.compile("data-original=\\\"(.+)\\\" ");
                Matcher matcher = pattern.matcher(s);
                while (matcher.find()) {
                    all.set(i, "<img src=\"%s\\\">".formatted(matcher.group(1)));
                }
                continue;
            }
            all.set(i, s.replaceAll("<[^>]+>", "").replaceAll("<\\[^>]+>", ""));
        }
        page.putField("content", all);
        // 发布时间
        page.putField("ans_info", page.getHtml().css(ANS_INFO).get().replaceAll("<[^>]+>", "").replaceAll("<\\[^>]+>"
                , ""));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static String folderName(String url) {
        Pattern pattern = Pattern.compile("question/(\\d*)/answer/(\\d*)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return "D:\\webmagic\\www.zhihu.com\\Q%sA%s.md".formatted(matcher.group(1), matcher.group(2));
        } else {
            return "1";
        }
    }
}
