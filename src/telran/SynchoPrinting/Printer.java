package telran.SynchoPrinting;

public class Printer extends Thread {
	
	Printer nextPrinter;
	int numberToPrint;
	int portionSize;
	int numberOfPortionsLeft;
	Object mutex;
	
	public Printer(Object mutex, int numberToPrint,
			int totalNumbersToPrint, int portionSize) {
		this.numberToPrint = numberToPrint;
		this.portionSize = portionSize;
		this.numberOfPortionsLeft = totalNumbersToPrint / portionSize;
		this.mutex = mutex;
	}
	
	
	public void setNextPrinter(Printer nextPrinter) {
		this.nextPrinter = nextPrinter;
	}

	@Override
	public void run() {
		while( numberOfPortionsLeft > 0) {
		synchronized (mutex) {
			try {
				mutex.wait();
			} catch (InterruptedException e) {
					System.out.println(String.valueOf(numberToPrint).repeat(portionSize));
					numberOfPortionsLeft--;
					nextPrinter.interrupt();;
				}
			}
		}
	}
}
