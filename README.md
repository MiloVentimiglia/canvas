# Canvas program

## How to run:

Boot: ```sbt run```

Microbenchmarks: ```sbt jmh:run```


## Area Filling Algorithm:

 1. Pick initial point in the canvas (represented as "Ö").

 2. Check which neighboring nodes are already colored.

 3. IF A COLORED NODE EXIST:
   a1. Move at random to one of the colored nodes.
   a2. Split the respective horizontal line in 2 substrings. Replace trailing whitespaces
       on the left substring with the colour. Likewise, replace leading whitespaces with
       the colour on the right substring.
   a3. Increment iter and acc.
   a4. Repeat step 2
   ELSE:
   a1. Move to a neighboring coordinate randomly and increment iter.
   a2. Repeat step 2

 4. Repeat step 2 and 3 until the accummulator-iteration ratio is below 1E-4.


Example:

iter=0, acc=0          iter=1, acc=1          iter=2, acc=2          iter=3, acc=2
-----------------      -----------------      -----------------      -----------------
|           xxxx|      |           xxxx|      |           xxxx|      |           xxxx|
|xxxxx   Ö  x  x| ---> |xxxxxoooÖoox  x| ---> |xxxxxoooÖoox  x| ---> |xxxxxoooÖoox  x|
|    x      xxxx|      |    x      xxxx|      |    xooooÖoxxxx|      |    xoooÖÖoxxxx|
|    x          |      |    x          |      |    x          |      |    x          |
-----------------      -----------------      -----------------      -----------------
