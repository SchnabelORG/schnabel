package com.schnabel.schnabel.rabbitmq;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static java.lang.System.getenv;

@Configuration
public class RabbitMQConfig
{
    
    @Value("${custom.rabbitmq.exchange}")
    private String _exchange;

    @Value("${custom.rabbitmq.specialOffersQueue}")
    private String _specialOffersQueue;
    @Value("${custom.rabbitmq.usageReportsQueue}")
    private String _usageReportsQueue;
    
    @Value("${custom.rabbitmq.specialOffersKey}")
    private String _specialOffersKey;
    @Value("${custom.rabbitmq.usageReportsKey}")
    private String _usageReportsKey;
    
    @Bean
    DirectExchange exchange()
    {
        return new DirectExchange(_exchange);
    }

    @Bean
    Queue specialOffersQueue()
    {
        return new Queue(_specialOffersQueue, false);
    }

    @Bean
    Queue usageReportsQueue()
    {
        return new Queue(_usageReportsQueue, false);
    }
    
    @Bean
    Binding specialOffersBinding(DirectExchange exchange)
    {
        return BindingBuilder.bind(specialOffersQueue()).to(exchange).with(_specialOffersKey);
    }

    Binding usageReportsBinding(DirectExchange exchange)
    {
        return BindingBuilder.bind(usageReportsQueue()).to(exchange).with(_usageReportsKey);
    }
    
    @Bean
    public ConnectionFactory connectionFactory()
    {
        final URI rabbitMqUrl;
        try
        {
            rabbitMqUrl = new URI(getEnvOrThrow("CLOUDAMQP_URL"));
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
   
        final CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername(rabbitMqUrl.getUserInfo().split(":")[0]);
        factory.setPassword(rabbitMqUrl.getUserInfo().split(":")[1]);
        factory.setHost(rabbitMqUrl.getHost());
        factory.setPort(rabbitMqUrl.getPort());
        factory.setVirtualHost(rabbitMqUrl.getPath().substring(1));
   
        return factory;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer()
    {
        SimpleMessageListenerContainer simpleMessageListenerContainer
            = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory((connectionFactory()));
        simpleMessageListenerContainer.setQueues(usageReportsQueue());
        simpleMessageListenerContainer.setMessageListener(new RabbitMQListener());
        return simpleMessageListenerContainer;
    }

    private static String getEnvOrThrow(String name)
    {
        final String env = getenv(name);
        if (env == null)
        {
            throw new IllegalStateException("Environment variable [" + name + "] is not set.");
        }
        return env;
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
    {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
