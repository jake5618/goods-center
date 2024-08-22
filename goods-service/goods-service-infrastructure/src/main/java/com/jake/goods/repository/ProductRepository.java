package com.jake.goods.repository;

import com.jake.goods.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author chenjun
 * @since 2024/8/22
 */
public interface ProductRepository  extends ElasticsearchRepository<Product, String> {

}
