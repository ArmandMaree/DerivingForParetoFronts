CC = g++
CFLAGS = -std=c++11
LFLAGS = -static
TARGET = main
OBJECTS = cell.o chromosome.o geneticAlgorithm.o main.o

# Linking all the object code:
main: $(OBJECTS)
	$(CC) $(LFLAGS) $(OBJECTS) -o $(TARGET)

# Dependencies:
main.o: main.cpp


 Compilation rule:
%.o: %.cpp
	$(CC) $(CFLAGS) -c $?

# Custom commands:
clean:
	rm -f $(TARGET) $(OBJECTS) *.gch *~ # deleting executable, .o’s and backups

run:
	./$(TARGET) # executing the program
	
all:
	make clean
	make
	make run
	make clean