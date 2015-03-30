import java.util.concurrent.Semaphore;


public class CommSimple implements Communication {
	private Employee[] message;
	private int messageLength;
	private int index = 0;
	
	private Sema sent; // note: there is also a Semaphore defined among the Java predefined classes
	private Sema received;


	public CommSimple(int size){
		messageLength = size;
		message = new Employee[messageLength];
		sent = new Sema(0);    // first the thread Producer will call the acquire() method
		received = new Sema(1);
	}
	
	public int get_messageLength(){return messageLength;}
	
	public void nextEmpToSend(Employee e){
		message[index] = e;
		index ++;		
	}
		
	public Employee getNextEmpReceived(){
		Employee thisEmployee = message[index];
		index ++;
		return(thisEmployee);		
	}

		
	public void send(){
		sent.release();
	}
	public void receive() {
		sent.acquire();
		index = 0;
		}
	
	public void sendAck(){
		received.release(); }
	
	public void receiveAck() {
		received.acquire();
		index = 0;
	}

}
