Moving pieces
-> Moving: removing from original position and placing at new position
-> When removing that 1 bit, you can '&' with a number that has 1s at every other position but that spot
-> You can come up with that number by first coming up with a number that has a 0s at every other position but that spot then negate it
-> to come up with that number you only need to realize that a number that has a 1 at only one spot is actually a power of 2.
-> So get the move as a notation -> Convert notation to bit position -> ~2^(bit position) is your number
-> Same thing with plaing at the new position

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
-> Implementing a LinkedList class or Another class to handle addition using a fixed sized array and pointers.
-> Finally found out Java has it's own LinkedList... just went with that
