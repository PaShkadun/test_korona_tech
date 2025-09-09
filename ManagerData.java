import java.util.ArrayList;

public class ManagerData extends PersonData
{
    public String DepartmentName;
    
    @Override
    public String GetFormatedData()
    {
        ArrayList<String> dataParts = new ArrayList<String>();
        
        for (String data : OriginalString.split(","))
        {
            dataParts.add(data.trim());
        }

        dataParts.remove(dataParts.size() - 1);
        
        String formatedData = String.join(",", dataParts);
        
        return formatedData;
    }
}