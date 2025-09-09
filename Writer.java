import java.util.ArrayList;
import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class Writer
{
    private final String FILE_EXTENSION = ".sb";
    
    public void OutputData(ArrayList<DepartmentData> departmentsData, ConsoleParametersData params)
    {
        OutputDepartmentFiles(departmentsData);
        
        if (!params.IsStat)
            return;
        
        if ((params.IsOutput && params.IsConsoleOutput) || !params.IsOutput)
        {
            OutputConsole(departmentsData);
        }
        else if (params.IsOutput && !params.IsConsoleOutput)
        {
            OutputFile(departmentsData, params.Path);
        }
    }
    
    private void OutputDepartmentFiles(ArrayList<DepartmentData> departmentsData)
    {
        for (DepartmentData department : departmentsData)
        {
            boolean isExistManager = department.Manager != null;
            String fileExtension = isExistManager ? FILE_EXTENSION : ".log";
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(department.Name + fileExtension)))
            {
                if (isExistManager)
                {
                    writer.write(department.Manager.GetFormatedData());
                    writer.newLine();
                }
                
                for (PersonData person : department.Employees)
                {
                    writer.write(person.GetFormatedData());
                    writer.newLine();
                }
            }
            catch (Exception exception)
            {
                System.out.println("Exception was thrown " + exception);
            }
        }
    }
    
    private void OutputConsole(ArrayList<DepartmentData> departmentsData)
    {
        for (DepartmentData department : departmentsData)
        {
            if (department.Manager == null)
                continue;
        
            StatisticData depStat = GetStatistic(department);
            
            DecimalFormatSymbols separator = new DecimalFormatSymbols();
            separator.setDecimalSeparator('.');
            
            DecimalFormat df = new DecimalFormat("#.##", separator);
            
            String formatedMax = df.format(depStat.Max);
            String formatedMin = df.format(depStat.Min);
            String formatedAverage = df.format(depStat.Average);
            
            System.out.println(String.join(",", department.Name, formatedMin, formatedMax, formatedAverage));
        }
    }
    
    private void OutputFile(ArrayList<DepartmentData> departmentsData, String fileName)
    {
        File file = new File(fileName);
        
        try
        {
            Path filePath = Paths.get(fileName);
            Files.createDirectories(filePath.getParent());
            
            if (!file.exists())
                file.createNewFile();
        }
        catch (java.io.IOException ioe)
        {
            System.out.println("Output file couldn't be created");
            
            return;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            writer.write("department, min, max, mid");
            writer.newLine();
            
            Collections.sort(departmentsData, (d1, d2) -> d1.Name.compareTo(d2.Name));
            
            for (DepartmentData department : departmentsData)
            {
                if (department.Manager == null)
                    continue;
                
                StatisticData depStat = GetStatistic(department);
                
                DecimalFormatSymbols separator = new DecimalFormatSymbols();
                separator.setDecimalSeparator('.');
                
                DecimalFormat df = new DecimalFormat("#.##", separator);
                
                String formatedMax = df.format(depStat.Max);
                String formatedMin = df.format(depStat.Min);
                String formatedAverage = df.format(depStat.Average);
                
                writer.write(String.join(",", department.Name, 
                                         formatedMin, formatedMax, formatedAverage));
                writer.newLine();
            }   
        }
        catch (Exception exception)
        {
            System.out.println("Exception was thrown " + exception);
        }
        
    }
    
    private StatisticData GetStatistic(DepartmentData department)
    {
        StatisticData stat = new StatisticData();
        
        stat.Name = department.Name;
        
        if (department.Employees.size() == 0)
            return stat;
            
        stat.Min = Float.MAX_VALUE;
        stat.Max = Float.MIN_VALUE;
        stat.Average = 0f;
        
        float sum = 0f;
        
        for (PersonData person : department.Employees)
        {
            if (person.Salary > stat.Max)
                stat.Max = person.Salary;
            
            if (person.Salary < stat.Min)
                stat.Min = person.Salary;
                
            sum += person.Salary;
        }
        
        stat.Average = sum / department.Employees.size();
        
        return stat;
    }
}