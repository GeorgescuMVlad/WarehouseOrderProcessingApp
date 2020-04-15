package bll.validators;

import model.Client;

/**
 * Class that implements the validator for the telephone number
 */
public class TelephoneValidator {
	
	/**
	 * Method to validate the client
	 * @param c the client to be validated
	 * @return an integer which is 1 in case the client was inserted correctly or 0 otherwise
	 */
	public static int validate(Client c)
	{
		if(c.getClientTelNumber().length()!=10)
        {
            return 0;
        }
        else{
            char ch;
            for(int i=0; i<c.getClientTelNumber().length(); i++)
            {
                ch=c.getClientTelNumber().charAt(i);
                if(!(ch>='0' && ch<='9') )
                {
                    return 0;
                }    
            }
        }
		return 1;
	}
	

}
