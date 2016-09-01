package com.ami;

import com.ami.clients.TransportClientManager;
import com.ami.config.AppConfig;
import com.ami.guicemodules.ElasticSearchModule;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by amit.khandelwal on 14/08/16.
 */
public class ElasticSearchDropWizardApp extends Application<AppConfig>{

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDropWizardApp.class);

    private ElasticSearchModule elasticSearchModule;


    /**
     * All guice modules get added here
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {

        elasticSearchModule = new ElasticSearchModule();
        GuiceBundle<AppConfig> guiceBundle = GuiceBundle.<AppConfig>newBuilder()
                .addModule(elasticSearchModule).enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(AppConfig.class).build(Stage.DEVELOPMENT);
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new SwaggerBundle<AppConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfig configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
    }

    /**
     * <p>Main calls the run initialization of all dependencies start here.
     *  Any resources that need to be managed along with the lifecycle of the application also will go here</p>
     *
     * @param appConfig
     * @param environment
     * @throws Exception
     */
    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        TransportClient client = TransportClient.builder().settings(Settings.settingsBuilder()
                .put("cluster.name", "esonaws")
                .put("client.transport.ping_timeout", 20, TimeUnit.SECONDS)).build();
        for(String config: appConfig.getElasticSearchConfiguration().getHosts()){
            String host = config.split(":")[0];
            String port = config.split(":")[1];
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),
                    Integer.parseInt(port)));
        }
        elasticSearchModule.setElasticSearchClient(client);
        try {
            TransportClientManager manager = new TransportClientManager(client);
            environment.lifecycle().manage(manager);
        } catch (Exception e) {
            logger.error("Error in initializing Elastic Search elasticSearchClient",e);
            throw new RuntimeException("Unable to connect to elastic Search");
        }
    }

    public static void main(String[] args) throws Exception {
        new ElasticSearchDropWizardApp().run(args);
    }


    @Override
    public String getName() {
        return "catalog";
    }
}
