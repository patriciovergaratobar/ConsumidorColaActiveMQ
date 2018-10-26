package cl.teigps.lab.colaactivemq;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ObjectMessage;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;

public class Envios {

	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	
    private static final boolean TRANSACTED_SESSION = true;
    
    public void iniciarConexion() throws JMSException {
		
		connectionFactory = new ActiveMQConnectionFactory(ActiveMqUtil.USER, ActiveMqUtil.PASSWORD, ActiveMqUtil.URL);
        connection = connectionFactory.createConnection();
        connection.start();
        
        session = connection.createSession(TRANSACTED_SESSION, Session.AUTO_ACKNOWLEDGE);
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

    /**
     * Metodo que se encarga de establecer la conexión con ActiveMQ y envia el mensaje.
     *
     * @param mensaje
     * @throws JMSException
     */
    public void envioMensaje(final Serializable mensaje) throws JMSException {
 
        final MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
 
        envioMensaje(mensaje, session, producer);
        session.commit();
        //System.out.println("Mensajes enviados correctamente");
    }

    /**
     * Metodo que realiza el envio del mensaje.
     *
     * @param message 
     * @param session 
     * @param producer 
     * @throws JMSException
     */
    private void envioMensaje(Serializable mensaje, Session session, MessageProducer producer) throws JMSException {
    	
        final ObjectMessage mensajeObj = session.createObjectMessage(mensaje);
        producer.send(mensajeObj);
    }
}
