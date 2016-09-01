package com.ami.service;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;

/**
 * Created by amit.khandelwal on 15/08/16.
 */

public interface IndexService {

    /**
     * get document by id.
     * @param id
     * @return GetResponse
     */
    GetResponse get(String indexName , String type,String id);


    /**
     * Index a document to a given idx.
     * @param indexName
     * @param id
     * @param document
     * @return IndexResponse
     */

    IndexResponse post(String indexName, String type, String id, String document);

    /**
     * Get all Documents from product index
     * //TODO :- Pagination impl.
     * @param
     * @return
     */
    SearchResponse getAll(String indexName);

    //TODO :- Free text query support.
}
