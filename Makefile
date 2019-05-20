JAVAC = ./javac
JAVA = ./java
APPLET = ./appletviewer

run:
	${JAVAC} Main.java
	${APPLET} Main.java

test:
	${JAVAC} JacobiCalculatorTest.java
	${JAVA} JacobiCalculatorTest


clean:
	rm *.class

