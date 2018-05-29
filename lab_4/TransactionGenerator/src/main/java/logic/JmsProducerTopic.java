package logic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class JmsProducerTopic {
    private final ConnectionFactory f;
    private final Connection connection;
    private final Session session;
    private final MessageProducer messageProducer;
    private final static Logger logger = LoggerFactory.getLogger("Console");

    public JmsProducerTopic(String broker, String topic) throws Exception {
        f = new ActiveMQConnectionFactory(broker);
        connection = f.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic t = session.createTopic(topic);
        messageProducer = session.createProducer(t);
    }

    public void sendMessage(String s) throws Exception {
        Message message = session.createTextMessage(s);
        logger.info("Sending transaction to JMS Topic: " + s);
        messageProducer.send(message);
    }

    public void closeTopic() throws JMSException {
        session.close();
        connection.close();
    }
}
