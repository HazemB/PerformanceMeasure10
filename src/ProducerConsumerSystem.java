public class ProducerConsumerSystem {
    static final int messageSize =100;
    static final int repetitions = 2;

    public static void main(String [] args) {

        //Communication com = new CommSimple(messageSize);


       //Communication com = new CommCoded(messageSize);
        Communication com = new CommByFile(messageSize);
        // Communication com_receiver = new CommByTCT_receiver(messageSize);
        // Communication com_sender = new CommByTCT_sender(messageSize);

        Producer p = new Producer(com, repetitions);
        Consumer c = new Consumer(com, repetitions);
        // Producer p = new Producer(com_sender, repetitions) ;
        // Consumer c = new Consumer(com_receiver, repetitions) ;

        DummyThread d1 = new DummyThread();
        DummyThread d2 = new DummyThread();
        DummyThread d3 = new DummyThread();
        DummyThread d4 = new DummyThread();
        DummyThread d5 = new DummyThread();

        p.start();
        c.start();


        //d1.start();
        //d2.start();
        //d3.start();
        //d4.start();
        //d5.start();

    }
}