package telran.SynchoPrinting;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ApplController {

	private static final int N_PRINTERS = 5;
	private static final int N_NUMBERS	 = 10;
	private static final int N_PORTIONS	 = 5;
	static Object mutex = new Object();

	public static void main(String[] args) {
		
		List<Printer> printers = IntStream.rangeClosed(1, N_PRINTERS)
			.mapToObj( i -> new Printer(mutex, i, N_NUMBERS, N_PORTIONS))
			.collect(Collectors.toCollection(LinkedList<Printer>::new));
		
		for ( int i = 0; i < N_PRINTERS; i++) {
			printers.get(0).setNextPrinter(printers.get(1));
			Collections.rotate(printers, -1);
		}
		
		printers.forEach(p -> p.start());
		printers.get(0).interrupt();
		

	}

}
