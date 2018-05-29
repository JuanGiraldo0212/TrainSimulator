package interfaz;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;

import javax.swing.*;

public class PanelInicio extends JPanel implements ActionListener, ImageObserver {

	public final static String COMENZAR_PARTIDA= "Comenzar Simulacion";
	
	private JButton btnComenzarPartida;
	private JPanel aux;
	private JPanel aux2;
	private JLabel imagen;
	private JLabel lblNombre;
	private JLabel lblCodigo;
	private JLabel logo;
	private JTextField txtNombre;
	private JTextField txtCodigo;
	private InterfazJuego principal;
	
	public PanelInicio(InterfazJuego v){
		aux=new JPanel();
		aux2=new JPanel();
		aux2.setLayout(new BorderLayout());
		principal = v;
		setLayout(new BorderLayout());
		imagen=new JLabel();
		imagen.setIcon(new ImageIcon("./data/Escenario/Fondo.png"));
		lblNombre=new JLabel("Nombre: ");
		
		lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
		lblCodigo= new JLabel("Codigo operario: ");
		
		lblCodigo.setFont(new Font("Arial", Font.BOLD, 20));
		txtNombre= new JTextField();
		txtNombre.setPreferredSize(new Dimension(250,50));
		txtNombre.setFont(new Font("Arial", Font.BOLD, 15));
		txtCodigo= new JTextField();
		txtCodigo.setPreferredSize(new Dimension(250,50));
		txtCodigo.setFont(new Font("Arial", Font.BOLD, 15));
		logo=new JLabel();
		logo.setIcon(new ImageIcon("./data/Escenario/logo.png"));
		add(imagen,BorderLayout.NORTH);
		btnComenzarPartida = new JButton(COMENZAR_PARTIDA);
		btnComenzarPartida.setPreferredSize(new Dimension(300,50));
		btnComenzarPartida.setFont(new Font("Arial", Font.BOLD, 20));
		btnComenzarPartida.setActionCommand(COMENZAR_PARTIDA);
		btnComenzarPartida.addActionListener(this);
		aux2.add(logo,BorderLayout.WEST);
		aux.add(lblNombre);
		aux.add(txtNombre);
		aux.add(lblCodigo);
		aux.add(txtCodigo);
		aux.add(btnComenzarPartida);
		aux2.add(aux,BorderLayout.CENTER);
		add(aux2,BorderLayout.CENTER);
//		
//		btnTopPuntajes = new JButton(TOP_PUNTAJES);
//		btnTopPuntajes.setActionCommand(TOP_PUNTAJES);
//		btnTopPuntajes.addActionListener(this);
//		add(btnTopPuntajes,BorderLayout.CENTER);
//		
//		btnInfo = new JButton(INFO);
//		btnInfo.setActionCommand(INFO);
//		btnInfo.addActionListener(this);
//		add(btnInfo,BorderLayout.CENTER);
//		
//		JPanel aux = new JPanel(new GridLayout(0, 1));
//		aux.add(btnComenzarPartida);
//		aux.add(btnTopPuntajes);
//		aux.add(btnInfo);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(COMENZAR_PARTIDA)){
			principal.cargarArchivo();
			principal.darUsuario(txtNombre.getText()+"-"+txtCodigo.getText());
			txtNombre.setText(" ");
			txtCodigo.setText(" ");
		}
		
	}
		
	
}
