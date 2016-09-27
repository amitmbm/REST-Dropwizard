package com.ami.guicemodules;

import com.ami.config.ElasticSearchConfiguration;
import com.ami.ApplicationConfig;
import com.ami.service.IndexService;
import com.ami.service.IndexServiceImpl;
import com.ami.service.admin.ElasticSearchClusterServiceImpl;
import com.ami.service.admin.ClusterService;
import com.ami.service.user.ProductService;
import com.ami.service.user.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amit khandelwal.
 *
 * <p>A guice module for all elastic search related services initialization.</p>
 */

@Getter @Setter @NoArgsConstructor
public class ElasticSearchModule extends AbstractModule {

	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchModule.class);
	private TransportClient elasticSearchClient;

	/* binding the interfaces to their implementations */
	@Override
	protected void configure() {
		bind(ClusterService.class).to(ElasticSearchClusterServiceImpl.class).in(Singleton.class);
		bind(IndexService.class).to(IndexServiceImpl.class).in(Singleton.class);
		bind(ProductService.class).to(ProductServiceImpl.class).in(Singleton.class);
	}

	/* provides the Elastic Search transport client */
	@Provides
	public TransportClient provideTransportClient(ApplicationConfig AppConfig){
		return elasticSearchClient;
	}

	/* provides the objectMapper */
	@Provides @Singleton
	public ObjectMapper provideObjectMapper(){
		return new ObjectMapper();
	}


	@Provides
	public ElasticSearchConfiguration providesESConfig(ApplicationConfig configuration){
		return configuration.getElasticSearchConfiguration();
	}

}
