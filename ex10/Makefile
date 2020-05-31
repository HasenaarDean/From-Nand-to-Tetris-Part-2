###############################################################################
#
# Makefile for Java project 10
#
# Students:
# Dean Hasenaar, ID 313584401, dean.hasenaar@mail.huji.ac.il
# Jonathan Meerson, ID 315363838, jonathan.meerson@mail.huji.ac.il
#
###############################################################################

JAVAC=javac
JAVACFLAGS=

SRCS=*.java  
EXEC=JackAnalyzer

TAR=tar
TARFLAGS=cvf
TARNAME=project10.tar
TARSRCS=$(SRCS) $(EXEC) README Makefile JackAnalyzer

all: compile

compile:
	$(JAVAC) $(JAVACFLAGS) $(SRCS)
	chmod +x $(EXEC)

tar:
	$(TAR) $(TARFLAGS) $(TARNAME) $(TARSRCS)

clean:
	rm -f *.class *~

