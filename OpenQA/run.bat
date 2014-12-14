:: Run script for Windows
rm edu/pku/openqa/*.class
rm edu/pku/openqa/util/*.class

javac -encoding UTF-8 -classpath "commons-logging-1.2.jar;jsoup-1.8.1.jar;commons-httpclient-3.1.jar;json-20140107.jar;commons-codec-1.6.jar;commons-cli-1.2.jar;commons-net-3.3.jar;." edu/pku/openqa/EntryPoint.java

java -classpath "commons-logging-1.2.jar;jsoup-1.8.1.jar;commons-httpclient-3.1.jar;json-20140107.jar;commons-codec-1.6.jar;commons-cli-1.2.jar;commons-net-3.3.jar;." edu/pku/openqa/EntryPoint