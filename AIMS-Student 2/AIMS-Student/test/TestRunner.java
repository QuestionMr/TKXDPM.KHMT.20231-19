import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      //Result result = JUnitCore.runClasses(ValidatePhoneNumberTest.class);
	  //Result result = JUnitCore.runClasses(ValidateNameTest.class);
	  //Result result = JUnitCore.runClasses(ValidateAddressTest.class);
	   //Result result = JUnitCore.runClasses(ValidateCardNumberTest.class);
	   Result result = JUnitCore.runClasses(ValidateSecurityCodeTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
}  