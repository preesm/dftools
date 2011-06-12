package net.sf.dftools.cdl.syntax;

import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.parser.antlr.ISyntaxErrorMessageProvider;
import org.eclipse.xtext.parser.antlr.SyntaxErrorMessage;

public class CdlSyntaxErrorMessageProvider implements
		ISyntaxErrorMessageProvider {

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage(IParserErrorContext context) {
		RecognitionException ex = context.getRecognitionException();
		if (ex instanceof MismatchedTokenException) {
			// MismatchedTokenException mte = (MismatchedTokenException) ex;
			/* get errors with mte.token.getText() */
		}

		return new SyntaxErrorMessage(context.getDefaultMessage(),
				CdlErrors.ERROR_DEFAULT);
	}

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage(
			IValueConverterErrorContext context) {

		return new SyntaxErrorMessage(context.getDefaultMessage(),
				CdlErrors.ERROR_DEFAULT);
	}

}
