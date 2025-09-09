import java.util.ArrayList;

public class Program
{
    public static void main(String[] args) 
    {
        try {
            ConsoleParametersData parameters = new ConsoleParametersData();
            parameters.SetArgs(args);
            
            ArrayList<PersonData> peopleData = Parser.ParseData();
            
            ArrayList<DepartmentData> departmentsInfo = DataSorter.SortData(parameters, peopleData);
            
            Writer writer = new Writer();
            writer.OutputData(departmentsInfo, parameters);
        }
        catch (Exception exception)
        {
            System.out.println("Exception was thrown " + exception);
        }
    }
}