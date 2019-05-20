JAVAC = ./javac
APPLET = ./appletviewer

run:
	${JAVAC} Main.java
	${APPLET} Main.java

clean:
	rm *.class

