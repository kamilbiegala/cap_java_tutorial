package com.sap.cap.bookstore.configuration;

import com.sap.cap.bookstore.components.RabbitReceiver;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@Configuration
@PropertySource("classpath:rabbit.properties")
public class RabbitConfiguration {
    @Autowired
    private Environment env;
    public static final String topicExchangeName = "exchange-name";

    public static final String queueName1 = "queue-name1";
    public static final String queueName2 = "queue-name2";

//    /**
//     * Establish a connection to a rabbit mq server.
//     * @return Rabbit connection factory for rabbitmq access.
//     * @throws IOException If wrong parameters are used for connection.
//     */
//    @Bean
//    public RabbitConnectionFactoryBean connectionFactoryBean() throws IOException {
//        RabbitConnectionFactoryBean connectionFactoryBean = new RabbitConnectionFactoryBean();
//        connectionFactoryBean.setHost(Objects.requireNonNull(env.getProperty("rabbit.host")));
//        connectionFactoryBean.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("rabbit.port"))));
//        connectionFactoryBean.setUsername(Objects.requireNonNull(env.getProperty("rabbit.username")));
////        connectionFactoryBean.setPassword(Objects.requireNonNull(env.getProperty("rabbit.password")));
//
//        // SSL-Configuration if set
//        if(env.getProperty("rabbit.ssl") != null) {
//            connectionFactoryBean.setUseSSL(true);
//            connectionFactoryBean.setSslAlgorithm(Objects.requireNonNull(env.getProperty("rabbit.ssl")));
//
//            // This information should be stored safely !!!
//            connectionFactoryBean.setKeyStore(Objects.requireNonNull(env.getProperty("rabbit.keystore.name")));
//            connectionFactoryBean.setKeyStorePassphrase(Objects.requireNonNull(env.getProperty("rabbit.keystore.password")));
//            connectionFactoryBean.setTrustStore(Objects.requireNonNull(env.getProperty("rabbit.truststore")));
//            connectionFactoryBean.setTrustStorePassphrase(Objects.requireNonNull(env.getProperty("rabbit.truststore.password")));
//        }
//
//        return connectionFactoryBean;
//    }
//
//    /**
//     * Connection factory which established a rabbitmq connection used from a connection factory
//     * @param connectionFactoryBean Connection factory bean to create connection.
//     * @return A connection factory to create connections.
//     * @throws Exception If wrong parameters are used for connection.
//     */
//    @Bean(name = "GEO_RABBIT_CONNECTION")
//    public ConnectionFactory connectionFactory(RabbitConnectionFactoryBean connectionFactoryBean) throws Exception {
//        return new CachingConnectionFactory(Objects.requireNonNull(connectionFactoryBean.getObject()));
//    }

    @Bean
    public Declarables topicBindings() {
        Queue topicQueue1 = new Queue(queueName1, false);
        Queue topicQueue2 = new Queue(queueName2, false);

        TopicExchange topicExchange = new TopicExchange(topicExchangeName);

        return new Declarables(
                topicQueue1,
                topicQueue2,
                topicExchange,
                BindingBuilder
                        .bind(topicQueue1)
                        .to(topicExchange).with("send.#"),
                BindingBuilder
                        .bind(topicQueue2)
                        .to(topicExchange).with("receive.#"));
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

//    @Bean(name="transactionManager")
//    public RabbitTransactionManager rabbitTransactionManager(final ConnectionFactory connectionFactory) {
//        RabbitTransactionManager rtm = new RabbitTransactionManager(connectionFactory);
//        rtm.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_ALWAYS);
//        return rtm;
//    }

    @Bean
    public RabbitReceiver receiver1() {
        return new RabbitReceiver();
    }
}
