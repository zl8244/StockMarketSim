
all: simple_threading_timer signal_interrupt_timer


simple_threading_timer: simple_threading_timer.o kbhit.o
	gcc -o simple_threading_timer -g simple_threading_timer.o kbhit.o -lrt

signal_interrupt_timer: signal_interrupt_timer.o kbhit.o
	gcc -o signal_interrupt_timer -g signal_interrupt_timer.o kbhit.o -lrt

simple_threading_timer.o: simple_threading_timer.c kbhit.h
	gcc -c -g -Wall simple_threading_timer.c

signal_interrupt_timer.o: signal_interrupt_timer.c kbhit.h
	gcc -c -g -Wall signal_interrupt_timer.c

kbhit.o: kbhit.c
	gcc -c -g -Wall kbhit.c

clean:
	rm simple_threading_timer
	rm signal_interrupt_timer

