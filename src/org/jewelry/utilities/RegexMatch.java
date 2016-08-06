package org.jewelry.utilities;

import java.util.regex.*;


public class RegexMatch {
	
	/*^                    # Start of the line
	  [a-z0-9_-]	     # Match characters and symbols in the list, a-z, 0-9 , underscore , hyphen
	             {3,15}  # Length at least 3 characters and maximum length of 15 
	$                    # End of the line*/
	private final static String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	
	/*(			# Start of group
  (?=.*\d)		#   must contains one digit from 0-9
  (?=.*[a-z])		#   must contains one lowercase characters
  (?=.*[A-Z])		#   must contains one uppercase characters
  (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
              .		#     match anything with previous condition checking
                {6,20}	#        length at least 6 characters and maximum of 20	
)			# End of group*/
	
	private static final String PASSWORD_PATTERN = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	
	/*^			#start of the line
  [_A-Za-z0-9-]+	#  must start with string in the bracket [ ], must contains one or more (+)
  (			#  start of group #1
    \\.[_A-Za-z0-9-]+	#     follow by a dot "." and string in the bracket [ ], must contains one or more (+)
  )*			#  end of group #1, this group is optional (*)
    @			#     must contains a "@" symbol
     [A-Za-z0-9]+       #        follow by string in the bracket [ ], must contains one or more (+)
      (			#	   start of group #2 - first level TLD checking
       \\.[A-Za-z0-9]+  #	     follow by a dot "." and string in the bracket [ ], must contains one or more (+)
      )*		#	   end of group #2, this group is optional (*)
      (			#	   start of group #3 - second level TLD checking
       \\.[A-Za-z]{2,}  #	     follow by a dot "." and string in the bracket [ ], with minimum length of 2
      )			#	   end of group #3
$			#end of the line*/
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	
	/*(			#start of group #1
 0?[1-9]		#  01-09 or 1-9
 |                  	#  ..or
 [12][0-9]		#  10-19 or 20-29
 |			#  ..or
 3[01]			#  30, 31
) 			#end of group #1
  /			#  follow by a "/"
   (			#    start of group #2
    0?[1-9]		#	01-09 or 1-9
    |			#	..or
    1[012]		#	10,11,12
    )			#    end of group #2
     /			#	follow by a "/"
      (			#	  start of group #3
       (19|20)\\d\\d	#	    19[0-9][0-9] or 20[0-9][0-9]
       )		#	  end of group #3
*/
	
	private static final String DDMMYYYY_PATTERN="(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
	
	private RegexMatch(){}
	
	public static boolean isUsername(String userName){
		return userName.matches(USERNAME_PATTERN);
	}
	
	public static boolean isGoodpassword(String password){
		return password.matches(PASSWORD_PATTERN);
	}
	
	public static boolean isEmail(String email){
		return email.matches(EMAIL_PATTERN);
	}
	
	public static boolean isDDMMYYYY(String date){
		return date.matches(DDMMYYYY_PATTERN);
	}
}
