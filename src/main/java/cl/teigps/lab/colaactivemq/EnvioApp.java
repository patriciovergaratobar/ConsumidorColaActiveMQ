package cl.teigps.lab.colaactivemq;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;

public class EnvioApp {

	public static void main(String[] args) {

		ExecutorService executorTest = Executors.newFixedThreadPool(100);

		int CANTIDAD_ENVIO = 999999999;
		for (int i = 0; i < CANTIDAD_ENVIO; i++) {
			executorTest.submit(() -> {

				final Envios enviosMq = new Envios();
				try {

					long imeiAutogenerado = new Random().nextLong();
					byte[] trama = "$GPRMC,001225,A,2832.1834,N,08101.0536,W,12,25,251211,1.2,E,A*03".getBytes();

					System.out.println("Hilo -  Enviando IMEI -> " + imeiAutogenerado);

					MensajeModel mensaje = new MensajeModel();
					mensaje.setImei(imeiAutogenerado);
					mensaje.setTramas(trama);

					enviosMq.iniciarConexion();
					enviosMq.envioMensaje(mensaje);

				} catch (JMSException e) {

					System.out.println("Error de  Enviando ");
					e.printStackTrace();
				} finally {
					enviosMq.finalizarConexion();
				}

			});
		}

		executorTest.shutdown();
	}

}
