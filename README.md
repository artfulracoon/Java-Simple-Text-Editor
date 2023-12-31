This is a fully functional notepad app coded on Java and designed with Swift. With all things you expect a notepad does, it also has an autocorrect function.  



Design Patterns Used in this Project:

1. Command
Employed for implementing functions such as "Open," "Close," "Undo," "Save," and "New." The Commands package can be reviewed. All necessary classes for the design, including Invoker, ICommand, and Receiver, are within the Commands package.

2. Iterator
Used to traverse through the dictionary while processing the "Correct Incorrect Words" function.

3. Singleton
The Invoker class used in the Command design pattern is implemented as a Singleton.

4. Mediator
When handling functions like "Correct Incorrect Word" and "Find and Replace Word," the objects containing these functions are consolidated within a Mediator object, and their methods are invoked through this object. For further examination, refer to the Mediator package.

5. Memento
Utilized a Memento stack for the "Undo" function. Each update in the Text Area is stored in a Memento, and these Mementos are kept in a stack within the Originator under a Caretaker. The undo function extracts Mementos from the stack as needed, updating the text area accordingly. Refer to the Memento package for a detailed review.





![image](https://github.com/artfulracoon/Java-Text-Editor-with-Autocorrect/assets/45047039/429c349b-30fe-43cc-a600-63e71d5a6ac4)
