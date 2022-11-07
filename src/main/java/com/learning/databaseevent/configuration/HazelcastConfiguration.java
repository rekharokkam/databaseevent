//https://reflectoring.io/spring-boot-hazelcast/

package com.learning.databaseevent.configuration;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config cacheConfig(){
        EvictionConfig evictionConfig = new EvictionConfig()
            .setEvictionPolicy(EvictionPolicy.NONE)
            .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
            .setSize(10000);

        MapConfig mapConfig = new MapConfig()
            .setName("product-cache")
            .setTimeToLiveSeconds(0)
            .setMaxIdleSeconds(0)
            .setEvictionConfig(evictionConfig);

        return new Config()
                .setInstanceName("hazel-instance")
                .addMapConfig(mapConfig);
    }
}
