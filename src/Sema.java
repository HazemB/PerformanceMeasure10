
public class Sema {
	private int s;
	public Sema(int permits) {s = permits;}

	public synchronized void acquire() {
		try {
			while(s == 0){wait();}
			s = s - 1;
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	public synchronized void release() {
		s = s + 1;
		notify();
	}
}
