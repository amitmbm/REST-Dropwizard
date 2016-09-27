package com.ami;

import com.ami.clients.TransportClientManager;
import com.ami.guicemodules.ElasticSearchModule;
import com.ami.guicemodules.RedisModule;
import com.bendb.dropwizard.redis.JedisBundle;
import com.bendb.dropwizard.redis.JedisFactory;
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
public class DropWizardApp extends Application<ApplicationConfig>{

    private static final Logger logger = LoggerFactory.getLogger(DropWizardApp.class);

    private ElasticSearchModule elasticSearchModule;
    private RedisModule redisModule;


    /**
     * All guice modules get added here
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<ApplicationConfig> bootstrap) {

        elasticSearchModule = new ElasticSearchModule();
        redisModule = new RedisModule();
        GuiceBundle<ApplicationConfig> guiceBundle = GuiceBundle.<ApplicationConfig>newBuilder()
                .addModule(redisModule)
                .addModule(elasticSearchModule).enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(ApplicationConfig.class).build(Stage.DEVELOPMENT);
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new SwaggerBundle<ApplicationConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ApplicationConfig configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
            bootstrap.addBundle(new JedisBundle<ApplicationConfig>() {
                @Override
                public JedisFactory getJedisFactory(ApplicationConfig configuration) {
                    return configuration.getRedisCacheConfig().getJedisFactory();
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
    public void run(ApplicationConfig appConfig, Environment environment) throws Exception {
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
        new DropWizardApp().run(args);
    }


    @Override
    public String getName() {
        return "catalog";
    }
}
