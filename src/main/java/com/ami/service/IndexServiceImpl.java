package com.ami.service;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Created by amit.khandelwal on 15/08/16.
 */

@Getter @Setter @NoArgsConstructor
public class IndexServiceImpl implements IndexService {

    private TransportClient esclient;

    @Inject
    public IndexServiceImpl(TransportClient esclient) {
        this.esclient = esclient;
    }

    @Override
    public GetResponse get(String indexName, String type,String id) {
        GetResponse response = esclient.prepareGet(indexName,type,id).get();
        return response;
    }

    @Override
    public IndexResponse post(String indexName, String type, String id, String document) {
        IndexResponse response = esclient.prepareIndex(indexName, type,id)
                .setSource(document)
                .get();
        return response;
    }

    @Override
    public SearchResponse getAll(String indexName) {
        SearchResponse response = esclient.prepareSearch(indexName)
                .execute()
                .actionGet();
        return response;
    }
}
