package com.ami.clients;

import io.dropwizard.lifecycle.Managed;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.client.transport.TransportClient;

/**
 *
 * <p>A manager for transport client. To be used in Dropwizard managed client</p>
 * @author Amit Khandelwal
 */

@Getter @Setter @NoArgsConstructor
public class TransportClientManager implements Managed {

	private TransportClient elasticSearchClient;

	public TransportClientManager(TransportClient elasticSearchClient){
		this.elasticSearchClient = elasticSearchClient;
	}

	public void start() throws Exception {

	}

	public void stop() throws Exception {

		if(elasticSearchClient != null) {
			elasticSearchClient.close();
		}
	}
}
