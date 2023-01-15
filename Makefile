build: trial rise redemption

BUILD_PATH = ./build

run_trial: trial
	java -cp $(BUILD_PATH) Trial

run_rise: rise
	java -cp $(BUILD_PATH) Rise

run_redemption: redemption
	java -cp $(BUILD_PATH) Redemption

trial: src/Trial.java src/Task.java
	javac -d $(BUILD_PATH) $^

rise: src/Rise.java src/Task.java src/Trial.java
	javac -d $(BUILD_PATH) $^

redemption: src/Redemption.java src/Task.java
	javac -d $(BUILD_PATH) $^

clean:
	rm -rf $(BUILD_PATH)

.PHONY: build clean
