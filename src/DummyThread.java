
public class DummyThread extends Thread { 
	public void run() {
		int i = 0; 
		while(true){
			i++; 
			if(i==1000000){i = 0;}
			}
		}
}
