package com.ami.service.admin;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;

/**
 *
 * <p> Admin api's for the search cluster.</p>
 * @author Amit Khandelwal.
 */


public interface ClusterService {

    CreateIndexResponse createIndex(String indexName, String type, Settings settings);

    DeleteIndexResponse deleteIndex(String indexName , Settings settings);

}
