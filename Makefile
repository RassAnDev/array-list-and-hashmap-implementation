setup:
	gradle wrapper --gradle-version 8.2

clean:
	gradle clean

build:
	gradle clean build

lint:
	gradle checkstyleMain checkstyleTest

test:
	gradle test

.PHONY: build
