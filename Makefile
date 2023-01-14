build: trial

BUILD_PATH = ./build

run_trial:
	java -cp $(BUILD_PATH) Trial

trial: src/Trial.java src/Task.java
	javac -d $(BUILD_PATH) $^

clean:
	rm -f *.class

.PHONY: build clean
