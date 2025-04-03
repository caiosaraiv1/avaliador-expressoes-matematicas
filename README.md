# Mathematical Expressions Evaluator

A Java-based REPL (Read-Evaluate-Print-Loop) application that evaluates mathematical expressions with variables and operations. This project was developed as part of a Data Structures course assignment.

## Features

- **Expression Evaluation**: Supports infix mathematical expressions with conversion to postfix notation
- **Variable Management**: Store and manipulate variables (A-Z) with numerical values
- **Command System**: Various commands to manage variables and recordings
- **Recording Functionality**: Record, play, and manage sequences of commands

## Commands

| Command | Description |
|---------|-------------|
| `VARS` | Lists all defined variables and their values |
| `RESET` | Resets all variables |
| `REC` | Starts recording commands (up to 10) |
| `STOP` | Stops the recording |
| `PLAY` | Plays back recorded commands |
| `ERASE` | Erases all recorded commands |
| `EXIT` | Exits the application |

## Mathematical Operations

The application supports the following operations:
- Addition (`+`)
- Subtraction (`-`)
- Multiplication (`*`)
- Division (`/`)
- Exponentiation (`^`)

## Usage Examples

```
> X = 10
X = 10.00
> Y = 5
Y = 5.00
> X + Y
15.00
> X * (Y - 2)
30.00
> VARS
X = 10.0
Y = 5.0
> REC
Starting recording... (REC: 0/10)
> Z = 20
(REC: 1/10) Z = 20
> X + Y + Z
(REC: 2/10) X + Y + Z
> STOP
Ending recording... (2/10)
> PLAY
Playing recording...
Z = 20
X + Y + Z
35.0
```

## Project Structure

- **Main**: The entry point for the application
- **CommandManager**: Manages and processes user commands
- **VariableManager**: Handles variable storage and operations
- **EquationEvaluator**: Validates, converts, and calculates mathematical expressions
- **Stack**: Custom implementation of a stack data structure
- **Queue**: Custom implementation of a queue data structure

## Technical Details

- **Expression Validation**: Checks for syntax errors, unbalanced parentheses, and invalid operators
- **Infix to Postfix Conversion**: Transforms standard notation to postfix for easier evaluation
- **Data Structures**: Uses custom Stack and Queue implementations

## Requirements

- Java 8 or higher
- No external dependencies required

## Installation

1. Clone the repository
2. Compile the Java files
   ```
   javac apl1/*.java
   ```
3. Run the application
   ```
   java apl1.Main
   ```

## Contributors

- Caio Ariel
- Isabela Hissa
- Kaique Paiva
