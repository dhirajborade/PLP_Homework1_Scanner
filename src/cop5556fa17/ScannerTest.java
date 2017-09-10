/**
 * /**
 * JUunit tests for the Scanner for the class project in COP5556 Programming Language Principles 
 * at the University of Florida, Fall 2017.
 * 
 * This software is solely for the educational benefit of students 
 * enrolled in the course during the Fall 2017 semester.  
 * 
 * This software, and any software derived from it,  may not be shared with others or posted to public web sites,
 * either during the course or afterwards.
 * 
 *  @Beverly A. Sanders, 2017
 */

package cop5556fa17;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556fa17.Scanner.LexicalException;
import cop5556fa17.Scanner.Token;

import static cop5556fa17.Scanner.Kind.*;

public class ScannerTest {

	// set Junit to be able to catch exceptions
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// To make it easy to print objects and turn this output on and off
	static final boolean doPrint = true;

	private void show(Object input) {
		if (doPrint) {
			System.out.println(input.toString());
		}
	}

	/**
	 * Retrieves the next token and checks that it is an EOF token. Also checks
	 * that this was the last token.
	 * 
	 * @param scanner
	 * @return the Token that was retrieved
	 */

	Token checkNextIsEOF(Scanner scanner) {
		Scanner.Token token = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF, token.kind);
		assertFalse(scanner.hasTokens());
		return token;
	}

	/**
	 * Retrieves the next token and checks that its kind, position, length,
	 * line, and position in line match the given parameters.
	 * 
	 * @param scanner
	 * @param kind
	 * @param pos
	 * @param length
	 * @param line
	 * @param pos_in_line
	 * @return the Token that was retrieved
	 */
	Token checkNext(Scanner scanner, Scanner.Kind kind, int pos, int length,
			int line, int pos_in_line) {
		Token t = scanner.nextToken();
		assertEquals(scanner.new Token(kind, pos, length, line, pos_in_line), t);
		return t;
	}

	/**
	 * Retrieves the next token and checks that its kind and length match the
	 * given parameters. The position, line, and position in line are ignored.
	 * 
	 * @param scanner
	 * @param kind
	 * @param length
	 * @return the Token that was retrieved
	 */
	Token check(Scanner scanner, Scanner.Kind kind, int length) {
		Token t = scanner.nextToken();
		assertEquals(kind, t.kind);
		assertEquals(length, t.length);
		return t;
	}

	/**
	 * Simple test case with a (legal) empty program
	 * 
	 * @throws LexicalException
	 */
	@Test
	public void testEmpty() throws LexicalException {
		String input = ""; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and
														// initialize it
		show(scanner); // Display the Scanner
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	/**
	 * Test illustrating how to put a new line in the input program and how to
	 * check content of tokens.
	 * 
	 * Because we are using a Java String literal for input, we use \n for the
	 * end of line character. (We should also be able to handle \n, \r, and \r\n
	 * properly.)
	 * 
	 * Note that if we were reading the input from a file, as we will want to do
	 * later, the end of line character would be inserted by the text editor.
	 * Showing the input will let you check your input is what you think it is.
	 * 
	 * @throws LexicalException
	 */
	@Test
	public void testSemi() throws LexicalException {
		String input = ";;\n;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 3, 1, 2, 1);
		checkNext(scanner, SEMI, 4, 1, 2, 2);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testCarrageReturn() throws LexicalException {
		String input = ";;\r;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 3, 1, 2, 1);
		checkNext(scanner, SEMI, 4, 1, 2, 2);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testSemiCarrageReturnNewLine() throws LexicalException {
		String input = ";;\r\n;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 4, 1, 2, 1);
		checkNext(scanner, SEMI, 5, 1, 2, 2);
		checkNextIsEOF(scanner);
	}

	/**
	 * This example shows how to test that your scanner is behaving when the
	 * input is illegal. In this case, we are giving it a String literal that is
	 * missing the closing ".
	 * 
	 * Note that the outer pair of quotation marks delineate the String literal
	 * in this test program that provides the input to our Scanner. The
	 * quotation mark that is actually included in the input must be escaped,
	 * \".
	 * 
	 * The example shows catching the exception that is thrown by the scanner,
	 * looking at it, and checking its contents before rethrowing it. If caught
	 * but not rethrown, then JUnit won't get the exception and the test will
	 * fail.
	 * 
	 * The test will work without putting the try-catch block around new
	 * Scanner(input).scan(); but then you won't be able to check or display the
	 * thrown exception.
	 * 
	 * @throws LexicalException
	 */
	@Test
	public void failUnclosedStringLiteral() throws LexicalException {
		String input = "\" greetings  ";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a
												// LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) { //
			show(e);
			assertEquals(13, e.getPos());
			throw e;
		}
	}

	/**
	 * This example tests the assign and the equals operator in the DFA tree
	 */
	@Test
	public void equalsTest() throws LexicalException {
		String input = ";;==;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, OP_EQ, 2, 2, 1, 3);
		checkNext(scanner, SEMI, 4, 1, 1, 5);
		checkNext(scanner, SEMI, 5, 1, 1, 6);
		checkNextIsEOF(scanner);
	}

	@Test
	public void assignLiteralTest() throws LexicalException {
		String input = ";;=;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, OP_ASSIGN, 2, 1, 1, 3);
		checkNext(scanner, SEMI, 3, 1, 1, 4);
		checkNext(scanner, SEMI, 4, 1, 1, 5);
		checkNextIsEOF(scanner);
	}

	/*
	 * The following test case test comments and division operator branch of the
	 * DFA tree
	 */
	@Test
	public void commentTest() throws LexicalException {
		String input = "%//the\n;@;==;\n\n==/=";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, OP_MOD, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 7, 1, 2, 1);
		checkNext(scanner, OP_AT, 8, 1, 2, 2);
		checkNext(scanner, SEMI, 9, 1, 2, 3);
		checkNext(scanner, OP_EQ, 10, 2, 2, 4);
		checkNext(scanner, SEMI, 12, 1, 2, 6);
		checkNext(scanner, OP_EQ, 15, 2, 4, 1);
		checkNext(scanner, OP_DIV, 17, 1, 4, 3);
		checkNext(scanner, OP_ASSIGN, 18, 1, 4, 4);
		checkNextIsEOF(scanner);
	}

	// The following test case tests lessthan, lessthan equals, leftarrow, tabs
	// and white spaces
	@Test
	public void LeftAngleBracketTest() throws LexicalException {
		String input = "%//the\n;<;<=;\n\n<-/=\t :";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, OP_MOD, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 7, 1, 2, 1);
		checkNext(scanner, OP_LT, 8, 1, 2, 2);
		checkNext(scanner, SEMI, 9, 1, 2, 3);
		checkNext(scanner, OP_LE, 10, 2, 2, 4);
		checkNext(scanner, SEMI, 12, 1, 2, 6);
		checkNext(scanner, OP_LARROW, 15, 2, 4, 1);
		checkNext(scanner, OP_DIV, 17, 1, 4, 3);
		checkNext(scanner, OP_ASSIGN, 18, 1, 4, 4);
		checkNext(scanner, OP_COLON, 21, 1, 4, 7);
		checkNextIsEOF(scanner);
	}

	// The following test case tests gtaterthan and gtaterthanequals
	@Test
	public void RightAngleBracketTest() throws LexicalException {
		String input = "%++>@@>==";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, OP_MOD, 0, 1, 1, 1);
		checkNext(scanner, OP_PLUS, 1, 1, 1, 2);
		checkNext(scanner, OP_PLUS, 2, 1, 1, 3);
		checkNext(scanner, OP_GT, 3, 1, 1, 4);
		checkNext(scanner, OP_AT, 4, 1, 1, 5);
		checkNext(scanner, OP_AT, 5, 1, 1, 6);
		checkNext(scanner, OP_GE, 6, 2, 1, 7);
		checkNext(scanner, OP_ASSIGN, 8, 1, 1, 9);
		checkNextIsEOF(scanner);
	}

	// The following test case tests NOT and NOT EQUALS operator
	@Test
	public void ExclaimationTest() throws LexicalException {
		String input = "%++!@@!==";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, OP_MOD, 0, 1, 1, 1);
		checkNext(scanner, OP_PLUS, 1, 1, 1, 2);
		checkNext(scanner, OP_PLUS, 2, 1, 1, 3);
		checkNext(scanner, OP_EXCL, 3, 1, 1, 4);
		checkNext(scanner, OP_AT, 4, 1, 1, 5);
		checkNext(scanner, OP_AT, 5, 1, 1, 6);
		checkNext(scanner, OP_NEQ, 6, 2, 1, 7);
		checkNext(scanner, OP_ASSIGN, 8, 1, 1, 9);
		checkNextIsEOF(scanner);
	}

	// The following test case tests NOT and NOT EQUALS operator
	@Test
	public void MinusTest() throws LexicalException {
		String input = "%++-@@->=";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, OP_MOD, 0, 1, 1, 1);
		checkNext(scanner, OP_PLUS, 1, 1, 1, 2);
		checkNext(scanner, OP_PLUS, 2, 1, 1, 3);
		checkNext(scanner, OP_MINUS, 3, 1, 1, 4);
		checkNext(scanner, OP_AT, 4, 1, 1, 5);
		checkNext(scanner, OP_AT, 5, 1, 1, 6);
		checkNext(scanner, OP_RARROW, 6, 2, 1, 7);
		checkNext(scanner, OP_ASSIGN, 8, 1, 1, 9);
		checkNextIsEOF(scanner);
	}

	// The following test case tests TIMES and POWER operator
	@Test
	public void MULTest() throws LexicalException {
		String input = "%++*@@**=";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, OP_MOD, 0, 1, 1, 1);
		checkNext(scanner, OP_PLUS, 1, 1, 1, 2);
		checkNext(scanner, OP_PLUS, 2, 1, 1, 3);
		checkNext(scanner, OP_TIMES, 3, 1, 1, 4);
		checkNext(scanner, OP_AT, 4, 1, 1, 5);
		checkNext(scanner, OP_AT, 5, 1, 1, 6);
		checkNext(scanner, OP_POWER, 6, 2, 1, 7);
		checkNext(scanner, OP_ASSIGN, 8, 1, 1, 9);
		checkNextIsEOF(scanner);
	}

	// The following test case Tests for integer literals
	@Test
	public void IntLiteralTest() throws LexicalException {
		String input = "%++0@@\n01230=";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, OP_MOD, 0, 1, 1, 1);
		checkNext(scanner, OP_PLUS, 1, 1, 1, 2);
		checkNext(scanner, OP_PLUS, 2, 1, 1, 3);
		checkNext(scanner, INTEGER_LITERAL, 3, 1, 1, 4);
		checkNext(scanner, OP_AT, 4, 1, 1, 5);
		checkNext(scanner, OP_AT, 5, 1, 1, 6);
		checkNext(scanner, INTEGER_LITERAL, 7, 1, 2, 1);
		checkNext(scanner, INTEGER_LITERAL, 8, 4, 2, 2);
		checkNext(scanner, OP_ASSIGN, 12, 1, 2, 6);
		checkNextIsEOF(scanner);
	}

	// The following test case tests for identifiers reserved words and keywords
	@Test
	public void IsReservedWord() throws LexicalException {
		String input = "%;akshay X a ;\r\n= true,0123";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, OP_MOD, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, IDENTIFIER, 2, 6, 1, 3);
		checkNext(scanner, KW_X, 9, 1, 1, 10);
		checkNext(scanner, KW_a, 11, 1, 1, 12);
		checkNext(scanner, SEMI, 13, 1, 1, 14);
		checkNext(scanner, OP_ASSIGN, 16, 1, 2, 1);
		checkNext(scanner, BOOLEAN_LITERAL, 18, 4, 2, 3);
		checkNext(scanner, COMMA, 22, 1, 2, 7);
		checkNext(scanner, INTEGER_LITERAL, 23, 1, 2, 8);
		checkNext(scanner, INTEGER_LITERAL, 24, 3, 2, 9);
		checkNextIsEOF(scanner);
	}

	// The following test cases test for valid string literals
	@Test
	public void StringLiteralTest1() throws LexicalException {
		String input = "x = \"Hi, how are you?\";";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, KW_x, 0, 1, 1, 1);
		checkNext(scanner, OP_ASSIGN, 2, 1, 1, 3);
		checkNext(scanner, STRING_LITERAL, 4, 18, 1, 5);
		checkNext(scanner, SEMI, 22, 1, 1, 23);
		checkNextIsEOF(scanner);

	}

	@Test
	public void StringLiteralTest2() throws LexicalException {
		String input = "x = \"John Doe said,\\\"Hi, How are you?\\\"\";";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, KW_x, 0, 1, 1, 1);
		checkNext(scanner, OP_ASSIGN, 2, 1, 1, 3);
		checkNext(scanner, STRING_LITERAL, 4, 36, 1, 5);
		checkNext(scanner, SEMI, 40, 1, 1, 41);
		checkNextIsEOF(scanner);

	}

	/**
	 * The test will work without putting the try-catch block around new
	 * Scanner(input).scan(); but then you won't be able to check or display the
	 * thrown exception.
	 * 
	 * @throws LexicalException
	 */
	@Test
	public void failStringLiteralEscapeSequence1() throws LexicalException {
		String input = "\" greetings \\ \"";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a
												// LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) { //
			show(e);
			assertEquals(12, e.getPos());
			throw e;
		}
	}

	@Test
	public void StringLiteralTest3() throws LexicalException {
		try {
			String input = "x=\"\b\";";
			Scanner scanner = new Scanner(input).scan();
			show(input);
			show(scanner);
			checkNext(scanner, KW_x, 0, 1, 1, 1);
			checkNext(scanner, OP_ASSIGN, 1, 1, 1, 2);
			checkNext(scanner, STRING_LITERAL, 2, 3, 1, 3);
			checkNext(scanner, SEMI, 5, 1, 1, 6);
			checkNextIsEOF(scanner);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void StringLiteralTest4() throws LexicalException {
		try {
			String input = "x=xx;";
			Scanner scanner = new Scanner(input).scan();
			show(input);
			show(scanner);
			checkNext(scanner, KW_x, 0, 1, 1, 1);
			checkNext(scanner, OP_ASSIGN, 1, 1, 1, 2);
			checkNext(scanner, IDENTIFIER, 2, 2, 1, 3);
			checkNext(scanner, SEMI, 4, 1, 1, 5);
			checkNextIsEOF(scanner);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void assignLiteralTest1() throws LexicalException {
		String input = "int var_name = 10;	//Comments here\nstring\tsomething = \"Testing input...\\n\\\"Input Compiled Successfully\\\"->Yes\\n\\\'Did not compile\\\'->No\\n\";\n";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, KW_int, 3);
		check(scanner, IDENTIFIER, 8);
		check(scanner, OP_ASSIGN, 1);
		check(scanner, INTEGER_LITERAL, 2);
		check(scanner, SEMI, 1);
		check(scanner, IDENTIFIER, 6);
		check(scanner, IDENTIFIER, 9);
		check(scanner, OP_ASSIGN, 1);
		check(scanner, STRING_LITERAL, 83);
		check(scanner, SEMI, 1);

		checkNextIsEOF(scanner);
	}

	// The following test case Tests for String literals
	@Test
	public void StringLiteralTest() throws LexicalException {
		String input = "string var_name = \"She said,\\\"Welcome home\\\".\\nNow,goodbye\\n\";";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0, 6, 1, 1);
		checkNext(scanner, IDENTIFIER, 7, 8, 1, 8);
		checkNext(scanner, OP_ASSIGN, 16, 1, 1, 17);
		checkNext(scanner, STRING_LITERAL, 18, 43, 1, 19);
		check(scanner, SEMI, 1);
		checkNextIsEOF(scanner);
	}

	// The following test case Tests for illegal String literals
	@Test
	public void IllegalStringLiteralTest() throws LexicalException {
		String input = "string var_name = \"She said,\\\"Welcome home\\\".\nNow,goodbye\\n\";";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) {
			show(e);
			throw e;
		}
	}

	// The following test case Tests for illegal alphabets
	@Test
	public void IllegalCharactersTest() throws LexicalException {
		String input = "hello;;\nint letsFail \\ done";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) {
			show(e);
			throw e;
		}
	}

	// The following test case Tests for illegal integer literals
	@Test
	public void IllegalIntLiteralTest() throws LexicalException {
		String input = "0%@123 2147483648 ";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) {
			show(e);
			throw e;
		}
	}

	// This will test the scanner for non-ASCII characters
	@Test
	public void IllegalUnicodeTest() throws LexicalException {
		StringBuilder str = new StringBuilder();
		str.append((char) 68);
		str.append((char) 113);
		str.append((char) -1);
		str.append((char) 206);
		str.append((char) 78);
		show(str);
		thrown.expect(LexicalException.class);
		try {
			new Scanner(str.toString()).scan();
		} catch (LexicalException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void failStringLiteralEscapeSequence11() throws LexicalException {
		String input = "\" greetings \\ \"";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a
												// LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) { //
			show(e);
			assertEquals(12, e.getPos());
			throw e;
		}
	}

}