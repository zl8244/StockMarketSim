// Original source credit:
// https://en.wikipedia.org/wiki/Pthreads
//
// Minor updates by L. Kiser 2/10/2023

// Description from Pthreads page:
//
// This program creates five threads, each executing the function perform_work
// that prints the unique number of this thread to standard output. 
// If a programmer wanted the threads to communicate with each other, this would 
// require defining a variable outside of the scope of any of the functions, 
// making it a global variable. This program can be compiled using the gcc 
//compiler with the following command:

// gcc pthreads_demo.c -pthread -o pthreads_demo

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <pthread.h>
#include <unistd.h>

#define NUM_THREADS (5)

static void *perform_work( void *arguments )
{
  int index = *((int *)arguments);
  int sleep_time = 1 + rand() % NUM_THREADS;

  printf("THREAD %d: Started.\n", index);
  printf("THREAD %d: Will be sleeping for %d seconds.\n", index, sleep_time);
  sleep(sleep_time);
  printf("THREAD %d: Ended.\n", index);

  return NULL;
}

int main( int arg_count, char *arg_strings[] )
{
  pthread_t threads[NUM_THREADS];
  int thread_args[NUM_THREADS];
  int i;
  int result_code;
  
  //create all threads one by one
  for (i = 0; i < NUM_THREADS; i++) {
    printf("IN MAIN: Creating thread %d.\n", i);
    thread_args[i] = i;
    result_code = pthread_create(&threads[i], NULL, perform_work, &thread_args[i]);
    assert(!result_code);
  }

  printf("IN MAIN: All threads are created.\n");

  //wait for each thread to complete
  for (i = 0; i < NUM_THREADS; i++) {
    result_code = pthread_join(threads[i], NULL);
    assert(!result_code);
    printf("IN MAIN: Thread %d has ended.\n", i);
  }

  printf("MAIN program has ended.\n");
  return 0;
}
