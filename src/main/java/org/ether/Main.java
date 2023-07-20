package org.ether;

import org.ether.processor.ZhihuPassageProcessor;
import org.ether.zhihu.pipeline.MixPipeline;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ��������
        while (true) {
            try {
                // ��ȡ����������
                Scanner sc = new Scanner(System.in);
                String url = sc.nextLine();
                if (url.contains("www.zhihu.com/question") && url.contains("answer")) {
                    Spider.create(new org.ether.ZhihuQAProcessor()).addUrl(url).thread(3).addPipeline(new MixPipeline(org.ether.ZhihuQAProcessor.folderName(url))).run();
                } else if (url.contains("zhuanlan.zhihu.com")) {
                    Spider.create(new org.ether.ZhihuQAProcessor()).addUrl(url).thread(3).addPipeline(new MixPipeline(ZhihuPassageProcessor.folderName(url))).run();
                } else {
                    System.out.println("������֪������");
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }
}