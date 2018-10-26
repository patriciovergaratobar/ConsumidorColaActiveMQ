package cl.teigps.lab.colaactivemq;

import java.io.Serializable;


public class MensajeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private long imei;
	private byte[] tramas;

	public final long getImei() {
		return imei;
	}

	public final void setImei(long imei) {
		this.imei = imei;
	}

	public final byte[] getTramas() {
		return tramas;
	}

	public final void setTramas(byte[] tramas) {
		this.tramas = tramas;
	}

}
