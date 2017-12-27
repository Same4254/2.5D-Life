package Entity.FreeMoving.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.WorldObjects.Lot.Lot;
import Entity.WorldObjects.Objects.Wall;

public class PathFinding {
	public static ArrayList<Vector2f> aStar(Entity e, Lot lot, Vector2f start, Vector2f end) {
		NodeGrid grid = generateNodeGrid(lot);
		
		Node startNode = grid.getNode(start);
		Node endNode = grid.getNode(end);
		
		boolean endUnWalkable = !endNode.isWalkable();
		if(endUnWalkable)
			endNode.setWalkable(true);
		
		ArrayList<Node> open = new ArrayList<>();
		ArrayList<Node> closed = new ArrayList<>();
		open.add(startNode);

		while(true) {
			open.sort(Node.COMPARATOR);

			Node currentNode;
			if(open.isEmpty())//No more tiles (path impossible)
				return null;
			else 
				currentNode = open.remove(0);
			
			closed.add(currentNode);
			if(currentNode == endNode) break;
			
			for(Node node : grid.getNeighborsNoCorners(currentNode)) {
				if(!node.isWalkable() || closed.contains(node)) continue;
				
				int newCost = currentNode.getGCost() + Node.getDistance(currentNode, node);
				if(newCost < node.getGCost() || !open.contains(node)) {
					node.setGCost(newCost);
					node.setHCost(Node.getDistance(node, endNode));
					
					node.setParent(currentNode);
					if(!open.contains(node))
						open.add(node);
				}
			}
		}
		
		ArrayList<Vector2f> path = new ArrayList<>();

		Node node = closed.get(closed.size() - 1);
		path.add(new Vector2f(node.getX() + lot.getPosition().x, node.getY() + lot.getPosition().y));
		while(true) {
			Node temp = node.getParent();
			if(temp == null)
				break;
			else {
				node = temp;
				path.add(new Vector2f(node.getX() + lot.getPosition().x, node.getY() + lot.getPosition().y));
			}
		}
		
		if(endUnWalkable)
			path.remove(0);
		
		Collections.reverse(path);
		return path;
	}

//	public static ArrayList<Vector2f> getEffectiveArea(Lot lot, WorldObject worldObject, int radius, boolean frontOnly) {
//		
//	}
	
	public static ArrayList<Vector2f> getEffectiveArea(Lot lot, int px, int py, int radius) {
		Node[][] tempNodes = new Node[(radius * 2) + 1][(radius * 2) + 1];
		
		for(int x = px - radius; x <= radius + px; x++) {
			for(int y = py - radius; y <= radius + py; y++) {
				if(x > 0 && x < lot.getWidth()) {
					if(lot.getTiles()[x][y].getObject() instanceof Wall) {
						tempNodes[x - px + radius][y - py + radius] = new Node(x, y, false);
					} else {
						tempNodes[x - px + radius][y - py + radius] = new Node(x, y, true);
					}
				}
			}
		}
		
		NodeGrid grid = new NodeGrid(tempNodes);
		
 	}
	
	private static ArrayList<Node> addNeighbores(NodeGrid grid, Node node, ArrayList<Node> toAdd, int px, int py, int radius) {
		ArrayList<Node> temp = grid.checkNeighbores(node);
		toAdd.addAll(temp);
		
		for(Node tempNode : temp) 
			addNeighbores(grid, tempNode, toAdd, px, py, radius);
		
		return temp;
	}
	
	private static NodeGrid generateNodeGrid(Lot lot) {
		Node[][] field = new Node[lot.getTiles().length][lot.getTiles()[0].length];
		
		for(int x = 0; x < lot.getTiles().length; x++) 
		for(int y = 0; y < lot.getTiles()[x].length; y++) 
			field[x][y] = new Node(x, y, !lot.getTiles()[x][y].containsAnything());
		
		return new NodeGrid(field);
	}
	
	/*
	 * Simplifies the path down to the important vertices
	 * Gets rid of the origin (No need to go to where we already are)
	 */
	public static ArrayList<Vector2f> simplifyPath(ArrayList<Vector2f> pathPoints) {
		if(pathPoints == null)
			return null;
		
		ArrayList<Vector2f> path = new ArrayList<>(pathPoints);
		
		Vector2f lastDistance = null;
		for(int i = 0; i < path.size() - 1; i++) {
			Vector2f point = path.get(i);
			Vector2f nextPoint = path.get(i + 1);
			
			if(lastDistance == null) { 
				lastDistance = nextPoint.subtract(point);
			} else if(nextPoint.subtract(point).equals(lastDistance)) {
				path.remove(point);
				i--;
			} else 
				lastDistance = nextPoint.subtract(point);
		}
		
		path.remove(0);
		return path;
	}
}

class NodeGrid {
	private Node[][] nodes;
	
	public NodeGrid(Node[][] nodes) {
		this.nodes = nodes;
	}
	
	public ArrayList<Vector2f> asVectors(ArrayList<Node> nodes) {
		ArrayList<Vector2f> temp = new ArrayList<>();
		
		for(Node node : nodes) 
			temp.add(node.getPosition());
		return temp;
	}
	
	public ArrayList<Node> getNeighbors(Node node) {
		ArrayList<Node> neighbores = new ArrayList<>();
		
		for(int x = node.getX() - 1; x <= node.getX() + 1; x++) {
			for(int y = node.getY() - 1; y <= node.getY() + 1; y++) {
				Node temp = getNode(x,y);
				
				if(temp != null && temp != node)
					neighbores.add(temp);
			}
		}
		
		return neighbores;
	}
	
	public ArrayList<Node> checkNeighbores(Node node) {
		ArrayList<Node> temp = getAdjacentNeighbores(node);
		for(int i = temp.size() - 1; i >= 0; i--) 
			if(!temp.get(i).isWalkable()) temp.remove(i);
		return temp;
	}
	
	/**
	 * Gets all the neighborers, but not the ones that are "technically possible" (can't cut through corners in between objects)
	 */
	public ArrayList<Node> getNeighborsNoCorners(Node node) {
		ArrayList<Node> neighbores = new ArrayList<>();
		
		for(int x = node.getX() - 1; x <= node.getX() + 1; x++) {
			for(int y = node.getY() - 1; y <= node.getY() + 1; y++) {
				Node temp = getNode(x,y);
				
				if(x != node.getX() && y != node.getY()) {
					if(x > node.getX()) {
						if(y > node.getY()) { 
							if(!isWalkable(x - 1, y) || !isWalkable(x, y - 1))
								continue;
						} else if(!isWalkable(x - 1, y) || !isWalkable(x, y + 1))
							continue;
					} else if(x < node.getX()) {
						if(y > node.getY()) {
							if(!isWalkable(x + 1, y) || !isWalkable(x, y - 1))
								continue;
						} else if(y < node.getY()) {
							if(!isWalkable(x + 1, y) || !isWalkable(x, y + 1))
								continue;
						}
					}
				}
				
				if(temp != null && temp != node)
					neighbores.add(temp);
			}
		}
		
		return neighbores;
	}
	
	/**
	 * Get only the 4 directly adjacent nodes
	 */
	public ArrayList<Node> getAdjacentNeighbores(Node node) {
		ArrayList<Node> temp = new ArrayList<>();
		
		temp.add(getNode(node.getPosition().add(1, 0)));
		temp.add(getNode(node.getPosition().add(-1, 0)));
		temp.add(getNode(node.getPosition().add(0, 1)));
		temp.add(getNode(node.getPosition().add(0, -1)));
		
		for(int i = temp.size() - 1 ; i >= 0; i--)
			if(temp.get(i) == null) temp.remove(i);
		return temp;
	}
	
	public Node getNode(int x, int y) {
		if(x >= 0 && x < nodes.length && y >= 0 && y < nodes[0].length)
			return nodes[x][y];
		return null;
	}
	
	public boolean isWalkable(int x, int y) {
		Node node = getNode(x, y);
		if(node == null) return false;
		return node.isWalkable();
	}
	
	public Node getNode(Vector2f pos) {
		return getNode((int) pos.x, (int) pos.y);
	}
}

class Node {
	public static final Comparator<Node> COMPARATOR = (o1, o2) -> {
		if(o1 == o2)
			return 0;
		if(o1.getFValue() == o2.getFValue()) { 
			if(o1.getHCost() < o2.getHCost())
				return -1;
			else
				return 1;
		} else if(o1.getFValue() < o2.getFValue())
			return -1;
		else 
			return 1;
	};
	
	private Node parent;

	private int x, y;
	private int gCost, hCost;
	private boolean walkable;
	
	public Node(int x, int y, boolean walkable) {
		this.x = x; 
		this.y = y;
		this.walkable = walkable;
	}
	
	public static int getDistance(Node node1, Node node2) {
		int dX = Math.abs(node1.getX() - node2.getX());
		int dY = Math.abs(node1.getY() - node2.getY());
		
		if(dX > dY) 
			return 14 * dY + 10 * (dX - dY);
		return 14 * dX + 10 * (dY - dX);
	}
	
	public Node getParent() { return parent; }
	public void setParent(Node parent) { this.parent = parent; } 
	
	public int getFValue() { return gCost + hCost; }
	public int getGCost() { return gCost; }
	public void setGCost(int gCost) { this.gCost = gCost; }
	public int getHCost() { return hCost; }
	public void setHCost(int hCost) { this.hCost = hCost; }
	
	public void setWalkable(boolean walkable) { this.walkable = walkable; }
	public boolean isWalkable() { return walkable; }
	
	public Vector2f getPosition() { return new Vector2f(x, y); }
	public int getX() { return x; }
	public int getY() { return y; }
}

