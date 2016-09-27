package com.ami.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by amit.khandelwal on 14/08/16.
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ElasticSearchConfiguration {

    private String[] hosts;

    @JsonProperty("default_no_of_shards")
    private int defaultNumberOfShardsPerIndex;

    @JsonProperty("default_no_of_replicas")
    private int defaultNumberOfReplicasOnIndex;


}
