package com.zhujs.configuration;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: jinshan.zhu
 * @Date: 2019/1/3 15:59
 */
@Configuration
public class DataSourceConfig {

    private static final String DATASOURCE01 = "test_0";

    private static final String DATASOURCE02 = "test_1";

    @Bean
    public DataSource dataSource() {
        return buildDataSource();
    }

    private DataSource getDataBase(String databaseName) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/" + databaseName + "?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        hikariDataSource.setJdbcUrl(jdbcUrl);
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("root");
        hikariDataSource.setMaximumPoolSize(100);
        hikariDataSource.setConnectionInitSql("set names utf8mb4");
        hikariDataSource.setMinimumIdle(50);
        hikariDataSource.setConnectionTimeout(3000);

        return hikariDataSource;
    }

    private DataSource buildDataSource() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DATASOURCE01, getDataBase(DATASOURCE01));
        dataSourceMap.put(DATASOURCE02, getDataBase(DATASOURCE02));

        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, DATASOURCE01);

        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(memberTableRule(dataSourceRule)))
                .build();

        return ShardingDataSourceFactory.createDataSource(shardingRule);
    }

    /**
     * user表分库分表策略
     * @param dataSourceRule
     * @return
     */
    private TableRule memberTableRule(DataSourceRule dataSourceRule) {
        TableRule memberTableRule = TableRule.builder("user")
                .actualTables(Arrays.asList("user_0", "user_1"))
                .dataSourceRule(dataSourceRule)
                .databaseShardingStrategy(new DatabaseShardingStrategy("id", new UserDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("age", new UserTableShardingAlgorithm()))
                .build();
        return memberTableRule;
    }

}
