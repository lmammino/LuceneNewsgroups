package lucenenewsgroups.commands;

import lucenenewsgroups.AppInfo;
import lucenenewsgroups.Command;

/**
 * Displays some information about the current version of the application
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 * @version 1.0
 */
public class VersionCommand extends Command {

    @Override
    public void execute()
    {
        System.out.println(AppInfo.name + "  " + AppInfo.version +
                            " by Luciano Mammino <lmammino[at]oryzone[dot]com>\n\n");
    }

}
