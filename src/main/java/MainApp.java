import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainApp implements Runnable {

    /*
    Zadanie:
    1. Wykonać link w przeglądarce: http://dummy.restapiexample.com/api/v1/employees
    2. Otworzyć nowy projekt Mavenowy
    3. Skonfigurować oraz wrzucić na gita
    4. Napisać parser JSON-a który zrzuci listę pracowników, wyświetli ich w konsoli oraz wrzucić kommit ze zmianami na gita
    5. Utworzyć liste obiektów które zwraca powyższe api
    6. Utworzyć metody, które przerobią liste oraz wyświetlić pracowników których wiek jest większy niż 30
    7. Utworzyć metody, które przerobią liste oraz wyświetlić pracowników posortować rosnąco po pensji
    8. Utworzyć metody, które przerobią liste oraz wyświetlić pracowników posortować malejąco po wieku
    */

    @Override
    public void run() {
        try {
            String response = new HttpService().connect(Config.APP_URL);
            parseJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Employee> parseJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArrayEmployees = jsonObject.getJSONArray("data");

        List<Employee> employeesList = new ArrayList<>();

        for (int i = 0; i < jsonArrayEmployees.length(); i++) {
            JSONObject one = (JSONObject) jsonArrayEmployees.get(i);
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(one.get("id").toString()));
            employee.setAge(Double.parseDouble(one.get("employee_age").toString()));
            employee.setName(one.get("employee_name").toString());
            employee.setSalary(Double.parseDouble(one.get("employee_salary").toString()));
            employeesList.add(employee);
        }

        System.out.println("Logs: ");
        System.out.println(employeesList);
        System.out.println(employeesList.size());
        System.out.println(jsonArrayEmployees.length());

        return employeesList;
    }

    private List<Employee> sortBySalary(List<Employee> employeesList){
        return employeesList != null ? employeesList.stream().sorted(Comparator.comparingDouble(Employee::getSalary)).collect(Collectors.toList()) : null;
    }

    private List<Employee> sortByAgeDesc(List<Employee> employeesList){
        return employeesList != null ? employeesList.stream().sorted(Comparator.comparingDouble(Employee::getAge).reversed()).collect(Collectors.toList()) : null;
    }

    private List<Employee> getEmployeesOlderThan30(List<Employee> employeesList){
        return employeesList != null ? employeesList.stream().filter(f -> f.getAge() > 30).collect(Collectors.toList()) : null;
    }

}
