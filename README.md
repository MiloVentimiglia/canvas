# Canvas program

## How to run the application in the IDE:
To boot the application execute ```sbt run``` in the command line and introduce the following commands:

     1. Create Canvas: C 20 4
     2. Draw Horizontal Line: L 1 2 6 2
     3. Draw Vertical Line: L 6 3 6 4
     4. Draw Square: R 14 1 18 3
     5. Fill area: B 10 3 o


## How to run the application in a cluster:

To assembly a fat jar execute ```sbt assembly``` in the command line. This will create
a ```canvas-assembly-0.1.0-SNAPSHOT.jar``` file in the target folder which, in turn,
can be used anywhere provided there is a JVM. To run the jar file execute either:

```scala canvas-assembly-0.1.0-SNAPSHOT.jar```
```java -jar canvas-assembly-0.1.0-SNAPSHOT.jar```.


## How to deploy and run the dockerized application:

To run the dockerized version of the application, first publish the project locally
by executing ```sbt docker:publishLocal```. Once it is completed search for the
docker images available in the OS ```docker images```. One should see:

     REPOSITORY    TAG                 IMAGE ID       CREATED          SIZE
       canvas        0.1.0-SNAPSHOT   655626f4279c   10 seconds ago   494MB


To execute the dockerized application execute ```docker run --rm -ti canvas:0.1.0-SNAPSHOT```
in the command line.


## Area Filling Algorithm:

          1. Pick initial point in the canvas (represented below as "Ö").
          2. Check which neighboring nodes are blank and colored.
          3. IF AN BLANK NODE EXISTS:
               1. Move at random to one of the blank nodes.
               2. Split the respective horizontal line in 2 substrings. Replace trailing whitespaces
                  on the left substring with the colour. Likewise, replace leading whitespaces with
                  the colour on the right substring.
               3. Increment iter and acc.
               4. Move to step 2
            ELSE:
               1. Move to a colored neighboring node randomly and increment iter.
               2. Move to step 2
          4. Repeat step 2 and 3 until the accummulator-iteration ratio is below 1E-4.



         Example:

         iter=0, acc=0          iter=1, acc=1          iter=2, acc=2          iter=3, acc=2
         -----------------      -----------------      -----------------      -----------------
         |           xxxx|      |           xxxx|      |           xxxx|      |           xxxx|
         |xxxxx   Ö  x  x| ---> |xxxxxoooÖoox  x| ---> |xxxxxoooÖoox  x| ---> |xxxxxoooÖoox  x|
         |    x      xxxx|      |    x      xxxx|      |    xooooÖoxxxx|      |    xoooÖÖoxxxx|
         |    x          |      |    x          |      |    x          |      |    x          |
         -----------------      -----------------      -----------------      -----------------


## Microbenchmark Harness:

To run the performance tests execute ```sbt jmh:run``` in the command line.





