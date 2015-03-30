
public class Consumer extends Thread {
	private Communication com;
	private int repetitions;
    
	public Consumer(Communication c, int n) { com = c; repetitions = n; }

	public void run() {
		int messageLength = com.get_messageLength();
		
			for(int i=0; i<repetitions; i++) {
				com.receive();
				// consume message
				int sum = 0; int number = 0;
				for(int j=0; j< messageLength; j++){
					Employee next = com.getNextEmpReceived();
					// System.out.println("Consumer: next salary = " + next.salary );
					sum = sum + next.salary;
					number++;
				}
 				System.out.println("Consumer: sum = " + sum + " number = " + number );
				com.sendAck();
			}
	}
}

