# Where Is My Judgment

This is a desktop application that parses data about judgments of different types of Polish courts and allows you to obtain data about them (either specific judgment or aggregated). It features a Swing terminal-like GUI which accepts pre-defined commands and shows the output. 

## Getting started

### Downloading judgment files

Before using the app, you should get some judgment files. Those can be as follows:
* JSON files downloaded from [SAOS](https://www.saos.org.pl/help/index.php/dokumentacja-api/api-pobierania-danych) (System of Court Judgments Analysis) for Common courts, Supreme Court, National Appeal Chamber and Constitutional Tribunal rulings.
* HTML files downloaded from [CBOSA](http://orzeczenia.nsa.gov.pl) (Central Base of Administrative Courts Judgments) for Regional and Supreme Administrative Court rulings.

Those files should be saved locally on your computer.

### Running the application

Two command-line arguments are taken:
* The first one is a path to a directory where above files are stored. They can also be placed in subdirectories (so you should avoid storing any other files there).
* The second one is optional - it is a path where output files are stored. Their content is essentially the same as what you will see on screen.

Should the directory with judgments be specified correctly, the program will load the files and display a confirmation.

## Using the application

Commands can be introduced in the field at the bottom of the window and accepted by pressing Enter key. *help* command will provide you with a detailed information on how to use them (the command can be followed with another command's name - then it will display information just about this specific command). Some examples:

```
judges 15
```
Shows top 15 judges with most judgments.

```
judge Wojciech Katner
```
Shows the number of judgments of a specific judge - Wojciech Katner in this case.

```
rubrum "Kp 1/11" "AmC XVII 380/12"
```
Shows general information about any number of court cases.

Previously used commands can be browsed through and modified by using up and down arrow keys (just like in Linux terminal).

## Created with

### External tools
* [jsoup](https://jsoup.org/) for parsing HTMLs
* [Gson](https://github.com/google/gson) for parsing JSONs
* [Gradle](http://gradle.org/) - build tool
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE.

### Java features
* Streams and lambdas
* Reflection
* NIO
* Swing (learnt *ad hoc* to make possible creating command history).

(To name the less common ones)

### Design patterns
* **Command**
* **Singleton** for providing consistent *JudgmentDatabase* object for all classes.

## Deficiencies

The list of features that still need improving includes:
* The *JudgmentDatabase* class that accepts, checks correctness and merges some judgments is too much for one class to handle
* Adding parameters to commands involves manually changing a help message for this command
* Messy code of *JudgmentFromHTMLCreator* class
* Issues related to GUI: Some cases of not-too-correct usage of commands spawn irrelevant error messages. Also some error messages are too technical for regular user to understand. Meanwhile, *months* command introduces English into Polish language application. Also input field can be buggy at times.

## Acknowledgements

I would like to thank [Dr. Aleksander Smywiński-Pohl](https://github.com/apohllo) for proposing a very developping project, providing advice for it and, last but not least, for teaching the best classes and lectures I have participanted in at the university till this day.
