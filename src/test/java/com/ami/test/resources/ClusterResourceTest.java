package com.ami.test.resources;

import com.ami.entities.Index;
import com.ami.resources.ClusterResource;
import com.ami.service.admin.ClusterService;
import com.ami.service.admin.ElasticSearchClusterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.elasticsearch.common.settings.Settings;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author: Amit Khandelwal
 * Date: 04/09/16
 */

public class ClusterResourceTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ClusterService clusterService = mock(ElasticSearchClusterServiceImpl.class);

    @ClassRule
    public static final ResourceTestRule rule = ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new ClusterResource(clusterService,objectMapper)).build();

    private static Index index;
    private static String idxAsString;
    @Before
    public void setup() throws Exception {
        index = new Index();
        index.setName("index-name");
        index.setType("type");
        idxAsString = objectMapper.writeValueAsString(index);
    }

    @Test
    public void testPostIndex() throws Exception{

        Response response = rule.getJerseyTest().target("/admin/cluster/indexes").request().post(Entity.entity(idxAsString, MediaType.APPLICATION_JSON));
        verify(clusterService,atLeastOnce()).createIndex(anyString(),anyString(),any(Settings.class));
        Assert.assertEquals(201,response.getStatus());
    }

}
