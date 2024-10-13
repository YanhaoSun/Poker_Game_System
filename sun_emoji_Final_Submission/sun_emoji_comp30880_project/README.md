# Software Engineering Project 3
## Developed by sun_emoji
Oscar Yeoh 20403662  
Haocheng Sun 19203637  
Yanhao Sun 19205434

## Poker
Created by lecturer.
Is available to play.  
Original functionality preserved.

## Blackjack
### Instructions to Run
1. Run jar package.
2. Select option 2
3. Enter details as prompted
4. Place bet using numbers when prompted
5. Select action option using the number label when prompted

### Justification of Implementation
Due to the distinct differences between Blackjack and Poker, despite their similarities, it was necessary to create standalone classes such as Player, DeckofCards, GameOfBlackjack, etc. Many of the code present in the Poker classes were replicated and modified to accomodate Blackjack. This is because extending the classes did not seem correct as Blackjack is not Poker. Additionally, it would require overriding many of the methods, making it difficult to debug. Thus, it was better to create them as standalone classes.

### Special Features
- Support multiple human and computer players

## Texas Hold'Em
### Instructions to run
1. Run jar package
2. Select option 3
3. Enter details as prompted
4. Follow prompts to play game

### Justification of Implementation
Since permission to edit the poker package was given, we tried to make minimal changes to the package in order to reuse the code. The implementation for Texas Hold'Em was done as abstract as possible to enable smooth development of Texas Scramble in the future.

### Special Features
- Support multiple human and computer players
- Graphical representation in the text UI
  - Only current player's cards will be printed out


## Texas Scramble
### Instructions to run
1. Run jar package
2. Select option 4
3. Enter details as prompted
4Follow prompts to play game

### Special Features
- Able to select what game to play
- Support multiple human and computer players
- Defined computer player personalities
- Different skill levels for computer players i.e. vocabulary size
- Computer players learn new words as games are played
- Submit multiple words for increased score
- If you used all the letters for multiple words you get 50 bonus points
- If you make a 7-length word you get 100 bonus points
- Show best word human player can make with his hand for word learning
- Graphical representation in the text UI
  - Only current player's cards will be printed out

### Note
1. Blank tile is represented by **^**.
2. For Texas Scramble: TestCanFormString is trying to reproduce the bug TA meets, the input cases TA provided are replicated in the test, code works good and no bug is found.
