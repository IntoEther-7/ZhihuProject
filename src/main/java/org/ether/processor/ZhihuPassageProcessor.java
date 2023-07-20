package org.ether.processor;

import org.ether.ZhihuQAProcessor;
import org.ether.utils.ImageTextHandler;
import org.ether.zhihu.pipeline.MixPipeline;
import org.junit.jupiter.api.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author IntoEther-7
 * @date 2023/7/4 13:44
 * @project ZhihuProject
 */
public class ZhihuPassageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private static final String AUTHOR_NAME = ".Post-Author .AuthorInfo > meta:nth-child(1)"; // content
    private static final String AUTHOR_IMG = ".Post-Author .AuthorInfo > meta:nth-child(2)"; // content
    private static final String AUTHOR_URL = ".Post-Author .AuthorInfo > meta:nth-child(3)"; // content
    public static final String ANS_CONTENT =
            ".Post-Main .Post-RichTextContainer .RichText.ztext.Post-RichText h1, " + "h2, h3, h4, h5, h6, p, figure";
    private static final String ANS_INFO = ".ContentItem-time";

    @Override
    public void process(Page page) {
        page.putField("author_name", page.getHtml().css(AUTHOR_NAME, "content").get());
        page.putField("author_img", page.getHtml().css(AUTHOR_IMG, "content").get());
        page.putField("author_url", page.getHtml().css(AUTHOR_URL, "content").get());

        // 文章内容处理
        Selectable content = page.getHtml().css(ANS_CONTENT);
        List<String> all = content.all();
        ImageTextHandler.htmlHandle(all);
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
        Pattern pattern = Pattern.compile("https://zhuanlan.zhihu.com/p/(\\d*)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return "D:\\webmagic\\www.zhihu.com\\P%s".formatted(matcher.group(1));
        } else {
            return "1";
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String url = sc.nextLine();

        Spider.create(new ZhihuQAProcessor()).addUrl(url).thread(3).addPipeline(new MixPipeline(folderName(url))).run();
    }

    @Test
    public void test() {
        String url = "https://zhuanlan.zhihu.com/p/636669984";

        Spider.create(new ZhihuPassageProcessor()).addUrl(url).thread(3).addPipeline(new MixPipeline(folderName(url))).run();
    }
}
