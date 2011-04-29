package lucenenewsgroups.commands;

import lucenenewsgroups.Command;

/**
 * Command that display the list of all the available commands
 * @author Luciano Mammino <lmammino [at] oryzone {dot} com>
 * @version 1.0
 */
public class UsageCommand extends Command {


    protected UsageSection section;
    
    /**
     * Default constructor. Creates an usage command that displays general
     * informations about commands
     */
    public UsageCommand()
    {
        this(UsageSection.GENERAL);
    }


    /**
     * Constructor. Crates an usage command that display information about a given
     * command (section)
     * @param section
     */
    public UsageCommand(UsageSection section)
    {
        this.section = section;
    }


    /**
     * @return the section
     */
    public UsageSection getSection() {
        return section;
    }


    /**
     * @param section the section to set
     */
    public void setSection(UsageSection section) {
        this.section = section;
    }


    @Override
    public void execute() {
        String text = "";

        switch(this.section)
        {
            case GENERAL:
                text += "\n";
                text += "usage: java -jar LuceneNewsgroups.jar <command> [<args>]\n";
                text += "\n";
                text += "Follows a list of all the available commands:";
                text += "\n version                   shows informations about the version of the software";
                text += "\n createIndex [<folder>]    creates an index for all the files contained in the <folder> directory";
                text += "\n search <query>            search within the index of the current dir for documents that match the given <query>";
                text += "\n open <id>                 display the content of the document indexed with the given <id>";
                break;

            case OPEN:
                text += "\n";
                text += "usage: java -jar LuceneNewsgroups.jar open <id>";
                break;

            case SEARCH:
                text += "\n";
                text += "usage: java -jar LuceneNewsgroups.jar search <query>";
                break;
        }

        System.out.println(text+"\n\n");
    }

    
    /**
     * Used to select which section to show
     */
    public enum UsageSection
    {
        GENERAL,
        OPEN,
        SEARCH
    }


}
