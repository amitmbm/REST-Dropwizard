package com.ami.config;

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
public class AppConfig extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("elasticSearch")
    private ElasticSearchConfiguration elasticSearchConfiguration;

    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration;
}
