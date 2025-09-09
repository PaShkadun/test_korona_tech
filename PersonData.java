import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class PersonData
{
    public String OriginalString;
    public PositionType Position;
    public int Id;
    public String Name;
    public float Salary;
    public boolean IsCorrect;
    
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