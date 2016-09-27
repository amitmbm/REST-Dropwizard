package com.ami;

import com.ami.config.ElasticSearchConfiguration;
import com.ami.config.RedisCacheConfig;
import com.bendb.dropwizard.redis.JedisFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by amit.khandelwal on 14/08/16.
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ApplicationConfig extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("elasticSearch")
    private ElasticSearchConfiguration elasticSearchConfiguration = new ElasticSearchConfiguration();

    @JsonProperty("swagger")
    @NotNull
    private SwaggerBundleConfiguration swaggerBundleConfiguration = new SwaggerBundleConfiguration();

    @JsonProperty("cacheConfig")
    private RedisCacheConfig redisCacheConfig = new RedisCacheConfig();



}
