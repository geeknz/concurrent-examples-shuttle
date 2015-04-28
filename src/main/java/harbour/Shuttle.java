package harbour;

import java.util.concurrent.Semaphore;

public class Shuttle implements Runnable {

	private final Semaphore tickets;
	private final Semaphore northShore;
	private final Semaphore auckland;

	public Shuttle( final Semaphore tickets, final Semaphore northShore, final Semaphore auckland ) {
		this.tickets = tickets;
		this.northShore = northShore;
		this.auckland = auckland;
	}

	@Override
	public void run() {

		try {

			while ( true ) {

				tickets.acquire( 10 );
				System.out.println( "Shuttle is going from Auckland to the Northshore" );
				Thread.sleep( 1000 );
				System.out.println( "Shuttle is at the Northshore" );
				auckland.release( 10 );

				tickets.acquire( 10 );
				System.out.println( "Shuttle is going from the Northshore to Auckland" );
				Thread.sleep( 1000 );
				System.out.println( "Shuttle is at Auckland" );
				northShore.release( 10 );
			}

		} catch ( InterruptedException ie ) {}
	}

	public static void main( final String... args ) throws InterruptedException {

		final Semaphore tickets = new Semaphore( 10 );
		final Semaphore northShore = new Semaphore( 10 );
		final Semaphore auckland = new Semaphore( 10 );

		tickets.acquire( 10 );
		auckland.acquire( 10 );

		new Thread( new Shuttle( tickets, northShore, auckland ) )
				.start();

		for ( int i = 0; i < 100; i++ ) {

			new Thread( new Customer( tickets, northShore, auckland ) ).start();
		}
	}
}
