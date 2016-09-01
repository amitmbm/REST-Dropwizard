package com.ami.test;

import com.ami.entities.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by amit.khandelwal on 01/09/16.
 */

public class ProductTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Product product = new Product("name","1","title","brand","desc");
        assertThat(MAPPER.writeValueAsString(product))
                .isEqualTo(fixture("fixtures/product.json"));
    }


}
