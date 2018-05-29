package logic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class JmsProducerQueue {
    private final ConnectionFactory f;
    private final Connection connection;
    private final Session session;
    private final MessageProducer messageProducer;
    private final static Logger logger = LoggerFactory.getLogger("Console");

    public JmsProducerQueue(String broker, String queue) throws Exception {
        f = new ActiveMQConnectionFactory(broker);
        connection = f.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue q = session.createQueue(queue);
        messageProducer = session.createProducer(q);
    }

    public void sendMessage(String s) throws Exception {
        Message message = session.createTextMessage(s);
        logger.info("Sending transaction to JMS Queue: " + s);
        messageProducer.send(message);
    }

    public void closeQueue() throws JMSException {
        session.close();
        connection.close();
    }
}
