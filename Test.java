import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test{
	public static void main(String[] args) {
        try{
            File file = new File("data.csv");
            InputStream is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is) );
            List<Customer> customers = br.lines().skip(1).map(item).collect(Collectors.toList());
            br.close();
            
            //sort by firstname , lastname
            Comparator<Customer> compareByName = Comparator
                    .comparing(Customer::getFirstName)
                    .thenComparing(Customer::getLastName);
            
            customers = customers.stream()
                    .sorted(compareByName)
                    .collect(Collectors.toList());
           
           //get latest version
           List<Customer> result = new ArrayList<Customer>();
           for(Customer customer: customers) {
        	   boolean found = false;
        	   for(Customer r : result) {
        		   if(r.getUserId().equals(customer.getUserId())) {
        			   found = true;
        			   if( Integer.valueOf(customer.getVersion()) >
        		   Integer.valueOf(r.getVersion()) )
        			   r.setVersion(customer.getVersion());
        			   break;
        		   }
        	   }
        	   if(!found) {
        		   result.add(customer);
        	   }
           }
           //end result
           System.out.println(result);
        }catch(Exception e){
        	System.out.println(e);
        }
    }
    
    private static Function<String, Customer> item = (line) ->{
      String[] s =line.split(",");
      Customer c= new Customer();
      c.setUserId(s[0]);c.setFirstName(s[1]);
      c.setLastName(s[2]); c.setVersion(s[3]);
      c.setInsuranceCompany(s[4]);
      return c;    
    };
}
class Customer{
    private String userId;
    private String firstName;
    private String lastName;
    private String version;
    private String insuranceCompany;
    
    public Customer(){}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
    
}