package fenetre;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import dessin.DessinPlan;
import files.File;
import listener.Listener;

public class Fenetre extends JFrame{
	DessinPlan d;
	
	public DessinPlan getPlanDrawer(){
		return d;
	}
	public void setPlanDrawer(DessinPlan e){
		getContentPane().setLayout(new BorderLayout());
		this.d=e;
		add(this.d);
	}
	public Fenetre(){
		setTitle("MY GAME");
		
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dimension);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                File.save(d.getPlan());
            }
        });
	}
	public  void myMouseListeneradd(Listener l){
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}
}
