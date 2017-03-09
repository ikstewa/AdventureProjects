# AdventureProjects

Requires JDK8.

To compile:
```javac -d build/ src/**/*.java```

To run:
```java -cp build/ org.ikstewa.adventureprojects.Main```


## Coding Exercise

Design a command-line controller program for a robotic arm that takes commands that move blocks stacked in a series of slots. After each command, output the state of the slots, which each line of output corresponding to a slot and each X corresponding to a block.

**Commands:**

  * size [n] - Adjusts the number of slots, resizing if necessary. Program must start with this to be valid.
  * add [slot] - Adds a block to the specified slot.
  * mv [slot1] [slot2] - Moves a block from slot1 to slot2.
  * rm [slot] - Removes a block from the slot.
  * replay [n] - Replays the last n commands.
  * undo [n] - Undo the last n commands.

### Example

```
> size 3
1:
2:
3:

> add 2
1:
2: X
3:

> add 3
1:
2: X
3: X

> mv 3 2
1:
2: XX
3:

> rm 2
1:
2: X
3:
```
