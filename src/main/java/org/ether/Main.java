package org.ether;

import org.ether.processor.ZhihuPassageProcessor;
import org.ether.zhihu.pipeline.MixPipeline;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 程序启动
        while (true) {
            try {
                // 获取发来的链接
                Scanner sc = new Scanner(System.in);
                String url = sc.nextLine();
                if (url.contains("www.zhihu.com/question") && url.contains("answer")) {
                    Spider.create(new org.ether.ZhihuQAProcessor()).addUrl(url).thread(3).addPipeline(new MixPipeline(org.ether.ZhihuQAProcessor.folderName(url))).run();
                } else if (url.contains("zhuanlan.zhihu.com")) {
                    Spider.create(new org.ether.ZhihuQAProcessor()).addUrl(url).thread(3).addPipeline(new MixPipeline(ZhihuPassageProcessor.folderName(url))).run();
                } else {
                    System.out.println("请输入知乎链接");
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }
}