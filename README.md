# GMParser

*Parser and grammar analyzer*

- - -

GMParser is a command-line based application that allows the user to
parse a word, analyze a grammar or transform a grammar.
Whatever the selected operation, the application will provide the user with
a comprehensive output to enjoy the art of formal languages and parsing.

## Installation
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

## Usage
Simply run the .jar with neither arguments nor options to access the
GMParser main view.

> (java -jar) gmparser.jar

A handy menu-based navigation will guide the user in the use of the app.

### NOTE: EMPTY WORD (ε)
The empty word (epsilon) is coded by the symbol ε (Unicode: 03b5).
So, to insert the empty word, the user should escape the Unicode,
as usual:

> (Ctrl+Shift+u then type '03b5' then press the space key).

### GRAMMAR ANALYSIS
A grammar analysis is a complete report about the specified grammar.
The following parameters are available:
- axiom
- epsilon;
- non terminal alphabet
- terminal alphabet
- set of productions
- grammar type (Chomsky hierarchy)
- grammar extension (Extended/S-Extended/None)
- grammar normal form (Chomsky/Greibach/None)
- subset of epsilon productions
- subset of unit productions
- subset of non trivial unit productions
- subset of trivial unit productions
- subset of nullable symbols
- subset of ungenerative symbols
- subset of unreacheable symbols
- subset of useless symbols

> (java -jar) gmparser.jar -a YOUR_GRAMMAR

### GRAMMAR TRANSFORMATIONS
The following transformations are available:
- Generation of Chomsky Normal Form (CYK)
- Removal of ungenerative symbols (RGS)
- Removal of unreacheable symbols (RRS)
- Removal of useless symbols (RUS)
- Removal of epsilon productions (REP)
- Removal of unit productions (RUP)

> (java -jar) gmparser.jar -t YOUR_TRANSFORMATION YOUR_GRAMMAR

### PARSING
The following parsers are available:
- Cocke-Younger-Kasami parser (CYK)
- LR(1) parser (LR1)

> (java -jar) gmparser.jar -p YOUR_PARSER YOUR_WORD YOUR_GRAMMAR

# DOCUMENTATION
The code is fully documented. Particular attention has been given to the documentation on the most critical and interesting portions of code. To facilitate the code navigability, had been inserted bookmarks in correspondence with the aforesaid portions (available by the Eclipse 'Bookmarks' view). JavaDocs available at http://gmarciani.com/project/gmparser/javadoc/index.html

## TEST
The tests were run on a 2 GHz Intel i5 with 2GB of RAM, running Linux Ubuntu 14.04 and Java 1.7. Every JUnit-based test suite is available in package gmparser.test. Tests output related to parsing session, both on Cocke-Younger-Kasami and LR(1) parser are available in folder /test.

## Authors
Giacomo Marciani, [giacomo.marciani@gmail.com](mailto:giacomo.marciani@gmail.com)

# REFERENCES
"Automata Theory and Formal Languages" (3rd edition), A.Pettorossi
"Techniques for Searching, Parsing and Matching" (3rd edition), A.Pettorossi

## Contributing
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

## License
GMParser is released under the [MIT License](https://opensource.org/licenses/MIT).
Please, read the file LICENSE.md for details.
