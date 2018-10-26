package cl.teigps.lab.colaactivemq;

import javax.jms.JMSException;

public class ConsumidorApp {

	public static void main(String[] args) {

		final Consumidor consumidor = new Consumidor();
		
		try {	

			consumidor.iniciarConexion();

			while (true) {

				System.out.println("Iniciando consumidor...");
				consumidor.procesarMensajes();

				try {

					Thread.sleep(2000L);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				System.out.println("Finalizando consumidor...");
			}

		} catch (JMSException e) {

			System.out.println("Error Conexion Consumidor Active MQ");
			e.printStackTrace();
			consumidor.finalizarConexion();
		}
	}

}
