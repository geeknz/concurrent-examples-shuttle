package harbour;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customer implements Runnable {

	private final Semaphore tickets;
	private final Semaphore northShore;
	private final Semaphore auckland;
	private final Random random = new Random();

	public Customer( final Semaphore tickets, final Semaphore northShore, final Semaphore auckland ) {
		this.tickets = tickets;
		this.northShore = northShore;
		this.auckland = auckland;
	}

	@Override
	public void run() {

		try {
			final boolean direction = random.nextBoolean();

			if ( direction ) {

				northShore.acquire();
				System.out.println( Thread.currentThread().getName() + " has boarded for the Northshore" );
			}

			else {

				auckland.acquire();
				System.out.println( Thread.currentThread().getName() + " has boarded for Auckland" );
			}

			tickets.release();

		} catch ( final InterruptedException ie ) {}
	}
}
