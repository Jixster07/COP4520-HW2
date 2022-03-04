
## Problem 1

file : BirthdayScenario.java

### How to run
1. clone this repo
2. compile with  ```javac BirthdayScenario.java```
3. run with ```java BirthdayScenario```<br>
4. Specify N, the number of guests, as well as whether to print the simulated actions using prompts.

### Solution
The solution I used for problem 1 was similar to solving the prisoner's problem in the book/class. The guests make a plan to have a designated leader to keep a total count of the number of guests who have entered at least once. He can do this reliably by only incrementing when the plate is missing the cupcake. In accordance, the other guests must never replace the cupcake and only eat once. This is how the leader can be completely certain that his count is correct. Once his count reaches N, he announces that all guests have entered.


### Implementation
As the problem stated, each guest is represented by a Guest thread each with their own protocol in run() as well as a Leader thread extending guest with his own protocol. The master thread (represents minotaur) selects a random guest to enter and then dispatches with .start() or .run() if it hasn't started before. Then it waits for execution to complete using the .join() method. These methods have internal synchronization and locks and handle mutual exclusion under the hood. Because it is certain that only one thread has access to shared data at a time (cupcakeOnPlate), it is not necessary to use atomic variables. The algorithm is completely sequential because it is clear only one guest may enter at a time.


### Evaluation / Experimentation
Execution time was recorded 3 times for 6 different levels of N. Ran without printing.

| N       | avg time (sec) |
| :---    |    ----:   |
| 1       |  0.009977433   |
| 10      | 0.013553867    |
| 100     | 0.0588007      |
| 1000    |  0.404372133   |
| 5000 |4.7465563 |
|10000|  22.34911477 | 

Processor: Intel i5 8250U @1.6GHz, 4 Cores

This appears to run in O(n^2) time. Given the limitations, I do not see a way to improve run-time.
## Problem 2
file : VaseScenario

### How to run
1. clone this repo, if you haven't
2. compile with  ```javac VaseScenario.java```
3. run with ```java VaseScenario```
4. Specify N, the number of guests, as well as whether to print the simulated actions using prompts.

### Discussion
Considering which of the three options to select:

-Option 1 is clearly not correct because crowding the room means the guests could break the vase.<br>
-Option 2 is a decent option because no guests waste their time waiting in line and can do other things. However, It is not guaranteed that all guests will be able to see the vase, and there are times when no one is viewing the vase, which is inefficient when trying to show it to as many people as possible.<br>
-Option 3 is good because all guests are able to see the vase, and there is no time in which the vase is not being viewed. The downside is that guests waste time waiting in line when they can be doing other things. Overall, I think option 3 has the best protocol.<br>

### Implementation
Each guest is represented by a thread object and is placed into a queue at the beginning of execution. The master thread continuously removes guests from the head of the queue, starts execution and waits for them to finish before next guest is sent inside. It uses the join() method to wait for the thread to complete execution. join() internally handles locking the resource. I made each thread have a random chance of re-entering the queue after viewing based on the amount of times it has viewed.

### Evaluation / Experimentation

I ran the program at 5 different levels of N and plotted points to look for a pattern.

| N       | avg time (sec) |
| :---    |    ----:   |
| 1       |  0.0095275    |
| 100     | 0.0539571      |
| 1000    | 0.344701   |
|5000 | 1.6741268 | 
|10000|  4.2551401 |

Processor: Intel i5 8250U @1.6GHz, 4 Cores

The run time seems to be O(N) on average.
