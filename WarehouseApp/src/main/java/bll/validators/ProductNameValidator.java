package bll.validators;

import model.Product;

/**
 * Class that implements the validator for product name
 */
public class ProductNameValidator {

	/**
	 * Method to validate the prooduct
	 * @param p which is the product to be validated
	 * @return an integer which is 1 if the product was inserted correctly or 0 otherwise
	 */
	public static int validate(Product p)
	{
		 char ch;
         for(int i=0; i<p.getProductName().length(); i++)
         {
             ch=p.getProductName().charAt(i);
             if(!(ch>='a' && ch<='z') && !(ch>='A' && ch<='Z') && !(ch>=' '))
             {
                 return 0;
             }    
         }
         return 1;
	}
	
	
}
