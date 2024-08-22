package com.jake.goods.test;

import com.jake.goods.Application;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chenjun
 * @since 2024/8/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsClientTest {

    @Autowired
    private ElasticsearchOperations operations;

    @Test
    public void testEsClient() throws IOException {
        String clusterName = operations.cluster().health().getClusterName();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>:" + clusterName);
    }

}
