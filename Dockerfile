FROM ubuntu:16.04

# Temporarily install these things so that we can debug the container
RUN apt-get -o Acquire::http::Pipeline-Depth="0" update && \
    apt-get -o Acquire::http::Pipeline-Depth="0" install  --no-install-recommends -y \
    net-tools iputils-ping host wget curl groff man less vim openjdk-8-jdk python-pip python-setuptools python-wheel && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*


RUN pip install awscli

RUN mkdir -p /usr/share/product-service && chmod 700 /usr/share/product-service
RUN mkdir -p /etc/product-service && chmod 700 /etc/product-service
RUN mkdir -p /var/log/ami/product-service

COPY target/dropwizard*.jar /usr/share/product-service/dropwizard.jar

# Copy all the configs into the image
COPY productservice.yml  /etc/product-service/productservice.yml

WORKDIR /usr/share/product-service

EXPOSE 9090 9091


CMD java -server  -Xms512m -Xmx512m -XX:+UseConcMarkSweepGC -XX:SurvivorRatio=4 -XX:CompileThreshold=100 -cp /usr/share/product-service/dropwizard.jar com.ami.ElasticSearchDropWizardApp server /etc/product-service/productservice.yml
