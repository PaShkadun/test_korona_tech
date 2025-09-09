import java.util.ArrayList;

public class DepartmentData
{
    public String Name;
    public ArrayList<PersonData> Employees;
    public ManagerData Manager;
    
    public DepartmentData(String name, ArrayList<PersonData> employees, ManagerData manager)
    {
        Name = name;
        Employees = employees;
        Manager = manager;
    }
}