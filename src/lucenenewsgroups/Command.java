package lucenenewsgroups;

/**
 * Abstract class used to implement all the command of the application
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 * @version 1.0
 */
public abstract class Command
{

    /**
     * Executes the command
     */
    public abstract void execute();

    /**
     * Shows the progress of an operation on the console
     * @param current the current value of the operation
     * @param max the maximum value of the progress
     */
    protected static void showProgress(int current, int max)
    {
        System.out.print("\r");
        int percent = (int) Math.floor((float)current/(float)max * 100);
        int j=0;
        for(j=0; j < percent; j=j+10)
        {
            System.out.print("|");
        }
        for(int z=j; z < 100; z=z+10)
        {
            System.out.print(".");
        }

        System.out.print(" " + current + "/" + max + "(" + percent + "%)");
    }

}
