package Visitor;

import jchess.core.Player;

public interface Visitor {
	
	public abstract void visit(Player player);
	
	public abstract void print();
}