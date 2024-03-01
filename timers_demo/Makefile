
all: simple_threading_timer signal_interrupt_timer


simple_threading_timer: simple_threading_timer.c
	gcc -o simple_threading_timer -Wall simple_threading_timer.c -lrt

signal_interrupt_timer: signal_interrupt_timer.c
	gcc -o signal_interrupt_timer -Wall signal_interrupt_timer.c -lrt

clean:
	rm simple_threading_timer
	rm signal_interrupt_timer

