
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
Execution time was recorded 3 times for 4 different levels of N. Ran without printing.

| N       | avg time (sec) |
| :---    |    ----:   |
| 1       |  0.009977433   |
| 10      | 0.013553867    |
| 100     | 0.0588007      |
| 1000    |  0.404372133   |


## Problem 2
file : VaseScenario

### How to run
1. clone this repo, if you haven't
2. compile with  ```javac VaseScenario.java```
3. run with ```java VaseScenario```
4. Specify N, the number of guests, as well as whether to print the simulated actions using prompts.

### Solution


### Implementation


### Evaluation / Experimentation
