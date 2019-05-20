JAVAC = ./javac
JAVA = ./java
APPLET = ./appletviewer

run:
	${JAVAC} main.java
	${APPLET} main.java

test:
	${JAVAC} JacobiCalculatorTest.java
	${JAVA} JacobiCalculatorTest


clean:
	rm *.class

