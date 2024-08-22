package com.jake.goods.test;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.jake.goods.Application;
import com.jake.goods.entity.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.DocumentOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
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
    public void testEsClient() throws InterruptedException {
        List<IndexQuery> indexQueries = null;
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 10; j++) {
                indexQueries = new ArrayList<>();
                Product product = new Product();
                product.setId(IdUtil.fastUUID());
                product.setName(RandomUtil.randomString(16));
                product.setPrice(RandomUtil.randomDouble(11,999));
                product.setDescription(RandomUtil.randomString(33));
                product.setCategory(RandomUtil.randomString(8));
                product.setStock(RandomUtil.randomInt(1,99));
                IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(product.getId())
                    .withObject(product)
                    .build();
                indexQueries.add(indexQuery);
            }

            List<IndexedObjectInformation> informationList = operations.bulkIndex(indexQueries, Product.class);
            // 处理索引后的结果
            //informationList.forEach(System.out::println);
            TimeUnit.SECONDS.sleep(1);

        }
    }

}
