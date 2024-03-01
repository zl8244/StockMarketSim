// Source from https://opensource.com/article/21/10/linux-timers
//
// Text from the above site
// Let's take a look at the simple_threading_timer.c example.
// This is the simplest one: It shows how an interval timer is created,
// which calls the function expired on expiration. On each expiration,
//  a new thread is created in which the function expiration is called.
//
// The advantage of this approach is its small footprint, in terms of code
// and simple debugging. The disadvantage is the additional overhead due
// to the creation of a new thread on expiration and, consequently, the less deterministic behavior.

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <signal.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#include <time.h>

#include "kbhit.h"

void expired(union sigval timer_data);

pid_t gettid(void);

struct t_eventData{
    int myData;
};

int main()
{
    int res = 0;
    timer_t timerId = 0;

    struct t_eventData eventData = { .myData = 0 };


    /*  sigevent specifies behaviour on expiration  */
    struct sigevent sev = { 0 };

    /* specify start delay and interval
     * it_value and it_interval must not be zero */

    struct itimerspec its = {   .it_value.tv_sec  = 1,
                                .it_value.tv_nsec = 0,
                                .it_interval.tv_sec  = 1,
                                .it_interval.tv_nsec = 0
                            };

    printf("Simple Threading Timer - thread-id: %d\n", gettid());

    sev.sigev_notify = SIGEV_THREAD;
    sev.sigev_notify_function = &expired;
    sev.sigev_value.sival_ptr = &eventData;


    /* create timer */
    res = timer_create(CLOCK_REALTIME, &sev, &timerId);


    if (res != 0){
        fprintf(stderr, "Error timer_create: %s\n", strerror(errno));
        exit(-1);
    }

    /* start timer */
    res = timer_settime(timerId, 0, &its, NULL);

    if (res != 0){
        fprintf(stderr, "Error timer_settime: %s\n", strerror(errno));
        exit(-1);
    }

    printf("Press ENTER Key to Exit\n");
    while( 1 )
	{
//		for ( int i = 0 ; i < 100000 ; i++ )
//			;
		if ( kbhit() )
			if ( getchar() == '\n' )
				break ;
	}
    return 0;
}


void expired(union sigval timer_data){
    struct t_eventData *data = timer_data.sival_ptr;
    printf("Timer fired %d - thread-id: %d\n", ++data->myData, gettid());
}
