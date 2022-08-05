# Thiseas :: escape the labyrinth

### Data Structures course assignment
Use a stack to traverse the pathways of a Labyrinth and guide Thiseas to the exit.
- **Do not use java.util structures**
- run with a labyrinth file as an arguement [^1]
[^1]: `java Thiseas path_to_file/LabyrinthFile.txt`

### input file format:

1. 9 7
2. 0 3
3. 1 1 1 Ε 1 1 1
4. 1 1 1 0 1 1 1
5. 1 0 0 0 1 0 1
6. 1 0 1 0 1 0 0
7. 1 1 1 0 1 1 1
8. 1 0 0 0 0 0 1
9. 1 0 1 1 1 0 1
10. 1 0 1 1 0 0 1
11. 0 1 1 1 0 1 1
---
- 1st line: labyrinth dimensions
- 2nd line: entrance coordinates
- E: entrance (unique)
- 1: wall
- 0: pathway
