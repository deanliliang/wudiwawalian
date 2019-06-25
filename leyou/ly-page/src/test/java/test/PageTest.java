package test;

import com.leyou.page.LyPageApplication;
import com.leyou.page.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ProjectName: leyou
 * @Package: test
 * @ClassName: Test01
 * @Author: Dean
 * @Description: page测试
 * @Date: 2019/6/25 0:47
 * @Version: 1.0
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyPageApplication.class)
public class PageTest {

    @Autowired
    private PageService pageService;

    @Test
    public void Test001() throws InterruptedException {
        Long[] arr = {96L, 114L, 124L, 125L, 175L};
        for (Long id : arr) {
            pageService.createHtmlItem(id);
            Thread.sleep(500);
        }
    }
}
