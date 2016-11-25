package interview.exception;

public enum ERROR_CODE {
	CODE_NOT_FOUND("Error Code Not Found"), 
	CURR_NOT_FOUND("Given Currency Code Not Found In The System"), 
	NULL_FILE("Input File Argument is Null"),
	NULL_CURRENCY("Input Currency Argument is Null"),
	FILE_NOT_FOUND("Input File Not Found"),
	INSUFFICIENT_DATA("Insufficient Data in the file."),
	INVALID_AMOUNT_FIELD("Amount field not invalid/corrupt"),
	NULL_COUNTRY_CITY("Country/City Details not available."),
	NULL_CREDIT_RATING("Credit Rating not available."),
	APPL_EXCEPTION("Application Exception");
	
	private String errDesc;

	private ERROR_CODE(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getErrorDesc() {
		return errDesc;
	}
}
