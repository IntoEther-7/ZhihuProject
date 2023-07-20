package org.ether.pipeline;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Font;
import com.aspose.words.ParagraphFormat;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

/**
 * @author IntoEther-7
 * @date 2023/7/4 14:50
 * @project ZhihuProject
 */
public class WordPipeline implements Pipeline {
    private final File file;
    private final File folder;
    private final File png;

    public WordPipeline(String path) {
        String suffix = ".docx";
        file = new File(path + suffix);
        folder = new File(file.getParent());


        png = new File(path + ".png");
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        // 创建文件夹
        try {
            if (!folder.exists()) {
                folder.mkdirs();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);

            // 设置字体
            Font font = builder.getFont();
            font.setSize(12);
            font.setName("等线");
            font.setColor(Color.BLACK);
            font.setBold(false);

            // 设置段落格式
            ParagraphFormat paragraphFormat = builder.getParagraphFormat();
            paragraphFormat.setAlignment(0);

            Map<String, Object> map = resultItems.getAll();
            builder.writeln("%s: [%s](%s)".formatted("作者名称", map.get("author_name"), map.get("author_url")));
            builder.write("%s:".formatted("作者头像"));
            builder.insertImage(new DataInputStream(new URL((String) map.get("author_img")).openStream()));
            builder.writeln();

            builder.writeln();

            for (String s : (List<String>) map.get("content")) {
                if (s.startsWith("<img>")) {
                    String url = s.substring(5);
                    builder.insertImage(new DataInputStream(new URL(url).openStream()));
                } else {
                    builder.writeln(s);
                }
            }
            builder.writeln(((String) map.get("ans_info")));

            doc.save(file.getPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
