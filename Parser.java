import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

public class Parser
{
    private static final String FILE_EXTENSION = "sb";
    private static final String NAME_REGEX = "[a-zA-Z]* [a-zA-Z]*";
    private static final String DEPARTMENT_REGEX = "[a-zA-Z ]*";
    
    private static final int DATA_FIELDS_COUNT = 5;
    private static final int EMPLOYEE_FIELDS_COUNT = 5;
    
    public static ArrayList<PersonData> ParseData()
    {
        ArrayList<PersonData> people = ParsePeople(GetFilesData());

        return people;
    }
    
    private static ArrayList<PersonData> ParsePeople(ArrayList<ArrayList<String>> filesData)
    {
        ArrayList<PersonData> people = new ArrayList<PersonData>();
        
        for (ArrayList<String> fileData : filesData) 
        {
            for (String personData : fileData)
            {
                try 
                {   
                    if (personData.length() == 0)
                    {
                        System.out.println("Empty data found" + personData);
                    }
                    
                    String[] data = personData.split(",");

                    PositionType position = PositionType.valueOf(data[0].trim());
                    
                    if (position == PositionType.Manager)
                        people.add(GetManagerData(personData, data));
                    else
                        people.add(GetEmployeeData(personData, data));
                }
                catch (Exception exception)
                {
                    System.out.println("Exception was thrown " + exception);
                }
            }
        }
        
        return people;
    }
    
    private static PersonData GetManagerData(String originalData, String[] data)
    {
        ManagerData manager = new ManagerData();
        
        manager.OriginalString = originalData;
        
        if (data.length != DATA_FIELDS_COUNT)
        {
            manager.IsCorrect = false;
            
            return manager;
        }
        
        try 
        {
            manager.Position = PositionType.valueOf(data[0].trim());

            SetId(manager, data[1]);
            
            if (!TrySetName(manager, data[2]))
            {
                return manager;
            }
            
            if (!TrySetSalary(manager, data[3]))
            {
                return manager;
            }
            
            if (!data[4].matches(DEPARTMENT_REGEX))
            {
                manager.IsCorrect = false;
                
                return manager;
            }
            
            manager.DepartmentName = data[4];
            manager.IsCorrect = true;
        }
        catch (Exception exception)
        {
            System.out.println("Exception was thrown " + exception);
            
            manager.IsCorrect = false;
        }
        
        return manager;
    }
    
    private static PersonData GetEmployeeData(String originalData, String[] data)
    {
        EmployeeData employee = new EmployeeData();
        
        employee.OriginalString = originalData;
        
        if (data.length != DATA_FIELDS_COUNT)
        {
            employee.IsCorrect = false;
            
            return employee;
        }
        
        try 
        {
            employee.Position = PositionType.valueOf(data[0].trim());

            SetId(employee, data[1]);
            
            if (employee.Id == 107)
            {
                System.out.println();
            }
            
            if (!TrySetName(employee, data[2]))
            {
                return employee;
            }
            
            if (!TrySetSalary(employee, data[3]))
            {
                return employee;
            }
            
            employee.ManagerId = Integer.parseInt(data[4].trim());
            employee.IsCorrect = true;
        }
        catch (Exception exception)
        {
            System.out.println("Exception was thrown " + exception);
            
            employee.IsCorrect = false;
        }
        
        return employee;
    }
    
    private static boolean TrySetSalary(PersonData person, String salary) throws java.text.ParseException
    {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setDecimalSeparator('.');
        DecimalFormat parseFormat = new DecimalFormat("#.000");
        parseFormat.setDecimalFormatSymbols(formatSymbols);

        float salaryValue = parseFormat.parse(salary.trim()).floatValue();

        if (salaryValue <= 0f)
        {
            person.IsCorrect = false;
            
            return false;
        }
            
        person.Salary = salaryValue;
        
        return true;
    }
    
    private static boolean TrySetName(PersonData person, String name)
    {
        if (!name.trim().matches(NAME_REGEX))
        {
            person.IsCorrect = false;
            
            return false;
        }
            
        person.Name = name;
        
        return true;
    }
    
    private static void SetId(PersonData person, String id)
    {
        int personId = Integer.parseInt(id);
            
        person.Id = personId;
    }
    
    public static ArrayList<ArrayList<String>> GetFilesData()
    {
        File[] files = new File(".").listFiles();
        ArrayList<ArrayList<String>> filesData = new ArrayList<ArrayList<String>>();
        
        for (File file : files)
        {
            if (!IsSbFile(file.getName()))
                continue;
                
            ArrayList<String> fileData = GetFileData(file);
            
            if (fileData == null || fileData.size() == 0)
                continue;
            
            filesData.add(fileData);
        }
        
        return filesData;
    }
    
    private static ArrayList<String> GetFileData(File file)
    {   
        try 
        {
            ArrayList<String> data = new ArrayList<String>();
            Scanner reader = new Scanner(file);
        
            while (reader.hasNextLine())
            {
                data.add(reader.nextLine());
            }
            
            reader.close();
            
            return data;
        }
        catch (Exception exception)
        {
            System.out.println("Exception was thrown " + exception);
        }
        
        return null;
    }
    
    private static boolean IsSbFile(String fileName)
    {
        return fileName.endsWith(FILE_EXTENSION);
    }
}