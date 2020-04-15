package bll.validators;

import model.Client;

/**
 * Class that implements the validator for email
 */
public class EmailValidator {

	/**
	 * Method to validate the email. The email must have at least 3 characters, must contain nly 1 @ and must not start or end with @ 
	 * @param c the client which is validated
	 * @return an integer which is 1 in case the client was correct inserted or 0 otherwise
	 */
	public static int validate(Client c)
	{
		if(c.getClientEmail().length()<3)
        {
            return 0;
        }
        else{
            int contor=0;
            for(int i=0; i<c.getClientEmail().length(); i++)
            {
                if(c.getClientEmail().charAt(0)=='@'  || c.getClientEmail().charAt(c.getClientEmail().length()-1)=='@')
                {
                    return 0;
                }
                
                    if(c.getClientEmail().charAt(i)=='@')
                    {
                        contor++;
                    }
    
            }
            if(contor==0)
            {
               return 0;
            }
            else if(contor>1)
            {
                return 0;
            }
        }
		return 1;
	}
	
}
