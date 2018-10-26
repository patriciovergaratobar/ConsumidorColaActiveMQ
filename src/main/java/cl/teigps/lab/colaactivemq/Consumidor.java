package cl.teigps.lab.colaactivemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumidor {
 
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	
	public void iniciarConexion() throws JMSException {
		
		connectionFactory = new ActiveMQConnectionFactory(ActiveMqUtil.USER, ActiveMqUtil.PASSWORD, ActiveMqUtil.URL);
        connection = connectionFactory.createConnection();
        connection.start();
        
        session = connection.createSession(ActiveMqUtil.TRANSACTED_SESSION, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(ActiveMqUtil.DESTINATION_QUEUE);
	}
	
	public void finalizarConexion() {
		try {
			
			if (session != null) {
				
				session.close();
			}
			
			if (connection != null) {
				
				connection.close();
			}
		} catch (JMSException e) {

			e.printStackTrace();
		}
	}
	
	/***
	 * Metodo que dispara el procesamiento de los datos.
	 * 
	 * @throws JMSException
	 */
    public void procesarMensajes() throws JMSException {
 
        final MessageConsumer consumer = session.createConsumer(destination);
        procesarAllMessagesInQueue(consumer);
        consumer.close();
    }
 
    /**
     * Metodo que procesa todos los datos de la cola.
     * @param consumer
     * @throws JMSException
     */
    private void procesarAllMessagesInQueue(MessageConsumer consumer) throws JMSException {
        Message message;
        while ((message = consumer.receive(ActiveMqUtil.TIMEOUT)) != null) {
        	procesarMensaje(message);
        }
    }
 
    /**
     * Metodo que procesa un mensaje de la cola y lo castea al modelo.
     * 
     * @param message
     * @throws JMSException
     */
    private void procesarMensaje(Message message) throws JMSException {
    	
        if (message instanceof ObjectMessage) {
        	
            final ObjectMessage mensaje = (ObjectMessage) message;
            final MensajeModel mensajeModel = (MensajeModel) mensaje.getObject();
            
            System.out.println("Mensaje obtenido:");
            System.out.println(mensajeModel.getImei());
            System.out.println(mensajeModel.getTramas().toString());
          
            
        }
    }
}
