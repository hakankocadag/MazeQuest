package mazegame;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;

public class CommandProcessor {
    private static final Pattern REPEAT_WITH_ACTION = Pattern.compile(
        "(GO|LEFT|RIGHT)\\((\\d+),(GO|LEFT|RIGHT|CATCH)\\)", Pattern.CASE_INSENSITIVE);
    private static final Pattern SIMPLE_REPEAT = Pattern.compile(
        "(GO|LEFT|RIGHT)\\((\\d+)\\)", Pattern.CASE_INSENSITIVE);
    private static final Pattern SIMPLE_COMMAND = Pattern.compile(
        "(GO|LEFT|RIGHT|CATCH)\\(\\)", Pattern.CASE_INSENSITIVE);

    public String parseInput(String input) {
        Matcher m = REPEAT_WITH_ACTION.matcher(input);
        if (m.matches()) {
            return m.group(1).toUpperCase() + "(" + m.group(2) + "," + m.group(3).toUpperCase() + ")";
        }
        
        m = SIMPLE_REPEAT.matcher(input);
        if (m.matches()) {
            return m.group(1).toUpperCase() + "(" + m.group(2) + ")";
        }
        
        m = SIMPLE_COMMAND.matcher(input);
        if (m.matches()) {
            return m.group(1).toUpperCase() + "()";
        }
        
        return null;
    }

    public void reNumberCommands(DefaultListModel<String> model) {
        ArrayList<String> commands = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            String cmd = model.getElementAt(i);
            commands.add(cmd.substring(cmd.indexOf(":") + 2));
        }
        model.clear();
        for (int i = 0; i < commands.size(); i++) {
            model.addElement((i + 1) + ": " + commands.get(i));
        }
    }

    public ArrayList<Character> getExecutionSequence(DefaultListModel<String> model) {
        ArrayList<Character> sequence = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            String rawCmd = model.getElementAt(i);
            String cmd = rawCmd.substring(rawCmd.indexOf(":") + 2);
            
            Matcher m = REPEAT_WITH_ACTION.matcher(cmd);
            if (m.matches()) {
                int count = Integer.parseInt(m.group(2));
                char mainAction = m.group(1).toUpperCase().charAt(0);
                char followAction = m.group(3).toUpperCase().charAt(0);
                for (int k = 0; k < count; k++) sequence.add(mainAction);
                sequence.add(followAction);
                continue;
            }
            
            m = SIMPLE_REPEAT.matcher(cmd);
            if (m.matches()) {
                int count = Integer.parseInt(m.group(2));
                char action = m.group(1).toUpperCase().charAt(0);
                for (int k = 0; k < count; k++) sequence.add(action);
                continue;
            }
            
            if (cmd.startsWith("GO")) sequence.add('G');
            else if (cmd.startsWith("LEFT")) sequence.add('L');
            else if (cmd.startsWith("RIGHT")) sequence.add('R');
            else if (cmd.startsWith("CATCH")) sequence.add('C');
        }
        return sequence;
    }
    
    public ArrayList<Integer> getLineMapping(DefaultListModel<String> model) {
        ArrayList<Integer> mapping = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            String rawCmd = model.getElementAt(i);
            String cmd = rawCmd.substring(rawCmd.indexOf(":") + 2);
            
            Matcher m = REPEAT_WITH_ACTION.matcher(cmd);
            if (m.matches()) {
                int count = Integer.parseInt(m.group(2));
                for (int k = 0; k < count + 1; k++) mapping.add(i);
                continue;
            }
            
            m = SIMPLE_REPEAT.matcher(cmd);
            if (m.matches()) {
                int count = Integer.parseInt(m.group(2));
                for (int k = 0; k < count; k++) mapping.add(i);
                continue;
            }
            
            mapping.add(i);
        }
        return mapping;
    }
    
    public ArrayList<Character> getExecutionSequenceFromCommands(ArrayList<String> commands) {
        ArrayList<Character> sequence = new ArrayList<>();
        for (String cmd : commands) {
            Matcher m = REPEAT_WITH_ACTION.matcher(cmd);
            if (m.matches()) {
                int count = Integer.parseInt(m.group(2));
                char mainAction = m.group(1).toUpperCase().charAt(0);
                char followAction = m.group(3).toUpperCase().charAt(0);
                for (int k = 0; k < count; k++) sequence.add(mainAction);
                sequence.add(followAction);
                continue;
            }
            
            m = SIMPLE_REPEAT.matcher(cmd);
            if (m.matches()) {
                int count = Integer.parseInt(m.group(2));
                char action = m.group(1).toUpperCase().charAt(0);
                for (int k = 0; k < count; k++) sequence.add(action);
                continue;
            }
            
            if (cmd.startsWith("GO")) sequence.add('G');
            else if (cmd.startsWith("LEFT")) sequence.add('L');
            else if (cmd.startsWith("RIGHT")) sequence.add('R');
            else if (cmd.startsWith("CATCH")) sequence.add('C');
        }
        return sequence;
    }
    
    public ArrayList<Integer> getLineMappingFromCommands(ArrayList<String> commands) {
        ArrayList<Integer> mapping = new ArrayList<>();
        for (int i = 0; i < commands.size(); i++) {
            String cmd = commands.get(i);
            
            Matcher m = REPEAT_WITH_ACTION.matcher(cmd);
            if (m.matches()) {
                int count = Integer.parseInt(m.group(2));
                for (int k = 0; k < count + 1; k++) mapping.add(i);
                continue;
            }
            
            m = SIMPLE_REPEAT.matcher(cmd);
            if (m.matches()) {
                int count = Integer.parseInt(m.group(2));
                for (int k = 0; k < count; k++) mapping.add(i);
                continue;
            }
            
            mapping.add(i);
        }
        return mapping;
    }
}