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

import cop5556fa17.Scanner.Kind;
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
	 * Retrieves the next token and checks that it is an EOF token. Also checks that
	 * this was the last token.
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
	 * Retrieves the next token and checks that its kind, position, length, line,
	 * and position in line match the given parameters.
	 * 
	 * @param scanner
	 * @param kind
	 * @param pos
	 * @param length
	 * @param line
	 * @param pos_in_line
	 * @return the Token that was retrieved
	 */
	Token checkNext(Scanner scanner, Scanner.Kind kind, int pos, int length, int line, int pos_in_line) {
		Token t = scanner.nextToken();
		assertEquals(scanner.new Token(kind, pos, length, line, pos_in_line), t);
		return t;
	}

	/**
	 * Retrieves the next token and checks that its kind and length match the given
	 * parameters. The position, line, and position in line are ignored.
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
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	/**
	 * Test illustrating how to put a new line in the input program and how to check
	 * content of tokens.
	 * 
	 * Because we are using a Java String literal for input, we use \n for the end
	 * of line character. (We should also be able to handle \n, \r, and \r\n
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

	/**
	 * This example shows how to test that your scanner is behaving when the input
	 * is illegal. In this case, we are giving it a String literal that is missing
	 * the closing ".
	 * 
	 * Note that the outer pair of quotation marks delineate the String literal in
	 * this test program that provides the input to our Scanner. The quotation mark
	 * that is actually included in the input must be escaped, \".
	 * 
	 * The example shows catching the exception that is thrown by the scanner,
	 * looking at it, and checking its contents before rethrowing it. If caught but
	 * not rethrown, then JUnit won't get the exception and the test will fail.
	 * 
	 * The test will work without putting the try-catch block around new
	 * Scanner(input).scan(); but then you won't be able to check or display the
	 * thrown exception.
	 * 
	 * @throws LexicalException
	 */
	// @Test
	public void failUnclosedStringLiteral() throws LexicalException {
		String input = "\" greetings  ";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) { //
			show(e);
			assertEquals(13, e.getPos());
			throw e;
		}
	}

	@Test
	public void testPlus() throws LexicalException {
		String input = "+"; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, OP_PLUS, 0, 1, 1, 1);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testTimes() throws LexicalException {
		String input = "*"; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, OP_TIMES, 0, 1, 1, 1);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testAssign() throws LexicalException {
		String input = "="; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, OP_ASSIGN, 0, 1, 1, 1);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testEq() throws LexicalException {
		String input = "=="; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, OP_EQ, 0, 2, 1, 1);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testTripleEq() throws LexicalException {
		String input = "==="; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, OP_EQ, 0, 2, 1, 1);
		checkNext(scanner, OP_ASSIGN, 2, 1, 1, 3);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testOpwithspace() throws LexicalException {
		String input = "     ==="; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, OP_EQ, 5, 2, 1, 6);
		checkNext(scanner, OP_ASSIGN, 7, 1, 1, 8);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testident1() throws LexicalException {
		String input = "sin Def"; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, KW_sin, 0, 3, 1, 1);
		checkNext(scanner, IDENTIFIER, 4, 3, 1, 5);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testOpIdent() throws LexicalException {
		String input = "absDef="; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, IDENTIFIER, 0, 6, 1, 1);
		checkNext(scanner, OP_ASSIGN, 6, 1, 1, 7);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testboolxequalstrue() throws LexicalException {
		String input = "boolean x=true"; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		check(scanner, KW_boolean, 7);
		check(scanner, KW_x, 1);
		check(scanner, OP_ASSIGN, 1);
		check(scanner, BOOLEAN_LITERAL, 4);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testdigit() throws LexicalException {
		String input = "01234"; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		check(scanner, INTEGER_LITERAL, 1);
		check(scanner, INTEGER_LITERAL, 4);
		// check(scanner, KW_x,1);
		// check(scanner, OP_ASSIGN,1);
		// check(scanner, BOOLEAN_LITERAL,4);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testTokensWithSpace() throws LexicalException {
		String input = "_abc>=";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, IDENTIFIER, 4);
		check(scanner, OP_GE, 2);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testunderscore() throws LexicalException {
		String input = "_abc>";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, IDENTIFIER, 4);
		check(scanner, OP_GT, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testseparator() throws LexicalException {
		String input = ",abc>";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, COMMA, 1);
		check(scanner, IDENTIFIER, 3);
		check(scanner, OP_GT, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testtab() throws LexicalException {
		String input = ";;\t;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 3, 1, 1, 4);
		checkNext(scanner, SEMI, 4, 1, 1, 5);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testescapeseq() throws LexicalException {
		String input = ";;\t;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 3, 1, 1, 4);
		checkNext(scanner, SEMI, 4, 1, 1, 5);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testbadevalasequence() throws LexicalException {
		String input = "abCde+-09843\tabas";

		Scanner scanner = new Scanner(input).scan();

		show(input);
		show(scanner);

		check(scanner, IDENTIFIER, 5);
		check(scanner, OP_PLUS, 1);
		check(scanner, OP_MINUS, 1);
		check(scanner, INTEGER_LITERAL, 1);
		check(scanner, INTEGER_LITERAL, 4);
		check(scanner, IDENTIFIER, 4);

		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringliteral() throws LexicalException {
		String input = "\"sangwan\"";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, STRING_LITERAL, 9);

		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringliteralslashrn() throws LexicalException {
		String input = "\"abcd\refgh\"";
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(input);
		} catch (LexicalException e) {
			show(e);
			assertEquals(5, e.getPos());
			throw e;
		}

	}

	@Test
	public void testStringliteralslasht() throws LexicalException {
		String input = "\"abcd\tefgh\"";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, STRING_LITERAL, 11);

		checkNextIsEOF(scanner);

	}

	@Test
	public void testStringliteralmultipleslashes() throws LexicalException {
		String input = "\"abcd\\tefgh\"";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, STRING_LITERAL, 12);
		checkNextIsEOF(scanner);

	}

	@Test
	public void testStringliteralmultipleslashesinend() throws LexicalException {
		String input = "\"abcd\\\"";
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(input);
		} catch (LexicalException e) {
			show(e);
			assertEquals(7, e.getPos());
			throw e;
		}

	}

	@Test
	public void testStringliteralslashrandslashn() throws LexicalException // \n\r bhi chalra hai
	{
		String input = "abcd\r\nefgh";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, IDENTIFIER, 4);
		check(scanner, IDENTIFIER, 4);// alag alag identifier hojaate hain kya \n ki vjh se
		checkNextIsEOF(scanner);

	}

	// ab ye hain docs vaale test
	//
	//
	//
	//
	/////////////////////////////

	@Test
	public void check_ForLineChange() throws LexicalException {
		Scanner scanner = new Scanner("\n").scan();
		checkNextIsEOF(scanner);
		scanner = new Scanner("\r\n").scan();
		checkNextIsEOF(scanner);
		scanner = new Scanner("\r").scan();
		checkNextIsEOF(scanner);
		scanner = new Scanner("\r\r").scan();
		checkNextIsEOF(scanner);
	}

	@Test
	public void check_ForWhiteSpaces() throws LexicalException {
		Scanner scanner = new Scanner("  ").scan();
		checkNext(scanner, Kind.EOF, 2, 0, 1, 3);
		scanner = new Scanner("\t").scan();
		checkNext(scanner, Kind.EOF, 1, 0, 1, 2);
		scanner = new Scanner("\f").scan();
		checkNext(scanner, Kind.EOF, 1, 0, 1, 2);
	}

	@Test
	public void check_ForComments() throws LexicalException {
		Scanner scanner = new Scanner("//aman").scan();
		checkNextIsEOF(scanner);
		scanner = new Scanner("//aman \n").scan();
		checkNextIsEOF(scanner);
		scanner = new Scanner("//aman \n\r").scan();
		checkNextIsEOF(scanner);
	}

	@Test
	public void check_ForOperators() throws LexicalException {
		Scanner scanner = new Scanner("== =\n?").scan();
		checkNext(scanner, Kind.OP_EQ, 0, 2, 1, 1);
		checkNext(scanner, Kind.OP_ASSIGN, 3, 1, 1, 4);
		checkNext(scanner, Kind.OP_Q, 5, 1, 2, 1);
		checkNext(scanner, Kind.EOF, 6, 0, 2, 2);
	}

	@Test
	public void check_ForSeparators() throws LexicalException {
		Scanner scanner = new Scanner("() []").scan();
		checkNext(scanner, Kind.LPAREN, 0, 1, 1, 1);
		checkNext(scanner, Kind.RPAREN, 1, 1, 1, 2);
		checkNext(scanner, Kind.LSQUARE, 3, 1, 1, 4);
		checkNext(scanner, Kind.RSQUARE, 4, 1, 1, 5);
		checkNext(scanner, Kind.EOF, 5, 0, 1, 6);
	}

	@Test
	public void check_ForValidIntegerLiteral() throws LexicalException {
		Scanner scanner = new Scanner("0 1234").scan();
		checkNext(scanner, Kind.INTEGER_LITERAL, 0, 1, 1, 1);
		checkNext(scanner, Kind.INTEGER_LITERAL, 2, 4, 1, 3);
		checkNext(scanner, Kind.EOF, 6, 0, 1, 7);
	}

	@Test
	public void check_validIntegerLiteral_WhenStartWithZero() throws LexicalException {
		Scanner scanner = new Scanner("0123").scan();
		checkNext(scanner, Kind.INTEGER_LITERAL, 0, 1, 1, 1);
		checkNext(scanner, Kind.INTEGER_LITERAL, 1, 3, 1, 2);
	}

	@Test
	public void check_ForIdentifier() throws LexicalException {
		Scanner scanner = new Scanner("abcd $abc _abcd __ a0").scan();
		checkNext(scanner, Kind.IDENTIFIER, 0, 4, 1, 1);
		checkNext(scanner, Kind.IDENTIFIER, 5, 4, 1, 6);
		checkNext(scanner, Kind.IDENTIFIER, 10, 5, 1, 11);
		checkNext(scanner, Kind.IDENTIFIER, 16, 2, 1, 17);
		checkNext(scanner, Kind.IDENTIFIER, 19, 2, 1, 20);
	}

	@Test
	public void check_ForKeyWord() throws LexicalException {
		Scanner scanner = new Scanner("a boolean").scan();
		checkNext(scanner, Kind.KW_a, 0, 1, 1, 1);
		checkNext(scanner, Kind.KW_boolean, 2, 7, 1, 3);
	}

	@Test
	public void check_ForBooleanLiteral() throws LexicalException {
		Scanner scanner = new Scanner("true false").scan();
		checkNext(scanner, Kind.BOOLEAN_LITERAL, 0, 4, 1, 1);
		checkNext(scanner, Kind.BOOLEAN_LITERAL, 5, 5, 1, 6);
	}

	@Test
	public void check_ForStringLiteral() throws LexicalException {
		Scanner scanner = new Scanner("\"aman\"").scan();
		checkNext(scanner, Kind.STRING_LITERAL, 0, 6, 1, 1);
	}

	@Test
	public void check_ForStringLiteral_WithEscapeChars() throws LexicalException {
		Scanner scanner = new Scanner("\"abc\\\"\"").scan();
		show("\"abc\\\"\"");
		checkNext(scanner, Kind.STRING_LITERAL, 0, 7, 1, 1);
	}

	@Test
	public void failUnclosedStringLiteral2() throws LexicalException {
		String input = "\" greetings  ";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) {
			show(e);
			assertEquals(13, e.getPos());
			throw e;
		}
	}

	@Test
	public void testComment1() throws LexicalException {
		String input = ";;//abcd\nmno";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, IDENTIFIER, 9, 3, 2, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testIntLiteral() throws LexicalException {
		String input = "123+456";
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		check(scanner, INTEGER_LITERAL, 3);
		check(scanner, OP_PLUS, 1);
		check(scanner, INTEGER_LITERAL, 3);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testCorrectIdentifier() throws LexicalException {
		String input = "boolean";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, KW_boolean, 7);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testCorrectIdentifierWithValues() throws LexicalException {
		String input = "boolean x=true";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, KW_boolean, 7);
		check(scanner, KW_x, 1);
		check(scanner, OP_ASSIGN, 1);
		check(scanner, BOOLEAN_LITERAL, 4);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testTokensWithSpace2() throws LexicalException {
		String input = "$Bansari 123";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, IDENTIFIER, 8);
		check(scanner, INTEGER_LITERAL, 3);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testTokensWithEscape() throws LexicalException {
		String input = "\"man\\tgirl\"";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, STRING_LITERAL, 11);
		checkNextIsEOF(scanner);
	}

	@Test
	public void UnfailTokensWithEscape() throws LexicalException {
		String input = "\"man\tgirl\"";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, STRING_LITERAL, 10);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testTokensWithEscapewith() throws LexicalException {
		String input = "SCREEN(\"a\nb\");";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(9, e.getPos());
			throw e;
		}
	}

	@Test
	public void testStringLiteral() throws LexicalException {
		String input = "\"abc\"def";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, STRING_LITERAL, 5);
		check(scanner, IDENTIFIER, 3);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringLiterals() throws LexicalException {
		String input = "abc=d";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0, 3, 1, 1);
		check(scanner, OP_ASSIGN, 1);
		check(scanner, IDENTIFIER, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testNumLiterals() throws LexicalException {
		String input = "02345";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, INTEGER_LITERAL, 1);// ,3,1,1);
		check(scanner, INTEGER_LITERAL, 4);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testCommentWithNewline() throws LexicalException {
		String input = ";;//;;\n;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, SEMI, 1);
		check(scanner, SEMI, 1);
		check(scanner, SEMI, 1);
		check(scanner, SEMI, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testIntWithEqual() throws LexicalException {
		String input = "bansu\b=";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(5, e.getPos());
			throw e;
		}
	}

	@Test
	public void checkEOF() throws LexicalException {
		String input = "x=\"\b\";";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, KW_x, 0, 1, 1, 1);
		checkNext(scanner, OP_ASSIGN, 1, 1, 1, 2);
		checkNext(scanner, STRING_LITERAL, 2, 3, 1, 3);
		checkNext(scanner, SEMI, 5, 1, 1, 6);
		checkNextIsEOF(scanner);
	}

	@Test
	public void assignLiteralTest() throws LexicalException {
		String input = "int var_name = 10;    //Comments here\nstring\tsomething = \"Testing input...\\n\\\"Input Compiled Successfully\\\"->Yes\\n\\\'Did not compile\\\'->No\\n\";\n";
		show(input);
		Scanner scanner = new Scanner(input).scan();
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

	@Test
	public void testIntWithEqual123() throws LexicalException {
		String input = "\"ab\\\"def";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(8, e.getPos());
			throw e;
		}
	}

	@Test
	public void testInCorrectIntegerLit() throws LexicalException {
		String input = "9999999999999999999";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(0, e.getPos());
			throw e;
		}
	}

	@Test
	public void trial123() throws LexicalException {
		String input = "\" gr\\teetings \"abcd \"\"";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, STRING_LITERAL, 0, 15, 1, 1);
		checkNext(scanner, IDENTIFIER, 15, 4, 1, 16);
		checkNext(scanner, STRING_LITERAL, 20, 2, 1, 21);
		checkNextIsEOF(scanner);
	}

	public void testSemi2() throws LexicalException {
		String input = "00123a+";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, INTEGER_LITERAL, 0, 1, 1, 1);
		checkNext(scanner, INTEGER_LITERAL, 1, 1, 1, 2);
		checkNext(scanner, INTEGER_LITERAL, 2, 3, 1, 3);
		checkNext(scanner, KW_a, 5, 1, 1, 6);
		checkNext(scanner, OP_PLUS, 6, 1, 1, 7);
	}

	@Test
	public void test_integer_literal() throws LexicalException {
		String input = "123456";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, INTEGER_LITERAL, 6);
		checkNextIsEOF(scanner);
	}

	@Test
	public void test_comment() throws LexicalException {
		String input = "X//abc";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, KW_X, 0, 1, 1, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void test_number_keyword() throws LexicalException {
		String input = "00123a+";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, INTEGER_LITERAL, 0, 1, 1, 1);
		checkNext(scanner, INTEGER_LITERAL, 1, 1, 1, 2);
		checkNext(scanner, INTEGER_LITERAL, 2, 3, 1, 3);
		checkNext(scanner, KW_a, 5, 1, 1, 6);
		checkNext(scanner, OP_PLUS, 6, 1, 1, 7);
	}

	@Test
	public void test_Correct_KW_Identifier() throws LexicalException {
		String input = "boolean";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, KW_boolean, 7);
		checkNextIsEOF(scanner);
	}

	@Test
	public void test_Correct_Identifier() throws LexicalException {
		String input = "ABC";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, IDENTIFIER, 3);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testCorrectIdentifierWithValue() throws LexicalException {
		String input = "boolean x = true";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, KW_boolean, 7);
		check(scanner, KW_x, 1);
		check(scanner, OP_ASSIGN, 1);
		check(scanner, BOOLEAN_LITERAL, 4);
		checkNextIsEOF(scanner);
	}

	@Test
	public void test_twoKW() throws LexicalException {
		String input = "boolean x";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, KW_boolean, 7);
		check(scanner, KW_x, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void test_string_literal() throws LexicalException {
		String input = "\"PRACHI\"";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, STRING_LITERAL, 8);
		checkNextIsEOF(scanner);
	}

	@Test
	public void t4() throws LexicalException {
		String input = "\"PRA\nCHI\"";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) {
			show(e);
			assertEquals(4, e.getPos());
			throw e;
		}
	}

	@Test
	public void t42() throws LexicalException {
		String input = "\"abc\\\\ \"a";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		checkNext(scanner, STRING_LITERAL, 0, 8, 1, 1);
		checkNext(scanner, KW_a, 8, 1, 1, 9);
		checkNextIsEOF(scanner);
	}

	@Test
	public void t43() throws LexicalException {
		String input = "\"abc\\\\def \"a";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		checkNext(scanner, STRING_LITERAL, 0, 11, 1, 1);
		checkNext(scanner, KW_a, 11, 1, 1, 12);
		checkNextIsEOF(scanner);
	}

	@Test
	public void test_newline_in_strlit() throws LexicalException {
		String input = "\"PRA\\nCHI\"";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, STRING_LITERAL, 10);
		checkNextIsEOF(scanner);
	}

	@Test
	public void test_escape_seq() throws LexicalException {
		String input = "\"\t\"";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, STRING_LITERAL, 3);
		checkNextIsEOF(scanner);
	}

	@Test
	public void test_backbackslashesa() throws LexicalException {
		String input = "(\"\\a\")";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) {
			show(e);
			assertEquals(3, e.getPos());
			throw e;
		}
	}

	// @Test
	public void test_number_es() throws LexicalException {
		String input = "123\b";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) {
			show(e);
			assertEquals(4, e.getPos());
			throw e;
		}
	}

	@Test
	public void test_four_backslash() throws LexicalException {
		String input = "\"ab\\def\"";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) {
			show(e);
			assertEquals(3, e.getPos());
			throw e;
		}
	}

	@Test
	public void trial() throws LexicalException {
		String input = "&true@=";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, OP_AND, 1);
		check(scanner, BOOLEAN_LITERAL, 4);
		check(scanner, OP_AT, 1);
		check(scanner, OP_ASSIGN, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void LegalIdentifierStart() throws LexicalException {
		String input = "/*the*/";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, OP_DIV, 1);
		check(scanner, OP_TIMES, 1);
		check(scanner, IDENTIFIER, 3);
		check(scanner, OP_TIMES, 1);
		check(scanner, OP_DIV, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void TestCases() throws LexicalException {
		String input = "(\"\\a\")";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) {
			show(e);
			assertEquals(3, e.getPos());
			throw e;
		}
	}

	@Test
	public void testSemis() throws LexicalException {
		String input = "00123a+";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, INTEGER_LITERAL, 0, 1, 1, 1);
		checkNext(scanner, INTEGER_LITERAL, 1, 1, 1, 2);
		checkNext(scanner, INTEGER_LITERAL, 2, 3, 1, 3);
		checkNext(scanner, KW_a, 5, 1, 1, 6);
		checkNext(scanner, OP_PLUS, 6, 1, 1, 7);
		checkNextIsEOF(scanner);
	}

	@Test
	public void t6() throws LexicalException {
		String input = "_abc_";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		check(scanner, IDENTIFIER, 5);
		checkNextIsEOF(scanner);
	}

	@Test
	public void teststring() throws LexicalException {
		String input = "5===01234\"abc\"def";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		check(scanner, INTEGER_LITERAL, 1);
		check(scanner, OP_EQ, 2);
		check(scanner, OP_ASSIGN, 1);
		check(scanner, INTEGER_LITERAL, 1);
		check(scanner, INTEGER_LITERAL, 4);
		check(scanner, STRING_LITERAL, 5);
		check(scanner, IDENTIFIER, 3);
		checkNextIsEOF(scanner);
	}

	@Test
	public void t() throws LexicalException {
		String input = "x=\"\b\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		checkNext(scanner, KW_x, 0, 1, 1, 1);
		checkNext(scanner, OP_ASSIGN, 1, 1, 1, 2);
		checkNext(scanner, STRING_LITERAL, 2, 3, 1, 3);
		checkNext(scanner, SEMI, 5, 1, 1, 6);
		checkNextIsEOF(scanner);// EOF,,6,0,1,7);
	}

	@Test
	public void t1() throws LexicalException {
		String input = "b\bab";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(1, e.getPos());
			throw e;
		}
	}

	// @Test
	public void t5() throws LexicalException {
		String input = "0001234M+";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
	}

	@Test
	public void testComment() throws LexicalException {
		String input = "// This is a comment. // This is the second one\nint x=0;//Declaration of x\rint y=1;//Declaration of y\r\nString p=\"Done\";";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, KW_int, 48, 3, 2, 1);
		checkNext(scanner, KW_x, 52, 1, 2, 5);
		checkNext(scanner, OP_ASSIGN, 53, 1, 2, 6);
		checkNext(scanner, INTEGER_LITERAL, 54, 1, 2, 7);
		checkNext(scanner, SEMI, 55, 1, 2, 8);
		checkNext(scanner, KW_int, 75, 3, 3, 1);
		checkNext(scanner, KW_y, 79, 1, 3, 5);
		checkNext(scanner, OP_ASSIGN, 80, 1, 3, 6);
		checkNext(scanner, INTEGER_LITERAL, 81, 1, 3, 7);
		checkNext(scanner, SEMI, 82, 1, 3, 8);
		checkNext(scanner, IDENTIFIER, 103, 6, 4, 1);
		checkNext(scanner, IDENTIFIER, 110, 1, 4, 8);
		checkNext(scanner, OP_ASSIGN, 111, 1, 4, 9);
		checkNext(scanner, STRING_LITERAL, 112, 6, 4, 10);
		checkNext(scanner, SEMI, 118, 1, 4, 16);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testDigit() throws LexicalException {
		String input = "99 934\n0 2147483647\r657342\r\n-2147483647";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, INTEGER_LITERAL, 0, 2, 1, 1);
		checkNext(scanner, INTEGER_LITERAL, 3, 3, 1, 4);
		checkNext(scanner, INTEGER_LITERAL, 7, 1, 2, 1);
		checkNext(scanner, INTEGER_LITERAL, 9, 10, 2, 3);
		checkNext(scanner, INTEGER_LITERAL, 20, 6, 3, 1);
		checkNext(scanner, OP_MINUS, 28, 1, 4, 1);
		checkNext(scanner, INTEGER_LITERAL, 29, 10, 4, 2);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testDigitException() throws LexicalException {
		String input = "488b 47747458758798398\n";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(5, e.getPos());
			throw e;
		}
	}

	@Test
	public void testBoolean() throws LexicalException {
		String input = "true false\ntruefalse";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, BOOLEAN_LITERAL, 0, 4, 1, 1);
		checkNext(scanner, BOOLEAN_LITERAL, 5, 5, 1, 6);
		checkNext(scanner, IDENTIFIER, 11, 9, 2, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringLiteral1() throws LexicalException {
		String input = "this is \"A test \\b \\t \\n \\f \\r  \' for String \\n \\tLiteral...\"";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0, 4, 1, 1);
		checkNext(scanner, IDENTIFIER, 5, 2, 1, 6);
		checkNext(scanner, STRING_LITERAL, 8, 53, 1, 9);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringException() throws LexicalException {
		String input = "\"String has error because we have included \\ \"";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(44, e.getPos());
			throw e;
		}
	}

	@Test
	public void testLFInStringLiteral() throws LexicalException {
		String input = "This example , \"of newline \n is invalid. \" ";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) { //
			show(e);
			assertEquals(27, e.getPos());
			throw e;
		}
	}

	@Test
	public void testIdent() throws LexicalException {
		String input = "myName YOURn8m3 \r\n ____weirDD\n$Dollar0\rcarri3geR37urn";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0, 6, 1, 1);
		checkNext(scanner, IDENTIFIER, 7, 8, 1, 8);
		checkNext(scanner, IDENTIFIER, 19, 10, 2, 2);
		checkNext(scanner, IDENTIFIER, 30, 8, 3, 1);
		checkNext(scanner, IDENTIFIER, 39, 14, 4, 1);
		checkNextIsEOF(scanner);

	}

	@Test
	public void testBackspaceIdentifierToken() throws LexicalException {
		String input = "b\bab";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) { //
			show(e);
			assertEquals(1, e.getPos());
			throw e;
		}
	}

	@Test
	public void testSeparator() throws LexicalException {
		String input = "(  random var )\n[ more var ] parentheses[seperator] num(33(4)) 565788[8778] 8390(34)  ;  ,";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, LPAREN, 0, 1, 1, 1);
		checkNext(scanner, IDENTIFIER, 3, 6, 1, 4);
		checkNext(scanner, IDENTIFIER, 10, 3, 1, 11);
		checkNext(scanner, RPAREN, 14, 1, 1, 15);
		checkNext(scanner, LSQUARE, 16, 1, 2, 1);
		checkNext(scanner, IDENTIFIER, 18, 4, 2, 3);
		checkNext(scanner, IDENTIFIER, 23, 3, 2, 8);
		checkNext(scanner, RSQUARE, 27, 1, 2, 12);
		checkNext(scanner, IDENTIFIER, 29, 11, 2, 14);
		checkNext(scanner, LSQUARE, 40, 1, 2, 25);
		checkNext(scanner, IDENTIFIER, 41, 9, 2, 26);
		checkNext(scanner, RSQUARE, 50, 1, 2, 35);
		checkNext(scanner, IDENTIFIER, 52, 3, 2, 37);
		checkNext(scanner, LPAREN, 55, 1, 2, 40);
		checkNext(scanner, INTEGER_LITERAL, 56, 2, 2, 41);
		checkNext(scanner, LPAREN, 58, 1, 2, 43);
		checkNext(scanner, INTEGER_LITERAL, 59, 1, 2, 44);
		checkNext(scanner, RPAREN, 60, 1, 2, 45);
		checkNext(scanner, RPAREN, 61, 1, 2, 46);
		checkNext(scanner, INTEGER_LITERAL, 63, 6, 2, 48);
		checkNext(scanner, LSQUARE, 69, 1, 2, 54);
		checkNext(scanner, INTEGER_LITERAL, 70, 4, 2, 55);
		checkNext(scanner, RSQUARE, 74, 1, 2, 59);
		checkNext(scanner, INTEGER_LITERAL, 76, 4, 2, 61);
		checkNext(scanner, LPAREN, 80, 1, 2, 65);
		checkNext(scanner, INTEGER_LITERAL, 81, 2, 2, 66);
		checkNext(scanner, RPAREN, 83, 1, 2, 68);
		checkNext(scanner, SEMI, 86, 1, 2, 71);
		checkNext(scanner, COMMA, 89, 1, 2, 74);
		checkNextIsEOF(scanner);

	}

	@Test
	public void testOperator() throws LexicalException {
		String input = "898989*66767 = > < ! ? : == != <= >= & | + - * / % ** -> <- @";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, INTEGER_LITERAL, 0, 6, 1, 1);
		checkNext(scanner, OP_TIMES, 6, 1, 1, 7);
		checkNext(scanner, INTEGER_LITERAL, 7, 5, 1, 8);
		checkNext(scanner, OP_ASSIGN, 13, 1, 1, 14);
		checkNext(scanner, OP_GT, 15, 1, 1, 16);
		checkNext(scanner, OP_LT, 17, 1, 1, 18);
		checkNext(scanner, OP_EXCL, 19, 1, 1, 20);
		checkNext(scanner, OP_Q, 21, 1, 1, 22);
		checkNext(scanner, OP_COLON, 23, 1, 1, 24);
		checkNext(scanner, OP_EQ, 25, 2, 1, 26);
		checkNext(scanner, OP_NEQ, 28, 2, 1, 29);
		checkNext(scanner, OP_LE, 31, 2, 1, 32);
		checkNext(scanner, OP_GE, 34, 2, 1, 35);
		checkNext(scanner, OP_AND, 37, 1, 1, 38);
		checkNext(scanner, OP_OR, 39, 1, 1, 40);
		checkNext(scanner, OP_PLUS, 41, 1, 1, 42);
		checkNext(scanner, OP_MINUS, 43, 1, 1, 44);
		checkNext(scanner, OP_TIMES, 45, 1, 1, 46);
		checkNext(scanner, OP_DIV, 47, 1, 1, 48);
		checkNext(scanner, OP_MOD, 49, 1, 1, 50);
		checkNext(scanner, OP_POWER, 51, 2, 1, 52);
		checkNext(scanner, OP_RARROW, 54, 2, 1, 55);
		checkNext(scanner, OP_LARROW, 57, 2, 1, 58);
		checkNext(scanner, OP_AT, 60, 1, 1, 61);
		checkNextIsEOF(scanner);

	}

	@Test
	public void testKeyword() throws LexicalException {
		String input = "normalIdent x X y Y r R a A Z DEF_X DEF_Y SCREEN cart_x cart_y polar_a polar_r \n abs \r sin \r\n cos atan log image int boolean url file";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0, 11, 1, 1);
		checkNext(scanner, KW_x, 12, 1, 1, 13);
		checkNext(scanner, KW_X, 14, 1, 1, 15);
		checkNext(scanner, KW_y, 16, 1, 1, 17);
		checkNext(scanner, KW_Y, 18, 1, 1, 19);
		checkNext(scanner, KW_r, 20, 1, 1, 21);
		checkNext(scanner, KW_R, 22, 1, 1, 23);
		checkNext(scanner, KW_a, 24, 1, 1, 25);
		checkNext(scanner, KW_A, 26, 1, 1, 27);
		checkNext(scanner, KW_Z, 28, 1, 1, 29);
		checkNext(scanner, KW_DEF_X, 30, 5, 1, 31);
		checkNext(scanner, KW_DEF_Y, 36, 5, 1, 37);
		checkNext(scanner, KW_SCREEN, 42, 6, 1, 43);
		checkNext(scanner, KW_cart_x, 49, 6, 1, 50);
		checkNext(scanner, KW_cart_y, 56, 6, 1, 57);
		checkNext(scanner, KW_polar_a, 63, 7, 1, 64);
		checkNext(scanner, KW_polar_r, 71, 7, 1, 72);
		checkNext(scanner, KW_abs, 81, 3, 2, 2);
		checkNext(scanner, KW_sin, 87, 3, 3, 2);
		checkNext(scanner, KW_cos, 94, 3, 4, 2);
		checkNext(scanner, KW_atan, 98, 4, 4, 6);
		checkNext(scanner, KW_log, 103, 3, 4, 11);
		checkNext(scanner, KW_image, 107, 5, 4, 15);
		checkNext(scanner, KW_int, 113, 3, 4, 21);
		checkNext(scanner, KW_boolean, 117, 7, 4, 25);
		checkNext(scanner, KW_url, 125, 3, 4, 33);
		checkNext(scanner, KW_file, 129, 4, 4, 37);
		checkNextIsEOF(scanner);

	}

	@Test
	public void invalidCharacter() throws LexicalException {
		String input = "Invalid character like copyright sign in identifier - pound©";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) { //
			show(e);
			assertEquals(59, e.getPos());
			throw e;
		}
	}

	@Test
	public void testAlphaNumericalInput() throws LexicalException {
		String input = "alphabet_thenNumber6565ss\nThis startes with number 64748Abc";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0, 25, 1, 1);
		checkNext(scanner, IDENTIFIER, 26, 4, 2, 1);
		checkNext(scanner, IDENTIFIER, 31, 7, 2, 6);
		checkNext(scanner, IDENTIFIER, 39, 4, 2, 14);
		checkNext(scanner, IDENTIFIER, 44, 6, 2, 19);
		checkNext(scanner, INTEGER_LITERAL, 51, 5, 2, 26);
		checkNext(scanner, IDENTIFIER, 56, 3, 2, 31);
		checkNextIsEOF(scanner);

	}

}
