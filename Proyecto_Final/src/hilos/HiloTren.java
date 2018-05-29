package hilos;

import interfaz.InterfazJuego;
import mundo.Tren;
import mundo.Vertex;

public class HiloTren extends Thread{
	
	private Tren tren;
	private InterfazJuego ventana;
	public HiloTren(Tren tren,InterfazJuego ventana){
		this.tren=tren;
		this.ventana=ventana;
	}
	
	@Override
	public void run() {
		super.run();
		while(!tren.isDetenido()){
			ventana.actualizar();
			if(tren.getVelocidad()>0){
				
			if(!tren.isOnVertex()){
			
					tren.movimiento();
					//System.out.println(tren.getPosX()+" "+tren.getPosY());
					try {
						Thread.sleep(100-(20*tren.getVelocidad()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				ventana.actualizar();
				
			}
			else{
				ventana.getMundo().getCamino().add(tren.getActual());
				if(!tren.getActual().getCodigo().equals(tren.getMeta().getCodigo())){
					
					if(tren.getVelocidad()<=tren.getActual().getVelocidad()){
						Vertex temp=tren.getActual();
						tren.setSalida(temp);
						tren.setActual(ventana.getPanelJuego().getSeleccionado());
						ventana.cambiarSeleccionado();
					
						ventana.actualizar();
					}
					else{
						System.out.println("C marlon");
						tren.setDetenido(true);
						ventana.getMundo().setJugando(false);
						ventana.actualizar();
						ventana.altaVelocidad();
						ventana.jugarOtraVez();
						ventana.actualizar();
					}
				}
				else{
					System.out.println("Win");
					tren.setDetenido(true);
					ventana.getMundo().setJugando(false);
					ventana.actualizar();
					ventana.terminar();
					ventana.jugarOtraVez();
					ventana.actualizar();
				}
			}
		}
		}
	}

}
