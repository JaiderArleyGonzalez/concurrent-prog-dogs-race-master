package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private final Monitor monitor;
	public Galgo(Carril carril, String name, RegistroLlegada reg, Monitor monitor) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
		this.monitor=monitor;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {	
			try {
				monitor.esperarSiSuspendido();	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			
			if (paso == carril.size()) {
				synchronized(regl){						
					carril.finish();
					int ubicacion=regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada(ubicacion+1);
					System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);
					if (ubicacion==1){
						regl.setGanador(this.getName());
					}
				}
				
			}
		}
	}


	@Override
	public void run() {
		
		try {
			
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
