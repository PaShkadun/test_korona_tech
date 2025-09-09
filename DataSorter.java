import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class DataSorter
{
    public static ArrayList<DepartmentData> SortData(ConsoleParametersData consoleParameters, 
                                                   ArrayList<PersonData> persons)
    {
        TrySort(consoleParameters, persons);
        
        ArrayList<PersonData> managers = persons.stream()
                                                .filter(p -> p.Position == PositionType.Manager)
                                                .collect(Collectors.toCollection(ArrayList::new));
        
        ArrayList<DepartmentData> departmentsInfo = new ArrayList<DepartmentData>();
        
        for (PersonData manager : managers)
        {
            ArrayList<PersonData> employees = persons.stream()
                                                     .filter(p -> p instanceof EmployeeData && 
                                                             ((EmployeeData)p).ManagerId == manager.Id)
                                                     .collect(Collectors.toCollection(ArrayList::new));
            ManagerData castedManager = (ManagerData)manager;
                                                     
            DepartmentData department = new DepartmentData(
                                                castedManager.DepartmentName, employees, castedManager);
            
            departmentsInfo.add(department);
            
            persons.remove(manager);
            persons.removeAll(employees);
        }
        
        if (persons.size() > 0)
        {
            DepartmentData errorDep = new DepartmentData("error", persons, null);
            
            departmentsInfo.add(errorDep);
        }
        
        return departmentsInfo;
    }
    
    private static void TrySort(ConsoleParametersData consoleParameters, ArrayList<PersonData> persons)
    {
        if (!consoleParameters.IsSort)
            return;
            
        if (consoleParameters.IsSortByName)
        {
            if (consoleParameters.IsOrder && consoleParameters.IsAscOrder)
                Collections.sort(persons, (p1, p2) -> p1.Name.compareTo(p2.Name));
            else if (consoleParameters.IsOrder && !consoleParameters.IsAscOrder)
                Collections.sort(persons, (p1, p2) -> p2.Name.compareTo(p1.Name));
                
            return;
        }
        
        if (consoleParameters.IsOrder && consoleParameters.IsAscOrder)
            Collections.sort(persons, (p1, p2) -> Float.compare(p1.Salary, p2.Salary));
        else if (consoleParameters.IsOrder && !consoleParameters.IsAscOrder)
            Collections.sort(persons, (p1, p2) -> Float.compare(p2.Salary, p1.Salary));
    }
}