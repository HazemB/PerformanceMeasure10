
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class Producer extends Thread {
	private Communication com;
	private int repetitions;
	private int messageLength;
	private Employee[] employeePool;
	private long startTime = 0;
	private long endTime = 0;
	Random r;

	public Producer(Communication c, int n) {
		com = c; repetitions = n;
		r = new Random();
		messageLength = com.get_messageLength();
		int totalNumber = repetitions * messageLength;
		employeePool = new Employee[totalNumber];
		for(int i=0; i<totalNumber; i++ ) {
			employeePool[i] = new Employee(
					"employee"+ Integer.toString(i),
					"address"+ Integer.toString(i),
					10000 + i,
					15 + i );
		}
	}

	public void run() {
		System.out.println("type GO new line");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {String Q1 = input.readLine();} catch (IOException e) {e.printStackTrace();}

		startTime = System.currentTimeMillis();
		int sum = 0;
		for(int i=0; i<repetitions; i++) {
			com.receiveAck();
			// prepare message
			sum = 0;
			for(int j=0; j< messageLength; j++){
				int next = i*(messageLength) + j;
				com.nextEmpToSend(employeePool[next]);
				// System.out.println("next employee salary " + employeePool[next].salary);
				sum = (sum + employeePool[next].salary);
			}
			System.out.println("Producer sum " + Integer.toString(sum));
			com.send();
		}
		endTime = System.currentTimeMillis();
		System.out.println("Producer terminates" );
		System.out.println("Producer's execution time "  + (endTime-startTime));
	}
}

