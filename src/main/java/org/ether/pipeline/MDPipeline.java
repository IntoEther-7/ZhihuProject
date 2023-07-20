package org.ether;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author IntoEther-7
 * @date 2023/6/30 21:03
 * @project ZhihuProject
 */
public class MDPipeline implements Pipeline {

    private File file;
    private File folder;

    public MDPipeline(String path) {
        file = new File(path + ".md");
        folder = new File(file.getParent());

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

        // 获取数据
        Map<String, Object> map = resultItems.getAll();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
            /**
             * author_name
             * author_img
             * author_url
             * content
             * ans_info
             */
            writer.write("%s: [%s](%s)\n".formatted("作者名称", map.get("author_name"), map.get("author_url")));
            writer.write("%s: <img src=\"%s\"/>\n".formatted("作者头像", map.get("author_img")));
            for (String s : (List<String>) map.get("content")) {
                if (s.startsWith("<img>")) {
                    writer.write(
                            "<img src=\"%s\"/>".formatted(s.substring(5))
                    );
                } else {
                    writer.write((s + "\n"));
                }
            }
            writer.write(((String) map.get("ans_info")));
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
