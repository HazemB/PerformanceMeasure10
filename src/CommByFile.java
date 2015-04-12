import java.io.*;


public class CommByFile implements Communication {
    private Employee[] messageSent;
    private Employee[] messageReceived;
    private int messageLength;
    private int index = 0;
    private File file;

    private String codedMessage;
    int charIndex = 0; // index in the encoded message

    private Sema sent; // note: there is also a Semaphore defined among the Java predefined classes
    private Sema received;


    public CommByFile(int size){

        file = new File ("comm.txt");
        messageLength = size;
        messageSent = new Employee[messageLength+1];
        messageReceived = new Employee[messageLength+1];
        sent = new Sema(0);    // first the thread Producer will call the acquire() method
        received = new Sema(1);
    }

    public int get_messageLength(){return messageLength;}

    public void nextEmpToSend(Employee e){
        messageSent[index] = e;
        index ++;
    }

    public Employee getNextEmpReceived(){
        Employee thisEmployee = messageReceived[index];
        index ++;
        return(thisEmployee);
    }


    //instead of assigning to variable, just write to file
    public void send() {
        // encode the message


        codedMessage = new String();

        BufferedWriter bw = null;
        try {
                file.createNewFile();

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                bw = new BufferedWriter(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0; i<messageLength; i++){

            codedMessage = stringEncodeEmployee(messageSent[i]);
            try {
                bw.write(codedMessage + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sent.release();
    }

    //instead of getting from variable, just read from file
    public void receive() {
        sent.acquire();
        index = 0;
        // decode the message
        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            codedMessage = br.readLine();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        char[] mess = codedMessage.toCharArray();
        charIndex = 0;
        for(int i=0; i<mess.length; i++){
            // message[i] = ...
            String name = getNextString(mess);
            String addr = getNextString(mess);
            int s = Integer.valueOf(getNextString(mess));
            int a = Integer.valueOf(getNextString(mess));
            messageReceived[i] = new Employee(name,addr,s,a);
        }
    }

    private String getNextString(char[] mess){
        String s = new String();
        System.out.println(mess.length);
        while(mess[charIndex]!= ' '){s = s + mess[charIndex]; charIndex ++;}
        charIndex ++;
        return(s);
    }

    public void sendAck(){
        received.release();
    }

    public void receiveAck() {
        received.acquire();
        index = 0;
    }

    private String stringEncodeEmployee(Employee e){
        String s = new String(e.name);
        String separator = " ";
        s = s +  separator +e.address + separator + Integer.toString(e.salary)
                + separator + Integer.toString(e.age) + separator;
        return(s);
    }

}