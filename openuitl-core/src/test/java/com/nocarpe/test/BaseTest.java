package com.nocarpe.test;

import com.google.gson.Gson;
import com.nocarpe.openutil.feign.EnableFeignUtilClients;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : yaoximing
 * @date : 2020/8/21
 * @description :
 **/

@RunWith(SpringRunner.class)
@ActiveProfiles("test1")
@SpringBootTest(classes = BaseTest.class)
@EnableFeignUtilClients(basePackages = "com.nocarpe.test")
@EnableAutoConfiguration
public class BaseTest {

    private Logger logger = LoggerFactory.getLogger(BaseTest.class);


    @Autowired
    private TestClient testClient;

    @Test
    public void test1() {
        List<BaseResp> resps = testClient.contributors("nocarpe", "repo");

        logger.info("======={}", new Gson().toJson(resps));
    }

}
