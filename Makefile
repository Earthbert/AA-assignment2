build: trial rise

BUILD_PATH = ./build

run_trial:
	java -cp $(BUILD_PATH) Trial

run_rise:
	java -cp $(BUILD_PATH) Rise

trial: src/Trial.java src/Task.java
	javac -d $(BUILD_PATH) $^

rise: src/Rise.java src/Task.java
	javac -d $(BUILD_PATH) $^

clean:
	rm -f *.class

.PHONY: build clean
