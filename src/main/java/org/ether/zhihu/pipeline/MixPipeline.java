package org.ether.zhihu.pipeline;

import org.ether.MDPipeline;
import org.ether.pipeline.WordPipeline;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author IntoEther-7
 * @date 2023/7/4 17:26
 * @project ZhihuProject
 */
public class MixPipeline implements Pipeline {
    String path;

    public MixPipeline(String path) {
        this.path = path;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        new MDPipeline(path).process(resultItems, task);
        new WordPipeline(path).process(resultItems, task);
    }
}
