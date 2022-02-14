# Canvas program

## How to run the application:
To boot the application execute ```sbt run``` in the command line and introduce the following commands:
1. Create Canvas: C 20 4
2. Draw Horizontal Line: L 1 2 6 2
3. Draw Vertical Line: L 6 3 6 4
4. Draw Square: R 14 1 18 3
5. Fill area: B 10 3 o

## Area Filling Algorithm:

 1. Pick initial point in the canvas (represented below as "Ö").
 2. Check which neighboring nodes are blank and colored.
 3. IF AN BLANK NODE EXISTS:
   a1. Move at random to one of the blank nodes.
   a2. Split the respective horizontal line in 2 substrings. Replace trailing whitespaces
       on the left substring with the colour. Likewise, replace leading whitespaces with
       the colour on the right substring.
   a3. Increment iter and acc.
   a4. Repeat step 2
   ELSE:
   a1. Move to a colored neighboring node randomly and increment iter.
   a2. Repeat step 2
 4. Repeat step 2 and 3 until the accummulator-iteration ratio is below 1E-4.


## Microbenchmark Harness:

To run the performance tests execute ```sbt jmh:run``` in the command line.
