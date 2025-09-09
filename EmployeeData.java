import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class EmployeeData extends PersonData
{
    public int ManagerId;
    
    @Override
    public String GetFormatedData()
    {
        ArrayList<String> dataParts = new ArrayList<String>();
        
        for (String data : OriginalString.split(","))
        {
            dataParts.add(data.trim());
        }

        String formatedData = String.join(",", dataParts);
        
        return formatedData;
    }
}