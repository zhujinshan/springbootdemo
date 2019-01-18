package com.zhujs.configuration;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Description: user数据库分库策略
 * @Author: jinshan.zhu
 * @Date: 2019/1/3 16:40
 */
public class UserDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Long> {

    @Override
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        for (String availableTargetName : availableTargetNames) {
            if (availableTargetName.endsWith(shardingValue.getValue() % 2 + "")) {
                return availableTargetName;
            }
        }
        throw new IllegalArgumentException("分库匹配失败");
    }

    @Override
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        Set<String> databaseSet = new LinkedHashSet<>();
        for (Long index : shardingValue.getValues()) {
            for (String targetName : availableTargetNames) {
                if (targetName.endsWith(index % 2 + "")) {
                    databaseSet.add(targetName);
                }
            }
        }
        return databaseSet;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        Set<String> databaseSet = new LinkedHashSet<>();
        for (Long index : shardingValue.getValues()) {
            for (String targetName : availableTargetNames) {
                if (targetName.endsWith(index % 2 + "")) {
                    databaseSet.add(targetName);
                }
            }
        }
        return databaseSet;
    }
}
