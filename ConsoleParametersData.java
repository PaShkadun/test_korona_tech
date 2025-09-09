public class ConsoleParametersData
{
    private final String SortArg = "--sort";
    private final String SortArgAlternative = "-s";
    private final String OrderArg = "--order";
    private final String StatArg = "--stat";
    private final String OutputArg = "--output";
    private final String OutputArgAlternative = "-o";
    private final String PathArg = "--path";
    
    private final String SortNameValue = "name";
    private final String SortSalaryValue = "salary";
    private final String OrderAscValue = "asc";
    private final String OrderDescValue = "desc";
    private final String OutputConsoleValue = "console";
    private final String OutputFileValue = "file";

    public boolean IsSort;
    public boolean IsSortByName;
    public boolean IsOrder;
    public boolean IsAscOrder;
    public boolean IsStat;
    public boolean IsOutput;
    public boolean IsConsoleOutput;
    public String Path;
    
    public void SetArgs(String[] args) throws Exception
    {
        for (String arg : args)
        {
            String[] argParts = arg.split("=");
        
            switch (argParts[0].toLowerCase()) 
            {
                case SortArg:
                case SortArgAlternative:
                    TrySetSortArg(arg.toLowerCase());
                    break;
                    
                case OrderArg:
                    TrySetOrderArg(arg.toLowerCase());
                    break;
                    
                case StatArg:
                    IsStat = true;
                    break;
                    
                case OutputArg:
                case OutputArgAlternative:
                    TrySetOutputArg(arg.toLowerCase());
                    break;
                    
                case PathArg:
                    TrySetPathArg(arg.toLowerCase());
                    break;
                    
                default:
                    System.out.println("Unknown args: " + arg);
                    break;
            }
        }
        
        if (IsSort && !IsOrder)
        {
            throw new Exception("Invalid sort arg");
        }
        
        if (!IsStat)
        {
            IsOutput = false;
            IsConsoleOutput = false;
            Path = null;
        }
        
        if (IsOutput && !IsConsoleOutput && Path == null)
        {
            System.out.println("throw");
            throw new Exception("Invalid output arg");
        }
    }
    
    private void TrySetPathArg(String arg) throws Exception
    {
        String[] pathParts = arg.split("=");
        
        if (pathParts.length < 2 || pathParts.length > 2)
        {
            throw new Exception("Invalid path arg");
        }
        
        if (pathParts[1].length() == 0)
        {
            throw new Exception("Invalid path arg");
        }
        
        Path = pathParts[1];
    }
    
    private void TrySetOutputArg(String arg) throws Exception
    {
        String[] outputParts = arg.split("=");
        
        if (outputParts.length < 2 || outputParts.length > 2)
        {
            throw new Exception("Invalid output arg");
        }
        
        if (!outputParts[1].equals(OutputConsoleValue) && !outputParts[1].equals(OutputFileValue))
        {
            throw new Exception("Invalid output arg");
        }
        
        IsOutput = true;
        IsConsoleOutput = outputParts[1].equals(OutputConsoleValue);
    }
    
    private void TrySetOrderArg(String arg) throws Exception
    {
        String[] orderParts = arg.split("=");
        
        if (orderParts.length < 2 || orderParts.length > 2)
        {
            throw new Exception("Invalid order arg");
        }
        
        if (!orderParts[1].equals(OrderAscValue) &&  !orderParts[1].equals(OrderDescValue))
        {
            throw new Exception("Invalid order arg");
        }
        
        IsOrder = true;
        IsAscOrder = orderParts[1].equals(OrderAscValue);
    }
    
    private void TrySetSortArg(String arg) throws Exception
    {
        String[] sortParts = arg.split("=");
        
        if (sortParts.length < 2 || sortParts.length > 2)
        {
            throw new Exception("Invalid sort arg");
        }
        
        if (!sortParts[1].equals(SortNameValue) &&  !sortParts[1].equals(SortSalaryValue))
        {
            throw new Exception("Invalid sort arg");
        }
        
        IsSort = true;
        IsSortByName = sortParts[1].equals(SortNameValue);
    }
}