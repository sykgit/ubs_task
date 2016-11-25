package interview.exception;

import org.junit.Assert;
import org.junit.Test;

public class TestAccountException {

	@Test
	public void testGenerateException_NullErrorCode() {
		AccountException generateException = AccountException.generateException(null);
		Assert.assertNotNull(generateException);
		Assert.assertEquals(ERROR_CODE.CODE_NOT_FOUND, generateException.getErrorCode());
	}

	@Test
	public void testGenerateException_NullArgs() {
		AccountException generateException = AccountException.generateException(null, new String[1]);
		Assert.assertNotNull(generateException);
		Assert.assertEquals(ERROR_CODE.CODE_NOT_FOUND, generateException.getErrorCode());
	}

	@Test
	public void testGenerateException_NullErrorCodeWithParam() {
		AccountException generateException = AccountException.generateException(null, "Param 1");
		Assert.assertNotNull(generateException);
		Assert.assertEquals(ERROR_CODE.CODE_NOT_FOUND, generateException.getErrorCode());
	}

	@Test
	public void testGenerateException_NullErrorCodeWithMultipleParams() {
		AccountException generateException = AccountException.generateException(null, "Param 1", "Param 2");
		Assert.assertNotNull(generateException);
		Assert.assertEquals(ERROR_CODE.CODE_NOT_FOUND, generateException.getErrorCode());
	}

	@Test
	public void testGenerateException_ValidErrorCodeWithNullParam() {
		AccountException generateException = AccountException.generateException(ERROR_CODE.APPL_EXCEPTION, new String[1]);
		Assert.assertNotNull(generateException);
		Assert.assertEquals(ERROR_CODE.APPL_EXCEPTION, generateException.getErrorCode());
	}

	@Test
	public void testGenerateException_ValidErrorCodeWithMultipleParams() {
		AccountException generateException = AccountException.generateException(ERROR_CODE.APPL_EXCEPTION, "Param 1", "Param 2");
		Assert.assertNotNull(generateException);
		Assert.assertEquals(ERROR_CODE.APPL_EXCEPTION, generateException.getErrorCode());
	}

	@Test
	public void testGenerateExceptionFromThrowable_NullArgs() {
		AccountException generateException = AccountException.generateExceptionFromThrowable(null, null);
		Assert.assertNotNull(generateException);
		Assert.assertEquals(ERROR_CODE.CODE_NOT_FOUND, generateException.getErrorCode());
	}

	@Test
	public void testGenerateExceptionFromThrowable_NullErrorCodeValidException() {
		AccountException generateException = AccountException.generateExceptionFromThrowable(null, new NullPointerException());
		Assert.assertNotNull(generateException);
		Assert.assertEquals(ERROR_CODE.CODE_NOT_FOUND, generateException.getErrorCode());
	}

	@Test
	public void testGenerateExceptionFromThrowable_ValidErrorCodeAndException() {
		AccountException generateException = AccountException.generateExceptionFromThrowable(ERROR_CODE.APPL_EXCEPTION,
				new NullPointerException());
		Assert.assertNotNull(generateException);
		Assert.assertEquals(ERROR_CODE.APPL_EXCEPTION, generateException.getErrorCode());
	}
}
