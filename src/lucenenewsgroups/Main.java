package lucenenewsgroups;

import lucenenewsgroups.commands.CreateIndexCommand;
import lucenenewsgroups.commands.VersionCommand;
import lucenenewsgroups.commands.OpenCommand;
import lucenenewsgroups.commands.SearchCommand;
import lucenenewsgroups.commands.UsageCommand;

/**
 * The main class of the project
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 * @version 1.0
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Command command = null;

        if(args.length == 0)
            command = new UsageCommand();
        else
        {
            if(args[0] == null ? "version" == null : args[0].equals("version"))
            {
                command = new VersionCommand();
            }
            else if(args[0] == null ? "createIndex" == null : args[0].equals("createIndex"))
            {
                if(args.length > 1)
                    command = new CreateIndexCommand(Main.joinArgs(args, 1));
                else
                    command = new CreateIndexCommand(".");
            }
            else if(args[0] == null ? "open" == null : args[0].equals("open"))
            {
                if(args.length > 1)
                    command = new OpenCommand(args[1]);
                else
                    command = new UsageCommand(UsageCommand.UsageSection.OPEN);
            }
            else if(args[0] == null ? "search" == null : args[0].equals("search"))
            {
                if(args.length > 1)
                    command = new SearchCommand(Main.joinArgs(args, 1));
                else
                    command = new UsageCommand(UsageCommand.UsageSection.SEARCH);
            }
            else
            {
                System.out.println("Command '" + args[0] + "' not recognized.\n" );
                command = new UsageCommand();
            }

        }

        command.execute();

    }


    protected static String joinArgs(String[] args, int from)
    {
        String joined = "";
        if( args.length >= from)
        {
            for(int i=from; i < args.length; i++)
            {
                joined += args[i] + " ";
            }
        }
        return joined.substring(0, joined.length() -1);
    }

}
