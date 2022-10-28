Moving pieces
-> Moving: removing from original position and placing at new position
-> When removing that 1 bit, you can '&' with a number that has 1s at every other position but that spot
-> You can come up with that number by first coming up with a number that has a 0s at every other position but that spot then negate it
-> to come up with that number you only need to realize that a number that has a 1 at only one spot is actually a power of 2.
-> So get the move as a notation -> Convert notation to bit position -> ~2^(bit position) is your number
-> Same thing with placing at the new position

Iterating through bits
-> Went from 0-64 -> leadign-trailing -> jumping exactly to where we have 1s
-> O(64) -> O(64 - T - L) -> O(n)
-> T: trailing zeros, L: leading zeros,  n: number of ones

Coming up with an Object-Oriented Design
-> avoid lot of code repitition, easier to partition the work based on objects, lesser maintenance code, more concision, scalable
-> Main: an entry point to the program and just starts the game by calling game.play =() where game is an instance.
-> Board: supposed to simulate behavior an actual physical board. It doesn't interprate its own state. It doesn't enforce any rules (it would mean
interprating the state of the board). Doesn't understand check, checkmate, or draw.
: The board should support any variation of chess. Even if we come up with our own rule.
: It just support moving pieces which is what you do on actual board.... has movePiece() method.
: Other methods just useful methods that have to do with Boards in general
-> Piece: Abstract class. Makes sure that all concrete piece classes have pieceType, can draw itself, and return all possible moves pieces its pieces can
make on a board, given the status of a board.
Concrete Pieces should not track the state of the board or where their pieces are on the board. They should just contain the logic/rules to
dictate what moves their pieces can make.


Storing History using fixed sized collection
-> Since removing from the first has linear time complexity for an array and an arraylist (for deques as well I think), I considered either
   implementing a LinkedList class or Another class to handle addition using a fixed sized array and pointers.
-> Finally found out Java has it's own LinkedList... just went with that


**A couple of mistakes I made**
1. Misplaced an if check. It should have been a block or 2 up. The engine ended up computing moves for 
   squares that didn't even have pieces on them and included them in the final answer. Made the engine think
   the king was under attack even when it wasn't. Made the same mistake for all sliding pieces.
   Could've been avoided if the testing was exaustive enough. 
   I had 2 oppertunities to discover the bug
    - When testing the routines that generated either attacks or moves based on a boolean parameter, I tested 
      the code for moves. Didn't test the routine when it was asked for attacks instead. May have slipped my mind 
      because the implementation for both was almost identical.
    - When testing inCheck() routine, I only tested a couple of position with few pieces on the board to get quick boolean results to tell me 
      if the king is in check or not. That and the same thing for the other color. If I wrote actual code to simulate different positionings for 
      the king and different placements of different pieces, I'd've caught it. Worth noting tho that I'm still not sure how I can do that exactly.

2. Typo: When executing the rook move in castling, the engine ends up removing the correct rook but placing a white Rook regardless of which piece
   casteled. When I was testing castling for white, I didn't notice this for obvious reasons. When I was testing it for black tho, it still took time
   to notice as the problem I was seeing was not the placement of the wrong rook color. When you castle, you place the rook besides your king. When
   the rook you placed is your enemies, your king ends up in check. So the move got rejected. 
   This ended up sending me on a wild goose chase trying to find a bug within inCheck() routine, Rook class, Agent... before I finally got it. 
   I could've noticed that the rook next to the king was an 'R' and not an 'r',
