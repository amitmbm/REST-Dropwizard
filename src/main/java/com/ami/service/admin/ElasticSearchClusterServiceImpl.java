package com.ami.service.admin;

import com.ami.config.ElasticSearchConfiguration;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

import java.util.Map;

/**
 * Created by amit.khandelwal on 14/08/16.
 */

@Getter @Setter @NoArgsConstructor
public class ElasticSearchClusterServiceImpl implements ClusterService {

    private TransportClient esclient;
    private ElasticSearchConfiguration elasticSearchConfiguration;

    @Inject
    public ElasticSearchClusterServiceImpl(TransportClient esclient, ElasticSearchConfiguration elasticSearchConfiguration) {
        this.esclient = esclient;
        this.elasticSearchConfiguration = elasticSearchConfiguration;
    }

    @Override
    public CreateIndexResponse createIndex(String indexName, String type, Settings settings) {
        CreateIndexRequestBuilder builder = esclient.admin().indices().prepareCreate(indexName);

        Settings.Builder settingsBuilder = Settings.builder().put("index.number_of_shards", elasticSearchConfiguration.getDefaultNumberOfShardsPerIndex())
                .put("index.number_of_replicas", elasticSearchConfiguration.getDefaultNumberOfReplicasOnIndex())
                .put("type", type);
        if (settings != null) {
            for (Map.Entry<String, String> entry : settings.getAsMap().entrySet()) {
                settingsBuilder.put(entry.getKey(), entry.getValue());
            }
        }
        return builder.setSettings(settingsBuilder.build()).get();
    }

    @Override
    /**
     * TODO :- test the method.
     */
    public DeleteIndexResponse deleteIndex(String indexName, Settings settings) {
        return esclient.admin().indices().delete(new DeleteIndexRequest(indexName)).actionGet();

    }

}
