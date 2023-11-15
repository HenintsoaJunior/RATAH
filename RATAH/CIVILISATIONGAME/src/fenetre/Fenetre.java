package fenetre;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import dessin.DessinPlan;
import files.File;

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
		setTitle("window");
		setSize(1000,1000);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                File.save(d.getPlan());
            }
        });
	}
	public  void myMouseListeneradd(listener.Listener l){
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}
}
