public class ConsoleParametersData
{
    private final String SORT_ARG = "--sort";
    private final String SORT_ARG_ALTERNATIVE = "-s";
    private final String ORDER_ARG = "--order";
    private final String STAT_ARG = "--stat";
    private final String OUTPUT_ARG = "--output";
    private final String OUTPUT_ARG_ALTERNATIVE = "-o";
    private final String PATH_ARG = "--path";
    
    private final String SORT_NAME_VALUE = "name";
    private final String SORT_SALARY_VALUE = "salary";
    private final String ORDER_ASC_VALUE = "asc";
    private final String ORDER_DESC_VALUE = "desc";
    private final String OUTPUT_CONSOLE_VALUE = "console";
    private final String OUTPUT_FILE_VALUE = "file";

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
                case SORT_ARG:
                case SORT_ARG_ALTERNATIVE:
                    TrySetSortArg(arg.toLowerCase());
                    break;
                    
                case ORDER_ARG:
                    TrySetOrderArg(arg.toLowerCase());
                    break;
                    
                case STAT_ARG:
                    IsStat = true;
                    break;
                    
                case OUTPUT_ARG:
                case OUTPUT_ARG_ALTERNATIVE:
                    TrySetOutputArg(arg.toLowerCase());
                    break;
                    
                case PATH_ARG:
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
        
        if (!outputParts[1].equals(OUTPUT_CONSOLE_VALUE) && !outputParts[1].equals(OUTPUT_FILE_VALUE))
        {
            throw new Exception("Invalid output arg");
        }
        
        IsOutput = true;
        IsConsoleOutput = outputParts[1].equals(OUTPUT_CONSOLE_VALUE);
    }
    
    private void TrySetOrderArg(String arg) throws Exception
    {
        String[] orderParts = arg.split("=");
        
        if (orderParts.length < 2 || orderParts.length > 2)
        {
            throw new Exception("Invalid order arg");
        }
        
        if (!orderParts[1].equals(ORDER_ASC_VALUE) &&  !orderParts[1].equals(ORDER_DESC_VALUE))
        {
            throw new Exception("Invalid order arg");
        }
        
        IsOrder = true;
        IsAscOrder = orderParts[1].equals(ORDER_ASC_VALUE);
    }
    
    private void TrySetSortArg(String arg) throws Exception
    {
        String[] sortParts = arg.split("=");
        
        if (sortParts.length < 2 || sortParts.length > 2)
        {
            throw new Exception("Invalid sort arg");
        }
        
        if (!sortParts[1].equals(SORT_NAME_VALUE) &&  !sortParts[1].equals(SORT_SALARY_VALUE))
        {
            throw new Exception("Invalid sort arg");
        }
        
        IsSort = true;
        IsSortByName = sortParts[1].equals(SORT_NAME_VALUE);
    }
}