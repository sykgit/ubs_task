/**
 * 
 */
package interview.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author skuppuraju
 *
 */
public final class AccountException extends Exception {
	private static final long serialVersionUID = -4176953561129000380L;
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AccountException.class);
	private ERROR_CODE errorCode;

	private AccountException(String message, ERROR_CODE errCode) {
		super(message);
		this.errorCode = errCode;
	}

	private AccountException(Throwable cause, ERROR_CODE errCode) {
		super(cause);
		this.errorCode = errCode;
	}

	public static AccountException generateException(ERROR_CODE errCode, String... params) {
		StringBuilder errMessage = new StringBuilder();
		if (errCode == null) {
			errCode = ERROR_CODE.CODE_NOT_FOUND;
		}
		errMessage.append(errCode.getErrorDesc());
		errMessage.append("  Params: | ");
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				errMessage.append(params[i]);
				if (i < params.length - 1) {
					errMessage.append(", ");
				}
			}
		} else {
			errMessage.append("EMPTY");
		}
		String errMsg = errMessage.toString();
		//logger.error(errMsg);
		return new AccountException(errMsg, errCode);
	}

	public static AccountException generateExceptionFromThrowable(ERROR_CODE errCode, Throwable cause) {
		StringBuilder errMessage = new StringBuilder();
		if (errCode == null) {
			errCode = ERROR_CODE.CODE_NOT_FOUND;
		}

		errMessage.append(errCode.getErrorDesc());
		errMessage.append("  Exception: | ");
		if (cause == null) {
			errMessage.append("THROWABLE CAUSE NOT FOUND");
		} else {
			if (cause instanceof AccountException) {
				return (AccountException) cause;
			} else {
				errMessage.append(cause.toString());
			}
		}
		String errMsg = errMessage.toString();
		//logger.error(errMsg, cause);
		if (cause != null) {
			return new AccountException(cause, errCode);
		} else {
			return new AccountException(errMsg, errCode);
		}

	}

	public ERROR_CODE getErrorCode() {
		return errorCode;
	}

}
