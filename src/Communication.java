
public interface Communication {
	public void nextEmpToSend(Employee e);
	public Employee getNextEmpReceived();
	public int get_messageLength();
	
	public void send();
	public void receive(); 
	public void sendAck(); 
	public void receiveAck(); // to be executed by Producer before next message is prepared
}
