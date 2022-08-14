package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.util.LinkedList;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.User;



// format %s#%s#%s type,name,what


public class Protocol {
	public static enum GameCommand {PAINT,SEND_MESSAGE,GET_MESSAGE,WIN,END,SWITCH_PAINTER,READY, ERROR, OK};
	public static final String SEPARATOR = "#";
	private LinkedList<String> messages;
	private User self;
	
	
	public Protocol(User u) {
		messages = new LinkedList<String>();
		self = u;
	}
	private String[] parseCommand(String command) {
        return command.split(SEPARATOR);
    }
	public void addNewMessage(String message) {
		messages.addFirst(message);
	}
	
	public static boolean isOK (String s) {
        return s.equals(GameCommand.OK.toString());
    }
	public String process(String command) {
		if (isOK(command)) {
			return null;
		} 
        StringBuilder response = new StringBuilder("%s".formatted(GameCommand.OK));
        StringBuilder m;
        String[] commands = parseCommand(command);
        GameCommand keyword = GameCommand.valueOf(commands[0]);
        if (keyword == GameCommand.ERROR) {
            throw new RuntimeException("Received ERROR %s".formatted(commands[1]));
        }
        try {
        	switch (self.getRole()) {
        	case Painter->{
        		switch(keyword) {
        		case GET_MESSAGE->{
        			m = new StringBuilder("%s: %s".formatted(commands[1],commands[2]));
        			addNewMessage(m.toString());
        		}
        		case PAINT->{
        			
        		}
        		case END->{
        			self.ChangeToGuesser();
        		}
        		default->throw new RuntimeException("Unhandled WHAT Command %s".formatted(command));
        		}
        	}
        	case Winner->{
        		switch(keyword) {
        		case GET_MESSAGE->{
        			m = new StringBuilder("%s: %s".formatted(commands[1],commands[2]));
        			addNewMessage(m.toString());
        		}
        		case PAINT->{
        			
        		}
        		case END->{
        			
        		}
        		case SWITCH_PAINTER->{
        			self.ChangeToPainter();
        		}
        		default->throw new RuntimeException("Unhandled WHAT Command %s".formatted(command));
        		}
        		
        	}
        	case Guesser->{
        		switch(keyword) {
        		case GET_MESSAGE->{
        			m = new StringBuilder("%s: %s".formatted(commands[1],commands[2]));
        			addNewMessage(m.toString());
        		}
        		case SEND_MESSAGE->{
        			m = new StringBuilder("%s: %s".formatted(commands[1],commands[2]));
        			addNewMessage(m.toString());
        			response = new StringBuilder("%s#%s#%s".formatted(GameCommand.GET_MESSAGE,commands[1],commands[2]));
        		}
        		case PAINT->{
        			
        		}
        		case SWITCH_PAINTER->{
        			self.ChangeToPainter();
        		}
        		case WIN->{
        			int s = Integer.parseInt(commands[1]);
        			self.addscore(s);
        			self.ChangeToWinner();
        		}
        		case END->{
        			
        		}
        		default->throw new RuntimeException("Unhandled WHAT Command %s".formatted(command));
        		}
        	}
        	
        	}
        } catch (Exception e) {
            e.printStackTrace();
            response = new StringBuilder("%s#%s".formatted(GameCommand.ERROR, e.getMessage()));
        }
        return response.toString();
	}
}
